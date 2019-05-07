package nickgao.com.emotionrain;

import android.graphics.Bitmap;

public class EmotionBean {
    //因为这个控件设计到很多图标的下落，比较复杂，对于这种场景我自己建议大家应该从一个图标下落开始入手，
    //完成一个图标随机下落后，我们然后扩展控件让它适配多个图标下落，能降低很多复杂性，下面我们先来写数据bean
    public Bitmap bitmap;

    public int x,y;

    public int velocityX,velocityY;

    public int appearTimestamp;

}
