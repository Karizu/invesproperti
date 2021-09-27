package com.selada.invesproperti.presentation.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.selada.invesproperti.presentation.home.HomeFragment;
import com.selada.invesproperti.presentation.inbox.InboxFragment;
import com.selada.invesproperti.presentation.portofolio.PortofolioFragment;
import com.selada.invesproperti.presentation.profile.ProfileFragment;
import com.selada.invesproperti.presentation.questioner.Quesioner01Fragment;
import com.selada.invesproperti.presentation.questioner.Quesioner02Fragment;
import com.selada.invesproperti.presentation.questioner.Quesioner03Fragment;
import com.selada.invesproperti.presentation.questioner.Quesioner04Fragment;
import com.selada.invesproperti.presentation.questioner.Quesioner05Fragment;
import com.selada.invesproperti.presentation.questioner.Quesioner06Fragment;

public class QuestionerViewPagerAdapter extends FragmentStatePagerAdapter {

    public QuestionerViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new Quesioner01Fragment();
            case 1:
                return new Quesioner02Fragment();
            case 2:
                return new Quesioner03Fragment();
            case 3:
                return new Quesioner04Fragment();
            case 4:
                return new Quesioner05Fragment();
            case 5:
                return new Quesioner06Fragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 6;
    }
}
