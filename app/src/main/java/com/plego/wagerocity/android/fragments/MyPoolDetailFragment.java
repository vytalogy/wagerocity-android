package com.plego.wagerocity.android.fragments;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.plego.wagerocity.R;
import com.plego.wagerocity.android.WagerocityPref;
import com.plego.wagerocity.android.adapters.MyPoolDetailAdapter;
import com.plego.wagerocity.android.model.Game;
import com.plego.wagerocity.android.model.MyPool;
import com.plego.wagerocity.android.model.PoolMember;
import com.plego.wagerocity.android.model.RestClient;
import com.plego.wagerocity.utils.AndroidUtils;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MyPoolDetailFragment.OnMyPoolDetailFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MyPoolDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyPoolDetailFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private MyPool pool;


    private OnMyPoolDetailFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param myPool .
     * @return A new instance of fragment MyPoolDetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MyPoolDetailFragment newInstance(MyPool myPool) {
        MyPoolDetailFragment fragment = new MyPoolDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PARAM1, myPool);
        fragment.setArguments(args);
        return fragment;
    }

    public MyPoolDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            pool = getArguments().getParcelable(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_pool_detail, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Double poolCredits = 0.0;
        String userId = new WagerocityPref(this.getActivity()).user().getUserId();

        for (PoolMember member : pool.getPoolMembers()) {
            if (member.getUserId().equals(userId)) {
                poolCredits = member.getDollars();
            }
        }

        TextView poolName = (TextView) view.findViewById(R.id.textView_my_pool_detail_pool_name);
        poolName.setText(pool.getName());

        TextView commisionerName = (TextView) view.findViewById(R.id.textView_my_pool_detail_commisioner_name);
        commisionerName.setText(pool.getCommisioner().get(0).getDisplayname());

        TextView leagueName = (TextView) view.findViewById(R.id.textView_my_pool_detail_pool_league_name);
        leagueName.setText(pool.getPoolLeague());

        TextView startDate = (TextView) view.findViewById(R.id.textView_my_pool_detail_pool_start_date);
        try {
            startDate.setText(AndroidUtils.getFormatedDateMMHHYYYY(pool.getToDate()));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        TextView endDate = (TextView) view.findViewById(R.id.textView_my_pool_detail_pool_end_date);
        try {
            endDate.setText(AndroidUtils.getFormatedDateMMHHYYYY(pool.getFromDate()));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        TextView size = (TextView) view.findViewById(R.id.textView_my_pool_detail_pool_size);
        size.setText(pool.getSize());

//        TextView status = (TextView) view.findViewById(R.id.textView_my_pool_detail_pool_status);
//        status.setText(pool.getStatus());

        TextView isPaid = (TextView) view.findViewById(R.id.textView_my_pool_detail_pool_paid);
        isPaid.setText((pool.getIsPaid().equals("1")) ? "YES" : "NO");

        TextView amount = (TextView) view.findViewById(R.id.textView_my_pool_detail_pool_amount);
        amount.setText(pool.getAmount());

        Button button = (Button) view.findViewById(R.id.button_my_pool_detail_pool_view_game);
        final Double finalPoolCredits = poolCredits;
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RestClient restClient = new RestClient();
                restClient.getApiService().getGames(pool.getPoolLeague(), new Callback<ArrayList<Game>>() {
                    @Override
                    public void success(ArrayList<Game> games, Response response) {

                        if (games.size() > 0) {

                            for (Game game: games) {
                                game.setPoolCredits(finalPoolCredits);
                            }

                            Uri uri = Uri.parse(getActivity().getString(R.string.uri_open_games_list_fragment));
                            mListener.onMyPoolDetailFragmentInteraction(uri, games, pool.getPoolLeague(), pool.getPoolId());

                        } else {

                            SweetAlertDialog pDialog = AndroidUtils.showDialog(
                                    getActivity().getString(R.string.no_games_found),
                                    "There are no " + pool.getPoolLeague() + " games going on for now. Please come back later",
                                    SweetAlertDialog.ERROR_TYPE,
                                    getActivity()
                            );
                        }


                    }

                    @Override
                    public void failure(RetrofitError error) {
                        SweetAlertDialog pDialog = AndroidUtils.showDialog(
                                getActivity().getString(R.string.no_games_found),
                                "There are no " + pool.getPoolLeague() + " games going on for now. Please come back later",
                                SweetAlertDialog.ERROR_TYPE,
                                getActivity()
                        );
                    }
                });
            }
        });


        final ListView leaderboardListView = (ListView) view.findViewById(R.id.listview_my_pool_detail_players);

        MyPoolDetailAdapter myPoolDetailAdapter = new MyPoolDetailAdapter((ArrayList<PoolMember>) pool.getPoolMembers(), getActivity());

        leaderboardListView.setAdapter(myPoolDetailAdapter);

        setListViewHeightBasedOnItems(leaderboardListView);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnMyPoolDetailFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnMyPoolDetailFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onMyPoolDetailFragmentInteraction(Uri uri, ArrayList<Game> games, String leagueName, String poolId);
    }

    public static boolean setListViewHeightBasedOnItems(ListView listView) {

        MyPoolDetailAdapter listAdapter = (MyPoolDetailAdapter) listView.getAdapter();
        if (listAdapter != null) {

            int numberOfItems = listAdapter.getCount();
            // Get total height of all items.
            int totalItemsHeight = 0;
            for (int itemPos = 0; itemPos < numberOfItems; itemPos++) {
                View item = listAdapter.getView(itemPos, null, listView);
                item.measure(0, 0);
                totalItemsHeight += item.getMeasuredHeight();
            }

            // Get total height of all item dividers.
            int totalDividersHeight = listView.getDividerHeight() *
                    (numberOfItems - 1);

            // Set list height.
            ViewGroup.LayoutParams params = listView.getLayoutParams();
            params.height = totalItemsHeight + totalDividersHeight;
            listView.setLayoutParams(params);
            listView.requestLayout();

            return true;

        } else {
            return false;
        }

    }

}
