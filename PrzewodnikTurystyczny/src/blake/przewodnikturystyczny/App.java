package blake.przewodnikturystyczny;

import android.app.Application;

import com.activeandroid.ActiveAndroid;

public class App extends Application {

	@Override
	public void onCreate() {
		super.onCreate();
		
//		Runnable initek = new Runnable() {
//			
//			@Override
//			public void run() {
//				ActiveAndroid.initialize(getApplicationContext(), true);
//				
//			}
//		};
//		
//		ThreadGroup group = new ThreadGroup("threadGroup");
//		new Thread(group, initek, "YourThreadName", 2000000).start();
		
//		ActiveAndroid.initialize(this);
		ActiveAndroid.initialize(this, true);
	}
	
	@Override
	public void onTerminate() {
		super.onTerminate();
		ActiveAndroid.dispose();
	}
}
