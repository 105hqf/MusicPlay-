package com.hqf.a1056388105hqf.myfirstapplication.MyDBhelper;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.util.Log;

import com.hqf.a1056388105hqf.myfirstapplication.R;

//  调用数据库
public class DBHelper {

    private final int BUFFER_SIZE = 400000;
    public static final String DB_NAME = "mybase.db"; //保存的数据库文件名
    public static final String DB_PATH = "/data/data/com.hqf.a1056388105hqf.myfirstapplication/data";
    private final String DATABASE_PATH = android.os.Environment
            .getExternalStorageDirectory().getAbsolutePath();
    private String DATABASE_FILENAME = "mybase.db";

    private SQLiteDatabase database;
    private Context context;
    //  构造函数
    public DBHelper(Context context) {
        this.context = context;
    }
    //  打开数据库
    public SQLiteDatabase openDatabase() {
        return this.openDatabase(DATABASE_PATH + "/" + DATABASE_FILENAME);
    }
    private SQLiteDatabase openDatabase(String dbfile) {
        try {
            if (!(new File(dbfile).exists())) {//判断数据库文件是否存在，若不存在则执行导入，否则直接打开数据库
                InputStream is = this.context.getResources().openRawResource(R.raw.mybase); //欲导入的数据库
                FileOutputStream fos = new FileOutputStream(dbfile);
                byte[] buffer = new byte[BUFFER_SIZE];
                int count = 0;
                while ((count = is.read(buffer)) > 0) {

                    fos.write(buffer, 0, count);

                }
                fos.close();
                is.close();
              }
              SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(dbfile,null);

            return db;
          } catch (FileNotFoundException e) {

            Log.e("Database", "File not found");

            e.printStackTrace();

            } catch (IOException e) {
                        Log.e("Database", "IO exception");
                        e.printStackTrace();
            }
        return null;
    }
        public void closeDatabase() {this.database.close();}
}
