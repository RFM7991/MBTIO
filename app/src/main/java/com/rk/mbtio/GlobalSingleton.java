package com.rk.mbtio;

import android.app.Application;
import android.support.v4.view.ViewPager;

import java.util.HashMap;
import com.rk.mbtio.DriverActivity.SectionsPagerAdapter;

public class GlobalSingleton extends Application {

    private ViewPager viewPager;
    private SectionsPagerAdapter pagerAdapter;

    private User user;

    private HashMap<Integer, Conversation> conversations;

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

    public void setConversations(HashMap<Integer, Conversation> conversations) {
        this.conversations = conversations;
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
}
