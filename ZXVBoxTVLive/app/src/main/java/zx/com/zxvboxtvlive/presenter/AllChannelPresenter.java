package zx.com.zxvboxtvlive.presenter;

import android.app.Activity;
import android.content.res.AssetManager;
import android.text.TextUtils;

import org.jsoup.nodes.Document;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import zx.com.zxvboxtvlive.App;
import zx.com.zxvboxtvlive.Constants;
import zx.com.zxvboxtvlive.data.tvSource.TvSourceSet;
import zx.com.zxvboxtvlive.mode.TvSource;
import zx.com.zxvboxtvlive.utils.JsoupUtils;
import zx.com.zxvboxtvlive.utils.Logger;
import zx.com.zxvboxtvlive.utils.PinyinUtils;
import zx.com.zxvboxtvlive.view.IMainView;

/**
 * User: ShaudXiao
 * Date: 2017-03-31
 * Time: 14:49
 * Company: zx
 * Description:
 * FIXME
 */


public class AllChannelPresenter extends Presenter {

    private Activity mActivity;
    private IMainView mAllChannelView;

    public AllChannelPresenter(Activity activity, IMainView view) {
        mActivity = activity;
        mAllChannelView = view;
    }

    public void updateChannelData() {
        Observable.create(new Observable.OnSubscribe<Document>() {

            @Override
            public void call(Subscriber<? super Document> subscriber) {
                try {
//                    subscriber.onNext(Jsoup.connect(JsoupUtils.BASE_URL).timeout(5000).userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6").get());
                    subscriber.onNext(JsoupUtils.getHtmlDocument(Constants.BASE_CHANNEL_URL, ""));
                    subscriber.onCompleted();
                } catch (IOException e) {
                    e.printStackTrace();
                    Logger.getLogger().e("Jsoup connect error");
//                    mAllChannelView.hideLoadingView();
//                    mAllChannelView.showError(true);

                }

            }
        }).map(new Func1<Document, List<TvSource>>() {

            @Override
            public List<TvSource> call(Document document) {
//                Logger.getLogger().d(document.toString());
//                JsoupUtils.getShowPlayTimeList();
//                JsoupUtils.getShowPlayTimeList("/epg/cctv-5?class=yangshi");
                return JsoupUtils.getTVSourceList(document);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new TvSouceSubcriber()); 调试用
                .subscribe(new Subscriber<List<TvSource>>() {
                    @Override
                    public void onCompleted() {
                        Logger.getLogger().d(" onCompleted");
                        mAllChannelView.hideLoadingView();

                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.getLogger().d(" onError " + e.getMessage());
                        mAllChannelView.hideLoadingView();
                        mAllChannelView.showError(true);

                        mAllChannelView.checkNextSource();
                    }

                    @Override
                    public void onNext(List<TvSource> tvSources) {
                        for(TvSource source : tvSources) {
                            Logger.getLogger().i(source.toString());
                        }
                        mAllChannelView.updateChannleAdapter(tvSources);
                    }
                });


    }

    public void updateChannelDataOther() {
        Observable.create(new Observable.OnSubscribe<List<TvSource>>() {
            @Override
            public void call(Subscriber<? super List<TvSource>> subscriber) {

                List<TvSource> sources = new ArrayList<TvSource>();

                if(TvSourceSet.tvSource1.size() == 0) {
                    BufferedReader reader = null;
                    try {
                        AssetManager am = App.getInstance().getAssets();
                        InputStream is = am.open("tvlist.txt", AssetManager.ACCESS_BUFFER);
                        reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                        String lineBuf = null;
                        while ((lineBuf = reader.readLine()) != null) {
                            if(!TextUtils.isEmpty(lineBuf)) {
//                            Logger.getLogger().i("source : " + lineBuf);
                                String[] channel = lineBuf.split(",");
//                            Logger.getLogger().i("size " + channel.length +
//                            "channel[0] = " + channel[0]);
                                TvSource source = new TvSource();
                                source.setTvName(channel[0]);
                                source.setTvDataSource(channel[1]);
                                source.setPinyingLog(PinyinUtils.processTVPinyinLog(channel[0]));
                                sources.add(source);
                                Logger.getLogger().i(source.toString());
                            }
                        }
                        TvSourceSet.tvSource1.addAll(sources);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {

                        try {
                            if (null != reader) {
                                reader.close();
                            }
                        } catch (IOException e) {

                        }

                    }
                } else {
                    sources.addAll(TvSourceSet.tvSource1);
                }

                subscriber.onNext(sources);
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Subscriber<List<TvSource>>() {

            @Override
            public void onCompleted() {
                Logger.getLogger().d(" onCompleted");
                mAllChannelView.hideLoadingView();
            }

            @Override
            public void onError(Throwable e) {
                Logger.getLogger().d(" onError " + e.getMessage());
                mAllChannelView.hideLoadingView();
                mAllChannelView.showError(true);
            }

            @Override
            public void onNext(List<TvSource> tvSources) {
//                for(TvSource source : tvSources) {
//                    Logger.getLogger().i(source.toString());
//                }
                mAllChannelView.updateChannleAdapter(tvSources);
            }
        });
    }

}
