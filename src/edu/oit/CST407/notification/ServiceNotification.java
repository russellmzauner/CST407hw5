package edu.oit.CST407.notification;

import android.app.IntentService;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

/** 
 * ServiceNotification is the service ServiceActivity calls
 * to set and clear notifications in the Notification Bar 
 * as well as keep count of the number of times "Start" has been
 * clicked.
 * 
 * In addition, Strings are to be added as Extras in the Intent Bundle
 * of the service.
 * 
 * Assignment specifies that the service must count how many
 * times it's been started and show a persistent notification
 * which includes the count.  Upon clearing the notification the service 
 * should reset the count and clear the notification.
 * 
 * This class implements the ServiceIntent handler as well as managing 
 * the notifications. 
 * 
 * @author Russell Zauner
 * @version 0.1 120730
 *
 */

public class ServiceNotification extends IntentService{
	
	private static final String TAG = "ServiceNotification";
	private static Integer mCount = 0;
	private final static int SERVICE_NOTIFICATION_ID = 1;

		
	// 
	// Constructor is required with IntentService
	//
	public ServiceNotification() {
		super("ServiceNotification");
		
	}
	
	//
	// Overriden methods provide their own JavaDocs in
	// the SDK.
	//
	@Override
	protected void onHandleIntent(Intent intent) {
		
		//
		// This gets the data passed with the ServiceIntent
		// object.  It's used to determine whether the 
		// service is setting or clearing the notification
		// and counter.
		//
		String whichButton = intent.getDataString();
		
		//
		// This wasn't required by the assignment, but 
		// I needed to verify that the String in the 
		// Bundle's Extra data was getting set correctly.
		//		
		String extraString = intent.getStringExtra("StringExtra");
		
		//
		// These are mostly logging what's happening
		// with the service to verify via LogCat debug 
		// messages.
		//
		// I need to investigate why (I know why, it told me -
		// I just don't yet *understand* why) I had to catch 
		// a possible interrupt when sleeping the thread.
		//
		switch(whichButton){
		
		case "START":
			++mCount;
			
			Log.d(TAG, "Service Started");
			Log.d(TAG, extraString );			
			Log.d(TAG, Integer.toString(mCount));
			
			try {
				Thread.sleep(3000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
			notifyMe(Integer.toString(mCount), true);
			break;
			
		case "CLEAR":
			mCount = 0;
			
			Log.d(TAG, "Service Cleared");
			Log.d(TAG, extraString );
			Log.d(TAG, Integer.toString(mCount));
			
			notifyMe(Integer.toString(mCount), false);
			break;
		}		
	}

	/**
	 * Initializes and builds the notification object, then instantiates
	 * the Notification Manager to set/clear the notification.
	 * 
	 * @param msg 	This is a String that holds the service counter, 
	 * 				which is displayed in the notification 
	 * @param flag 	This is a Bool that tells the Notification 
	 * 				Manager whether to set or clear the notification
	 */
	
	private void notifyMe(String msg, Boolean flag) {
		//
		// Building notification object
		//
		NotificationCompat.Builder nBuilder = new NotificationCompat.Builder(this);
		nBuilder.setContentTitle("ServiceNotification");
		nBuilder.setContentText("Start has been clicked " + msg + " times!");
		nBuilder.setSmallIcon(android.R.drawable.ic_dialog_email);
		
		//
		// Sets notification object to be persistent
		//
		nBuilder.setAutoCancel(false);
		
		// 
		// Create reference to NotificationManager object so we can handle 
		// the notification object.
		//
		NotificationManager nManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
		
		//
		// When the Start button is clicked, NotificationManager sends notification.
		// when the Clear button is clicked, NotificationManager clears notification.
		//
		if (flag){
			nManager.notify(SERVICE_NOTIFICATION_ID, nBuilder.build());
			Log.d(TAG, "Notification Posted");
		} else {
			nManager.cancel(SERVICE_NOTIFICATION_ID);
			Log.d(TAG, "Notification Cleared");
		}
	}
}
