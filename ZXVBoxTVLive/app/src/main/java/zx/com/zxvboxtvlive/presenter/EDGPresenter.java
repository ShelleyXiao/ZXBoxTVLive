package zx.com.zxvboxtvlive.presenter;

import android.content.Context;

import zx.com.zxvboxtvlive.mode.TvSource;
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
    private IMainView  mMainView;

    public EDGPresenter(Context context, IMainView mainView) {
        mContext = context;
        mMainView = mainView;
    }

    public void updateShowInfo(TvSource source) {

    }
}
