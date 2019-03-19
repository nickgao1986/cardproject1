package nickgao.com.emotionrain;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class EmotionRainView extends View {

    private boolean isRaining = false;

    //表情雨图标的高度和宽度
    private float mEmotionHeight;
    private float mEmotionWidth;

    private Random mRandom;
    private Matrix matrix;
    private Paint mPaint;
    //开始下表情雨的时间
    private long mStartTimeStamp;
    private Context mContext;

    //数据源
    private List<EmotionBean> mEmotionList = new ArrayList<>();

    public EmotionRainView(Context context, @Nullable AttributeSet attrs) {
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
        mPaint.setAntiAlias(true);
        mPaint.setFilterBitmap(true);
        mPaint.setDither(true);
        mPaint.setColor(getResources().getColor(android.R.color.holo_red_light));

    }


    public void start(final List<Bitmap> list) {
        if(list == null || list.size() == 0) {
            return;
        }

        stop();
        setVisibility(View.VISIBLE);
        post(new Runnable() {
            @Override
            public void run() {
                initAndResetData(list);
                isRaining = true;
                invalidate();
            }
        });

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if(!isRaining || mEmotionList.size() == 0) {
            return;
        }

        long currentTimeStamp = System.currentTimeMillis();
        long totalTimeInteval = currentTimeStamp - mStartTimeStamp;

        for(int i=0;i<mEmotionList.size();i++) {
            EmotionBean emotion = mEmotionList.get(i);
            Bitmap bitmap = emotion.bitmap;
            if(bitmap.isRecycled() || isOutOfBottomBound(i) ||
                    totalTimeInteval < emotion.appearTimestamp) {
                continue;
            }

            matrix.reset();
            float heightScale = mEmotionHeight / bitmap.getHeight();
            float widthScale = mEmotionWidth / bitmap.getWidth();

            matrix.setScale(widthScale,heightScale);

            emotion.x = emotion.x + emotion.velocityX;
            emotion.y = emotion.y + emotion.velocityY;

            matrix.postTranslate(emotion.x, emotion.y);
            canvas.drawBitmap(bitmap,matrix,mPaint);
        }

        postInvalidate();
    }

    private boolean isOutOfBottomBound(int position) {
        return mEmotionList.get(position).y > getHeight();
    }

    /**
     * 初始化数据源
     * @param list
     */
    private void initAndResetData(final List<Bitmap> list) {

        this.mEmotionHeight = this.mEmotionWidth = dp2px(mContext,50);

        this.mStartTimeStamp = System.currentTimeMillis();

        mEmotionList.clear();

        int currentDuration = 0;
        int i = 0;
        int size = list.size();
        int maxDuration = 2000;

        while(currentDuration < maxDuration) {
            EmotionBean bean = new EmotionBean();
            bean.bitmap = list.get(i%size);
            bean.x = mRandom.nextInt(getWidth());
            bean.y = (int)(-Math.ceil(mEmotionHeight));

            float duration = mRandom.nextInt(500)+maxDuration;
            bean.velocityY = (int)(getHeight()*16 / duration);
            bean.velocityX = Math.round(mRandom.nextFloat());
            bean.appearTimestamp = currentDuration;
            mEmotionList.add(bean);

            currentDuration += mRandom.nextInt(250);
            i++;
        }
    }

    public static int dp2px(Context context,float dpValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int)(dpValue*scale+0.5f);
    }

    public void stop() {
        isRaining = false;
        setVisibility(GONE);
    }

}
