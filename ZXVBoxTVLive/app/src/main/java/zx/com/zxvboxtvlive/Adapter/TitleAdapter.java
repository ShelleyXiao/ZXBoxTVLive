package zx.com.zxvboxtvlive.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import zx.com.zxvboxtvlive.R;
import zx.com.zxvboxtvlive.mode.WeekInfo;
import zx.com.zxvboxtvlive.utils.TimeUtils;
import zx.com.zxvboxtvlive.view.autolayout.utils.AutoUtils;

/**
 * User: ShaudXiao
 * Date: 2017-04-07
 * Time: 15:34
 * Company: zx
 * Description:
 * FIXME
 */


public class TitleAdapter extends BaseAdapter {

    private Context mContext;
    private List<String> mDatas;
    private List<WeekInfo> mWeekInfos;

    public TitleAdapter(Context context, List<String> data) {
        mContext = context;
        mDatas = data;
        mWeekInfos = TimeUtils.getCurrentWeekInfoList();
    }

    @Override
    public int getCount() {
        return mDatas != null ? mDatas.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return mDatas != null ? mDatas.get(position) : null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.title_item, parent, false);

        TextView title = (TextView) view.findViewById(R.id.title);
        TextView titleDate = (TextView) view.findViewById(R.id.title_date);
        title.setText(mDatas.get(position));
        titleDate.setText(mWeekInfos.get(position).getWeekOfDay());

        AutoUtils.autoSize(view);

        return view;
    }


}
