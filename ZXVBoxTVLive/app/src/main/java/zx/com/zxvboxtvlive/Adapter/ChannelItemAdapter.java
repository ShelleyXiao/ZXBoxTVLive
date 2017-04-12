/*
 * Copyright (C) 2016 hejunlin <hejunlin2013@gmail.com>
 * 
 * Github:https://github.com/hejunlin2013/LivePlayback
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package zx.com.zxvboxtvlive.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import zx.com.zxvboxtvlive.R;
import zx.com.zxvboxtvlive.mode.TvSource;
import zx.com.zxvboxtvlive.utils.Logger;


public class ChannelItemAdapter extends RecyclerView.Adapter<ChannelItemAdapter.ViewHolder> {
    private static final String TAG = ChannelItemAdapter.class.getSimpleName();

    private Context mContext;
    private int id;
    private View.OnFocusChangeListener mOnFocusChangeListener;
    private OnBindListener onBindListener;

    private List<TvSource> mDataList = new ArrayList<>();

    private int channelId = -1;

    public interface OnBindListener {
        void onBind(View view, int i);

        void onKeyListener(int keyCode, KeyEvent event);
    }

    public ChannelItemAdapter(Context context) {
        super();
        mContext = context;
    }

    public ChannelItemAdapter(Context context, int id) {
        super();
        mContext = context;
        this.id = id;
    }

    public ChannelItemAdapter(Context context, int id, View.OnFocusChangeListener onFocusChangeListener) {
        super();
        mContext = context;
        this.id = id;
        this.mOnFocusChangeListener = onFocusChangeListener;
    }

    public void setData(List<TvSource> data) {
        mDataList.clear();
        mDataList.addAll(data);
    }

    public void setOnBindListener(OnBindListener onBindListener) {
        this.onBindListener = onBindListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        int resId = R.layout.detail_menu_item;
        if (this.id > 0) {
            resId = this.id;
        }
        View view = LayoutInflater.from(mContext).inflate(resId, viewGroup, false);
        ViewHolder holder = new ViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int i) {
        if (mDataList.size() == 0) {
            Logger.getLogger().d(TAG, "mDataset has no data!");
            return;
        }
        viewHolder.itemView.setTag(i);
//        Logger.getLogger().i(" i " + i + " name : " + mDataList.get(i).getTvName());
        viewHolder.mTextView.setText(mDataList.get(i).getTvName());

        if(mDataList.get(i).getId() == channelId) {
            viewHolder.mSelectOverlay.setVisibility(View.VISIBLE);
            viewHolder.mTextView.setTextColor(Color.BLACK);
        } else {
            viewHolder.mSelectOverlay.setVisibility(View.INVISIBLE);
            viewHolder.mTextView.setTextColor(Color.WHITE);
        }

        viewHolder.itemView.setOnFocusChangeListener(mOnFocusChangeListener);
        final View view = viewHolder.itemView;
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onBindListener != null) {
                    onBindListener.onBind(view, i);
                }
                TvSource source = mDataList.get(i);
                if(source.getId() != channelId) {
                    channelId = source.getId();
                }
                notifyDataSetChanged();
            }
        });

        viewHolder.itemView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                Logger.getLogger().e("holder itemview keyCode =  " + keyCode );
                if(onBindListener != null) {
                    onBindListener.onKeyListener(keyCode, event);
                }
                return false;
            }
        });



    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView mTextView;
        public View mSelectOverlay;

        public ViewHolder(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView.findViewById(R.id.tv_menu_title);
            mSelectOverlay = itemView.findViewById(R.id.selected_overlay);
        }
    }


}
