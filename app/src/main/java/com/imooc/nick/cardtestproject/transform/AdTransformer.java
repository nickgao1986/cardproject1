package com.imooc.nick.cardtestproject.transform;

import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;

import static android.content.ContentValues.TAG;

public class AdTransformer implements ViewPager.PageTransformer {
    @Override
    public void transformPage(View page, float position) {
        Log.d(TAG,"<<<<<<page="+page+"position="+position);

        if(position < -1) {
            position = -1;
        }else if(position > 1) {
            position = 1;
        }

        float tempScale = position < 0 ? 1+position : 1-position;
        float scaleValue = 0.9f+tempScale*0.1f;
        page.setScaleX(scaleValue);
        page.setScaleY(scaleValue);
    }
}
