package zx.com.zxvboxtvlive.data.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import zx.com.zxvboxtvlive.mode.ShowPlayTimes;
import zx.com.zxvboxtvlive.utils.Logger;

/**
 * User: ShaudXiao
 * Date: 2017-03-30
 * Time: 15:28
 * Company: zx
 * Description:
 * FIXME
 */


public class SqlShowTimeTableManager  {

    private DbHelper mDbHelper;
    private SQLiteDatabase db;

    private static SqlShowTimeTableManager sSqlShowTimeTableManager;

    public static SqlShowTimeTableManager getInstance() {
        if(sSqlShowTimeTableManager == null) {
            throw new NullPointerException(" sql instance null!");
        }

        return sSqlShowTimeTableManager;
    }

    public static void initShowTimeTableManager(Context context) {
        sSqlShowTimeTableManager = new SqlShowTimeTableManager(context);
    }

    private SqlShowTimeTableManager(Context context) {
        mDbHelper = new DbHelper(context);
        db = mDbHelper.getWritableDatabase();
    }

    public void addShowTimeTable(List<ShowPlayTimes> timesList) {
        try {
            db.beginTransaction();
            for(ShowPlayTimes playTimes : timesList) {
                ContentValues values = new ContentValues();
                values.put(ShowTimesTableColumn.CHANNEL_NAME, playTimes.getChanelName());
                values.put(ShowTimesTableColumn.SHOWS_NAME, playTimes.getShowName());
                values.put(ShowTimesTableColumn.SHOWS_PIC_URL, playTimes.getPic());
                values.put(ShowTimesTableColumn.SHOWS_START_TIME, playTimes.getShowStartTime());
                values.put(ShowTimesTableColumn.SHOWS_END_TIME, playTimes.getShowEndTime());
                values.put(ShowTimesTableColumn.SHOWS_CONTENT, playTimes.getShowContent());

                long rowId = db.insert(ShowTimesTableColumn.SHOWTIMES_TABLE_NAME, null, values);
                if(rowId != -1) {

                }

                Logger.getLogger().d("SqlShowTimeTableManager add id: " + rowId);

            }
            db.setTransactionSuccessful();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }
    }

    public void addShowPlayTimes(ShowPlayTimes times) {
        try {
            db.beginTransaction();

            ContentValues values = new ContentValues();
            values.put(ShowTimesTableColumn.CHANNEL_NAME, times.getChanelName());
            values.put(ShowTimesTableColumn.SHOWS_NAME, times.getShowName());
            values.put(ShowTimesTableColumn.SHOWS_PIC_URL, times.getPic());
            values.put(ShowTimesTableColumn.SHOWS_START_TIME, times.getShowStartTime());
            values.put(ShowTimesTableColumn.SHOWS_END_TIME, times.getShowEndTime());
            values.put(ShowTimesTableColumn.SHOWS_CONTENT, times.getShowContent());

            long rowId = db.insert(ShowTimesTableColumn.SHOWTIMES_TABLE_NAME, null, values);
            if(rowId != -1) {

            }

            Logger.getLogger().d("SqlShowTimeTableManager add id: " + rowId);

            db.setTransactionSuccessful();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }
    }

    public List<ShowPlayTimes> queryShowPlayTimes(String[] columns, String selection, String[] selectionArgs, String groupBy,
                                                  String having, String orderBy) {
        List<ShowPlayTimes> qShowPlayTimes = new ArrayList<>();
        Cursor cursor = db.query(ShowTimesTableColumn.SHOWTIMES_TABLE_NAME, columns, selection, selectionArgs,
                groupBy, having, orderBy);
        while(cursor.moveToNext()) {
            ShowPlayTimes times = new ShowPlayTimes();
            times.setChanelName(cursor.getString(cursor.getColumnIndex(ShowTimesTableColumn.CHANNEL_NAME)));
            times.setShowName(cursor.getString(cursor.getColumnIndex(ShowTimesTableColumn.SHOWS_NAME)));
            times.setPic(cursor.getString(cursor.getColumnIndex(ShowTimesTableColumn.SHOWS_PIC_URL)));
            times.setShowStartTime(cursor.getString(cursor.getColumnIndex(ShowTimesTableColumn.SHOWS_START_TIME)));
            times.setShowEndTime(cursor.getString(cursor.getColumnIndex(ShowTimesTableColumn.SHOWS_END_TIME)));
            times.setShowContent(cursor.getString(cursor.getColumnIndex(ShowTimesTableColumn.SHOWS_CONTENT)));
        }
        cursor.close();

        return qShowPlayTimes;
    }

    public int delShowPlayTimes(String selection, String[] selectionArgs) {
        int count = db.delete(ShowTimesTableColumn.SHOWTIMES_TABLE_NAME, selection, selectionArgs);
        Logger.getLogger().i("SqlShowTimeTableManager del " + count);

        return count;
    }

    /***********************************************/

    public void insertShowTableUpdateTime(String channelName) {
        try {
            db.beginTransaction();
            ContentValues values = new ContentValues();
            values.put(ShowsPlayTableUpdateColumn.SHOWS_CHANNEL_NAME, channelName);
            long rowId = db.insert(ShowsPlayTableUpdateColumn.SHOWS_PLAY_TABLE_UPDATE_TABLE_NAME, null, values);
            if(rowId != -1) {

            }

            db.setTransactionSuccessful();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }
    }

    public Date queryShowTableUpdateTime(final String channelName) {
        String sql = "SELECT datetime(timestamp,'localtime') FROM " + ShowsPlayTableUpdateColumn.SHOWS_PLAY_TABLE_UPDATE_TABLE_NAME;
        Cursor cursor = db.rawQuery(sql, new String[] {channelName});
        String dateStr = cursor.getString(cursor.getColumnIndex("datetime(timestamp,'localtime')"));
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = null;
        try {
            date = format.parse(dateStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }


    public void delShowTableUpdateTime(final String channelName) {
        String selection = ShowsPlayTableUpdateColumn.SHOWS_CHANNEL_NAME + "=?";
        int count = db.delete(ShowsPlayTableUpdateColumn.SHOWS_PLAY_TABLE_UPDATE_TABLE_NAME, selection, new String[] {channelName});
        Logger.getLogger().i("SqlShowTimeTableManager del " + count);
    }

    public int uupdateShowtableUpdateTime(final String channelName)  {
        String selection = ShowsPlayTableUpdateColumn.SHOWS_CHANNEL_NAME + "=?";
        ContentValues values = new ContentValues();
        values.put(ShowsPlayTableUpdateColumn.SHOWS_CHANNEL_NAME, channelName);
        int count = db.update(ShowsPlayTableUpdateColumn.SHOWS_PLAY_TABLE_UPDATE_TABLE_NAME, values, selection, new String[] {channelName});
        return count;
    }
}
