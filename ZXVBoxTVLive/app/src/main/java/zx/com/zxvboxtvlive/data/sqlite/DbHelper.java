package zx.com.zxvboxtvlive.data.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import zx.com.zxvboxtvlive.utils.Logger;

/**
 * User: ShaudXiao
 * Date: 2017-03-30
 * Time: 14:52
 * Company: zx
 * Description:
 * FIXME
 */


public class DbHelper extends SQLiteOpenHelper {

    private static final int DB_VSERSION = 1;
    private static final String DATABASE_NAME = "tv.db";

    private static final String SQL_CREATE_SHOWPLAYTIMES_TABLE =
            "CREATE TABLE " + ShowTimesTableColumn.SHOWTIMES_TABLE_NAME + " (" +
                    ShowTimesTableColumn.CHANNEL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    ShowTimesTableColumn.CHANNEL_INDEX + " INTEGER," +
                    ShowTimesTableColumn.CHANNEL_NAME + " VARCHAR(30)," +
                    ShowTimesTableColumn.SHOWS_PIC_URL + " VARCHAR(300)," +
                    ShowTimesTableColumn.SHOWS_NAME + " VARCHAR(60)," +
                    ShowTimesTableColumn.SHOWS_START_TIME + " VARCHAR(12)," +
                    ShowTimesTableColumn.SHOWS_END_TIME + " VARCHAR(12)," +
                    ShowTimesTableColumn.SHOWS_CONTENT + " TEXT," +
                    ShowTimesTableColumn.SHOWS_PLAYING + " INTEGER" +
                    ") ";

    private static final String SQL_CREATE_SHOWSPLAYTABLE_UPDATE_TABLE =
            "CREATE TABLE " + ShowsPlayTableUpdateColumn.SHOWS_PLAY_TABLE_UPDATE_TABLE_NAME + " (" +
                    ShowsPlayTableUpdateColumn.SHOWS_PLAY_TIME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    ShowsPlayTableUpdateColumn.SHOWS_CHANNEL_NAME + " VARCHAR(30), " +
//                    ShowsPlayTableUpdateColumn.SHOWS_UPDATE_TIME + " DATETIME DEFAULT CURRENT_TIMESTAMP" +
                    ShowsPlayTableUpdateColumn.SHOWS_UPDATE_TIME + " VARCHAR(60)" +
                    ") ";

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DB_VSERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Logger.getLogger().i(" ************* onCreate");
        db.execSQL(SQL_CREATE_SHOWPLAYTIMES_TABLE);
        db.execSQL(SQL_CREATE_SHOWSPLAYTABLE_UPDATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
