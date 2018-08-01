package ua.example.ioeug.surdomay;

import android.database.Cursor;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by Asus on 03.05.2017.
 */

public class MyAdapter extends FragmentStatePagerAdapter {

    Cursor c = null;

    public MyAdapter(FragmentManager mgr){
        super(mgr);
    }

    @Override
    public Fragment getItem(int position) {
        return (PageFragmentAlfabet.newInstance(position));
    }

    @Override
    public int getCount() {
        return 32;
    }
}
