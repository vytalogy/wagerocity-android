package com.plego.wagerocity.android.fragments;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.plego.wagerocity.R;
import com.plego.wagerocity.android.adapters.LeaderboardPlayersListAdapter;
import com.plego.wagerocity.android.model.LeaderboardPlayer;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link LeaderBoardListFragment.OnLeaderboardListFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link LeaderBoardListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LeaderBoardListFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARGS_LEADERBOARD_PLAYERS = "args_leadeboard_players";


    // TODO: Rename and change types of parameters
    private ArrayList<LeaderboardPlayer> leaderboardPlayers;

    private OnLeaderboardListFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param leaderboardPlayers Parameter 1.
     * @return A new instance of fragment LeaderBoardListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LeaderBoardListFragment newInstance(ArrayList<LeaderboardPlayer> leaderboardPlayers) {
        LeaderBoardListFragment fragment = new LeaderBoardListFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARGS_LEADERBOARD_PLAYERS, leaderboardPlayers);
        fragment.setArguments(args);
        return fragment;
    }

    public LeaderBoardListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.leaderboardPlayers= getArguments().getParcelableArrayList(ARGS_LEADERBOARD_PLAYERS);
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        Spinner leaderboardFilterSpinner = (Spinner) view.findViewById(R.id.spinner_leaderboard_filters);
//        leaderboardFilterSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(parent.getContext(), parent.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });

        final ListView leaderboardListView = (ListView) view.findViewById(R.id.listview_leaderboards);

        LeaderboardPlayersListAdapter leaderboardPlayersListAdapter = new LeaderboardPlayersListAdapter(this.leaderboardPlayers, getActivity());

        leaderboardListView.setAdapter(leaderboardPlayersListAdapter);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_leader_board_list, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onLeaderboardListFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnLeaderboardListFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnLeaderboardListFragmentInteractionListener");
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
    public interface OnLeaderboardListFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onLeaderboardListFragmentInteraction(Uri uri);
    }

}
