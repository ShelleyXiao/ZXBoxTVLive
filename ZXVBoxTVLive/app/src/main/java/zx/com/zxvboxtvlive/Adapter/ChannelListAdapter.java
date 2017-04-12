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
import android.os.Build;
import android.support.v4.view.ViewCompat;
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


public class ChannelListAdapter extends RecyclerView.Adapter<ChannelListAdapter.ViewHolder> {
    private static final String TAG = ChannelListAdapter.class.getSimpleName();

    private Context mContext;
    private int id;
    private View.OnFocusChangeListener mOnFocusChangeListener;
    private OnBindListener onBindListener;

    private int channelId = -1;

    private List<TvSource> mDataList = new ArrayList<>();

    public interface OnBindListener {
        void onBind(View view, int i);
    }

    public ChannelListAdapter(Context context) {
        super();
        mContext = context;
    }

    public ChannelListAdapter(Context context, int id) {
        super();
        mContext = context;
        this.id = id;
    }

    public ChannelListAdapter(Context context, int id, View.OnFocusChangeListener onFocusChangeListener) {
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
        int resId = R.layout.channel_list_item;
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
        viewHolder.mTextView.setText(mDataList.get(i).getTvName());
        viewHolder.itemView.setTag(i);
        viewHolder.itemView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (Build.VERSION.SDK_INT >= 21) {
                    if (hasFocus) {
                        ViewCompat.animate(v).scaleX(1.17f).scaleY(1.17f).translationZ(1).start();
                    } else {
                        ViewCompat.animate(v).scaleX(1.0f).scaleY(1.0f).translationZ(1).start();
                    }
                }
            }
        });
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
                Logger.getLogger().d("channelId = " + channelId);
            }

        });

        viewHolder.itemView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    ViewCompat.animate(v).scaleX(1.17f).scaleY(1.17f).translationZ(1).start();
                } else {
                    ViewCompat.animate(v).scaleX(1.0f).scaleY(1.0f).translationZ(1).start();
                }
            }
        });

        viewHolder.itemView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                Logger.getLogger().i("keyCode = " + keyCode);
                if(event.getAction() == KeyEvent.ACTION_UP) {
                    Logger.getLogger().i("keyCode = " + keyCode);
                }
                return false;
            }
        });


        if(mDataList.get(i).getId() == channelId) {
            viewHolder.mSelectOverlay.setVisibility(View.VISIBLE);
            viewHolder.mTextView.setTextColor(Color.BLACK);
        } else {
            viewHolder.mSelectOverlay.setVisibility(View.INVISIBLE);
            viewHolder.mTextView.setTextColor(Color.WHITE);
        }


    }

    public TvSource getSelectTvSrouce() {
        return mDataList.get(channelId);
    }

    public int getChannelId() {
        return channelId;
    }

    public void setChannelId(int channelId) {
        this.channelId = channelId;
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
            mTextView = (TextView) itemView.findViewById(R.id.tv_channel_name);
            mSelectOverlay = itemView.findViewById(R.id.selected_overlay);
        }
    }


}
