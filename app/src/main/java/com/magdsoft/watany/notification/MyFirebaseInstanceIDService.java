package com.magdsoft.watany.notification;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;


public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    private static final String TAG = "MyFirebaseIIDService";
    private static final String FBToken_SERVICE = "http://demos.magdsoft.com/basra2/setFBApiToken?fb_token=";
    static String refreshedToken;

    @Override
    public void onTokenRefresh() {

        // Getting registration token
        refreshedToken = FirebaseInstanceId.getInstance().getToken();

        // Displaying token on logcat
        // sony
        // fl7cQDxl2dc:APA91bEJhXMJIXKLEp6VTbEYWu8NOp-kCziT5ESxE6dAmi3CymRCCATMpEzfVeUCaiwrq4aWV7WcsKFJRjtvK-WM8d-ZzSf1TRHeES1I2AsYoslu7MHdXvBkvW9eIpqVYI2upvVtKGoW
        // lenovo
        // d_U0APg8Nys:APA91bEQkTN7vCJgKIrJ1KTi5Gk1vn8EwdpH-G9wKDNGluKuuGGQHDX6-E-3FbZ8nVWz7zhS89zsAsPe2TPaeqPj8Q06O4hw1vEp3mwqXVzTip_afjegnEURycvdtwYz-sMZvakr9HWU
        // device Nexus_5X_API_24
        // modon : fDUqqGoDO9M:APA91bFok5tJhMD7hBgvrJ4h_7ibAR5kvFEfSaomY5LseWfD01HPUcpKUzz9djou-Js4da6mhdsXBc9sta6hR3coWtAgnPL_ZdLxe1M0SUHyNjzx5M7P00YYPIx5ZEF9yQ2x-YlI3P7d


        Log.d(TAG, "Refreshed token : " + refreshedToken);


//        FirebaseMessaging.getInstance().subscribeToTopic("content");
    }

    private void sendRegistrationToServer(String token) {
        //You can implement this method to store the token on your server
        //Not required for current project
    }

}
