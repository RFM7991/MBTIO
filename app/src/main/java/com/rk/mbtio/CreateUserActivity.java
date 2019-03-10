package com.rk.mbtio;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.rk.mbtio.CreateUserFragments.CreateProfileFragment;
import com.rk.mbtio.CreateUserFragments.Q1Fragment;
import com.rk.mbtio.CreateUserFragments.Q2Fragment;
import com.rk.mbtio.CreateUserFragments.Q3Fragment;
import com.rk.mbtio.CreateUserFragments.Q4Fragment;
import com.rk.mbtio.CreateUserFragments.ResultsFragment;
import com.rk.mbtio.DriverFragments.ChatFragment;
import com.rk.mbtio.DriverFragments.MatchesFragment;
import com.rk.mbtio.DriverFragments.UserProfileFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class CreateUserActivity extends AppCompatActivity {

    // trust SSL Request for Google cloud server
    protected static final String TAG = "NukeSSLCerts";

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;

    // string values for MB profile
    String q1, q2, q3, q4;

    // createProfile Fragment
    CreateProfileFragment profileFragment;

    // For calls to API
    private JsonRequestTool requestTool;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // trust ssl
        //      nuke();

        init();


    }

    public void init() {
        // initailize sections pager adapter and view pager
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager = findViewById(R.id.fragmentContainer);
        mViewPager.setOffscreenPageLimit(10);
        ((ViewPager) mViewPager).setAdapter(mSectionsPagerAdapter);

        // init JSONRequest tool
        requestTool = new JsonRequestTool(this.getApplicationContext());

        // test loop
        //   loop();

        // test
        sendMessage();
    }
    // swap viewpager to free fragment
    public void goToChat(View view) {
        mViewPager.setCurrentItem(2);

    }

    // store quiz choices between fragments
    public void saveChoice(View view) {
        Button button = (Button) view;

        String choice = button.getText().toString();

        button.setBackgroundColor(Color.RED);

        // highlight choice
        // to-do clear other choices


        if (mViewPager.getCurrentItem() == 0) {
            q1 = choice;

        } else if (mViewPager.getCurrentItem() == 1) {
            q2 = choice;
        } else if (mViewPager.getCurrentItem() == 2) {
            q3 = choice;
        } else if (mViewPager.getCurrentItem() == 3) {
            q4 = choice;
        }

        Log.i("RFM", q1 +", " + q2 +", "+  q3 +", "+ q4);
    }

    public void loop() {
        Thread loop = new Thread(new Runnable() {
            public void run() {
                while (true) {

                    Log.d("RFM", mViewPager.getCurrentItem() + "");

                    // sleep
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        break;
                    }
                }
            }
        });
        loop.start();
    }

    // test POST for send message
    public void sendMessage() {

        JSONObject data = new JSONObject();

        try {
            data.put("sid", 0);
            data.put("rid", 42);
            data.put("num", 0);
            data.put("pin", 1);
            data.put("message", "Lo");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        requestTool.JSONRequestObj("/messages/send", "POST", data, new JsonRequestTool.VolleyObjCallback() {
            @Override
            public void onSuccess(JSONObject results) {

                Log.d("JSON", results.toString());
            }
        });
    }

    public void onResume(){
        super.onResume();
        sendMessage();
    }

    public void onPause() {
        super.onPause();
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        ArrayList<Fragment> fragments;

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
            fragments = new ArrayList<Fragment>();

            //load fragements


            // q1 - 0
            fragments.add(Q1Fragment.newInstance());

            // q2 - 1
            fragments.add(Q2Fragment.newInstance());

            // q3 - 2
            fragments.add(Q3Fragment.newInstance());

            // q4 - 3
            fragments.add(Q4Fragment.newInstance());

            // results
            fragments.add(ResultsFragment.newInstance());

            // create profile
       //     profileFragment = new CreateProfileFragment();
        //    fragments.add(profileFragment);

        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        public void addFragment(Fragment f) {
            fragments.add(f);
        }

        public void clearFragments() {
            fragments.clear();
        }
    }

    // for SSL verification
    public static void nuke() {
        try {
            TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        public X509Certificate[] getAcceptedIssuers() {
                            X509Certificate[] myTrustedAnchors = new X509Certificate[0];
                            return myTrustedAnchors;
                        }

                        @Override
                        public void checkClientTrusted(X509Certificate[] certs, String authType) {
                        }

                        @Override
                        public void checkServerTrusted(X509Certificate[] certs, String authType) {
                        }
                    }
            };

            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
            HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String arg0, SSLSession arg1) {
                    return true;
                }
            });
        } catch (Exception e) {
        }
    }

        // to delete?
    // finish quiz button
    public void finishQuiz(View view) {

        if (q1 != null && q2 != null
                && q3 != null && q4 != null) {

            // to-do turn I into E
            String q2_temp = q2.substring(0,1);
            if (q2.substring(0,1).equals("I")) {
                q2_temp = "";
            }


            // concatenate traits
            String MBTI = q1.substring(0, 1) + q2_temp + q3.substring(0, 1) + q4.substring(0, 1);

            // add results fragment to adapter, set view pager to new item
            // then clear previous fragments

            // to do - set text for button

    //        Log.i("RFM", "" + profileFragment.getView());
            //   cpf.setText(MBTI);

            //           mSectionsPagerAdapter.addFragment(cpf);
//            mSectionsPagerAdapter.notifyDataSetChanged();
            //      mViewPager.setCurrentItem(mSectionsPagerAdapter.getCount() -1);
            //      mSectionsPagerAdapter.clearFragments();
            //     mSectionsPagerAdapter.notifyDataSetChanged();

        } else {
            return;
        }
    }

    // create button
    public void createProfile(View view) {

        // concatenate traits
        if (q1 != null && q2 != null
        && q3 != null  && q4 != null) {

            // handle case of N for Intuitive
            String q2_temp = q2.substring(0,1);
            if (q2_temp.equals("I")){
                q2 = "N";
            }
            String MBTI = q1.substring(0, 1) + q2_temp + q3.substring(0, 1) + q4.substring(0, 1);

            // add results fragment to adapter, set view pager to new item
            // then clear previous fragments


            mSectionsPagerAdapter.addFragment(CreateProfileFragment.newInstance(MBTI));
            mSectionsPagerAdapter.notifyDataSetChanged();
            mViewPager.setCurrentItem(mSectionsPagerAdapter.getCount() - 1);
            profileFragment = (CreateProfileFragment) mSectionsPagerAdapter.fragments.get(mSectionsPagerAdapter.getCount() - 1);
            //      mSectionsPagerAdapter.clearFragments();
            //     mSectionsPagerAdapter.notifyDataSetChanged();
        }
    }

    // pressed on clicking finish button in createProfile fragment
    public void finishProfile(View view) {
        JSONObject data = profileFragment.getJSON();
        Log.i("RFM", profileFragment.getFields().toString() + "\n");
        Log.i("RFM", "" + profileFragment.isReady());
        // check if ready for POST
        profileFragment.readyForPost();

        // if post is successful launch Driver activity
        if (profileFragment.isReady()) {
            launchDriver();
        }
    }




    // check if profile is ready for POST
    public void readyForUpload(JSONObject obj) {

        requestTool.JSONRequestObj( "/profile/ready", "POST", obj, new JsonRequestTool.VolleyObjCallback() {
            @Override
            public void onSuccess(JSONObject results) {

                Log.d("JSON", results.toString());
            }
        });
    }

    // launch Driver Activity
    public void launchDriver() {
        Intent intent = new Intent(this, DriverActivity.class);
        this.startActivity(intent);
    }

}

