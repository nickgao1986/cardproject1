package com.imooc.nick.cardtestproject.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.imooc.nick.cardtestproject.bean.QuestionInfo;
import com.imooc.nick.cardtestproject.fragment.CardFragment;

import java.util.List;

public class CardFragmentPagerAdapter extends FragmentStatePagerAdapter {

    private List<QuestionInfo> mList;

    public CardFragmentPagerAdapter(FragmentManager fm, List<QuestionInfo> list) {
        super(fm);
        this.mList = list;
    }

    @Override
    public Fragment getItem(int position) {
        return CardFragment.newInstance(mList.get(position));
    }

    @Override
    public int getCount() {
        return this.mList.size();
    }
}
