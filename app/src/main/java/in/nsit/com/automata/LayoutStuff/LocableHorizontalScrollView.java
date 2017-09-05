package in.nsit.com.automata.LayoutStuff;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.HorizontalScrollView;

/**
 * Created by ( Jatin Bansal ) on 04-02-2017.
 */

public class LocableHorizontalScrollView extends HorizontalScrollView {

    private boolean mScrollable = true;

    public LocableHorizontalScrollView(Context context) {
        super(context);
    }

    public LocableHorizontalScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LocableHorizontalScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    public void setScrollingEnabled(boolean enabled) {
        mScrollable = enabled;
    }

    public boolean isScrollable() {
        return mScrollable;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // if we can scroll pass the event to the superclass
                if (mScrollable) return super.onTouchEvent(ev);
                // only continue to handle the touch event if scrolling enabled
                return mScrollable; // mScrollable is always false at this point
            default:
                return super.onTouchEvent(ev);
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        // Don't do anything with intercepted touch events if
        // we are not scrollable
        if (!mScrollable) return false;
        else return super.onInterceptTouchEvent(ev);
    }

}
