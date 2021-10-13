package com.selada.invesproperti.presentation.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.selada.invesproperti.presentation.home.HomeFragment;
import com.selada.invesproperti.presentation.inbox.InboxFragment;
import com.selada.invesproperti.presentation.portofolio.PortofolioFragment;
import com.selada.invesproperti.presentation.profile.ProfileFragment;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new HomeFragment();
            case 1:
                return new PortofolioFragment();
            case 2:
                return new InboxFragment();
            case 3:
                return new ProfileFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 4;
    }
}
