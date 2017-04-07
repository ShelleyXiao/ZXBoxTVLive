package zx.com.zxvboxtvlive;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * User: ShaudXiao
 * Date: 2017-04-07
 * Time: 11:12
 * Company: zx
 * Description:
 * FIXME
 */


public class Setting {

    private static final String CHANNLE_RECORD = "channel";
    private static final String CHANNEL_NAME = "channel_name";

    public static void lastPlayChannel(Context context, String channelName) {
        SharedPreferences sp = context.getSharedPreferences(CHANNLE_RECORD, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(CHANNEL_NAME, channelName);
        editor.commit();

    }

    public static String getLastPlayChannelName(Context context) {
        SharedPreferences sp = context.getSharedPreferences(CHANNLE_RECORD, Context.MODE_PRIVATE);
        String name = sp.getString(CHANNEL_NAME, null);

        return name;
    }
}
