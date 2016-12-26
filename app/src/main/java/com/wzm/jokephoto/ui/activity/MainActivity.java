package com.wzm.jokephoto.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.squareup.okhttp.Request;
import com.umeng.analytics.MobclickAgent;
import com.umeng.socialize.UMShareAPI;
import com.wzm.framework.Tools.Logger;
import com.wzm.framework.Tools.Tag;
import com.wzm.framework.Tools.Tools;
import com.wzm.framework.base.BaseActivity;
import com.wzm.jokephoto.R;
import com.wzm.jokephoto.ui.Impl.CallBackData;
import com.wzm.jokephoto.ui.adapter.CacheFragmentStatePagerAdapter;
import com.wzm.jokephoto.ui.fragment.HomeFragment;
import com.wzm.jokephoto.ui.widgets.PagerSlidingTabStrip;
import com.wzm.tools.A;
import com.wzm.tools.CircularAnim;
import com.wzm.tools.DES2;
import com.wzm.tools.HttpUtils;
import com.wzm.tools.MD5Util;
import com.wzm.tools.SharedPreferencesUtils;
import com.wzm.tools.UIHelper;

import org.json.JSONObject;

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

    @Bind(R.id.iv_author)
    SimpleDraweeView iv_author;

    @Bind(R.id.tv_mylove)
    TextView tv_like;


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

        MobclickAgent.setScenarioType(mContext, MobclickAgent.EScenarioType.E_UM_NORMAL);

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

        getID();

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
                                startActivity(new Intent(MainActivity.this, WbMakerActivity.class));
                            }
                        });
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

//        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
//        navigationView.setNavigationItemSelectedListener(this);
//
//
//        iv_author = (SimpleDraweeView)navigationView.getHeaderView(0).findViewById(R.id.iv_author);
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
                                startActivity(new Intent(MainActivity.this,SplashActivity.class));
                            }
                        });
            }
        });


        tv_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CircularAnim.fullActivity(MainActivity.this, v)
                        .colorOrImageRes(R.color.primary)
                        .go(new CircularAnim.OnAnimationEndListener() {
                            @Override
                            public void onAnimationEnd() {
                                startActivity(new Intent(MainActivity.this,MyLikeActivity.class));
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
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Tools.goActivity(mContext,SettingActivity.class,null,0,0,false);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu)
    {
        //menu.findItem(R.id.action_add).setVisible(true);
        return super.onPrepareOptionsMenu(menu);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);

    }

    public void getID()
    {
        String uid = (String)SharedPreferencesUtils.getParam(mContext,A.TAGID,"");
        if(!TextUtils.isEmpty(uid))
        {
            return;
        }
        String id_url = HttpUtils.GETID+"?key="+ UIHelper.getDeviceId(mContext);
        Logger.info(id_url);
        HttpUtils.get(mContext, id_url, Tag.EVENT_BEGIN, new CallBackData() {
            @Override
            public void CommonParseData(String data,boolean cached, int event_tag) {
                Logger.info("getid:"+data);
                try {
                    data = DES2.decrypt(data, MD5Util.key);
                    JSONObject retJson = new JSONObject(data);
                    String status = retJson.getString("s");
                    if(status.equals("1"))
                    {
                        String uid = retJson.getString("uid");
                        SharedPreferencesUtils.setParam(mContext, A.TAGID,uid);
                    }
                }catch(Exception e)
                {
                    Logger.error(e.getMessage());
                }finally{

                }
            }

            @Override
            public void FailData(int code, int event_tag) {
            }

            @Override
            public void onBefore(Request request) {

            }

            @Override
            public void onAfter() {
                //getID();
            }
        });
    }
}
