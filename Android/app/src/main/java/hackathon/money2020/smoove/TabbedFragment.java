package hackathon.money2020.smoove;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by sai on 10/24/15.
 */
public class TabbedFragment extends Fragment {
    public static final String ARG_PAGE = "ARG_PAGE";
    public static final int PAGE_TRANSACTIONS = 2;

    private OnFragmentInteractionListener mListener;
    public int mPage;
    ListView listView;
    ListViewRow[] listOfTransactions;
    ListViewRow[] listOfReservations;
    ListViewRowAdapter adapter;
    TextView textView;

    public static TabbedFragment newInstance(int page) {
        TabbedFragment fragment = new TabbedFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        fragment.setArguments(args);
        return fragment;
    }

    public TabbedFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(ARG_PAGE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tabbed, container, false);
        textView = (TextView) view.findViewById(R.id.fragmentView);

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);

        listView = (ListView) view.findViewById(R.id.listViewTabs);

        //If Transactions page, hide FAB
        if (mPage == PAGE_TRANSACTIONS) {
            fab.hide();
            new CallApis(this).execute(Utils.server_transact);
            listOfTransactions = new ListViewRow [] {};
            updateUi();
        } else {
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getContext(), SearchActivity.class);
                    startActivity(intent);
                }
            });
            new CallApis(this).execute(Utils.server_reserve);
            listOfReservations = new ListViewRow[]{};
            updateUi();
        }
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
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

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

    public void setListOfTransactions(JSONArray obj) {
        if (obj == null) {
            listOfTransactions = new ListViewRow[2];
            listOfTransactions[0] = new ListViewRow("The Grill House - $36.10", "You were here on 2015-10-24 20:20:20", "");
            listOfTransactions[1] = new ListViewRow("Everything with Fries - $9.50", "You were here on 2015-10-31 20:20:20", "");
            return;
        }
        Log.e("SearchA", obj.toString());

        listOfTransactions = new ListViewRow[obj.length()];

        for (int i = 0; i < listOfTransactions.length; i++) {
            JSONObject jsonObj = obj.optJSONObject(i);
            if (jsonObj!=null) {
                String date = jsonObj.optString("txn_date");
                String merchant_name = jsonObj.optString("merchant_name");
                String tips = jsonObj.optString("txn_tip");
                String total = jsonObj.optString("txn_amount");

                String title = merchant_name + " - $" + total;
                String desc = "You were here on " + date;
                listOfTransactions[i] = new ListViewRow(title, desc, "");
            } else {
                Log.e("TabbedF", "Error");
            }
        }
    }

    public void setListOfReservations(JSONArray obj) {
        if (obj == null) {
            listOfReservations = new ListViewRow[3];
            listOfReservations[0] = new ListViewRow("Fish and Co. for 5", "Reservation at 2015-11-10 20:20:20", "");
            listOfReservations[1] = new ListViewRow("Din Tai Fung for 2", "Reservation at 2015-11-23 12:00:00", "");
            listOfReservations[2] = new ListViewRow("Koi Palace for 3", "Reservation at 2015-12-31 23:59:59", "");
            return;
        }
        Log.e("SearchA", obj.toString());

        listOfReservations = new ListViewRow[obj.length()];

        for (int i = 0; i < listOfReservations.length; i++) {
            JSONObject jsonObj = obj.optJSONObject(i);
            if (jsonObj!=null) {
                String merchant_name = jsonObj.optString("merchant_name");
                String pax = jsonObj.optString("pax");
                String rsvn_time = jsonObj.optString("rsvn_time");
                String title = merchant_name + " for " + pax;
                String desc = "Reservation at: "+rsvn_time.substring(0, rsvn_time.length()-3);
                listOfReservations[i] = new ListViewRow(title, desc, "");
            } else {
                Log.e("TabbedF", "Error");
            }
        }
    }


    public void updateUi() {
        if (mPage == PAGE_TRANSACTIONS) {
            adapter = new ListViewRowAdapter(getActivity(), R.layout.list_view_item_row, listOfTransactions);
            listView.setAdapter(adapter);

            if (listView.getCount() == 0) {
                textView.setText("You have no transactions yet!");
            } else {
                textView.setVisibility(View.GONE);
            }
        } else {
            adapter = new ListViewRowAdapter(getActivity(), R.layout.list_view_item_row, listOfReservations);
            listView.setAdapter(adapter);

            if (listView.getCount() == 0) {
                textView.setText("You have no reservations yet!");
            } else {
                textView.setVisibility(View.GONE);
            }
        }


    }
}
