package zx.com.zxvboxtvlive.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import zx.com.zxvboxtvlive.Adapter.ChannelListAdapter;
import zx.com.zxvboxtvlive.Adapter.ShowTimeListAdapter;
import zx.com.zxvboxtvlive.Adapter.TitleAdapter;
import zx.com.zxvboxtvlive.Adapter.ViewPagerAdapter;
import zx.com.zxvboxtvlive.Constants;
import zx.com.zxvboxtvlive.R;
import zx.com.zxvboxtvlive.ijkplayer.media.IjkVideoView;
import zx.com.zxvboxtvlive.mode.ShowPlayTimes;
import zx.com.zxvboxtvlive.mode.TvSource;
import zx.com.zxvboxtvlive.mode.WeekInfo;
import zx.com.zxvboxtvlive.presenter.EDGPresenter;
import zx.com.zxvboxtvlive.presenter.VideoViewPresenter;
import zx.com.zxvboxtvlive.utils.ImageLoadUtil;
import zx.com.zxvboxtvlive.utils.Logger;
import zx.com.zxvboxtvlive.utils.TimeUtils;
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


public class EPGActivity extends FullActivity implements TitleAction, IVideoView, IMainView, ChannelListAdapter.OnBindListener, PageViewIndicator.PageChangeListener ,
        ShowTimeListAdapter.OnItemSelectedListener{

    private final String[] titles = {
            "星期一", "星期二", "星期三", "星期四", "星期五", "星期六", "星期日"
    };

    private List<WeekInfo> mWeekInfos;

    private TitleAdapter mTitleAdapter;
    private PageViewIndicator mTitleIndicator;
    private VideoViewPresenter mVideoPresenter;
    private RecyclerView mChannelListView;
    private ChannelListAdapter mChannelListAdapter;
    private ViewPager mViewPager;

    private List<TvSource> mChannelList = new ArrayList<>();

    private EDGPresenter mEDGPresenter;

    private List<View> mShowTimesView = new ArrayList<>();
    private HashMap<String, List<ShowPlayTimes>> mListHashMap = new HashMap<>();

    private ShowTimeListAdapter mShowTimeListAdapter;

    private int mCurrentViewIndex = 0;
    private TvSource mSelectedTvsource;
    private TvSource mPlayingTvSource;

    private Map<String, Bitmap> mChannelImageCache;
    private Map<String, Target> mChannelImageTargetCache;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_epg);

        Intent intent = getIntent();
        if (intent != null) {
            List<TvSource> channelList = intent.getParcelableArrayListExtra(Constants.CHANNLE_LIST_KEY);
            mChannelList.addAll(channelList);
            mSelectedTvsource = channelList.get(0);

            mPlayingTvSource = intent.getParcelableExtra(Constants.PLAYING_CHANNEL_KEY);
        }

        mWeekInfos = TimeUtils.getCurrentWeekInfoList();
        mEDGPresenter = new EDGPresenter(this, this);
        mVideoPresenter = new VideoViewPresenter(this, this, (IjkVideoView) findViewById(R.id.videoview));

        mChannelImageCache = new HashMap<>();
        mChannelImageTargetCache = new HashMap<>();

        initView();

        mVideoPresenter.playVideo(mPlayingTvSource.getTvDataSource());

        View rootview = EPGActivity.this.getWindow().getDecorView();
        View aaa = rootview.findFocus();
        if(aaa != null)
            Logger.getLogger().d("enter " + aaa.toString());

        new Thread(new Runnable() {

            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    View rootview = EPGActivity.this.getWindow().getDecorView();
                    View aaa = rootview.findFocus();
                    if(aaa != null)
                        Logger.getLogger().d("" + aaa.toString());
                }

            }
        }).start();

    }

    private void initView() {
        mTitleIndicator = (PageViewIndicator) findViewById(R.id.indicator);
        mTitleAdapter = new TitleAdapter(this, Arrays.asList(titles));
        mTitleIndicator.setAdapter(mTitleAdapter);
        mTitleIndicator.setAction(this);

        mChannelListView = (RecyclerView) findViewById(R.id.channel_list);
        LinearLayoutManager lm = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mChannelListView.setLayoutManager(lm);
//        mChannelListView.addItemDecoration(new ChannelAdapterDecoration(this));


        mChannelListAdapter = new ChannelListAdapter(this);
        mChannelListAdapter.setData(mChannelList);
        mChannelListView.setAdapter(mChannelListAdapter);
        mChannelListAdapter.setOnBindListener(this);

        int selectId = mPlayingTvSource.getId();
        mChannelListAdapter.setChannelId(selectId);

        mChannelListView.getLayoutManager().smoothScrollToPosition(mChannelListView, null , selectId);

        mViewPager = (ViewPager) findViewById(R.id.showtimes_viewpaper);
        mViewPager.setOffscreenPageLimit(2);

        for (int i = 0; i < titles.length; i++) {
            View view = getLayoutInflater().inflate(R.layout.show_time_list, null);
            mShowTimesView.add(view);
        }

        mViewPager.setAdapter(new ViewPagerAdapter(mShowTimesView));

        mTitleIndicator.setPager(mViewPager);
        mTitleIndicator.setOnPageChangeListener(this);

        mTitleIndicator.setCurrentPosition(TimeUtils.getDayOfWeekPosition() - 1);

        mShowTimeListAdapter = new ShowTimeListAdapter(this);
        mShowTimeListAdapter.setOnItemSelectedListener(this);

        IjkVideoView videoView = (IjkVideoView) findViewById(R.id.videoview);
        videoView.getViewTreeObserver().addOnGlobalFocusChangeListener(new ViewTreeObserver.OnGlobalFocusChangeListener() {
            @Override
            public void onGlobalFocusChanged(View oldFocus, View newFocus) {
                Logger.getLogger().e("oldFocus = " + oldFocus + " newFocus =  " + newFocus);
            }
        });

        mChannelListView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) {
                    Logger.getLogger().i("channel name list view hasFoucus");
                }
            }
        });
    }

    @Override
    public void onTitleMoveFocus(View v, boolean hasFocus) {

    }

    @Override
    public void onTitleClick(View v) {

    }

    @Override
    public void showError(boolean show) {
        View view = mShowTimesView.get(mCurrentViewIndex);
        if (null != view) {
            View errorView = findViewById(R.id.load_showtime_error);
            errorView.setVisibility(show ? View.VISIBLE : View.GONE);
        }
    }

    @Override
    public void hideLoadingView() {
        showTimeshideLoadingView(mCurrentViewIndex);
    }

    @Override
    public void showLoadingView(int id) {
        showTimesLoadingView(mCurrentViewIndex);
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
        View view = mShowTimesView.get(mCurrentViewIndex);
        if (null != view) {
            final RecyclerView listView = (RecyclerView) view.findViewById(R.id.show_time_list);
            listView.setVisibility(View.VISIBLE);
            LinearLayoutManager lm = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            listView.setLayoutManager(lm);

            Logger.getLogger().e("size = " + timesList.size());
            listView.setAdapter(mShowTimeListAdapter);
            mShowTimeListAdapter.setData(timesList);
            mShowTimeListAdapter.notifyDataSetChanged();


            int week = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
//            Logger.getLogger().e("week " + week);
            mListHashMap.put(titles[mCurrentViewIndex], timesList);

            listView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if(hasFocus) {
                        if(listView.getChildCount() > 0) {
                            View view = listView.getChildAt(0);
                            view.requestFocus();
                        }
                    }
                }
            });

            listView.getLayoutManager().smoothScrollToPosition(listView, null, 0);
            showtimesInfo(timesList.get(0));
        }

    }

    @Override
    public void onBind(View view, int i) {
        TvSource source = mChannelList.get(i);
        if (source != null) {
            mSelectedTvsource = source;
            mEDGPresenter.getDayofShowTimeList(source, TimeUtils.getCurrentDate());

        }
    }


    @Override
    public void onItemSelected(int position) {
        List<ShowPlayTimes> showPlayTimesList =  mListHashMap.get(titles[mCurrentViewIndex]);
        if(showPlayTimesList != null && showPlayTimesList.size() > 0) {
            ShowPlayTimes playTimes = showPlayTimesList.get(position);
            showtimesInfo(playTimes);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        Logger.getLogger().e("onPageSelected ******");
        mCurrentViewIndex = position;
        View view = mShowTimesView.get(position);
        if (null != view) {
            List<ShowPlayTimes> mDatas = mListHashMap.get(titles[position]);
            ImageView progeressbar = (ImageView) view.findViewById(R.id.load_showtime_progress);
            AnimationDrawable animationDrawable = (AnimationDrawable) progeressbar.getBackground();
            animationDrawable.start();

            RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.show_time_list);
//            recyclerView.setAdapter(mShowTimeListAdapter);
            if (mDatas != null && mDatas.size() > 0) {
                mShowTimeListAdapter.setData(mDatas);
                recyclerView.getLayoutManager().smoothScrollToPosition(recyclerView, null, 0);
                showtimesInfo(mDatas.get(0));
            } else {
                WeekInfo weekInfo = mWeekInfos.get(position);
                Logger.getLogger().e(mSelectedTvsource.toString());
                mEDGPresenter.getDayofShowTimeList(mSelectedTvsource, weekInfo.getDate());
            }
        }
    }



    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private void showTimesLoadingView(int position) {
        View view = mShowTimesView.get(position);
        if (null != view) {
            ImageView progeressbar = (ImageView) view.findViewById(R.id.load_showtime_progress);
            AnimationDrawable animationDrawable = (AnimationDrawable) progeressbar.getBackground();
            animationDrawable.start();
        }
    }

    private void showTimeshideLoadingView(int position) {
        View view = mShowTimesView.get(position);
        if (null != view) {
            ImageView progeressbar = (ImageView) view.findViewById(R.id.load_showtime_progress);
            progeressbar.setVisibility(View.GONE);
        }
    }

    private void showtimesInfo(ShowPlayTimes playTimes) {
        if(null == playTimes) {
            return;
        }
        ImageView playTimesImg = (ImageView) findViewById(R.id.shows_img);
        TextView mShowName = (TextView) findViewById(R.id.show_name);
        TextView mShowTime = (TextView) findViewById(R.id.show_starttime);
        TextView mShowContent = (TextView) findViewById(R.id.show_content);

        final String imageURL = playTimes.getPic();

        if (mChannelImageCache.containsKey(imageURL)) {
            Bitmap image = mChannelImageCache.get(imageURL);
            playTimesImg.setImageBitmap(image);
        } else {

            if (!mChannelImageTargetCache.containsKey(imageURL)) {
                mChannelImageTargetCache.put(imageURL, new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        mChannelImageCache.put(imageURL, bitmap);
                        mChannelImageTargetCache.remove(imageURL);
                    }

                    @Override
                    public void onBitmapFailed(Drawable errorDrawable) {

                    }

                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {

                    }
                });

                ImageLoadUtil.loadImageInto(getApplicationContext(), imageURL, mChannelImageTargetCache.get(imageURL));
            }

            mShowName.setText(playTimes.getShowName());
            mShowTime.setText(playTimes.getShowStartTime());
            mShowContent.setText(playTimes.getShowContent());

        }

    }


}
