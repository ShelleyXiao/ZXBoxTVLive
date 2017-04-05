package zx.com.zxvboxtvlive;

import android.app.Application;

import zx.com.zxvboxtvlive.data.sqlite.SqlShowTimeTableManager;

/**
 * Created by jiang on 2016/11/23.
 */

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        SqlShowTimeTableManager.initShowTimeTableManager(getApplicationContext());
    }

}
