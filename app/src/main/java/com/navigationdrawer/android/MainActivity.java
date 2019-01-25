package com.navigationdrawer.android;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Fragment mFragment;
    private FragmentManager mFragmentManager;
    private ArrayList<NavDrawer> mDrawerList;

    RecyclerView mNavDrawerRV;
    Toolbar mToolbar;
    DrawerLayout mDrawerLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNavDrawerRV = (RecyclerView) findViewById(R.id.left_drawer);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        mDrawerList = new ArrayList<>();

        //Set action bar
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Fragment One");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        setUpNavigationDrawer();

        mFragmentManager = getSupportFragmentManager();
        mFragment = new FragmentOne();
        mFragmentManager.beginTransaction().replace(R.id.main_frame, mFragment).commit();

        getSupportActionBar().setHomeAsUpIndicator(setBadgeCount(this,R.drawable.ic_burger, 4));


    }

    private void setUpNavigationDrawer() {
        mDrawerList.add(new NavDrawer("Fragment One", R.mipmap.ic_launcher, "1"));
        mDrawerList.add(new NavDrawer("Fragment Two", R.mipmap.ic_launcher, "2"));
        mDrawerList.add(new NavDrawer("Fragment Three", R.mipmap.ic_launcher, "1"));

        //Nav ListView initialize
        mNavDrawerRV.setLayoutManager(new LinearLayoutManager(this));
        NavRecAdapter mNavDrawerAdapter = new NavRecAdapter(mDrawerList, new NavRecAdapter.onItemClickListerner() {
            @Override
            public void onClick(int pos) {
                switch (pos) {
                    case 0:
                        if (!(mFragment instanceof FragmentOne)) {
                            getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                            mFragment = new FragmentOne();
                            mFragmentManager.beginTransaction().replace(R.id.main_frame, mFragment).commit();
                        }
                        if (getSupportActionBar() != null) {
                            getSupportActionBar().setTitle("Fragment One");
                        }
                        mDrawerLayout.closeDrawers();
                        break;
                    case 1:
                        if (!(mFragment instanceof FragmentTwo)) {
                            getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                            mFragment = new FragmentTwo();
                            mFragmentManager.beginTransaction().replace(R.id.main_frame, mFragment).commit();
                        }
                        if (getSupportActionBar() != null) {
                            getSupportActionBar().setTitle("Fragment Two");
                        }
                        mDrawerLayout.closeDrawers();
                        break;
                    case 2:
                        if (!(mFragment instanceof FragmentThree)) {
                            getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                            mFragment = new FragmentThree();
                            mFragmentManager.beginTransaction().replace(R.id.main_frame, mFragment).commit();
                        }
                        if (getSupportActionBar() != null) {
                            getSupportActionBar().setTitle("Fragment Three");
                        }
                        mDrawerLayout.closeDrawers();
                        break;

                }
            }
        });
        mNavDrawerRV.setAdapter(mNavDrawerAdapter);

        // Initializing Drawer Layout and ActionBarToggle
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, 0, 0) {

            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes
                super.onDrawerClosed(drawerView);
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu();
            }
        };

        //Setting the actionbarToggle to drawer layout
        mDrawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

    }

    private Drawable setBadgeCount(Context context, int res, int badgeCount){
        LayerDrawable icon = (LayerDrawable) ContextCompat.getDrawable(context, R.drawable.ic_badge_drawable);
        Drawable mainIcon = ContextCompat.getDrawable(context, res);
        BadgeDrawable badge = new BadgeDrawable(context);
        badge.setCount(String.valueOf(badgeCount));
        icon.mutate();
        icon.setDrawableByLayerId(R.id.ic_badge, badge);
        icon.setDrawableByLayerId(R.id.ic_main_icon, mainIcon);

        return icon;
    }
}
