package br.vini.vinic.criptografiarsa;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import br.vini.vinic.criptografiarsa.Fragments.FragmentChaves;
import br.vini.vinic.criptografiarsa.Fragments.FragmentCriptografar;
import br.vini.vinic.criptografiarsa.Fragments.FragmentDescriptografar;


public class MyFragmentPagerAdapter extends FragmentPagerAdapter {

    private String[] mTabTitles;
    public Fragment f1;
    public Fragment f2;
    public Fragment f3;

    public MyFragmentPagerAdapter(FragmentManager fm, String[] mTabTitles) {
        super(fm);
        this.mTabTitles = mTabTitles;
        f1 = new FragmentChaves();
        f2 = new FragmentCriptografar();
        f3 = new FragmentDescriptografar();
    }

    public void  setF1(FragmentChaves f){
        f1 = f;
    }
    public void  setF2(FragmentCriptografar f){
        f2 = f;
    }
    public void  setF3(FragmentDescriptografar f){
        f3 = f;
    }


    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 0:{
                return f1;
            }
            case 1: {
                return f2;
            }
            case 2: {
                return f3;
            }
            default: return null;
        }
    }

    @Override
    public int getCount() {
        return this.mTabTitles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return this.mTabTitles[position];
    }
}
