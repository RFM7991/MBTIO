package com.rk.mbtio;

import android.app.Activity;
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

import java.util.ArrayList;

public class DriverActivity extends AppCompatActivity {

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;

    private JsonRequestTool requestTool;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager = findViewById(R.id.fragmentContainer);
        ((ViewPager) mViewPager).setAdapter(mSectionsPagerAdapter);
        mViewPager.setOffscreenPageLimit(5);
        // set to matches page
        mViewPager.setCurrentItem(1);


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
            fragments.add(new ChatFragment());

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
    public void pressSend(View view) {
        Button b = (Button) view;

        String text = b.getText().toString();

        // clear text in keytray
        ((Button) view).setText("0");

        // attempt message post
        sendMessage(text);
    }

    //  POST for send message
    public void sendMessage(String message) {
        JSONObject data = new JSONObject();

        try {
            data.put("sid", 0);
            data.put("rid", 42);
            data.put("num", 0);
            data.put("pin", 1);
            data.put("message", message);
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

    public void pressConversation(View view) {
     Log.d("RFM", "pressConversation");
     mViewPager.setCurrentItem(3);
    }

}

