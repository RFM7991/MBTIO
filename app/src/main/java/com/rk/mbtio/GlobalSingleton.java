package com.rk.mbtio;

import android.app.Application;
import android.content.SharedPreferences;
import android.support.v4.view.ViewPager;

import android.content.Context;
import android.util.Log;

import android.provider.Settings;

import java.util.ArrayList;
import java.util.HashMap;
import com.rk.mbtio.DriverActivity.SectionsPagerAdapter;
import com.rk.mbtio.DriverFragments.ConversationFragment;

//singleton class for all user data
public class GlobalSingleton extends Application {
    public final String API_URL = "https://mbtio-234017.appspot.com";

    public SharedPreferences settings;
    public ViewPager viewPager;
    public SectionsPagerAdapter pagerAdapter;

    public JsonRequestTool requestTool;

    public GlobalSingleton g;

    //stores user specific data
    public User user;

    public HashMap<Integer, User> userCache;
    public HashMap<Integer, User> matchCache;

    public HashMap<Integer, Conversation> conversations;

  //  private HashMap<Integer, Conversation> conversations;

    public void setViewPager(ViewPager v) {
        viewPager = v;
    }
    public ViewPager getViewPager() {
        return viewPager;
    }

    public HashMap<Integer, Conversation> getConversations() {
        return conversations;
    }
    public void addConversation(int rid, Conversation c) {
        conversations.put(rid, c);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public SectionsPagerAdapter getPagerAdapter() {
        return pagerAdapter;
    }

    public void setPagerAdapter(SectionsPagerAdapter pagerAdapter) {
        this.pagerAdapter = pagerAdapter;
    }

public void init() {
        if (settings == null) {
            settings = getApplicationContext().getSharedPreferences("PREFERENCES", 0);

            loadUser();

            conversations = new HashMap<Integer, Conversation>();
            userCache = new HashMap<Integer, User>();
            matchCache = new HashMap<Integer, User>();
        }
}

public void addMatch(User u) {
        matchCache.put(u.uid, u);
}

public ArrayList<Match> getMatches(int max) {
        ArrayList<Match> matches = new ArrayList<Match>();

        int index = 0;

        for(Integer key: matchCache.keySet()) {
    index++;
            //remove the match from the cache
            matches.add(new Match(matchCache.get(key)));

            if (index >= max) {
                break;
            }
        }

        return matches;
}

public void addUser(User u) {
        userCache.put(u.uid, u);
}

public User getUser(Integer uid) {
        if (userCache.containsKey(uid)) {
            return userCache.get(uid);
    }

    return null;
}

public void addMatches(ArrayList<User> u) {
        for(User ud : u) {
            matchCache.put(ud.uid, ud);
        }
}


  public   ArrayList<ConversationFragment> fragments;
public void setRequestTool(Context ctx) {
        requestTool = new JsonRequestTool((ctx));
}

    public void loadUser() {
        // Get from the SharedPreferences

        int uid = settings.getInt("uid", -1);
        int pin = settings.getInt("pin", -1);

        int height = settings.getInt("height", -1);
        int age = settings.getInt("age", -1);
        int state = settings.getInt("state", 0);

        String sex= settings.getString("sex", null);
        String interest = settings.getString("interest", null);
        String mbti = settings.getString("mbti", null);
        String name = settings.getString("name", null);

        User u = new User();

        if (state >= 1) { //user has been created
            u.uid = uid;
            u.pin = pin;
        }

        if (state >= 2) { //user is ready to date!
            u.height = height;
            u.age = age;
            u.interest = interest;
            u.name = name;
            u.mbti = mbti;
            u.sex = sex;
        }

        if ( u != null) {
            this.user = u;
        }
    }


    /*
        state = 0 => no user
        state = 1 => partial user
        state = 2 => ready user
     */
    public void recordState(int state) {
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt("state", state);
    }

    public void writeUserToPreferences(User u) {

        SharedPreferences.Editor editor = settings.edit();
        editor.putInt("uid", u.uid);
        editor.putInt("pin", u.pin);
        editor.putInt("state", u.state);

        if (u.state == 2 ) {
            editor.putString("name", u.name);
            editor.putString("mbti", u.mbti);
            editor.putInt("height", u.height);
            editor.putInt("age", u.age);
            editor.putString("sex", u.sex);
            editor.putString("interest", u.interest);
        }

        // Apply the edits!
        editor.apply();
    }

    //wipe everything
    public void clearPreferences() {
        SharedPreferences.Editor editor = settings.edit();
        editor.clear();
        editor.commit();
    }
}
