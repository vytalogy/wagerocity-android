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

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.plego.wagerocity.R;
import com.plego.wagerocity.android.WagerocityPref;
import com.plego.wagerocity.android.model.Game;
import com.plego.wagerocity.android.model.Pick;
import com.plego.wagerocity.android.model.RestClient;
import com.plego.wagerocity.utils.AndroidUtils;

import java.text.ParseException;
import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

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

        try {
            mListner = (OnPicksOfPlayerAdapterListAdapterFragmentInteractionListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnGamesListAdapterFragmentInteractionListener");
        }
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

        final Game game = games.get(position);

//        if (convertView == null) {

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

        if (game.getIsPurchased()) {
            viewHolder.button.setText("Show Pick");
            viewHolder.button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ArrayList arrayList = new ArrayList();
                    try {
                        arrayList.add(createPickObject(game));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    Uri uri = Uri.parse(context.getString(R.string.uri_open_my_picks_fragment));
                    mListner.onPicksOfPlayerAdapterListAdapterFragmentInteraction(uri, arrayList, true);
                }
            });
        } else {
            viewHolder.button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("Buy Pick?")
                            .setContentText("You can buy this pick with $5!")
                            .setCancelText("Cancel")
                            .setConfirmText("Yes, Do It!")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog dialog) {
                                    dialog.dismiss();


                                    final ArrayList<Pick> arrayList = new ArrayList<>();
                                    try {
                                        arrayList.add(createPickObject(game));
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }

                                    RestClient restClient = new RestClient();
                                    restClient.getApiService().purchasePicksOfPlayer(
                                            new WagerocityPref(context).user().getUserId(),
                                            game.getUser().getUserId(),
                                            game.getBet().getBetId(),
                                            "3.99",
                                            new Callback<Response>() {
                                                @Override
                                                public void success(Response response, Response response2) {
                                                    game.setIsPurchased(true);

                                                    Uri uri = Uri.parse(context.getString(R.string.uri_open_my_picks_fragment));
                                                    mListner.onPicksOfPlayerAdapterListAdapterFragmentInteraction(uri, arrayList, false);
                                                }

                                                @Override
                                                public void failure(RetrofitError error) {

                                                }
                                            });
                                }
                            })
                            .showCancelButton(true)
                            .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.cancel();
                                }
                            })
                            .show();


                }
            });
        }

//            convertView.setTag(viewHolder);
//
//        } else {
//            viewHolder = (ViewHolder) convertView.getTag();
//        }


        if (game != null) {
            viewHolder.teamNameA.setText(game.getTeamAFullname());
            viewHolder.teamNameB.setText(game.getTeamBFullname());

            DisplayImageOptions options = new DisplayImageOptions.Builder()
                    .cacheInMemory(true) // default
                    .cacheOnDisk(true) // default
                    .showImageOnFail(AndroidUtils.getDrawableFromLeagueName(game.getLeagueName()))
                    .showImageForEmptyUri(AndroidUtils.getDrawableFromLeagueName(game.getLeagueName()))
                    .build();

            ImageLoader.getInstance().displayImage(game.getTeamALogo(), viewHolder.teamFlagA, options);
            ImageLoader.getInstance().displayImage(game.getTeamBLogo(), viewHolder.teamFlagB, options);

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

    private Pick createPickObject(Game game) throws ParseException {
        Pick pick = new Pick();

        pick.setTeamName(game.getBet().getTeamName());
        pick.setStartTime(game.getCstStartTime());
        pick.setMatchDet(game.getBet().getMatchDet());
        pick.setBetOt(game.getBet().getBetOt());
        pick.setPos(game.getBet().getPos());
        pick.setOddsVal(game.getBet().getOddsVal());
        pick.setStake(game.getBet().getStake());
        pick.setBetResult(game.getBet().getBetResult());
        pick.setTeamALogo(game.getTeamALogo());
        pick.setTeamBLogo(game.getTeamBLogo());

        return pick;
    }

    public interface OnPicksOfPlayerAdapterListAdapterFragmentInteractionListener {
        public void onPicksOfPlayerAdapterListAdapterFragmentInteraction(Uri uri, ArrayList<Pick> picks, boolean isPurchased);
//        public void onPicksOfPlayerPurchasePickAdapterListAdapterFragmentInteraction(Uri uri, ArrayList<Pick> picks);
    }
}
