package com.plego.wagerocity.android.activities;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import com.plego.wagerocity.R;
import com.plego.wagerocity.android.fragments.DashboardFragment;
import com.plego.wagerocity.android.fragments.ExpertsFragment;
import com.plego.wagerocity.android.fragments.GetDollarsFragment;
import com.plego.wagerocity.android.fragments.LeaderBoardListFragment;
import com.plego.wagerocity.android.fragments.MyPoolsFragment;
import com.plego.wagerocity.android.fragments.NavigationBarFragment;
import com.plego.wagerocity.android.fragments.PoolsFragment;
import com.plego.wagerocity.android.fragments.StatsFragment;

import roboguice.activity.RoboFragmentActivity;

public class DashboardActivity
        extends RoboFragmentActivity
        implements NavigationBarFragment.OnNavigationBarFragmentInteractionListener,
        StatsFragment.OnStatsFragmentInteractionListener,
        DashboardFragment.OnDashboardFragmentInteractionListener,
        GetDollarsFragment.OnGetDollarsFragmentInteractionListener,
        LeaderBoardListFragment.OnLeaderboardListFragmentInteractionListener,
        PoolsFragment.OnPoolsFragmentInteractionListener,
        MyPoolsFragment.OnMyPoolsFragmentInteractionListener,
        ExpertsFragment.OnExpertsFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        addNavigationBarFragment();
        addStatsFragment();
        addDashboardFragment();
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            getSupportFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }

    private void addNavigationBarFragment() {
        NavigationBarFragment navigationBarFragment = new NavigationBarFragment();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.fragment_container_navigation_bar, navigationBarFragment);
        transaction.commit();
    }

    @Override
    public void onNavigationBarFragmentInteraction(Uri uri) {

    }

    private void addStatsFragment() {
        StatsFragment statsFragment = new StatsFragment();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.fragment_container_stats, statsFragment);
        transaction.commit();
    }

    @Override
    public void onStatsFragmentInteraction(Uri uri) {

    }

    private void addDashboardFragment() {
        DashboardFragment statsFragment = new DashboardFragment();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.fragment_container_dashboard, statsFragment);
        transaction.commit();
    }

    @Override
    public void onDashboardFragmentInteraction(Uri uri) {
        if (uri.toString().equals(getString(R.string.uri_open_get_dollars_fragment))) {
            replaceGetDollarsFragment();
        }

        if (uri.toString().equals(getString(R.string.uri_open_leaderboards_list_fragment))) {
            replaceFragment(new LeaderBoardListFragment());
        }

        if (uri.toString().equals(getString(R.string.uri_open_pools_fragment))) {
            replaceFragment(new PoolsFragment());
        }

        if (uri.toString().equals(getString(R.string.uri_open_experts_fragment))) {
            replaceFragment(new ExpertsFragment());
        }
    }

    private void replaceGetDollarsFragment() {
        replaceFragment(new GetDollarsFragment());
    }

    private void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container_dashboard, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onGetDollarsFragmentInteraction(Uri uri) {

    }

    @Override
    public void onLeaderboardListFragmentInteraction(Uri uri) {

    }

    @Override
    public void onPoolsFragmentInteraction(Uri uri) {
        if (uri.toString().equals(getString(R.string.uri_open_my_pools_fragment))) {
            replaceFragment(new MyPoolsFragment());
        }
    }

    @Override
    public void onMyPoolsFragmentInteraction(Uri uri) {

    }

    @Override
    public void onExpertsFragmentInteraction(Uri uri) {

    }
}
