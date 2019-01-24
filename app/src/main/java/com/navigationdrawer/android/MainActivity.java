package com.navigationdrawer.android;

import android.app.ActionBar;
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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Fragment mFragment;
    private FragmentManager mFragmentManager;
    private ArrayList<NavDrawer> mDrawerList;
    private NavRecAdapter mNavDrawerAdapter;

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
            getSupportActionBar().setTitle("Connect");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        setUpNavigationDrawer();

        mFragmentManager = getSupportFragmentManager();
        mFragment = new ConnectFragment();
        mFragmentManager.beginTransaction().replace(R.id.main_frame, mFragment).commit();

        getSupportActionBar().setHomeAsUpIndicator(setBadgeCount(this,R.drawable.ic_burger, 3));


    }

    private void setUpNavigationDrawer() {
        mDrawerList.add(new NavDrawer("Connect", R.mipmap.ic_launcher, "4"));
        mDrawerList.add(new NavDrawer("Fixtures", R.mipmap.ic_launcher, "2"));
        mDrawerList.add(new NavDrawer("Table", R.mipmap.ic_launcher, "0"));

        //Nav ListView initialize
        mNavDrawerRV.setLayoutManager(new LinearLayoutManager(this));
        mNavDrawerAdapter = new NavRecAdapter(mDrawerList, new NavRecAdapter.onItemClickListerner() {
            @Override
            public void onClick(int pos) {
                switch (pos) {
                    case 0:
                        if (!(mFragment instanceof ConnectFragment)) {
                            getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                            mFragment = new ConnectFragment();
                            mFragmentManager.beginTransaction().replace(R.id.main_frame, mFragment).commit();
                        }
                        if (getSupportActionBar() != null) {
                            getSupportActionBar().setTitle("Connect");
                        }
                        mDrawerLayout.closeDrawers();
                        break;
                    case 1:
                        if (!(mFragment instanceof FixturesFragment)) {
                            getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                            mFragment = new FixturesFragment();
                            mFragmentManager.beginTransaction().replace(R.id.main_frame, mFragment).commit();
                        }
                        if (getSupportActionBar() != null) {
                            getSupportActionBar().setTitle("Fixtures");
                        }
                        mDrawerLayout.closeDrawers();
                        break;
                    case 2:
                        if (!(mFragment instanceof TableFragment)) {
                            getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                            mFragment = new TableFragment();
                            mFragmentManager.beginTransaction().replace(R.id.main_frame, mFragment).commit();
                        }
                        if (getSupportActionBar() != null) {
                            getSupportActionBar().setTitle("Table");
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
