package com.rk.mbtio;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.android.volley.RequestQueue;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    private Activity activity;

    // JSONArray
    public JSONArray JSONArr = null;

    // first URL
     private RequestQueue queue;
    private JsonRequestTool requestor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        activity = this;

        init();
    }

    public void init() {

        (  (GlobalSingleton) getApplication()).init();

        (  (GlobalSingleton) getApplication()).setRequestTool(this);

        if ( (  (GlobalSingleton) getApplication()).user != null) {
            Log.i("RFM", "com.rk.mbtio.User found");
            // launch driver activity
            launchDriver();
        } else{
            Log.i("RFM", "com.rk.mbtio.User not found");
        }

        requestor = new JsonRequestTool(this.getApplicationContext());
    }

    public void onStop() {
        super.onStop();

        if (queue != null) {
            queue.cancelAll("Login");
        }
    }

    // hide keyboard on pressing message space
    public void pressMessageSpace(View view) {

        hideKeyboard(this);
    }

    // hide keyboard
    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

/*
    public void updateUserId(com.rk.mbtio.User user) {

        // to do get user
    //    requestor.JSONRequestObj(API_URL+ "", "GET", null, new JsonRequestTool.VolleyCallback() {
            @Override
        //    public void onSuccess(JSONObject results) {
                String name = null;
                int id = -1;
    //            Log.i("JSON", results.toString());
         //       try {
           //         name = results.get("Name").toString();
            //        id = Integer.parseInt(results.get("userID").toString());

              //      com.rk.mbtio.User user = new com.rk.mbtio.User(name, id);
                    // set global user
                    ((GlobalSingleton) getApplication()).setUser(user);

                    // write to preferences
                    writeUserToCache(user);

                    launchDriver();


               // } catch (JSONException e) {
                //    e.printStackTrace();
              //  }
            }
   //     });
    }
    */

    public void launchDriver() {
        // launch Driver activity
        Intent intent = new Intent(activity, DriverActivity.class);
      //  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
       //     activity.startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(activity).toBundle());
       // } else {
            activity.startActivity(intent);
      //  }
    }

    public String getDeviceId() {
           String  android_id = Settings.Secure.getString(this.getBaseContext().getContentResolver(),
                   Settings.Secure.ANDROID_ID);

           return android_id;
    }
}