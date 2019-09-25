package com.app.todolist.ui.main.tabs.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.app.todolist.R;
import com.app.todolist.ui.main.tabs.completed.CompletedTODOsFragment;
import com.app.todolist.ui.main.tabs.todo.TODOsFragment;

public class FragmentPagerAdapter extends android.support.v4.app.FragmentPagerAdapter {

    Context context;

    public FragmentPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        this.context = context;
    }

    // This determines the fragment for each tab
    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new TODOsFragment();
        } else if (position == 1) {
            return new CompletedTODOsFragment();
        }else {
            return null;
        }
    }

    // This determines the number of tabs
    @Override
    public int getCount() {
        return 2;
    }

    // This determines the title for each tab
    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        switch (position) {
            case 0:
                return context.getString(R.string.todos);
            case 1:
                return context.getString(R.string.completed);
            default:
                return null;
        }
    }
}
