package zx.com.zxvboxtvlive.activity;

import android.os.Bundle;

import com.fernandocejas.frodo.annotation.RxLogSubscriber;
import com.trello.rxlifecycle.android.ActivityEvent;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import zx.com.zxvboxtvlive.R;
import zx.com.zxvboxtvlive.mode.TvSource;
import zx.com.zxvboxtvlive.utils.JsoupUtils;
import zx.com.zxvboxtvlive.utils.Logger;


public class MainActivity extends RxAppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initData();
    }

    private void initData() {
        Observable.create(new Observable.OnSubscribe<Document>() {

            @Override
            public void call(Subscriber<? super Document> subscriber) {
                try {
                    subscriber.onNext(Jsoup.connect(JsoupUtils.BASE_URL).timeout(5000).userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6").get());
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
                return JsoupUtils.getTVSourceList(document);
            }
        }).compose(this.<List<TvSource>>bindUntilEvent(ActivityEvent.DESTROY))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new TvSouceSubcriber()); 调试用
                .subscribe(new Subscriber<List<TvSource>>() {
                    @Override
                    public void onCompleted() {
                        Logger.getLogger().d(" onCompleted");
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
                    }
                });

    }

    @RxLogSubscriber
    class TvSouceSubcriber extends Subscriber<List<TvSource>> {
        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {

        }

        @Override
        public void onNext(List<TvSource> tvSources) {

        }
    }
}
