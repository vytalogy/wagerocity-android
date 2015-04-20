package com.plego.wagerocity.android.adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.plego.wagerocity.R;
import com.plego.wagerocity.android.model.Game;
import com.plego.wagerocity.android.model.LeaderboardPlayer;
import com.plego.wagerocity.utils.AndroidUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by haris on 21/04/15.
 */
public class PicksOfPlayerAdapter extends BaseAdapter {

    private OnPicksOfPlayerAdapterListAdapterFragmentInteractionListener mListner;
    private ArrayList<Game> games;
    private Context context;

    public PicksOfPlayerAdapter(ArrayList<Game> games, Context context) {
        this.games = games;
        this.context = context;
    }

    class ViewHolder {
        TextView teamNameA;
        TextView teamNameB;
        ImageView teamFlagA;
        ImageView teamFlagB;
        Button button;

        TextView pointSpreadA;
        TextView pointSpreadB;

        TextView moneyLineA;
        TextView moneyLineB;

        TextView overA;
        TextView underA;
        TextView total;
    }

    @Override
    public int getCount() {
        return games.size();
    }

    @Override
    public Game getItem(int position) {
        return games.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;

        if (convertView == null) {

            viewHolder = new ViewHolder();

            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = inflater.inflate(R.layout.layout_cell_player_games_picks, parent, false);

            viewHolder.teamNameA = (TextView) convertView.findViewById(R.id.textview_player_games_team_a_name);
            viewHolder.teamNameB = (TextView) convertView.findViewById(R.id.textview_player_games_team_b_name);
            viewHolder.teamFlagA = (ImageView) convertView.findViewById(R.id.imageview_player_games_team_a_flag);
            viewHolder.teamFlagB = (ImageView) convertView.findViewById(R.id.imageview_player_games_team_b_flag);

            viewHolder.pointSpreadA = (TextView) convertView.findViewById(R.id.textview_player_games_pointspread_team_a);
            viewHolder.pointSpreadB = (TextView) convertView.findViewById(R.id.textview_player_games_pointspread_team_b);

            viewHolder.moneyLineA = (TextView) convertView.findViewById(R.id.textview_player_games_money_line_team_a);
            viewHolder.moneyLineB = (TextView) convertView.findViewById(R.id.textview_player_games_money_line_team_b);

            viewHolder.overA = (TextView) convertView.findViewById(R.id.textview_player_games_over_team_a);
            viewHolder.underA = (TextView) convertView.findViewById(R.id.textview_player_games_over_team_b);
            viewHolder.total = (TextView) convertView.findViewById(R.id.textview_player_games_over_under);

            viewHolder.button = (Button) convertView.findViewById(R.id.button_player_games_show_bet);

            viewHolder.button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Game game = games.get(position);


        if (game != null) {
            viewHolder.teamNameA.setText(game.getTeamAFullname());
            viewHolder.teamNameB.setText(game.getTeamBFullname());

            Picasso.with(context)
                    .load(game.getTeamALogo())
                    .placeholder(AndroidUtils.getDrawableFromLeagueName(game.getLeagueName()))
                    .error(AndroidUtils.getDrawableFromLeagueName(game.getLeagueName()))
                    .into(viewHolder.teamFlagA);

            Picasso.with(context)
                    .load(game.getTeamBLogo())
                    .placeholder(AndroidUtils.getDrawableFromLeagueName(game.getLeagueName()))
                    .error(AndroidUtils.getDrawableFromLeagueName(game.getLeagueName()))
                    .into(viewHolder.teamFlagB);

            viewHolder.pointSpreadA.setText(game.getTeamAOdd().getPointMid() + " (" + AndroidUtils.getSignedOddValue(game.getTeamAOdd().getPoint()) + ")");
            viewHolder.pointSpreadB.setText(game.getTeamBOdd().getPointMid() + " (" + AndroidUtils.getSignedOddValue(game.getTeamBOdd().getPoint()) + " )");

            viewHolder.moneyLineA.setText(AndroidUtils.getSignedOddValue(game.getTeamAOdd().getMoney()));
            viewHolder.moneyLineB.setText(AndroidUtils.getSignedOddValue(game.getTeamBOdd().getMoney()));

            viewHolder.overA.setText(AndroidUtils.getSignedOddValue(game.getTeamAOdd().getOver()));
            viewHolder.underA.setText(AndroidUtils.getSignedOddValue(game.getTeamAOdd().getUnder()));

            viewHolder.total.setText("Over \n" + "| " + game.getTeamAOdd().getTotalMid() + " |\n" + "Under");

        }

        return convertView;
    }



    public interface OnPicksOfPlayerAdapterListAdapterFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onPicksOfPlayerAdapterListAdapterFragmentInteraction(Uri uri, ArrayList<Game> games);
    }
}
