package com.example.marqueeview;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import java.lang.ref.WeakReference;

/**
 * Created by asus on 2019/4/22.
 */

public class MarqueeView extends FrameLayout {

    public static final int MODE_CUSTOM = 1;
    private RecyclerView rv_recycler;
    private MarqueeViewHandle marqueeViewHandle;
    private long interval = 1000; //間隔時間
    private int scrollHeight = 0;

    public MarqueeView(@NonNull Context context) {
        this(context, null);
    }

    public MarqueeView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MarqueeView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View.inflate(context, R.layout.marquee_layout, this);
        init(context);
    }

    private void init(Context context) {
        rv_recycler = findViewById(R.id.rv_recycler);
        rv_recycler.setLayoutManager(new LinearLayoutManager(context));
        rv_recycler.setHasFixedSize(true);
        marqueeViewHandle = new MarqueeViewHandle(this);
    }

    public void setInterval(long interval) {
        this.interval = interval;
    }

    public int getScrollHeight() {
        return scrollHeight;
    }

    public void setScrollHeight(int scrollHeight) {
        this.scrollHeight = scrollHeight;
    }

    private void scroll() {
        rv_recycler.smoothScrollBy(0, scrollHeight);
        marqueeViewHandle.sendEmptyMessageDelayed(0, interval);
        //如果未設置高度默認人第一個item的高度
        if (getScrollHeight() == 0 && rv_recycler.getAdapter().getItemCount() > 0) {
            setScrollHeight(rv_recycler.getChildAt(0).getMeasuredHeight());
        }
    }

    public void setAdapter(@NonNull RecyclerView.Adapter adapter) {
        if (adapter == null) {
            throw new NullPointerException("adapter is null");
        }
        rv_recycler.setAdapter(adapter);
        if (adapter.getItemCount() > 0) {
            marqueeViewHandle.sendEmptyMessageDelayed(0, interval);
        }
    }

    private static class MarqueeViewHandle extends Handler {
        private WeakReference<MarqueeView> marqueeView;

        public MarqueeViewHandle(MarqueeView marqueeView) {
            this.marqueeView = new WeakReference<>(marqueeView);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            MarqueeView view = this.marqueeView.get();
            if (view != null) {
                view.scroll();  //自定義滾動高度
            }
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_MOVE) {
            return true;//禁止滑動事件攔截
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        marqueeViewHandle.removeCallbacksAndMessages(null);
    }

}
