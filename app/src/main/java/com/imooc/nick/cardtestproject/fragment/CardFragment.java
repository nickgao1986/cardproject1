package com.imooc.nick.cardtestproject.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.imooc.nick.cardtestproject.MainActivity;
import com.imooc.nick.cardtestproject.R;
import com.imooc.nick.cardtestproject.api.QuestionSaveApi;
import com.imooc.nick.cardtestproject.bean.QuestionInfo;
import com.imooc.nick.cardtestproject.view.ButtonSelectView;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class CardFragment extends Fragment {

    private View mRootView;
    private TextView contentTitle;
    private ButtonSelectView mButtonSelectView1;
    private ButtonSelectView mButtonSelectView2;
    private TextView TipContentTv;
    private LinearLayout tip_layout;

    private QuestionInfo mCurrentInfo;
    private MainActivity mainActivity;

    public static CardFragment newInstance(QuestionInfo info) {
        CardFragment fragment = new CardFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("info",info);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_layout,container,false);
        return mRootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        mCurrentInfo = (QuestionInfo) getArguments().getSerializable("info");
        contentTitle.setText(mCurrentInfo.title);

        int type = mCurrentInfo.type;
        int answer = Integer.valueOf(mCurrentInfo.answer);
        int userOption = 1;
        if(!TextUtils.isEmpty(mCurrentInfo.option)) {
            userOption = Integer.valueOf(mCurrentInfo.option);
        }

        if(type == 1) {
            tip_layout.setVisibility(View.VISIBLE);
            TipContentTv.setText(mCurrentInfo.explain);
        }else{
            tip_layout.setVisibility(View.GONE);

        }

        mButtonSelectView1.setText(mCurrentInfo.options.get(0));
        mButtonSelectView2.setText(mCurrentInfo.options.get(1));

        mButtonSelectView1.setListenter(new ButtonSelectView.onButtonSelectClickListener() {
            @Override
            public void onClick() {
                saveOptionInfo(1);
            }
        });

        mButtonSelectView2.setListenter(new ButtonSelectView.onButtonSelectClickListener() {
            @Override
            public void onClick() {
                saveOptionInfo(2);
            }
        });


        if(type == 1) {
            if(answer == 1) {
                mButtonSelectView1.setIcon(R.mipmap.img_test_right);
                mButtonSelectView2.setIcon(R.mipmap.img_test_worn);
            }else{
                mButtonSelectView1.setIcon(R.mipmap.img_test_worn);
                mButtonSelectView2.setIcon(R.mipmap.img_test_right);
            }

            if(userOption == 1) {
                mButtonSelectView1.setSelect(true);
                mButtonSelectView2.setSelect(false);
            }else{
                mButtonSelectView1.setSelect(false);
                mButtonSelectView2.setSelect(true);
            }
        }

    }

    private void saveOptionInfo(final int option) {
//        new QuestionSaveApi(mCurrentInfo.question_id,
//                String.valueOf(option)).post(new ApiListener() {
//            @Override
//            public void success(ApiUtil api) {
//                QuestionSaveApi apiBase = (QuestionSaveApi)api;
//                boolean isCorrect = ((QuestionSaveApi) api).mRankInfo.is_correct.equals("1");
//                handleButtonSelectView(option,isCorrect);
//
//                tip_layout.setVisibility(View.VISIBLE);
//                TipContentTv.setText(mCurrentInfo.explain);
//                mainActivity.setBottomTipView(apiBase.mRankInfo.correct_count);
//
//            }
//
//            @Override
//            public void failure(ApiUtil api) {
//
//            }
//        });

        //todo
        String str = getFromAssets("submit.json", getContext());
        try{
            QuestionSaveApi apiBase = new QuestionSaveApi(mCurrentInfo.question_id,
                    String.valueOf(option));
            JSONObject jsonObject = new JSONObject(str);
            apiBase.loadLocalData(jsonObject);
            boolean isCorrect = apiBase.mRankInfo.is_correct.equals("1");
            handleButtonSelectView(option,isCorrect);

            tip_layout.setVisibility(View.VISIBLE);
            TipContentTv.setText(mCurrentInfo.explain);
            mainActivity.setBottomTipView(apiBase.mRankInfo.correct_count);
        }catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 获取本地的数据
     * @param fileName
     * @param context
     * @return
     */
    public String getFromAssets(String fileName,final Context context){
        try {
            InputStreamReader inputReader = new InputStreamReader(context.getResources().getAssets().open(fileName) );
            BufferedReader bufReader = new BufferedReader(inputReader);
            String line="";
            String Result="";
            while((line = bufReader.readLine()) != null)
                Result += line;
            return Result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mainActivity = (MainActivity)context;
    }

    private void handleButtonSelectView(int option, boolean isCorrect) {
        int rightOption;
        if(option == 1) {
            if(isCorrect) {
                rightOption = 1;
                mainActivity.startRain();
            }else{
                rightOption = 2;
            }
            mButtonSelectView1.setSelect(true);
            mButtonSelectView2.setSelect(false);
        }else{
            if(isCorrect) {
                rightOption = 2;
                mainActivity.startRain();
            }else{
                rightOption = 1;
            }
            mButtonSelectView1.setSelect(false);
            mButtonSelectView2.setSelect(true);
        }

        if(rightOption == 1) {
            mButtonSelectView1.setIcon(R.mipmap.img_test_right);
            mButtonSelectView2.setIcon(R.mipmap.img_test_worn);
        }else{
            mButtonSelectView1.setIcon(R.mipmap.img_test_worn);
            mButtonSelectView2.setIcon(R.mipmap.img_test_right);
        }

    }


    private void initView() {
        contentTitle = mRootView.findViewById(R.id.contentTitle);
        mButtonSelectView1 = mRootView.findViewById(R.id.first_option_layout);
        mButtonSelectView2 = mRootView.findViewById(R.id.second_option_layout);
        tip_layout = mRootView.findViewById(R.id.tip_layout);
        TipContentTv = mRootView.findViewById(R.id.tip_text);
    }
}
