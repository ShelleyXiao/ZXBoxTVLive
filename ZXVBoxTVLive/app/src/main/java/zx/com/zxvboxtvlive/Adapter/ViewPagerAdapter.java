package zx.com.zxvboxtvlive.Adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * User: ShaudXiao
 * Date: 2017-04-10
 * Time: 17:03
 * Company: zx
 * Description:
 * FIXME
 */


public class ViewPagerAdapter extends PagerAdapter {

    private List<View> mViews;

    public ViewPagerAdapter(List<View> views) {
        mViews = views;
    }

    @Override
    public View instantiateItem(ViewGroup container, int position) {
        container.addView(mViews.get(position));
        return mViews.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(mViews.get(position));
    }

    @Override
    public int getCount() {
        if (mViews != null && mViews.size() > 0) {
            return mViews.size();
        } else {
            return 0;
        }
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
