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
    private SharedPreferences settings;

    // JSONArray
    public JSONArray JSONArr = null;

    // first URL
    private final String API_URL = "https://glacial-hollows-10432.herokuapp.com";
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

        // init Shared Preferences
        settings = getApplicationContext().getSharedPreferences("PREFERENCES", 0);

        Log.i("RFM", getUserFromPreferences() + "");

        // clear shared preferences
       clearPreferences();

        Log.i("RFM", getUserFromPreferences() + "");
        User user = getUserFromPreferences();

        if (user != null) {
            Log.i("RFM", "com.rk.mbtio.User found");
            // set global user
            ((GlobalSingleton) getApplication()).setUser(user);

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

    public void sendName(View v) {
        EditText et = (EditText) findViewById(R.id.textEntry);
        String name = et.getText().toString();

        JSONObject data;

        if (name.length() <= 0) {
            return;
        } else {
            // post to api
            data = new JSONObject();

            try {
                data.put("name", name);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            requestor.JSONRequestObj(API_URL + "/user/create", "POST", data, new JsonRequestTool.VolleyObjCallback() {
                @Override
                public void onSuccess(JSONObject results) {
                    // update userId
                    Log.i("JSON", "RESULTS: " + results.toString());

                   try {
                     //   updateUserId(results.get("UserID").toString());
                       // to do user Id 0 atm


                    User user = new User(results.get("Name").toString(), "99");

                       ((GlobalSingleton) getApplication()).setUser(user);

                       // write to preferences
                       writeUserToCache(user);

                       Log.i("JSON", user.toString());

                       launchDriver();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            });
        }
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

    public void writeUserToCache(User u) {

        SharedPreferences.Editor editor = settings.edit();
        editor.putString("UserName", u.getName());
        editor.putString("userId", u.getUserId());

        // Apply the edits!
        editor.apply();
    }

    public User getUserFromPreferences() {
        // Get from the SharedPreferences
        String user_name = settings.getString("UserName", null);
        String user_id = settings.getString("userId", null);

        if (user_name != null && user_id != null)
            return new User(user_name, user_id);
        else
            return null;
    }

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

    public void clearPreferences() {
        SharedPreferences.Editor editor = settings.edit();
        editor.clear();
        editor.commit();
    }
}