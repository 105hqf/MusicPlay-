package com.hqf.a1056388105hqf.myfirstapplication.MyActivity;
import android.app.LocalActivityManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
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
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.hqf.a1056388105hqf.myfirstapplication.MyMethod.MediaBitmapChange;
import com.hqf.a1056388105hqf.myfirstapplication.R;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/7/4.
 */

public class MytestForMainActivity extends AppCompatActivity {

    //  初始化一些实例
    ViewPager viewpager = null;
    private LocalActivityManager manager = null;
    private RadioGroup radiogroup;
    private int flag;                               //  标记目前进入的是哪一个界面
    private int radioid;
    private int getReturnflag=0;
    private LinearLayout mylin;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewpager_layout_main);
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        mylin = (LinearLayout)findViewById(R.id.mySumActivity);
        mylin.setBackground(new BitmapDrawable(MediaBitmapChange.blurBitmap(
                BitmapFactory.decodeResource(getResources(),R.mipmap.item3),MytestForMainActivity.this)));
        manager = new LocalActivityManager(this , true);
        manager.dispatchCreate(savedInstanceState);
        radiogroup = (RadioGroup)findViewById(R.id.rg);
        radiogroup.setOnCheckedChangeListener(new myOnCheckedChangeListener());
        if(getIntent().getExtras()!=null&&getIntent().getExtras().getString("returnflag")!=null)
            //  获取返回过来的参数
        getReturnflag = Integer.parseInt(getIntent().getExtras().getString("returnflag"))-1;
        initViewPager();
    }

    /**
     * 初始化viewpager控件，并为其添加一个适配器
     */
    private void initViewPager(){
        viewpager = (ViewPager) findViewById(R.id.viewpage);
        final ArrayList<View> list = new ArrayList<View>();
        Intent intent = new Intent(MytestForMainActivity.this, mysecondAcitvity.class);
        list.add(getView("mysecondAcitvity.class", intent));
        Intent intent2 = new Intent(MytestForMainActivity.this,MySongEntrepot.class);
        list.add(getView("MySongEntrepot.class", intent2));
        Intent intent3 = new Intent(MytestForMainActivity.this, MypersonalAndSettingActivity.class);
        list.add(getView("MypersonalAndSettingActivity.class", intent3));
        //  添加适配器
        viewpager.setAdapter(new MyPagerAdapter(list));
        //  初始化当前显示的页面
        //  当界面从音乐播放界面中跳转到音乐库界面时，直接跳转，
        viewpager.setCurrentItem(getReturnflag);
        viewpager.setOnPageChangeListener(new MyOnPageChangeListener());
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
        if(flag<radioid){
            overridePendingTransition(R.anim.left_out,R.anim.right_in);
        }
        else if(flag>radioid){
            overridePendingTransition(R.anim.left_in,R.anim.right_out);
        }
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

    //**RadioGroup选择的事件
    private class myOnCheckedChangeListener implements RadioGroup.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
            switch(group.getCheckedRadioButtonId()){
                case R.id.rb1:radioid=1;
                    break;
                case R.id.rb2:radioid=2;
                    break;
                case R.id.rb3:radioid=3;
                    break;
                case R.id.rb4:radioid=4;
                    break;
            }
            if(radioid==3){
                ;
            }else if(radioid==4){
                viewpager.setCurrentItem(radioid-2);

                flag=4;
            }
            else {
                viewpager.setCurrentItem(radioid-1);
                if(radioid==3){
                    //  当出现第三个ID时，考虑两个页面的切换显示
                }
                flag=radioid;
            }
        }
    }
}
