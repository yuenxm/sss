package com.blueeyescloud.ext.view;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;

import com.blueeyescloud.ext.R;

/**
 * 标题栏
 */
public class TitleBar extends RelativeLayout {
    private ImageView mBackIv;
    private TextView mTitleTv;
    private OnBackClickListener mOnBackClickListener;
    private int mTitleTextResId;

    private final OnBackClickListener mDefaultOnBackListener = new OnBackClickListener() {
        @Override
        public void onBackClick() {
            ((Activity)getContext()).finish();
        }
    };

    public interface OnBackClickListener{
        void onBackClick();
    }

    public TitleBar(Context context) {
        super(context);
        init(context);
    }

    public TitleBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        handleAttributes(context, attrs);
        init(context);
    }

    private void handleAttributes(Context context, AttributeSet attrs) {
        TypedArray styledAttrs = context.obtainStyledAttributes(attrs, R.styleable.TitleBar);
        mTitleTextResId = styledAttrs.getResourceId(R.styleable.TitleBar_title, R.string.app_name);
        styledAttrs.recycle();
    }

    private void init(Context context){
        View view = LayoutInflater.from(context).inflate(R.layout.layout_title_bar, this, true);
        mBackIv = view.findViewById(R.id.ibtn_titlebar_back);
        mBackIv.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(mOnBackClickListener != null){
                    mOnBackClickListener.onBackClick();
                }
            }
        });
        mTitleTv = findViewById(R.id.tv_titlebar_title);
        mTitleTv.setText(mTitleTextResId);
        setBackgroundResource(R.color.primary_color);

        mOnBackClickListener = mDefaultOnBackListener;


//        setup();
    }

    public class ClickHandler{
        public void onClick(View view){
            if(mOnBackClickListener != null){
                mOnBackClickListener.onBackClick();
            }
        }
    }

    public void setOnBackClickListener(OnBackClickListener listener){
        mOnBackClickListener = listener;
    }

    public void setTitle(String title){
        mTitleTv.setText(title);
    }

    @BindingAdapter("onBack")
    public static void onBackClick(TitleBar titleBar, OnBackClickListener listener){
        titleBar.setOnBackClickListener(listener);
    }

    @BindingAdapter("title")
    public static void setTitle(TitleBar titleBar, String title){
        titleBar.setTitle(title);
    }

    public void setup() {
        int compatPadingTop = 0;
        // android 4.4以上将Toolbar添加状态栏高度的上边距，沉浸到状态栏下方
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            compatPadingTop = getStatusBarHeight();
        }
        Log.e("tag_ss", "this.getLayoutParams().height = " + this.getLayoutParams());
//        this.getLayoutParams().height = this.getLayoutParams().height + compatPadingTop;
//        setLayoutParams();
//        this.setPadding(getPaddingLeft(), getPaddingTop() + compatPadingTop, getPaddingRight(), getPaddingBottom());
    }

    public int getStatusBarHeight() {
        int statusBarHeight = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusBarHeight = getResources().getDimensionPixelSize(resourceId);
        }
        Log.d("TitleBar", "状态栏高度：" + px2dp(statusBarHeight) + "dp");
        return statusBarHeight;
    }

    public float px2dp(float pxVal) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (pxVal / scale);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        if(getLayoutParams() != null) {
            Log.e("tag_ss", "height = " + getLayoutParams().height);
        }else{
            Log.e("tag_ss", "height = null" );
        }

//        if(statusbar == -1){
//            statusbar = getStatusBarHeight();
//            ViewGroup.LayoutParams layoutParams = (ViewGroup.LayoutParams)getLayoutParams();
//            layoutParams.height = layoutParams.height + getStatusBarHeight();
//            Log.e("tag_ss", "new paddingtop - " + (getPaddingTop() + statusbar));
//            this.setPadding(getPaddingLeft(), -(getPaddingTop() + statusbar), getPaddingRight(), getPaddingBottom());
//            setLayoutParams(layoutParams);
//        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if(getLayoutParams() != null) {
            Log.e("tag_ss", "height2222 = " + getLayoutParams().height);
//            if(statusbar == -1){
//                statusbar = getStatusBarHeight();
//                ViewGroup.LayoutParams layoutParams = (ViewGroup.LayoutParams)getLayoutParams();
//                layoutParams.height = layoutParams.height + getStatusBarHeight();
//                Log.e("tag_ss", "new paddingtop - " + (getPaddingTop() + statusbar));
//                this.setPadding(getPaddingLeft(), getPaddingTop() + statusbar, getPaddingRight(), getPaddingBottom());
//                setLayoutParams(layoutParams);
//            }

        }else{
            Log.e("tag_ss", "height2222 = null" );
        }
    }
    private int statusbar = -1;
}
