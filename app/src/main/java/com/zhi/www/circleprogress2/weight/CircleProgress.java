package com.zhi.www.circleprogress2.weight;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.zhi.www.circleprogress2.R;

/**
 * Created by Administrator on 2016/9/28.
 */
public class CircleProgress extends View {
    public static final int STOKEN = 0;
    public static final int FILL = 1;

    private Paint paint;
    private int progress;

    /**
     * 以下参数为attr.xml 文件中定义的
     * */
    private int max;                  // 进度条的最大值
    private float roundWidth;         // 圆的直径
    private float textSize;           // 文字大小
    private int style;                // 是空心圆还是实心圆
    private int roundColor;           // 圆的颜色
    private int roundProgressColor;   // 进度条的颜色
    private int textColor;            // 文字颜色
    private boolean textShow;         // 是否显示文字

    public CircleProgress(Context context) {
        this(context, null);
    }

    public CircleProgress(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleProgress(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint = new Paint();
        /*从attr.xml文件中读取对应的属性值*/
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CircleProgress);
        roundColor = typedArray.getColor(R.styleable.CircleProgress_roundColor, Color.BLUE);
        roundProgressColor = typedArray.getColor(R.styleable.CircleProgress_roundProgressColor, Color.GREEN);
        textColor = typedArray.getColor(R.styleable.CircleProgress_textColor, Color.RED);
        textShow = typedArray.getBoolean(R.styleable.CircleProgress_textShow, true);
        textSize = typedArray.getDimension(R.styleable.CircleProgress_textSize, 15);
        roundWidth = typedArray.getDimension(R.styleable.CircleProgress_roundWidth, 5);
        max = typedArray.getInteger(R.styleable.CircleProgress_max, 100);
        style = typedArray.getInt(R.styleable.CircleProgress_style, 0);
        typedArray.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int centre = getWidth() / 2;
        int radius = (int) (centre - roundWidth / 2);

        //  画圆
        paint.setColor(roundColor);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(roundWidth);
        paint.setAntiAlias(true);  // 消除锯齿
        canvas.drawCircle(centre, centre, radius, paint);
        //  画文字
        paint.setStrokeWidth(0);
        paint.setColor(textColor);
        paint.setTextSize(textSize);
        paint.setTypeface(Typeface.DEFAULT_BOLD);  // 字的样式：加粗
        Log.e("progress:", progress+"");
        int percent = (int) (((float) progress / (float) max) * 100);
        float textWidth = paint.measureText(percent + "%");
        if (textShow && 0 != percent && style == STOKEN) {
            canvas.drawText(percent + "%", centre - textWidth / 2, centre + textSize / 2, paint);
        }
        // 画圆环
        paint.setStrokeWidth(roundWidth);
        paint.setColor(roundProgressColor);
        RectF ovel = new RectF(centre - radius, centre - radius, centre + radius, centre + radius);
        switch (style) {
            case STOKEN:
                paint.setStyle(Paint.Style.STROKE);
                canvas.drawArc(ovel, 0, 360 * progress / max, false, paint);
                break;
            case FILL:
                paint.setStyle(Paint.Style.FILL_AND_STROKE);
                if (0 != progress) {
                    canvas.drawArc(ovel, 0, 360 * progress / max, true, paint);
                }
                break;
        }
    }

    public synchronized void setProgress(int progress) {
        if (progress < 0) {
            throw new IllegalArgumentException("progress should more than 0");
        }
        if (progress > max) {
            progress = max;
        }
        if (progress <= max) {
            this.progress = progress;
            postInvalidate();
        }
    }
}
