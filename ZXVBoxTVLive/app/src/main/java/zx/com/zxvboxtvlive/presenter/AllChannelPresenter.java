package zx.com.zxvboxtvlive.presenter;

import android.app.Activity;

import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import zx.com.zxvboxtvlive.Constants;
import zx.com.zxvboxtvlive.mode.TvSource;
import zx.com.zxvboxtvlive.utils.JsoupUtils;
import zx.com.zxvboxtvlive.utils.Logger;
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

}
