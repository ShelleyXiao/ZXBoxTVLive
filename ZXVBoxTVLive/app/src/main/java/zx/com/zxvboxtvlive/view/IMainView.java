package zx.com.zxvboxtvlive.view;

import java.util.List;

import zx.com.zxvboxtvlive.mode.ShowPlayTimes;
import zx.com.zxvboxtvlive.mode.TvSource;

/**
 * User: ShaudXiao
 * Date: 2017-03-31
 * Time: 14:51
 * Company: zx
 * Description:
 * FIXME
 */


public interface IMainView {

    void showError(boolean show);

    void hideLoadingView();

    void showLoadingView(int id);

    void updateChannleAdapter(List<TvSource> data);

    void checkNextSource();

    void updateShowInfo();

    void updateShowInfoUI(List<ShowPlayTimes> timesList);

}
