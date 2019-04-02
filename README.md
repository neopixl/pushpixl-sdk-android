[![Twitter](https://img.shields.io/badge/Twitter-@Neopixl-blue.svg?style=flat)](http://twitter.com/neopixl)
[![Site](https://img.shields.io/badge/Site-neopixl.com-orange.svg?style=flat)](https://neopixl.com)


[![Travis](https://api.travis-ci.org/neopixl/pushpixl-sdk-android.svg?branch=master)](https://travis-ci.org/neopixl/pushpixl-sdk-android)
[![Coverage Status](https://coveralls.io/repos/github/neopixl/pushpixl-sdk-android/badge.svg?branch=master)](https://coveralls.io/github/neopixl/pushpixl-sdk-android?branch=master)
[![Bintray](https://img.shields.io/bintray/v/fdewasmes/Pushpixl/Pushpixl.svg)]()
[![API](https://img.shields.io/badge/API-14%2B-green.svg?style=flat)](https://android-arsenal.com/api?level=14)

# Pushpixl by @Neopixl

Here is the SDK of the Pushpixl service, this SDK is a toolbox for using the firebase cloud messaging service with Pushpixl.io


## Usage
This library was designed to be as simple to use as possible.  Here are the steps you'll need to follow:

* Setup your project with Firebase, use the assitant in Android Studio or [the documentation](https://firebase.google.com/docs/cloud-messaging/android/client)

* Include the maven dependencies for this library as well as it's dependencies build.gradle file.


		dependencies {
			implementation 'com.neopixl:pushpixl:2.0.0'
			implementation 'com.google.firebase:firebase-messaging:17.4.0'
		}
		
* Setup the configuration of your project (in the Application class)

		PushConfiguration configuration = new PushConfiguration(
                "APP TOKEN",
                "APP SECRET"
                , "TENANT")
                .debug(false)
                .autoRefresh(true)
                .useNotSecureHttp(false);

        PushpixlManager.install(this, configuration);


* Create a receiver for handling the push notifications

		public class PushNotificationReceiver extends PushpixlFirebaseInstanceIDService {
	    	private static final String TAG = PushNotificationReceiver.class.getSimpleName();
	
	    	private static final int REQUEST_CODE_NOTIFICATION_PUSH = 1;
	    	private static final int NOTIFICATION_DEFAULT_ID = 1;
	
		    @Override
		    public void onMessageReceived(RemoteMessage remoteMessage) {
		        super.onMessageReceived(remoteMessage);
		
		        PendingIntent pendingIntent;
		        Context context = getApplicationContext();
		
		        Log.e(TAG, "DID Received a notification");
		        Map<String, String> data = remoteMessage.getData();
		
		        Intent newIntent = new Intent(context, MainActivity.class);
		        newIntent.putExtra("remote_message", remoteMessage);
		        pendingIntent = PendingIntent.getActivity(context, REQUEST_CODE_NOTIFICATION_PUSH, newIntent, PendingIntent.FLAG_CANCEL_CURRENT);
		
		
		        if (pendingIntent != null) {
		            int notificationId = remoteMessage.getMessageId() != null ? remoteMessage.getMessageId().hashCode() : NOTIFICATION_DEFAULT_ID;
		
		            String message =  PushpixlData.extractData(remoteMessage, PushpixlData.Key.MESSAGE);
		
		            // Create you notification
		
		        }
	    	}
		}
		
* Upload the user information to Pushpixl

		List<String> tags = new ArrayList<>();
        tags.add("THISISATAGS");

        UserPreferences preferences = new UserPreferences("jerome")
                .quietTime(new QuietTime(22,0,5,0))
                .tags(tags);

        PushpixlManager.getInstance().updateUserPreferences(preferences, new UserPreferencesListener() {
            @Override
            public void onUserPreferencesUpdate(String s, UserPreferences userPreferences) {
                Toast.makeText(MainActivity.this, "Preference updated", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onUserPreferencesError(UserPreferences userPreferences, PushpixlException e) {
                Log.d(TAG, "Error occur", e);
                Toast.makeText(MainActivity.this, "An error occured", Toast.LENGTH_SHORT).show();
            }
        });
		
## Mark notifications as read

		if (getIntent().getExtras() != null) {
            RemoteMessage remoteMessage = getIntent().getExtras().getParcelable("remote_message");
            if (remoteMessage != null) {
                PushpixlManager.getInstance().confirmReading(remoteMessage, new ReadConfirmationListener() {
                    @Override
                    public void onMessageMarkedAsReadSuccess(String s, String s1) {
                        Toast.makeText(MainActivity.this, "Notification marked read", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onMessageMarkedAsReadError(String s, PushpixlException e) {
                        Log.d(TAG, "Error occur", e);
                        Toast.makeText(MainActivity.this, "An error occured", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
		
## Remove the token informations

		PushpixlManager.getInstance().removeUserPreferences(new UserPreferencesRemoveListener() {
            @Override
            public void onUserPreferencesRemoved(String s) {
                Toast.makeText(MainActivity.this, "Preference removed", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onUserPreferencesRemoveError(PushpixlException e) {
                Log.d(TAG, "Error occur", e);
                Toast.makeText(MainActivity.this, "An error occured", Toast.LENGTH_SHORT).show();
            }
        });
        
# Troubleshooting

* If you get a Firebase initialization error, ensure that your manifest do not *tools:node="replace"*. If yes you need to change this to --> *tools:node="merge"*
        
# Migrate from the 1.X.X versions

1. Remove GCM from the project
2. Remove the custom Neopixl maven server url
3. Install Firebase and Pushpixl as described above
4. Clear the informations provided in the manifest
5. If your app is exending "PushPixlApplication" you don't need it anymore
6. While updated the notification intent, you can now give the all remote_message to the PendingIntent

# Proguard

	-keep class com.neopixl.pushpixl.network.model.* { *; }
   
	# Jackson 2.x
	-keep class com.fasterxml.jackson.databind.ObjectMapper {
	    public <methods>;
	    protected <methods>;
	}
	-keep class com.fasterxml.jackson.databind.ObjectWriter {
	    public ** writeValueAsString(**);
	}
	-keep @com.fasterxml.jackson.annotation.JsonIgnoreProperties class * { *; }
	-keep class com.fasterxml.** { *; }
	-keep class org.codehaus.** { *; }
	-keepnames class com.fasterxml.jackson.** { *; }
	-keepclassmembers public final enum com.fasterxml.jackson.annotation.JsonAutoDetect$Visibility {
	    public static final com.fasterxml.jackson.annotation.JsonAutoDetect$Visibility *;
	}
	-keep class com.fasterxml.jackson.databind.ObjectMapper {
	    public <methods>;
	    protected <methods>;
	}
	-keep class com.fasterxml.jackson.databind.ObjectWriter {
	    public ** writeValueAsString(**);
	}
	-keepnames class com.fasterxml.jackson.** { *; }
	-dontwarn com.fasterxml.jackson.databind.**


# License
Pushpixl is released under the Apache 2.0 licence. See LICENSE for details.
