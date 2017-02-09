package nagarjuna.com.nagarjuna.com.nagarjunaapa.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import nagarjuna.com.nagarjunaapa.fragments.ImageSliderFragment;

/**
 * Created by User on 6/23/2016.
 */
public class CustomPagerAdapter extends FragmentPagerAdapter {

    public CustomPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return ImageSliderFragment.newInstance(position);
    }

    @Override
    public int getCount() {
        return 6;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return super.getPageTitle(position);
    }


}
