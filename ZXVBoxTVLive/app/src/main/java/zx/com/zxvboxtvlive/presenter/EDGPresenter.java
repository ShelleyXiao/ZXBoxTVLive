package zx.com.zxvboxtvlive.presenter;

import android.content.Context;
import android.text.TextUtils;

import com.fernandocejas.frodo.annotation.RxLogObservable;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import zx.com.zxvboxtvlive.Constants;
import zx.com.zxvboxtvlive.data.sqlite.ShowTimesTableColumn;
import zx.com.zxvboxtvlive.data.sqlite.SqlShowTimeTableManager;
import zx.com.zxvboxtvlive.mode.ShowPlayTimes;
import zx.com.zxvboxtvlive.mode.TvSource;
import zx.com.zxvboxtvlive.utils.JsoupUtils;
import zx.com.zxvboxtvlive.utils.Logger;
import zx.com.zxvboxtvlive.view.IMainView;

/**
 * User: ShaudXiao
 * Date: 2017-04-05
 * Time: 10:14
 * Company: zx
 * Description:
 * FIXME
 */


public class EDGPresenter extends Presenter {

    private Context mContext;
    private IMainView mMainView;

    private int playingIndexId = -1;
    private List<ShowPlayTimes> mPlayingTimes = new ArrayList<>();

    public EDGPresenter(Context context, IMainView mainView) {
        mContext = context;
        mMainView = mainView;
    }

    @RxLogObservable
    public void updateShowInfo(final TvSource source) {
        final String nameLog = source.getPinyingLog();
        if (TextUtils.isEmpty(nameLog)) {
            return;
        }

        final String name = source.getTvName();
        final String time = SqlShowTimeTableManager.getInstance().queryShowTableUpdateTime(name);
//        Logger.getLogger().i("date time " + time.toString());
        if (time == null || !compareUpdateTime(time)) {

            Observable.create(new Observable.OnSubscribe<List<ShowPlayTimes>>() {
                @Override
                public void call(Subscriber<? super List<ShowPlayTimes>> subscriber) {
                    List<ShowPlayTimes> times;
                    if (nameLog.contains("cctv")) {
                        times = JsoupUtils.getShowPlayTimeList(name, nameLog + Constants.BASE_EPG_YANSHI);
                    } else {
                        times = JsoupUtils.getShowPlayTimeList(name, nameLog + Constants.BASE_EPG_WEISHI);
                    }

                    Logger.getLogger().i("times: " + times.get(0).toString());
                    subscriber.onNext(times);
                    subscriber.onCompleted();
                }
            }).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<List<ShowPlayTimes>>() {

                        @Override
                        public void onCompleted() {
                            if (time == null) {
                                SqlShowTimeTableManager.getInstance().insertShowTableUpdateTime(name);
                            } else {
                                SqlShowTimeTableManager.getInstance().uupdateShowtableUpdateTime(name);
                            }
                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onNext(List<ShowPlayTimes> timesList) {
//                            for (ShowPlayTimes time : timesList) {
//                                Logger.getLogger().e("next: " + time.toString());
//                            }
                            if (time == null) {
                                SqlShowTimeTableManager.getInstance().addShowTimeTable(timesList);
                            } else {
                                int count = SqlShowTimeTableManager.getInstance().delShowPlayTimes(ShowTimesTableColumn.CHANNEL_NAME + "=?", new String[]{name});
                                Logger.getLogger().i("del item count = " + count);
                                SqlShowTimeTableManager.getInstance().addShowTimeTable(timesList);
                            }
                            mMainView.updateShowInfoUI(getPlayingShows(timesList));
                        }
                    });
        } else {
            List<ShowPlayTimes> times = SqlShowTimeTableManager.getInstance()
                    .queryShowPlayTimes(null, ShowTimesTableColumn.CHANNEL_NAME + "=?", new String[]{name}, null, null, null);
            for (ShowPlayTimes s : times) {
                Logger.getLogger().i("db : " + s.toString());
            }

            mMainView.updateShowInfoUI(getPlayingShows(times));
        }


    }

    private boolean compareUpdateTime(String date) {
        if (date == null) {
            return false;
        }
        String[] dateArr = date.split("-");
        int month = Integer.valueOf(dateArr[1]);
        int day = Integer.valueOf(dateArr[2]);
        Logger.getLogger().i("date = " + date + " Month " + month + " day = " + day);
        int currentMonth = Calendar.getInstance().get(Calendar.MONTH) + 1;
        int currentDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        Logger.getLogger().i("currentMonth = " + currentMonth + " currentDay =  " + currentDay);
        return month == currentMonth && day == currentDay;
    }

    private List<ShowPlayTimes> getPlayingShows(List<ShowPlayTimes> timesList) {
        List<ShowPlayTimes> playTimes = new ArrayList<>();
        int curHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        int curMinute = Calendar.getInstance().get(Calendar.MINUTE);
        int index = -1;
        for (int i = 0; i < timesList.size(); i++) {

            if (timesList.get(i).isPlaying() == true) {
                ShowPlayTimes time = new ShowPlayTimes(timesList.get(i));
                playTimes.add(time);
                index = i;
                break;
            }
        }
        if (index < timesList.size() - 1) {
            ShowPlayTimes time = new ShowPlayTimes(timesList.get(++index));
            playTimes.add(time);
        }
        for (ShowPlayTimes times : playTimes) {
            if(times.getShowStartTime() == null) {
                ShowPlayTimes time = mPlayingTimes.get(1);
                if(null != time) {
                    times.setShowStartTime(time.getShowStartTime());
                }
            }
            Logger.getLogger().d("playing: " + times.toString());
        }

        mPlayingTimes.clear();
        mPlayingTimes.addAll(playTimes);

        return playTimes;
    }
}
