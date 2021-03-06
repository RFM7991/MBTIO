package com.rk.mbtio;

import android.app.Activity;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;

import com.rk.mbtio.DriverFragments.ChatFragment;
import com.rk.mbtio.DriverFragments.InboxFragment;
import com.rk.mbtio.DriverFragments.MatchesFragment;
import com.rk.mbtio.DriverFragments.UserProfileFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Map;

public class DriverActivity extends AppCompatActivity {

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private JSONSingleton json;

    private JsonRequestTool requestTool;

    private boolean isRunning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        init();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager = findViewById(R.id.fragmentContainer);
        ((ViewPager) mViewPager).setAdapter(mSectionsPagerAdapter);
        mViewPager.setOffscreenPageLimit(5);
        // set to matches page
        mViewPager.setCurrentItem(1);

        ((GlobalSingleton) getApplication()).setViewPager(mViewPager);
        ((GlobalSingleton) getApplication()).setPagerAdapter(mSectionsPagerAdapter);

/*      For deletion
        // add Inbox and pas viewpager and PagerAdapter
        InboxFragment inbox= new InboxFragment();
        inbox.addPagerAdapter(mSectionsPagerAdapter);
        inbox.addViewPager(mViewPager);
        mSectionsPagerAdapter.addFragment(inbox);
        mSectionsPagerAdapter.notifyDataSetChanged();
*/
        // api caller
        requestTool = new JsonRequestTool(this);
    }

    // swap viewpager to free fragment
    public void goToChat(View view) {
        mViewPager.setCurrentItem(1);

    }

    public void onResume() {
        super.onResume();
        isRunning = true;
    }

    public void onPause() {
        super.onPause();
        isRunning = false;
    }

    public void ping() {
        isRunning = true;

        Thread ping = new Thread(new Runnable() {
            public void run() {
                while(true) {
                    if (isRunning) {
                        // testing
                        boolean isDifferenet = true;

                        String timeStamp = DateFormat.getTimeInstance().format(System.currentTimeMillis());
                        Log.i("PING", timeStamp);
             //           checkMessages();


                        SharedPreferences settings = getApplicationContext().getSharedPreferences("PREFERENCES", 0);
                        // preferences
                        Map<String,?> keys = settings.getAll();

                        for(Map.Entry<String,?> entry : keys.entrySet()){
                            Log.d("map values",entry.getKey() + ": " +
                                    entry.getValue().toString());
                        }

                    }
                    // delay 5 seconds
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        break;
                    }
                }
            }
        });

        ping.start();
    }


    // sections pager adapter for create user activity
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        ArrayList<Fragment> fragments;

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
            fragments = new ArrayList<Fragment>();

            //load fragments
            // Userprofile - 0; to the left
            fragments.add(UserProfileFragment.newInstance());

            // Matches - 1 - start here
            fragments.add(MatchesFragment.newInstance());

            // inbox -2  // one to the right
            fragments.add(new InboxFragment());

            // chcat - 3 // two to the right
        //    fragments.add(new ChatFragment());

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
    }

    public void init() {
        (  (GlobalSingleton) getApplication()).init();

        (  (GlobalSingleton) getApplication()).setRequestTool(this);
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

    // press send button in chat
   /* public void pressSend(View view) {
        Button b = (Button) view;

        String text = b.getText().toString();

        // clear text in keytray
        ((Button) view).setText("0");

        // attempt message post
        sendMessage(text);
    }
    */

}

