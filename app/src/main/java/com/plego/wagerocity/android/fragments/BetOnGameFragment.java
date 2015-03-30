package com.plego.wagerocity.android.fragments;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.plego.wagerocity.R;
import com.plego.wagerocity.android.model.Game;

import org.w3c.dom.Text;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link com.plego.wagerocity.android.fragments.BetOnGameFragment.OnBetOnGameFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link BetOnGameFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BetOnGameFragment extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARGS_GAME= "selected_game";
    private Game game;

    private OnBetOnGameFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param game Parameter 1.
     * @return A new instance of fragment BetOnGameFragment.
     */

    public static BetOnGameFragment newInstance(Game game) {
        BetOnGameFragment fragment = new BetOnGameFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARGS_GAME, game);
        fragment.setArguments(args);
        return fragment;
    }

    public BetOnGameFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            game = getArguments().getParcelable(ARGS_GAME);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bet_on_game, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView teamNameA = (TextView) view.findViewById(R.id.textview_betongame_team_a_name);
        teamNameA.setText(game.getTeamAFullname());

        TextView teamNameB = (TextView) view.findViewById(R.id.textview_betongame_team_b_name);
        teamNameB.setText(game.getTeamBFullname());

        ImageView teamFlagA = (ImageView) view.findViewById(R.id.imageview_betongame_team_a_flag);
        ImageView teamFlagB = (ImageView) view.findViewById(R.id.imageview_betongame_team_b_flag);

        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .cacheInMemory(true) // default
                .cacheOnDisk(true) // default
                .build();

        ImageLoader.getInstance().displayImage(game.getTeamALogo(), teamFlagA, options);
        ImageLoader.getInstance().displayImage(game.getTeamBLogo(), teamFlagB, options);

        Button pointSpreadTeamA = (Button) view.findViewById(R.id.button_betongame_pointspread_team_a);
        Button pointSpreadTeamB = (Button) view.findViewById(R.id.button_betongame_pointspread_team_b);

        Button moneyLineTeamA = (Button) view.findViewById(R.id.button_betongame_money_line_team_a);
        Button moneyLineTeamB = (Button) view.findViewById(R.id.button_betongame_money_line_team_b);

        Button overTeamA = (Button) view.findViewById(R.id.button_betongame_over_team_a);
        Button overTeamB = (Button) view.findViewById(R.id.button_betongame_over_team_b);

//        Button underTeamA = (Button) view.findViewById(R.id.button_betongame_under_team_a);
//        Button underTeamB = (Button) view.findViewById(R.id.button_betongame_under_team_b);

        pointSpreadTeamA.setText(Html.fromHtml(game.getTeamAPointspread()));
        pointSpreadTeamB.setText(Html.fromHtml(game.getTeamBPointspread()));

        moneyLineTeamA.setText(Html.fromHtml(game.getTeamAMoneyline()));
        moneyLineTeamB.setText(Html.fromHtml(game.getTeamBMoneyline()));

//        overTeamA.setText(Html.fromHtml(game.getTeamAOverMoney()));
//        overTeamB.setText(Html.fromHtml(game.getTeamBOverMoney()));

        overTeamA.setText("-130 o");
        overTeamB.setText("-105 u");

//        underTeamA.setText(Html.fromHtml(game.getTeamAUnderMoney()));
//        underTeamB.setText(Html.fromHtml(game.getTeamBUnderMoney()));


    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnBetOnGameFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnBetOnGameFragmentInteractionListener");
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
    public interface OnBetOnGameFragmentInteractionListener {

        public void onBetOnGameFragmentInteraction(Uri uri);
    }

}