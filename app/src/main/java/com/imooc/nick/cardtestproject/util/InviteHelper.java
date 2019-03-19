package com.imooc.nick.cardtestproject.util;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.view.View;
import android.widget.Toast;

public class InviteHelper {

    private boolean isPlaying;
    private boolean isShow;
    private Context mContext;
    private View mInviteLayout;

    public InviteHelper(Context context, View mInviteLayout) {
        this.mContext = context;
        this.mInviteLayout = mInviteLayout;
    }


    /**
     * 执行动画
     *
     * @param duration
     * @param view
     */
    private void startAnim(final long duration, final View view,final boolean showHelper) {
        if (view != null) {
            view.setVisibility(View.VISIBLE);
            view.post(new Runnable() {
                @Override
                public void run() {
                    float distance = view.getHeight();
                    ObjectAnimator translation;
                    if (!showHelper) {
                        if (!isShow) {
                            return;
                        }
                        translation = ObjectAnimator.ofFloat(view, "translationY", 0f, distance);
                        isShow = false;
                    } else {
                        if (isShow) {
                            return;
                        }
                        translation = ObjectAnimator.ofFloat(view, "translationY", distance, 0f);
                        isShow = true;
                    }
                    translation.setDuration(duration);
                    translation.addListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {
                            isPlaying = true;
                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            isPlaying = false;
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {
                            isPlaying = false;
                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {
                            isPlaying = true;
                        }
                    });
                    translation.start();
                }
            });

        }

    }

    public void hideInviteView() {
        startAnim(1000, mInviteLayout,false);
    }

    public void initInviteData() {
        mInviteLayout.setVisibility(View.INVISIBLE);
        startAnim(1000, mInviteLayout,true);
        mInviteLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext,"jump to imooc",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
