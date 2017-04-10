package zx.com.zxvboxtvlive.Adapter;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ItemDecoration;
import android.view.View;

/**
 * User: ShaudXiao
 * Date: 2017-04-10
 * Time: 10:39
 * Company: zx
 * Description:
 * FIXME
 */


public class ChannelAdapterDecoration extends ItemDecoration {

    private Context mContext;
    private Drawable mDivider;


    public static int[] ATTRS = new int[] {
            android.R.attr.listDivider
    };

    public ChannelAdapterDecoration(Context context) {
        mContext = context;
        final TypedArray ta = context.obtainStyledAttributes(ATTRS);
        this.mDivider = ta.getDrawable(0);
        ta.recycle();

    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();
        final int count = parent.getChildCount();
        for(int i = 0; i < count; i++) {
            final View child = parent.getChildAt(i);

            final RecyclerView.LayoutParams lp = (RecyclerView.LayoutParams) child.getLayoutParams();
            final int top = child.getBottom() + lp.bottomMargin;
            final int bottom = top + mDivider.getIntrinsicHeight();

            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {

    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.set(0, 0, 0, mDivider.getIntrinsicHeight());
    }


}
