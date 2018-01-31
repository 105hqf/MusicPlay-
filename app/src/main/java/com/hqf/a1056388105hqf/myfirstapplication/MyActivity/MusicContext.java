package com.hqf.a1056388105hqf.myfirstapplication.MyActivity;

import android.app.LocalActivityManager;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.hqf.a1056388105hqf.myfirstapplication.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/10/7.
 */


//  我的音乐button的ViewPagerActivity
public class MusicContext extends AppCompatActivity {
    //  初始化一个localActivityManager
    private LocalActivityManager manager = null;
    //  初始化一个ViewPager对象
    private ViewPager viewpager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        ////  显示musicViewPager的layout
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_music_viewpager);
        ///  沉浸模式显示
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

        ////  manager实例化
        manager = new LocalActivityManager(this , true);
        manager.dispatchCreate(savedInstanceState);
        initViewPager();
    }

    /**
     * 初始化viewpager控件，并为其添加一个适配器
     */
    private void initViewPager(){
        viewpager = (ViewPager) findViewById(R.id.Myviewpage);
        final ArrayList<View> list = new ArrayList<View>();
        Intent intent = new Intent(MusicContext.this, MySongControl.class);
        Intent intent1 = new Intent(MusicContext.this,MySongEntrepot.class);
        list.add(getView("MySongEntrepot.class",intent1));
        list.add(getView("MySongControl.class", intent));
        //  添加适配器
        viewpager.setAdapter(new MusicContext.MyPagerAdapter(list));
        //  初始化当前显示的页面
        //viewpager.setCurrentItem();
        viewpager.setOnPageChangeListener(new MusicContext.MyOnPageChangeListener());
    }
    class MyOnPageChangeListener implements ViewPager.OnPageChangeListener{
        @Override
        public void onPageSelected(int position) {

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }
    }
    /**
     * 通过activity获取视图
     * @param id
     * @param intent
     * @return
     */
    private View getView(String id, Intent intent) {
        return manager.startActivity(id, intent).getDecorView();
    }

    /**
     * Pager适配器
     */
    public class MyPagerAdapter extends PagerAdapter {
        List<View> list =  new ArrayList<View>();
        public MyPagerAdapter(ArrayList<View> list) {
            this.list = list;
        }

        @Override
        public void destroyItem(ViewGroup container, int position,
                                Object object) {
            ViewPager pViewPager = ((ViewPager) container);
            pViewPager.removeView(list.get(position));
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public int getCount() {
            return list.size();
        }
        @Override
        public Object instantiateItem(View arg0, int arg1) {
            ViewPager pViewPager = ((ViewPager) arg0);
            pViewPager.addView(list.get(arg1));
            return list.get(arg1);
        }

        @Override
        public void restoreState(Parcelable arg0, ClassLoader arg1) {

        }

        @Override
        public Parcelable saveState() {
            return null;
        }

        @Override
        public void startUpdate(View arg0) {
        }
    }
}
