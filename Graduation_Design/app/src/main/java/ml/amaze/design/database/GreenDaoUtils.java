package ml.amaze.design.database;

import android.content.Context;
import android.util.Log;

import org.greenrobot.greendao.database.Database;

import ml.amaze.design.bean.DaoMaster;
import ml.amaze.design.bean.DaoSession;

/**
 *
 * @author hxj
 * @date 2017/12/15 0015
 * 使用greenDao框架保存数据
 */

public class GreenDaoUtils {

    private static DaoSession daoSession=null;

     private static Database database;
    private static   DaoMaster.DevOpenHelper devOpenHelper;
    private static DaoSession getDaoSession(Context context) {

        devOpenHelper = new DaoMaster.DevOpenHelper(context, "test.db", null);

        //database = devOpenHelper.getEncryptedWritableDb("123");
        database = devOpenHelper.getWritableDb();
        DaoMaster daoMaster = new DaoMaster(database);
        DaoSession daoSession = daoMaster.newSession();
        Log.d("GreenDaoUtils","GreenDaoUtils执行创建方法");
        return daoSession;
    }

    /**
     * 使用单例设计模式确保daoSession唯一
     * @param context
     * @return
     */
    public static DaoSession getDaoSessionInstance(Context context) {

        if(daoSession==null){
            return getDaoSession(context);
        }else {
            return daoSession;
        }

    }


    public static void close(){
        if(database!=null){
            database.close();
        }
        if(devOpenHelper!=null){
            devOpenHelper.close();
        }
        Log.d("GreenDaoUtils","GreenDaoUtils执行关闭方法");

    }

}
