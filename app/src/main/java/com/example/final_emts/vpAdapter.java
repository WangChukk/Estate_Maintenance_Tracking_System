package com.example.final_emts;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class vpAdapter extends FragmentPagerAdapter {

    private final ArrayList<Fragment> fragmnetArrayList = new ArrayList<>();
    private final ArrayList<String> fragmentTitle = new ArrayList<>();
    public vpAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragmnetArrayList.get(position);
    }

    @Override
    public int getCount() {
        return fragmnetArrayList.size();
    }
    public void addFragment(Fragment fragment,String title){
        fragmnetArrayList.add(fragment);
        fragmentTitle.add(title);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return fragmentTitle.get(position);
    }
}
