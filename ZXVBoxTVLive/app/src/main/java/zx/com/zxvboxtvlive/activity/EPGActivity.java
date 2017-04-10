package zx.com.zxvboxtvlive.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import zx.com.zxvboxtvlive.Adapter.ChannelListAdapter;
import zx.com.zxvboxtvlive.Adapter.TitleAdapter;
import zx.com.zxvboxtvlive.Constants;
import zx.com.zxvboxtvlive.R;
import zx.com.zxvboxtvlive.ijkplayer.media.IjkVideoView;
import zx.com.zxvboxtvlive.mode.ShowPlayTimes;
import zx.com.zxvboxtvlive.mode.TvSource;
import zx.com.zxvboxtvlive.presenter.VideoViewPresenter;
import zx.com.zxvboxtvlive.utils.Logger;
import zx.com.zxvboxtvlive.view.IMainView;
import zx.com.zxvboxtvlive.view.IVideoView;
import zx.com.zxvboxtvlive.view.widget.PageViewIndicator;
import zx.com.zxvboxtvlive.view.widget.TitleAction;

/**
 * User: ShaudXiao
 * Date: 2017-04-07
 * Time: 14:45
 * Company: zx
 * Description:
 * FIXME
 */


public class EPGActivity extends  FullActivity implements TitleAction, IVideoView, IMainView{

    private final String[] titles = {
            "星期一","星期二","星期三","星期四","星期五","星期六","星期日"
    };

    private TitleAdapter mTitleAdapter;
    private PageViewIndicator mTitleIndicator;
    private VideoViewPresenter mVideoPresenter;
    private RecyclerView mChannelListView;
    private ChannelListAdapter mChannelListAdapter;

    private List<TvSource> mChannelList = new ArrayList<>();



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_epg);
        Intent intent = getIntent();
        if(intent != null) {
            List<TvSource> channelList = intent.getParcelableArrayListExtra(Constants.CHANNLE_LIST_KEY);
            mChannelList.addAll(channelList);
            Logger.getLogger().i("size = " + mChannelList.size());
        }

        initView();

        mVideoPresenter = new VideoViewPresenter(this, this, (IjkVideoView) findViewById(R.id.videoview));
        mVideoPresenter.playVideo("http://ivi.bupt.edu.cn/hls/hunanhd.m3u8");
    }

    private void initView() {
        mTitleIndicator = (PageViewIndicator) findViewById(R.id.indicator);
        mTitleAdapter = new TitleAdapter(this, Arrays.asList(titles));
        mTitleIndicator.setAdapter(mTitleAdapter);
        mTitleIndicator.setAction(this);

        mChannelListView = (RecyclerView)findViewById(R.id.channel_list);
        LinearLayoutManager lm = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mChannelListView.setLayoutManager(lm);
//        mChannelListView.addItemDecoration(new ChannelAdapterDecoration(this));


        mChannelListAdapter = new ChannelListAdapter(this);
        mChannelListAdapter.setData(mChannelList);
        mChannelListView.setAdapter(mChannelListAdapter);
    }

    @Override
    public void onTitleMoveFocus(View v, boolean hasFocus) {

    }

    @Override
    public void onTitleClick(View v) {

    }

    @Override
    public void showError(boolean show) {

    }

    @Override
    public void hideLoadingView() {

    }

    @Override
    public void showLoadingView(int id) {

    }

    @Override
    public void updateChannleAdapter(List<TvSource> data) {

    }

    @Override
    public void checkNextSource() {

    }

    @Override
    public void updateShowInfo() {

    }

    @Override
    public void updateShowInfoUI(List<ShowPlayTimes> timesList) {

    }
}
