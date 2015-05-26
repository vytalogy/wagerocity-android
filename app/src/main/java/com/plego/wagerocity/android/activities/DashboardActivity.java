package com.plego.wagerocity.android.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import com.anjlab.android.iab.v3.BillingProcessor;
import com.anjlab.android.iab.v3.TransactionDetails;
import com.plego.wagerocity.R;
import com.plego.wagerocity.android.WagerocityPref;
import com.plego.wagerocity.android.adapters.ExpertPlayerListAdapter;
import com.plego.wagerocity.android.adapters.GamesListAdapter;
import com.plego.wagerocity.android.adapters.LeaderboardPlayersListAdapter;
import com.plego.wagerocity.android.adapters.MyPicksListAdapter;
import com.plego.wagerocity.android.adapters.MyPoolsListAdapter;
import com.plego.wagerocity.android.adapters.PicksOfPlayerAdapter;
import com.plego.wagerocity.android.adapters.PoolsListAdapter;
import com.plego.wagerocity.android.fragments.BetOnGameFragment;
import com.plego.wagerocity.android.fragments.DashboardFragment;
import com.plego.wagerocity.android.fragments.ExpertsFragment;
import com.plego.wagerocity.android.fragments.GamesListFragment;
import com.plego.wagerocity.android.fragments.GetDollarsFragment;
import com.plego.wagerocity.android.fragments.LeaderBoardListFragment;
import com.plego.wagerocity.android.fragments.MyPicksFragment;
import com.plego.wagerocity.android.fragments.MyPoolDetailFragment;
import com.plego.wagerocity.android.fragments.MyPoolsFragment;
import com.plego.wagerocity.android.fragments.NavigationBarFragment;
import com.plego.wagerocity.android.fragments.PicksOfPlayerFragment;
import com.plego.wagerocity.android.fragments.PoolsFragment;
import com.plego.wagerocity.android.fragments.SettingsFragment;
import com.plego.wagerocity.android.fragments.SportsListFragment;
import com.plego.wagerocity.android.fragments.StatsFragment;
import com.plego.wagerocity.android.model.ExpertPlayer;
import com.plego.wagerocity.android.model.Game;
import com.plego.wagerocity.android.model.LeaderboardPlayer;
import com.plego.wagerocity.android.model.MyPool;
import com.plego.wagerocity.android.model.OddHolder;
import com.plego.wagerocity.android.model.Pick;
import com.plego.wagerocity.android.model.Pool;
import com.plego.wagerocity.android.model.RestClient;
import com.plego.wagerocity.constants.StringConstants;
import com.plego.wagerocity.utils.AndroidUtils;
import com.sromku.simple.fb.Permission;
import com.sromku.simple.fb.SimpleFacebook;
import com.sromku.simple.fb.SimpleFacebookConfiguration;
import com.sromku.simple.fb.entities.Feed;
import com.sromku.simple.fb.listeners.OnPublishListener;

import java.util.ArrayList;
import java.util.Collections;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import roboguice.activity.RoboFragmentActivity;

public class DashboardActivity
        extends RoboFragmentActivity
        implements BillingProcessor.IBillingHandler,
        NavigationBarFragment.OnNavigationBarFragmentInteractionListener,
        StatsFragment.OnStatsFragmentInteractionListener,
        DashboardFragment.OnDashboardFragmentInteractionListener,
        GetDollarsFragment.OnGetDollarsFragmentInteractionListener,
        LeaderBoardListFragment.OnLeaderboardListFragmentInteractionListener,
        PoolsFragment.OnPoolsFragmentInteractionListener,
        MyPoolsFragment.OnMyPoolsFragmentInteractionListener,
        ExpertsFragment.OnExpertsFragmentInteractionListener,
        SportsListFragment.OnSportsListFragmentInteractionListener,
        GamesListFragment.OnGamesListFragmentInteractionListener,
        GamesListAdapter.OnGamesListAdapterFragmentInteractionListener,
        BetOnGameFragment.OnBetOnGameFragmentInteractionListener,
        MyPicksFragment.OnMyPicksFragmentInteractionListener,
        LeaderboardPlayersListAdapter.OnLeaderboardPlayerListAdapterFragmentInteractionListener,
        ExpertPlayerListAdapter.OnExpertPlayerListAdapterFragmentInteractionListener,
        PoolsListAdapter.OnPoolsListAdapterFragmentInteractionListener,
        SettingsFragment.OnSettingFragmentInteractionListener,
        PicksOfPlayerFragment.OnPicksOfPlayerFragmentInteractionListener,
        PicksOfPlayerAdapter.OnPicksOfPlayerAdapterListAdapterFragmentInteractionListener,
        MyPoolsListAdapter.OnMyPoolsListAdapterFragmentInteractionListener,
        MyPoolDetailFragment.OnMyPoolDetailFragmentInteractionListener,
        MyPicksListAdapter.OnMyPickShareInteractionListener {

    SweetAlertDialog pDialog;
    SimpleFacebook simpleFacebook;
    OnPublishListener onPublishListener;
    BillingProcessor bp;
    ArrayList<Pick> showPurchasePicks;
    public Boolean shouldShare;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        bp = new BillingProcessor(this, this.getString(R.string.in_app_billing_public_key), this);

        addNavigationBarFragment();
        addStatsFragment();
        addDashboardFragment();

        String facebokoID = new WagerocityPref(getApplicationContext()).facebookID();
        if (facebokoID != null) {
            Log.e("FACEBOOKID", facebokoID);
        }

        Permission[] permissions = new Permission[] {
                Permission.BASIC_INFO,
                Permission.USER_ABOUT_ME,
                Permission.EMAIL,
                Permission.PUBLISH_ACTION
        };

        SimpleFacebookConfiguration configuration = new SimpleFacebookConfiguration.Builder()
                .setAppId(String.valueOf(R.string.app_id))
                .setNamespace("Wagerocity")
                .setPermissions(permissions)
                .build();

        SimpleFacebook.setConfiguration(configuration);

        onPublishListener = new OnPublishListener() {
            @Override
            public void onComplete(String postId) {
                Log.i("Facebook Post Tag", "Published successfully. The new post id = " + postId);
            }

            @Override
            public void onException(Throwable throwable) {
                super.onException(throwable);
            }

            @Override
            public void onFail(String reason) {
                Log.e("Facebook Post Failed!", "Publish failed = " + reason);
                super.onFail(reason);
            }
        };

    }

    @Override
    protected void onResume() {
        super.onResume();
        simpleFacebook = SimpleFacebook.getInstance(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        simpleFacebook.onActivityResult(this, requestCode, resultCode, data);

        if (!bp.handleActivityResult(requestCode, resultCode, data))
            super.onActivityResult(requestCode, resultCode, data);

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        if (bp != null)
            bp.release();

        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            getSupportFragmentManager().popBackStack();

//            if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
//                hideBackButton();
//            }

        } else {
            super.onBackPressed();
        }
    }

    private void showBackButton() {
        NavigationBarFragment fragment = (NavigationBarFragment) AndroidUtils.getFragmentByTag(this, StringConstants.TAG_FRAG_NAVIGATION);

        fragment.showBackButton(true);
    }

    private void hideBackButton() {
        NavigationBarFragment fragment = (NavigationBarFragment)AndroidUtils.getFragmentByTag(this, StringConstants.TAG_FRAG_NAVIGATION);

        fragment.showBackButton(false);
    }

    private void addNavigationBarFragment() {
        NavigationBarFragment navigationBarFragment = new NavigationBarFragment();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.fragment_container_navigation_bar, navigationBarFragment, StringConstants.TAG_FRAG_NAVIGATION);
        transaction.commit();
    }

    private void addStatsFragment() {
        StatsFragment statsFragment = new StatsFragment();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.fragment_container_stats, statsFragment, StringConstants.TAG_FRAG_STATS);
        transaction.commit();
    }

    private void addDashboardFragment() {
        DashboardFragment statsFragment = new DashboardFragment();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.fragment_container_dashboard, statsFragment, StringConstants.TAG_FRAG_DASHBOARD);
        transaction.commit();
    }

    @Override
    public void onStatsFragmentInteraction(Uri uri) {

    }

    @Override
    public void onNavigationBarFragmentInteraction(Uri uri) {
        if (uri.toString().equals(getString(R.string.uri_open_get_dollars_fragment))) {
            replaceGetDollarsFragment();
        }
    }

    @Override
    public void onNavigationBarGoHomeFragmentInteraction() {

        while (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStackImmediate();
        }
    }

    @Override
    public void onNavigationBarGoBackFragmentInteraction() {
        this.onBackPressed();
    }

    @Override
    public void onDashboardFragmentInteraction(Uri uri) {
        if (uri.toString().equals(getString(R.string.uri_open_get_dollars_fragment))) {
            replaceGetDollarsFragment();
        }

        if (uri.toString().equals(getString(R.string.uri_open_leaderboards_list_fragment))) {
            replaceFragment(SportsListFragment.newInstance(true, ""), StringConstants.TAG_FRAG_SPORTS_LIST);
        }

        if (uri.toString().equals(getString(R.string.uri_open_pools_fragment))) {

            pDialog = AndroidUtils.showDialog(
                    getString(R.string.loading),
                    null,
                    SweetAlertDialog.PROGRESS_TYPE,
                    DashboardActivity.this
            );

            RestClient restClient = new RestClient();
            restClient.getApiService().getAllPools(new WagerocityPref(this).user().getUserId(), new Callback<ArrayList<Pool>>() {
                @Override
                public void success(ArrayList<Pool> pools, retrofit.client.Response response) {
                    pDialog.dismiss();
                    replaceFragment(PoolsFragment.newInstance(pools), StringConstants.TAG_FRAG_POOLS_LIST);
                }

                @Override
                public void failure(RetrofitError error) {
                    pDialog.dismiss();
                    Log.e("getAllPools", String.valueOf(error));

                    AndroidUtils.showErrorDialog(error, DashboardActivity.this);
                }

            });
        }

        if (uri.toString().equals(getString(R.string.uri_open_experts_fragment))) {

            pDialog = AndroidUtils.showDialog(
                    getString(R.string.loading),
                    null,
                    SweetAlertDialog.PROGRESS_TYPE,
                    DashboardActivity.this
            );

            RestClient restClient = new RestClient();
            restClient.getApiService().getExperts(new WagerocityPref(this).user().getUserId(), new Callback<ArrayList<ExpertPlayer>>() {
                @Override
                public void success(ArrayList<ExpertPlayer> expertPlayers, retrofit.client.Response response) {

                    pDialog.dismiss();

                    replaceFragment(ExpertsFragment.newInstance(expertPlayers), StringConstants.TAG_FRAG_EXPERTS_LIST);
                }

                @Override
                public void failure(RetrofitError error) {
                    pDialog.dismiss();

                    AndroidUtils.showErrorDialog(error, DashboardActivity.this);

                }
            });

        }

        if (uri.toString().equals(getString(R.string.uri_open_my_picks_fragment))) {

            pDialog = AndroidUtils.showDialog(
                    getString(R.string.loading),
                    null,
                    SweetAlertDialog.PROGRESS_TYPE,
                    DashboardActivity.this
            );

            RestClient restClient = new RestClient();
            restClient.getApiService().getMyPicks(new WagerocityPref(this).user().getUserId(), new Callback<ArrayList<Pick>>() {
                @Override
                public void success(ArrayList<Pick> picks, Response response) {
                    pDialog.dismiss();


                    Collections.sort(picks, new Pick());

                    replaceFragment(MyPicksFragment.newInstance(picks), StringConstants.TAG_FRAG_MY_PICKS);
                }

                @Override
                public void failure(RetrofitError error) {
                    pDialog.dismiss();

                    AndroidUtils.showErrorDialog(error, DashboardActivity.this);
                }
            });
        }

        if (uri.toString().equals(getString(R.string.uri_open_sports_list_fragment))) {
            replaceFragment(SportsListFragment.newInstance(false, ""), StringConstants.TAG_FRAG_SPORTS_LIST);
        }

        if (uri.toString().equals(getString(R.string.uri_open_setting_fragment))) {
            replaceFragment(new SettingsFragment(), StringConstants.TAG_FRAG_SETTING);
        }
    }

    private void replaceGetDollarsFragment() {
        replaceFragment(new GetDollarsFragment(), StringConstants.TAG_FRAG_GET_DOLLARS);
    }

    private void replaceFragment(Fragment fragment, String TAG) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container_dashboard, fragment, TAG);
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
    public void onPoolsFragmentInteraction(Uri uri, ArrayList<MyPool> pools) {
        if (uri.toString().equals(getString(R.string.uri_open_my_pools_fragment))) {
            replaceFragment(MyPoolsFragment.newInstance(pools), StringConstants.TAG_FRAG_MY_POOLS_LIST);
        }
    }

    @Override
    public void onMyPoolsFragmentInteraction(Uri uri) {

    }

    @Override
    public void onExpertsFragmentInteraction(Uri uri) {

    }

    @Override
    public void onSportsListFragmentInteraction(Uri uri, ArrayList<Game> games, String sportsNameValueForParam, String poolId) {
        if (uri.toString().equals(getString(R.string.uri_open_games_list_fragment))) {
            replaceFragment(GamesListFragment.newInstance(games, sportsNameValueForParam, poolId), StringConstants.TAG_FRAG_GAMES_LIST);
        }
    }

    @Override
    public void onSportsListLeaderbaordsFragmentInteraction(Uri uri, ArrayList<LeaderboardPlayer> leaderboardPlayers, String sportsNameValueForParam) {
        if (uri.toString().equals(getString(R.string.uri_open_leaderboards_list_fragment))) {
            replaceFragment(LeaderBoardListFragment.newInstance(leaderboardPlayers), StringConstants.TAG_FRAG_LEADERBOARD_LIST);
        }
    }

    @Override
    public void onGamesListFragmentInteraction(Uri uri, ArrayList<OddHolder> oddHolders, String poolId) {

        if (uri.toString().equals(getString(R.string.uri_selected_game_for_betting))) {

            replaceFragment(BetOnGameFragment.newInstance(oddHolders, poolId), StringConstants.TAG_FRAG_BET_ON_GAME);
        }
    }

    @Override
    public void onGamesListAdapterFragmentInteraction(Uri uri, Game game) {
        if (uri.toString().equals(getString(R.string.uri_selected_game_for_betting))) {
//            Log.e("Select Game", game.getTeamAName());
//            replaceFragment(BetOnGameFragment.newInstance(game), StringConstants.TAG_FRAG_BET_ON_GAME);
        }
    }

    @Override
    public void onBetOnGameFragmentInteraction(Uri uri, ArrayList<Pick> picks) {
        if (uri.toString().equals(getString(R.string.uri_open_my_picks_fragment))) {
            replaceFragment(MyPicksFragment.newInstance(picks), StringConstants.TAG_FRAG_MY_PICKS);
        }
    }

    @Override
    public void onBetOnGameGoBackFragmentInteraction() {
        this.onBackPressed();
    }

    @Override
    public void onBetOnGameShareFragmentInteraction(Feed feed) {
        simpleFacebook.publish(feed, true, onPublishListener);
    }

    @Override
    public void onMyPicksFragmentInteraction(Uri uri) {

    }

    @Override
    public void onLeaderboardPlayerListAdapterFragmentInteraction(Uri uri, ArrayList<Game> games) {
        if (uri.toString().equals(getString(R.string.uri_open_picks_of_player_fragment))) {
            replaceFragment(PicksOfPlayerFragment.newInstance(games), StringConstants.TAG_FRAG_PICKS_OF_PLAYER);
        }
    }

    @Override
    public void onExpertPlayerListAdapterFragmentInteraction(Uri uri, ArrayList<Game> games) {
        if (uri.toString().equals(getString(R.string.uri_open_picks_of_player_fragment))) {
            replaceFragment(PicksOfPlayerFragment.newInstance(games), StringConstants.TAG_FRAG_PICKS_OF_PLAYER);
        }
    }

    @Override
    public void onPoolsListAdapterFragmentInteraction(Uri uri, ArrayList<MyPool> pools) {
        if (uri.toString().equals(getString(R.string.uri_open_my_pools_fragment))) {
            replaceFragment(MyPoolsFragment.newInstance(pools), StringConstants.TAG_FRAG_MY_POOLS_LIST);
        }
    }

    @Override
    public void onSettingFragmentInteraction(Uri uri) {

    }

    @Override
    public void onPicksOfPlayerFragmentInteraction(Uri uri) {

    }

    @Override
    public void onPicksOfPlayerAdapterListAdapterFragmentInteraction(Uri uri, ArrayList<Pick> picks, boolean isPurchased) {
        if (uri.toString().equals(getString(R.string.uri_open_my_picks_fragment))) {
            if (isPurchased) {
                replaceFragment(MyPicksFragment.newInstance(picks), StringConstants.TAG_FRAG_MY_PICKS);
            } else {
                bp.purchase(this, "android.test.purchased");
                bp.consumePurchase("android.test.purchased");
                showPurchasePicks = new ArrayList<>(picks);
            }
        }
    }

    @Override
    public void onMyPoolsListAdapterFragmentInteraction(Uri uri, MyPool pool) {
        if (uri.toString().equals(getString(R.string.uri_open_my_pool_detail_fragment))) {
            replaceFragment(MyPoolDetailFragment.newInstance(pool), StringConstants.TAG_FRAG_MY_POOL_DETAILS);
        }
    }

    @Override
    public void onMyPoolDetailFragmentInteraction(Uri uri, ArrayList<Game> games, String leagueName, String poolId) {
        if (uri.toString().equals(getString(R.string.uri_open_games_list_fragment))) {
            replaceFragment(GamesListFragment.newInstance(games, leagueName, poolId), StringConstants.TAG_FRAG_GAMES_LIST);
        }
    }

    @Override
    public void onMyPickShareInteraction(Feed feed) {
        simpleFacebook.publish(feed, true, onPublishListener);
    }


    @Override
    public void onProductPurchased(String s, TransactionDetails transactionDetails) {
        if (transactionDetails.productId.equals("android.test.purchased")) {
            replaceFragment(MyPicksFragment.newInstance(new ArrayList<>(this.showPurchasePicks)), StringConstants.TAG_FRAG_MY_PICKS);
            this.showPurchasePicks = null;

        }
    }

    @Override
    public void onPurchaseHistoryRestored() {

    }

    @Override
    public void onBillingError(int i, Throwable throwable) {

    }

    @Override
    public void onBillingInitialized() {

    }
}
