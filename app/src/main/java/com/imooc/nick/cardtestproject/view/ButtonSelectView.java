package com.imooc.nick.cardtestproject.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.imooc.nick.cardtestproject.R;

public class ButtonSelectView extends RelativeLayout {

    private String mText;
    private Drawable mDrawable;

    private TextView textView;
    private ImageView leftImg;

    private onButtonSelectClickListener mListenter;

    public ButtonSelectView(Context context, AttributeSet attrs) {
        super(context, attrs);
        inflate(context, R.layout.button_select_layout,this);
        TypedArray t = context.obtainStyledAttributes(attrs,R.styleable.ButtonSelectView);

        mText = t.getString(R.styleable.ButtonSelectView_text);
        mDrawable = t.getDrawable(R.styleable.ButtonSelectView_icon_left);

        textView = findViewById(R.id.tv_option1);
        leftImg = findViewById(R.id.img_option1);

        textView.setText(mText);
        leftImg.setImageDrawable(mDrawable);

        textView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(mListenter != null) {
                    mListenter.onClick();
                }
            }
        });

    }

    public void setIcon(int resource) {
        leftImg.setImageResource(resource);
    }

    public void setText(String text) {
        textView.setText(text);
    }

    public void setButtonState(String text,int resource) {
        setText(text);
        setIcon(resource);
    }

    public void enabled(boolean enable) {
        textView.setEnabled(enable);
    }

    public void setSelect(boolean select) {
        textView.setSelected(select);
        setEnabled(false);
    }

    public interface onButtonSelectClickListener{
        void onClick();
    }


    public void setListenter(onButtonSelectClickListener listenter) {
        this.mListenter = listenter;
    }


}
