package edu.oit.CST407.notification;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.view.View;

/** 
 * ServiceActivity is the main activity in an exercise in setting 
 * up a simple service that sets and clears notifications in the 
 * Notification Bar on the user clicking one of two buttons,
 * "Start" and "Clear".
 * 
 * In addition, Strings are to be added as Extras in the Intent Bundle
 * of the service.
 * 
 * Assignment specifies that the service must count how many
 * times it's been started and show a persistent notification
 * which includes the count.  Upon clearing the notification the service 
 * should reset the count and clear the notification.
 * 
 * This class simply sets the view and sets up the methods for calling
 * the service and adding the Extra Strings to the service Intent.
 * 
 * @author Russell Zauner
 * @version 0.1 120730
 *
 */

public class ServiceActivity extends Activity{

	public static final String START_MSG = "START";
	public static final String CLEAR_MSG = "CLEAR";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}
	
	//
	// Usually, a button would be driving by click events and 
	// setting up a listener.  Android will automatically handle
	// Button click events by designating an onClick attribute
	// in the layout XML, which has been done in this assignment.
	//
	// All we need to do is insert the logic that the buttons need 
	// to execute when they are clicked.  I think that it's possible 
	// to refactor these two methods into one method to handle both 
	// buttons, but this works.
	//
	
	/**
	 * 
	 * Creates a ServiceIntent object that is used with the Service.  
	 * <p>
	 * The ServiceIntent object is configured simply with the 
	 * Button that was used to activate it (Start) and adds a the 
	 * START_MSG String to the Extra data that's in its bundle.
	 * <p>
	 * The ServiceIntent object is then passed to the service.
	 * 
	 * @param v This View 
	 */
	public void startClicked(View v){
		Intent serviceIntent = new Intent(this, ServiceNotification.class);
		serviceIntent.putExtra("StringExtra", START_MSG);
		serviceIntent.setData(Uri.parse(START_MSG));
		startService(serviceIntent);		
	}

	/**
	 * 
	 * Creates a ServiceIntent object that is used with the Service.  
	 * <p>
	 * The ServiceIntent object is configured simply with the 
	 * Button that was used to activate it (Clear) and adds a the 
	 * CLEAR_MSG String to the Extra data that's in its bundle.
	 * <p>
	 * The ServiceIntent object is then passed to the service.
	 * 
	 * @param v This View 
	 */
	public void clearClicked(View v) {
		Intent serviceIntent = new Intent(this, ServiceNotification.class);
		stopService(serviceIntent);
		serviceIntent.putExtra("StringExtra", CLEAR_MSG);
		serviceIntent.setData(Uri.parse(CLEAR_MSG));
		startService(serviceIntent);		
	}
	
}
