package zx.com.zxvboxtvlive;

import android.app.Application;

import zx.com.zxvboxtvlive.data.sqlite.SqlShowTimeTableManager;

/**
 * Created by jiang on 2016/11/23.
 */

public class App extends Application {

    private static App instance;

    private volatile int currentSourceId ;

    @Override
    public void onCreate() {
        super.onCreate();

        SqlShowTimeTableManager.initShowTimeTableManager(getApplicationContext());

        instance = this;

        currentSourceId = 0;

    }

    public static App getInstance() {
        return  instance;
    }

    public int getCurrentSourceId() {
        return currentSourceId;
    }

    public void setCurrentSourceId(int currentSourceId) {
        if(currentSourceId >= Constants.MAX_SOURCE_NUM) {
            currentSourceId = 0;
        }
        this.currentSourceId = currentSourceId;
    }
}
