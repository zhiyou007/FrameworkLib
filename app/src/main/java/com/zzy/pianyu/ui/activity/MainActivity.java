package com.zzy.pianyu.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.zzy.framework.Tools.Tools;
import com.zzy.framework.base.BaseActivity;
import com.zzy.pianyu.R;
import com.zzy.pianyu.ui.adapter.CacheFragmentStatePagerAdapter;
import com.zzy.pianyu.ui.fragment.HomeFragment;
import com.zzy.pianyu.ui.widgets.PagerSlidingTabStrip;
import com.zzy.tools.CircularAnim;

import butterknife.Bind;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Bind(R.id.drawer_layout)
    DrawerLayout drawer;
    @Bind(R.id.fab)
    FloatingActionButton fab;

    @Bind(R.id.mViewPager)
    ViewPager mViewPager;

    @Bind(R.id.tabs)
    PagerSlidingTabStrip mTabStrip;

    ImageView iv_author;


    private TabsAdapter mTabAdapter = null;

    @Override
    protected View getLoadingTargetView() {
        return null;
    }

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_main;
    }

    @Override
    protected void initViewsAndEvents(Bundle savedInstanceState) {
        //首页不能左划
        setSwipeEnabled(false);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //去掉标题
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        mTabAdapter = new TabsAdapter(getSupportFragmentManager());
        mViewPager.setOffscreenPageLimit(menus.length);
        mViewPager.setAdapter(mTabAdapter);
        mViewPager.setCurrentItem(0);


        mTabStrip.setViewPager(mViewPager);



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                CircularAnim.fullActivity(MainActivity.this, view)
                        .colorOrImageRes(R.color.primary)
                        .go(new CircularAnim.OnAnimationEndListener() {
                            @Override
                            public void onAnimationEnd() {
                                startActivity(new Intent(MainActivity.this, SplashActivity.class));
                            }
                        });
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        iv_author = (ImageView)navigationView.getHeaderView(0).findViewById(R.id.iv_author);
        iv_author.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(mContext,"xxxxxx",Toast.LENGTH_SHORT).show();
                // 先将颜色展出铺满，然后启动新的Activity
                CircularAnim.fullActivity(MainActivity.this, view)
                        .colorOrImageRes(R.color.primary)
                        .go(new CircularAnim.OnAnimationEndListener() {
                            @Override
                            public void onAnimationEnd() {
                                startActivity(new Intent(MainActivity.this, SplashActivity.class));
                            }
                        });
            }
        });
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:

                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                } else {
                    android.os.Process.killProcess(android.os.Process.myPid());
                }

                break;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onBackPressed() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Tools.goActivity(mContext,SettingActivity.class,null,0,0,false);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private String[] menus = {"推荐","分类","片场"};


    private class TabsAdapter extends CacheFragmentStatePagerAdapter {

        public TabsAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        protected Fragment createItem(int position) {
            Fragment f;
            switch (position) {
                default:
                    f = HomeFragment.newInstance(position);
                    break;
                case 1:
                    f = HomeFragment.newInstance(position);
                    break;
                case 2:
                    f = HomeFragment.newInstance(position);
                    break;
            }
            return f;
        }

        @Override
        public int getCount() {
            return menus.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return menus[position];
        }

    }
}
