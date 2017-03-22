package wk.shop.ui;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import net.tsz.afinal.view.TitleBar;

import butterknife.Bind;
import butterknife.ButterKnife;
import wk.shop.R;
import wk.shop.base.BaseActivity;

/**
 * 我的任务
 * Created by Administrator on 2016/12/12.
 */

public class MyOrderActivity extends BaseActivity {
    public static MyOrderActivity mInstance;
    @Bind(R.id.tabs)
    TabLayout mTabLayout;
    @Bind(R.id.container)
    ViewPager mViewPager;
    @Bind(R.id.title_bar)
    TitleBar title_bar;
    SectionsPagerAdapter mSectionsPagerAdapter;
    int position = 0;
    OrderFragment dqu;//待取货
    OrderFragment ps;//配送中
    OrderFragment ywc;//已完成
    OrderFragment yqx;//已取消

    @Override
    public void initViews() {
        setContentView(R.layout.ac_my_order);
        ButterKnife.bind(this);
        mInstance = this;
        dqu = new OrderFragment();
        ps = new OrderFragment();
        ywc = new OrderFragment();
        yqx = new OrderFragment();
        title_bar.setLeftClick(() -> finish());
    }

    @Override
    public void initEvents() {
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setOffscreenPageLimit(4);
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);//设置tab模式，当前为系统默认模式
        mTabLayout.setupWithViewPager(mViewPager);//将TabLayout和ViewPager关联起来。
        mTabLayout.setTabsFromPagerAdapter(mSectionsPagerAdapter);//给Tabs设置适配器
        mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                position = tab.getPosition();
                switch (position) {
                    case 0:
                        dqu.refreshList(1);
                        break;
                    case 1:
                        ps.refreshList(2);
                        break;
                    case 2:
                        ywc.refreshList(3);
                        break;
                    case 3:
                        yqx.refreshList(4);
                        break;
                }
                mViewPager.setCurrentItem(tab.getPosition()); //解决单击Tab标签无法翻页的问题
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        dqu.refreshList(1);
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return dqu;
                case 1:
                    return ps;
                case 2:
                    return ywc;
                case 3:
                    return yqx;
            }
            return null;
        }

        @Override
        public int getCount() {
            return 4;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "待取货";
                case 1:
                    return "配送中";
                case 2:
                    return "已完成";
                case 3:
                    return "已取消";
            }
            return null;
        }
    }
}
