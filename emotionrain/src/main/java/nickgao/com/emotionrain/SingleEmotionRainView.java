package nickgao.com.emotionrain;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import java.util.Random;

public class SingleEmotionRainView extends View {

    private boolean isRaining = false;

    //表情雨图标的高度和宽度
    private float mEmotionHeight;
    private float mEmotionWidth;

    private Random mRandom;
    private Matrix matrix;
    private Paint mPaint;
    private Context mContext;

    //数据源
    private EmotionBean mEmotionBean = new EmotionBean();

    public SingleEmotionRainView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }

    private void init() {
        mRandom = new Random();
        setVisibility(GONE);
        matrix = new Matrix();
        initPaint();
    }

    private void initPaint() {
        mPaint = new Paint();
        //View当中包含有大量的图形，会出现锯齿
        mPaint.setAntiAlias(true);
        //对位图进行滤波处理
        mPaint.setFilterBitmap(true);
        mPaint.setDither(true);
        mPaint.setColor(getResources().getColor(android.R.color.holo_red_light));

    }


    public void start(final Bitmap bitmap) {
        if(bitmap == null) {
            return;
        }
        stop();
        setVisibility(View.VISIBLE);

        initAndResetData(bitmap);
        isRaining = true;
        invalidate();

    }

    public void stop() {
        isRaining = false;
        setVisibility(GONE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if(!isRaining || mEmotionBean == null) {
            return;
        }

        EmotionBean emotion = mEmotionBean;
        Bitmap bitmap = emotion.bitmap;

        if(bitmap.isRecycled() || isOutOfBottomBound()) {
            return;
        }

        matrix.reset();
        float heightScale = mEmotionHeight / bitmap.getHeight();
        float widthScale = mEmotionWidth / bitmap.getWidth();

        matrix.setScale(widthScale,heightScale);

        emotion.x = emotion.x + emotion.velocityX;
        emotion.y = emotion.y + emotion.velocityY;

        matrix.postTranslate(emotion.x, emotion.y);
        canvas.drawBitmap(bitmap,matrix,mPaint);

        postInvalidate();
    }

    private boolean isOutOfBottomBound() {
        return mEmotionBean.y > getHeight();
    }

    /**
     * 初始化数据源
     */
    private void initAndResetData(Bitmap bitmap) {

        this.mEmotionHeight = this.mEmotionWidth = dp2px(mContext,50);

        int maxDuration = 2000;

        EmotionBean bean = new EmotionBean();
        bean.bitmap = bitmap;
        bean.x = mRandom.nextInt(getWidth());
        bean.y = (int)(-Math.ceil(mEmotionHeight));

        float duration = mRandom.nextInt(500)+maxDuration;
        bean.velocityY = (int)(getHeight()*16 / duration);
        bean.velocityX = Math.round(mRandom.nextFloat());
        mEmotionBean = bean;
    }

    public static int dp2px(Context context,float dpValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int)(dpValue*scale+0.5f);
    }



}
