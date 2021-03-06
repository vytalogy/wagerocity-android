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
import com.plego.wagerocity.android.activities.DashboardActivity;
import com.plego.wagerocity.android.model.MyPool;
import com.plego.wagerocity.android.model.Pool;
import com.plego.wagerocity.android.model.PoolMember;
import com.plego.wagerocity.android.model.RestClient;
import com.plego.wagerocity.android.model.User;
import com.plego.wagerocity.utils.AndroidUtils;

import java.text.ParseException;
import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by haris on 08/04/15.
 */
public class PoolsListAdapter extends BaseAdapter {

    private OnPoolsListAdapterFragmentInteractionListener mListner;
    private ArrayList<Pool> pools;
    private Context context;
    private Boolean isMyPools;

    public PoolsListAdapter(ArrayList<Pool> pools, Context context, Boolean isMyPools) {
        this.pools = pools;
        this.context = context;
        this.isMyPools = isMyPools;

        try {
            mListner = (OnPoolsListAdapterFragmentInteractionListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnPoolsListAdapterFragmentInteractionListener");
        }
    }

    @Override
    public int getCount() {
        return this.pools.size();
    }

    @Override
    public Pool getItem(int position) {
        return this.pools.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;

//        if (convertView == null) {

            viewHolder = new ViewHolder();

            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = inflater.inflate(R.layout.layout_cell_pools, parent, false);

            viewHolder.textViewPoolName = (TextView) convertView.findViewById(R.id.textview_pools_cell_name);
            viewHolder.textViewStatus = (TextView) convertView.findViewById(R.id.textview_pool_cell_pool_open_close_status);
            viewHolder.textViewStartDate = (TextView) convertView.findViewById(R.id.textview_pool_cell_pool_start_date);
            viewHolder.textViewEndDate = (TextView) convertView.findViewById(R.id.textview_pool_cell_pool_end_date);
            viewHolder.button = (Button) convertView.findViewById(R.id.button_pool_join_unjoin);

            viewHolder.button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    final Pool pool = pools.get(position);

                    if (Integer.parseInt(pool.getAmount()) > 0) {
                        new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
                                .setTitleText("Are you sure?")
                                .setContentText("$" + pool.getAmount() + " will be deducted from your account, do you still want to join?")
                                .setConfirmText("Yes!")
                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sDialog) {
                                        sDialog.dismissWithAnimation();
                                        joinPool(pool);
                                        final WagerocityPref pref = new WagerocityPref(context);

                                        new RestClient().getApiService().getUser(pref.facebookID(), new Callback<User>() {
                                            @Override
                                            public void success(User user, Response response) {
                                                pref.setUser(user);
                                                AndroidUtils.updateStats((DashboardActivity)context);
                                            }

                                            @Override
                                            public void failure(RetrofitError error) {

                                            }
                                        });

//
//                                        new RestClient().getApiService().consumeCredits(user.getUserId(), Float.parseFloat(pool.getAmount()), new Callback<User>() {
//                                            @Override
//                                            public void success(User user, Response response) {
//                                                pref.setUser(user);
//                                                AndroidUtils.updateStats((DashboardActivity)context);
//                                            }
//
//                                            @Override
//                                            public void failure(RetrofitError error) {
//                                                AndroidUtils.showErrorDialog(error, context);
//                                            }
//                                        });

                                    }
                                })
                                .setCancelText("Cancel")
                                .showCancelButton(true)
                                .show();
                    } else {
                        joinPool(pool);
                    }

                }

            });


//            convertView.setTag(viewHolder);
//
//        } else {
//            viewHolder = (ViewHolder) convertView.getTag();
//        }

        Pool pool = this.pools.get(position);

        if (pool != null) {
            viewHolder.textViewPoolName.setText(pool.getName());
            viewHolder.textViewStatus.setText(pool.getPrivacy());
            try {
                viewHolder.textViewStartDate.setText(AndroidUtils.getFormatedDateMMHHYYYY(pool.getFromDate()));
                viewHolder.textViewEndDate.setText(AndroidUtils.getFormatedDateMMHHYYYY(pool.getToDate()));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            if (pool.isJoined()) {
                viewHolder.button.setText("Joined");
                viewHolder.button.setEnabled(false);
//                viewHolder.button.setVisibility(View.INVISIBLE);
            }
        }

        return convertView;
    }

    private void joinPool(Pool pool) {

        final SweetAlertDialog pDialog = AndroidUtils.showDialog(
                "Loading",
                null,
                SweetAlertDialog.PROGRESS_TYPE,
                context
        );

        final RestClient restClient = new RestClient();

        restClient.getApiService().joinPool(new WagerocityPref(context).user().getUserId(), pool.getPoolId(), new Callback<ArrayList<Pool>>() {
            @Override
            public void success(ArrayList<Pool> pools, Response response) {
                restClient.getApiService().getMyPools(new WagerocityPref(context).user().getUserId(), new Callback<ArrayList<MyPool>>() {
                    @Override
                    public void success(ArrayList<MyPool> myPools, Response response) {

                        pDialog.dismiss();

                        Uri uri = Uri.parse(context.getString(R.string.uri_open_my_pools_fragment));
                        mListner.onPoolsListAdapterFragmentInteraction(uri, myPools);
                    }

                    @Override
                    public void failure(RetrofitError error) {

                        pDialog.dismiss();
                        AndroidUtils.showErrorDialog(error, context);
                    }
                });
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }

    public void consumeCreditsAPI (Float credits) {


    }

    class ViewHolder {
        TextView textViewPoolName;
        TextView textViewStatus;
        TextView textViewStartDate;
        TextView textViewEndDate;
        Button button;
    }


    public interface OnPoolsListAdapterFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onPoolsListAdapterFragmentInteraction(Uri uri, ArrayList<MyPool> pools);
    }
}
