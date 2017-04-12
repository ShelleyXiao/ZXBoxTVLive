
package zx.com.zxvboxtvlive.Adapter;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import zx.com.zxvboxtvlive.R;
import zx.com.zxvboxtvlive.mode.ShowPlayTimes;
import zx.com.zxvboxtvlive.utils.Logger;


public class ShowTimeListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String TAG = ShowTimeListAdapter.class.getSimpleName();

    private static final int NORMAL_TYPE = 0X01;
    private static final int TRANSFORM_TYPE = 0X02;


    private Context mContext;
    private int id;
    private View.OnFocusChangeListener mOnFocusChangeListener;
    private OnItemSelectedListener mOnItemSelectedListener;

    private List<ShowPlayTimes> mDataList = new ArrayList<>();

    public interface OnItemSelectedListener {
        void onItemSelected(int position);
    }

    public ShowTimeListAdapter(Context context) {
        mContext = context;
    }

    public ShowTimeListAdapter(Context context, int id) {
        mContext = context;
        this.id = id;
    }

    public ShowTimeListAdapter(Context context, int id, View.OnFocusChangeListener onFocusChangeListener) {
        mContext = context;
        this.id = id;
        this.mOnFocusChangeListener = onFocusChangeListener;
    }

    public void setData(List<ShowPlayTimes> data) {
        mDataList.clear();
        mDataList.addAll(data);
    }

    public void setOnItemSelectedListener(OnItemSelectedListener onBindListener) {
        this.mOnItemSelectedListener = onBindListener;
    }

    @Override
    public int getItemViewType(int position) {

        if (position % 2 == 0) {
            return TRANSFORM_TYPE;
        } else {
            return NORMAL_TYPE;
        }
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        int resId = R.layout.showtime_list_item;
        if (this.id > 0) {
            resId = this.id;
        }
        RecyclerView.ViewHolder holder = null;
        View view = LayoutInflater.from(mContext).inflate(resId, viewGroup, false);
        if (viewType == NORMAL_TYPE) {
            holder = new ViewHolderNormal(view);
        } else if (viewType == TRANSFORM_TYPE) {
            holder = new ViewHolderTransform(view);
        }


        return holder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, final int i) {
        if (mDataList.size() == 0) {
            Logger.getLogger().d(TAG, "mDataset has no data!");
            return;
        }

        viewHolder.itemView.setTag(i);

        viewHolder.itemView.setFocusable(true);

        TextView mTimeText = (TextView) viewHolder.itemView.findViewById(R.id.tv_show_time);
        TextView mNameText = (TextView) viewHolder.itemView.findViewById(R.id.tv_show_name);

        ShowPlayTimes playTimes = mDataList.get(i);
        mTimeText.setText(playTimes.getShowStartTime());
        mNameText.setText(playTimes.getShowName());


        viewHolder.itemView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (Build.VERSION.SDK_INT >= 21) {
                    if (hasFocus) {
                        if(mOnItemSelectedListener != null) {
                            mOnItemSelectedListener.onItemSelected(i);
                        }
                        ViewCompat.animate(v).scaleX(1.17f).scaleY(1.17f).translationZ(1).start();
                        Logger.getLogger().i(" on foucs **********");
                    } else {
                        ViewCompat.animate(v).scaleX(1.0f).scaleY(1.0f).translationZ(1).start();
                    }
                }
                if (hasFocus) {
                    if (mOnItemSelectedListener != null) {
                        mOnItemSelectedListener.onItemSelected(i);
                    }
                }

            }
        });

    }


    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    public class ViewHolderNormal extends RecyclerView.ViewHolder {

//        public TextView mTimeText;
//        public TextView mNameText;
//
//        public View mSelectOverlay;

        public ViewHolderNormal(View itemView) {
            super(itemView);
//            mTimeText = (TextView) itemView.findViewById(R.id.tv_show_time);
//            mNameText = (TextView) itemView.findViewById(R.id.tv_show_name);
        }
    }

    public class ViewHolderTransform extends RecyclerView.ViewHolder {

//        public TextView mTimeText;
//        public TextView mNameText;
//
//        public View mSelectOverlay;

        public ViewHolderTransform(View itemView) {
            super(itemView);
            itemView.setBackground(new ColorDrawable(mContext.getResources().getColor(R.color.showtime_item_bg)));
//            mTimeText = (TextView) itemView.findViewById(R.id.tv_show_time);
//            mNameText = (TextView) itemView.findViewById(R.id.tv_show_name);
        }
    }


}
