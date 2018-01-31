package com.hqf.a1056388105hqf.myfirstapplication.MyActivity;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.BaseAdapter;
import android.widget.Toast;

import com.hqf.a1056388105hqf.myfirstapplication.MyMethod.MediaBitmapChange;
import com.hqf.a1056388105hqf.myfirstapplication.MySong.MusicBean;
import com.hqf.a1056388105hqf.myfirstapplication.R;

import java.io.File;
import java.util.ArrayList;



/**
 * Created by Administrator on 2017/5/19.
 */

public class MySongControl extends AppCompatActivity {
    //  最多100首歌曲     //  初始化一个布局对象
    //  初始化一个歌曲的集合
    private ArrayList<MusicBean> mMusicList = new ArrayList<>();
    private ListView mListView;
    private Handler mHandler = new Handler();
    private FileAdapter mAdapter;
    // Storage Permissions
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    private int getmessage;
    private TextView textsum;
    public int flag = 0;
    private ImageButton mysongcontrol_backforward;
    private LinearLayout third;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mysongcontrols);
        //  对界面修改
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        verifyStoragePermissions(MySongControl.this);
        mListView = (ListView) findViewById(R.id.music_list_view);
        mListView.setOnItemClickListener(new onMyclicklistener());
        textsum = (TextView) findViewById(R.id.Sumtext);
        third = (LinearLayout)findViewById(R.id.third);
        third.setBackground(new BitmapDrawable(MediaBitmapChange.blurBitmap(
                BitmapFactory.decodeResource(getResources(),R.mipmap.item3),MySongControl.this)));
        //  设置适配器
        mAdapter = new FileAdapter(this);
        mListView.setAdapter(mAdapter);
        mysongcontrol_backforward = (ImageButton)findViewById(R.id.MySongControl_backforward);
        mysongcontrol_backforward.setOnClickListener(new OnMySongtrolClickListener());
        //  设置状态，动态添加对SD卡的访问
        String state;
        state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            scanFileList(Environment.getExternalStorageDirectory());
        }
        //  启动线程
        mythread();
        Bundle bundleget = this.getIntent().getExtras();
        //  当从播放界面传递数据回来时，标记正在播放的音乐
        if (this.getIntent().getExtras() != null)
            getmessage = bundleget.getInt("position");
        else  if(MyElement_java.mylist!=null&&MyElement_java.mediaPlayer!=null){
            //  当当前的歌单已经存在元素，则标记正在播放的歌曲名
            getmessage = bundleget.getInt("position");
        }
        else {
            getmessage = -1;
            flag = 1;
        }
        textsum.setText("收藏的歌曲总数：" + mMusicList.size());

    }

    class onMyclicklistener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            //  listView items被点击时触发事件
            if(MyElement_java.MyMusic==null||MyElement_java.MyMusic.getMusicPath().equals(mMusicList.get(position).getMusicPath())==false)  //  如果点击的不是正在播放的曲子，则播放选择的曲子
            {
                Intent data = new Intent();
                data.putExtra("data", mMusicList.get(position).getMusicPath() + "_" + position);
                data.setClass(MySongControl.this, MyfirstAppMainActivity.class);
                MyElement_java.MyMusic = mMusicList.get(position);
                Toast.makeText(MySongControl.this, "执行了if", Toast.LENGTH_SHORT).show();
                if (flag == 0) {   //  若这个是已经开始播放后进入该页面，则通过setResult跳转
                    setResult(1,data);
                    finish();
                } else {
                    //  若是当前没有播放音乐，当前音乐播放页面没有激活，则利用Intent跳转
                    startActivity(data);
                    finish();
                }
            }
            else if(MyElement_java.MyMusic!=null&&MyElement_java.MyMusic.getMusicPath().equals(mMusicList.get(position).getMusicPath())==true){
                //  当点击的歌曲是当前正在播放的，则跳转页面
                //  到当前播放页面，继续播放，不重新开始
                setResult(0,null);
                Toast.makeText(MySongControl.this, "执行了else", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {

        super.onNewIntent(intent);
    }

    /**
     * 扫描SD卡
     */
    /**
     * 扫描Sdcard（外部存储）下所有文件
     */
    public void scanFileList(File parentFile) {

        File[] listFile = parentFile.listFiles();
        int length = listFile.length;

        if (listFile != null) {
            for (int i = 0; i < length; i++) {
                File file = listFile[i];

                if (file.isDirectory())           //  当前文件不是根目录，返回true，递归遍历当前文件夹下的文件
                {
                    scanFileList(file);           // 递归遍历文件
                } else {
                    if (file.getName().endsWith(".mp3")) {   //  当文件后缀名为Mp3时，将其取出
                        MusicBean music = new MusicBean();   //  保存到MusicBean对象中
                        String fileName = file.getName();
                        music.setMusicName(fileName.substring(0, fileName.length() - ".mp3".length()));
                        music.setMusicPath(file.getAbsolutePath());
                        mMusicList.add(music);
                        MyElement_java.mylist.add(music);
                    }
                }
            }
        } else Toast.makeText(this, "SD卡没有文件", Toast.LENGTH_LONG).show();
    }

    /**
     * Checks if the app has permission to write to device storage
     * <p>
     * If the app does not has permission then the user will be prompted to grant permissions
     *
     * @param activity
     */
    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }

    /**
     * 继承与BaseAdapter的对文件搜索数据后数据更新的适配器
     */
    //  适配器
    class FileAdapter extends BaseAdapter {
        private ArrayList<MusicBean> list = new ArrayList<>();
        private LayoutInflater layoutInflater;

        public FileAdapter(Context context) {
            //  layoutInflater是一个Layout.xml的继承对象，用于指向当前的Layout.xml文件，
            //  被封装在setOnCreatView函数中，用来刷新View
            layoutInflater = LayoutInflater.from(context);
        }

        public void setListData(ArrayList<MusicBean> list) {
            this.list = list;
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {

            return list.size();
        }

        @Override
        public Object getItem(int position) {

            return list.get(position);
        }

        @Override
        public long getItemId(int position) {

            return position;
        }

        //  当调用notifyDataSetChanged();函数后自动跳转到这个函数中
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = layoutInflater.inflate(R.layout.item_layout_list, null);
            }
            MusicBean file = (MusicBean) getItem(position);
            TextView nameTxt = (TextView) convertView;
            nameTxt.setText((position + 1) + "  " + file.getMusicName());
            int Position = getmessage;
            if (Position != -1 && Position == position) {

                nameTxt.setTextColor(Color.parseColor("#ff6600"));
            }
            return convertView;
        }
    }
    private class OnMySongtrolClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {

            if(v.getId()==R.id.MySongControl_backforward) {
                //  当点击返回按钮后，返回主界面的我的音乐界面
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putString("returnflag", "2");
                intent.putExtras(bundle);
                intent.setClass(MySongControl.this, MytestForMainActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.left_in,R.anim.right_out);
                finish();
                /**
                 *
                 *
                 *
                 * 这里的代码有很多需要改良，之后再改良，不用intent来跳转页面
                 *
                 *
                 *
                 **/
            }
        }
    }
    public void mythread(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                //ListView刷新必须在UI线程中 通过Handler消息机制发送刷新代码到UI主线程执行
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter.setListData(mMusicList);
                    }
                });
            }
        }).start();
    }
}
