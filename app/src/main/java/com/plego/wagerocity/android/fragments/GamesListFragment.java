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

import com.plego.wagerocity.R;
import com.plego.wagerocity.android.adapters.GamesListAdapter;
import com.plego.wagerocity.android.model.Game;
import com.plego.wagerocity.android.model.Odd;
import com.plego.wagerocity.android.model.OddHolder;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link com.plego.wagerocity.android.fragments.GamesListFragment.OnGamesListFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link GamesListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GamesListFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARGS_GAMES_LIST = "games_list";
    private static final String ARGS_SPORTS_NAME = "sports_name";
    private static final String ARGS_POOL_ID = "Pool_Id";

    private ArrayList<Game> games;
    private String sportsName;
    private String poolId;

    private OnGamesListFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param games Parameter 1.
     * @return A new instance of fragment GamesListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GamesListFragment newInstance(ArrayList<Game> games, String sportsName, String poolId) {
        GamesListFragment fragment = new GamesListFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARGS_GAMES_LIST, games);
        args.putString(ARGS_SPORTS_NAME, sportsName);
        args.putString(ARGS_POOL_ID, poolId);
        fragment.setArguments(args);
        return fragment;
    }

    public GamesListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            games = getArguments().getParcelableArrayList(ARGS_GAMES_LIST);
            sportsName = getArguments().getString(ARGS_SPORTS_NAME);
            poolId = getArguments().getString(ARGS_POOL_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_games_list, container, false);
    }


    @Override
    public void onResume() {
        super.onResume();

        for (Game game : games) {
            game.setOddHolders(new ArrayList<OddHolder>());
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final ListView gamesListView = (ListView) view.findViewById(R.id.listview_games_list);

        final GamesListAdapter gamesListAdapter = new GamesListAdapter(view.getContext(), games, sportsName);

        gamesListView.setAdapter(gamesListAdapter);

        Button showBetSlip = (Button) view.findViewById(R.id.button_cell_games_list_shoeBetSlip);
        showBetSlip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<OddHolder> oddHolders = new ArrayList<OddHolder>();

                for (Game game : games) {
                    if (game.getOddHolders() != null)
                        if (game.getOddHolders().size() > 0) {
                            oddHolders.addAll(game.getOddHolders());
                        }
                }

                if (oddHolders.size() > 0) {

                    Uri uri = Uri.parse(getString(R.string.uri_selected_game_for_betting));
                    mListener.onGamesListFragmentInteraction(uri, oddHolders, poolId);

                }

            }
        });

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnGamesListFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnGamesListFragmentInteractionListener");
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
    public interface OnGamesListFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onGamesListFragmentInteraction(Uri uri, ArrayList<OddHolder> oddHolders, String PoolId);
    }

}
