package com.gosuncn.core.widget;

import android.content.Context;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;

/**
 * 扩展RecyclerView
 * 增加滑动到底部加载更多监听器
 * 增加无item时显示提示视图,通过RecyclerViewExtend.setEmptyView()设置
 */

public class RecyclerViewExtend extends RecyclerView {
    private static final String TAG = "RecyclerViewExtend";
    /**
     * 只是滑到底部就触发加载监听器
     */
    public static final int LOADINGMODE_SLIDE_TO_BOTTOM = 1;
    /**
     * 滑到底部并且是上拉操作才触发加载监听器
     */
    public static final int LOADINGMODE_SLIDE_TO_BOTTOM_AND_PULL_UP = 2;

    @IntDef({LOADINGMODE_SLIDE_TO_BOTTOM, LOADINGMODE_SLIDE_TO_BOTTOM_AND_PULL_UP})
    public @interface LoadingMode {
    }

    public interface OnLoadingListener {
        void onLoading();
    }

    private OnLoadingListener onLoadingListener;
    /**
     * 是否正在加载，防止多次加载
     */
    private boolean isLoading = false;
    private View emptyView;
    private AdapterDataObserver observer;
    /**
     * 是否滚到底部
     */
    private boolean isBottom = false;
    /**
     * 判断是否上拉的距离
     */
    private int mTouchSlop;

    private int loadingMode = LOADINGMODE_SLIDE_TO_BOTTOM;

    /**
     * 按下时的y坐标
     */
    private int mYDown;
    /**
     * 抬起时的y坐标, 与mYDown一起用于滑动到底部时判断是上拉还是下拉
     */
    private int mLastY;
    public RecyclerViewExtend(Context context) {
        this(context, null, 0);
    }

    public RecyclerViewExtend(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RecyclerViewExtend(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        if (mTouchSlop < 50) {
            mTouchSlop = 80;
        }
        init();
    }

    /**
     * 设置加载监听器
     *
     * @param onLoadingListener
     */
    public void setOnLoadingListener(@NonNull OnLoadingListener onLoadingListener) {
        this.onLoadingListener = onLoadingListener;
    }

    @Override
    public void setAdapter(Adapter adapter) {
        final Adapter oldAdapter = getAdapter();
        if (oldAdapter != null) {
            oldAdapter.unregisterAdapterDataObserver(observer);
        }
        super.setAdapter(adapter);
        if (adapter != null) {
            adapter.registerAdapterDataObserver(observer);
        }
        setVisibilityByItemCount();
    }

    /**
     * 设置一个视图，当item项为0时显示此视图
     *
     * @param emptyView
     */
    public void setEmptyView(View emptyView) {
        this.emptyView = emptyView;
        setVisibilityByItemCount();
    }

    /**
     * 设置触发加载的模式
     * @param mode
     */
    public void setLoadingMode(@LoadingMode int mode) {
        this.loadingMode = mode;
    }

    /**
     * 设置是否正在加载，即每次请求加载后无论成功与否都要置为false
     * @param bool
     */
    public void setLoading(boolean bool) {
        this.isLoading = bool;
    }

    /**
     * 是否在加载，如果在加载则会屏蔽滑到底部事件，防止多次加载
     * @return
     */
    public boolean  getLoading() {
        return this.isLoading ;
    }

    private void init() {
        addOnScrollListener();
        initAdapterDataObserver();
    }

    private void initAdapterDataObserver() {
        //监听item子项个数的变化，以便在变化时设置emptyView的可见性
        observer = new AdapterDataObserver() {
            @Override
            public void onChanged() {
                setVisibilityByItemCount();
            }

            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                Log.i(TAG, "onItemRangeInserted" + itemCount);
                setVisibilityByItemCount();
            }

            @Override
            public void onItemRangeRemoved(int positionStart, int itemCount) {
                setVisibilityByItemCount();
            }
        };
    }

    private void addOnScrollListener() {
        this.addOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                // RecyclerView.canScrollVertically(1)的值表示是否能向上滚动，false表示已经滚动到底部
                //RecyclerView.canScrollVertically(-1)的值表示是否能向下滚动，false表示已经滚动到顶部
                if (computeVerticalScrollExtent() < computeVerticalScrollRange()) {
                    if (!canScrollVertically(1)) {
                        isBottom = true;
                        if (loadingMode == LOADINGMODE_SLIDE_TO_BOTTOM) {
                            if (onLoadingListener != null&&!isLoading) {
                                isLoading=true;
                                onLoadingListener.onLoading();
                            }
                        }
                        Log.i(TAG, "已经滚到底部");

                    } else {
                        isBottom = false;
                    }
                } else {
                    isBottom = false;
                }
            }
        });
    }

    /**
     * 根据item个数设置视图的可见性
     */
    private void setVisibilityByItemCount() {
        if (emptyView != null && getAdapter() != null) {
            final boolean emptyViewVisible = getAdapter().getItemCount() == 0;
            emptyView.setVisibility(emptyViewVisible ? VISIBLE : GONE);
            setVisibility(emptyViewVisible ? GONE : VISIBLE);
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        final int action = event.getAction();

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                // 按下
                mYDown = (int) event.getRawY();
                break;

            case MotionEvent.ACTION_MOVE:
                // 移动
                mLastY = (int) event.getRawY();
                break;

            case MotionEvent.ACTION_UP:
                mLastY = (int) event.getRawY();
                // 抬起
                if (canLoad()) {
                    Log.i(TAG, "已经滚到底部,且是上拉操作");
                    if (loadingMode == LOADINGMODE_SLIDE_TO_BOTTOM_AND_PULL_UP) {
                        if (onLoadingListener != null&&!isLoading) {
                            isLoading=true;
                            onLoadingListener.onLoading();
                        }

                    }
                }

                break;
            default:
                break;
        }

        return super.dispatchTouchEvent(event);
    }

    private boolean canLoad() {
        return isBottom && isPullUp();
    }

    /**
     * 是否是上拉操作
     *
     * @return
     */
    private boolean isPullUp() {
        return (mYDown - mLastY) >= mTouchSlop;
    }


}
