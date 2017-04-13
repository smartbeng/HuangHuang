package com.lansum.lansumhh.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lansum.lansumhh.R;

/**
 * Created by MaiBenBen on 2017/4/12.
 */

public class CommItemView extends RelativeLayout {
    private String leftTextContent;
    private String rightTextContent;
    private int leftTextSize;
    private int rightTextSize;
    private FrameLayout leftContainer;
    private FrameLayout rightContainer;
    private TextView leftText;
    private TextView rightText;
    private boolean isDividerVisibility;
    private View divider;

    public CommItemView(Context context, AttributeSet attrs) {
//        super(context, attrs);
        this(context, attrs, R.attr.CommItemViewAttr);
    }

    /**
     * @param context
     * @param attrs
     * @param defStyleAttr 引用属性的名称
     */
    public CommItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        // 默认加载的布局文件
        View.inflate(context, R.layout.comm_layout_item_view, this);

        // 将属性文件加载成 属性对象
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CommItemView,
                defStyleAttr, R.style.CommItemViewStyle);
        // 左右显示图片控件
        ImageView lImage = null;
        ImageView rImage = null;

        // 将属性文件的值赋给变量
        leftTextContent = a.getString(R.styleable.CommItemView_left_text);
        rightTextContent = a.getString(R.styleable.CommItemView_right_text);
        leftTextSize = a.getInt(R.styleable.CommItemView_left_text_size, 16);
        rightTextSize = a.getInt(R.styleable.CommItemView_right_text_size, 14);
        isDividerVisibility = a.getBoolean(R.styleable.CommItemView_is_divider_visibility, false);
        int leftColor = a.getColor(R.styleable.CommItemView_left_text_color, 0xff000000);
        int rightColor = a.getColor(R.styleable.CommItemView_right_text_color, 0xff8f8f8f);

        Drawable l = a.getDrawable(R.styleable.CommItemView_left_img_src);
        Drawable r = a.getDrawable(R.styleable.CommItemView_right_img_src);

        if (l != null) {
            lImage = new ImageView(context);
            lImage.setImageDrawable(l);
            lImage.setScaleType(ImageView.ScaleType.FIT_CENTER);
        }
        if (r != null) {
            rImage = new ImageView(context);
            rImage.setImageDrawable(r);
            rImage.setScaleType(ImageView.ScaleType.FIT_CENTER);
        }

        // 资源回收
        a.recycle();

        // 获取控件对象
        leftText = (TextView) findViewById(R.id.tv_left_text);
        rightText = (TextView) findViewById(R.id.tv_right_text);
        leftContainer = (FrameLayout) findViewById(R.id.fl_left_container);
        rightContainer = (FrameLayout) findViewById(R.id.fl_right_container);
        divider = findViewById(R.id.v_divider_bottom);

        // 判断是否显示分割线
        if (isDividerVisibility) {
            divider.setVisibility(VISIBLE);
        }
        // 给控件属性赋值
        leftText.setText(leftTextContent);
        rightText.setText(rightTextContent);
        leftText.setTextSize(leftTextSize);
        rightText.setTextSize(rightTextSize);
        leftText.setTextColor(leftColor);
        rightText.setTextColor(rightColor);

        // 将像素值转换成 dp
        int padding = px2dp(context, 8);

        // 像左右容器添加 ImageView 以及边距
        if (lImage != null) {
            leftContainer.addView(lImage);
            leftContainer.setPadding(padding, 0, 0, 0);
        }
        if (rImage != null) {
            rightContainer.addView(rImage);
            rightContainer.setPadding(0, 0, padding, 0);
        }
    }

    /**
     * 将像素值转换成 dp 值
     *
     * @param context
     * @param value
     * @return
     */
    private int px2dp(Context context, int value) {
        return (int) (value * context.getResources().getDisplayMetrics().density + 0.5f);
    }

    /**
     * 设置左边容器显示的控件
     *
     * @param v
     */
    public void setLeftView(View v) {
        cleanLeftView();
        leftContainer.addView(v);
    }

    public void setRightView(View v) {
        cleanRightView();
        rightContainer.addView(v);
    }


    /**
     * 清除左边容器的字控件
     */
    public void cleanLeftView() {
        if (leftContainer.getChildCount() > 0) {
            leftContainer.removeAllViews();
        }
    }


    public void cleanRightView() {
        if (rightContainer.getChildCount() > 0) {
            rightContainer.removeAllViews();
        }
    }


    public void setLeftText(CharSequence text) {
        leftText.setText(text);
    }

    public void setRightText(CharSequence text) {
        rightText.setText(text);
    }

    public void setLeftTextColor(int color) {
        leftText.setTextColor(color);
    }

    public void setRightTextColor(int color) {
        rightText.setTextColor(color);
    }

    public void setRightTextSize(float size) {
        rightText.setTextSize(size);
    }

    public void setLeftTextSize(float size) {
        leftText.setTextSize(size);
    }

    public FrameLayout getLeftContainer() {
        return leftContainer;
    }

    public FrameLayout getRightContainer() {
        return rightContainer;
    }

    /**
     * 控制分割线是否显示
     *
     * @param visible
     */
    public void setDividerVisible(Boolean visible) {
        divider.setVisibility(visible ? VISIBLE : INVISIBLE);
    }
}
