package ti.goosh;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;

import org.appcelerator.titanium.TiApplication;

public class PushHandlerActivity extends Activity {

	private static String LCAT = "ti.goosh.TiGooshPushHandlerActivity";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		Log.d(LCAT, "started");
		super.onCreate(savedInstanceState);

		TiGooshModule module = TiGooshModule.getModule();
		Context context = getApplicationContext();
		String notification = getIntent().getStringExtra(TiGooshModule.INTENT_EXTRA);

		Intent launcherIntent;

		if (TiApplication.getAppRootOrCurrentActivity() == null) {
			launcherIntent = context.getPackageManager().getLaunchIntentForPackage(context.getPackageName());
			launcherIntent.setFlags(Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
		} else {
			launcherIntent = TiApplication.getAppRootOrCurrentActivity().getIntent();
			if (module != null) {
				TiGooshModule.getModule().sendMessage(notification, true);
			}
		}

		launcherIntent.putExtra(TiGooshModule.INTENT_EXTRA, notification);
		startActivity(launcherIntent);

		finish();
	}

	@Override protected void onResume() {
		Log.d(LCAT, "resumed");
		super.onResume();
	}
	
}