package hackathon.money2020.smoove;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.support.v4.app.Fragment;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by sai on 10/24/15.
 */
public class TabbedFragment extends Fragment {
    public static final String ARG_PAGE = "ARG_PAGE";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private int mPage;
    ListView listViewTab;
    ListViewRow[] listOfTransactions;
    ListViewRowAdapter adapter;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param page Parameter 1.
     * @return A new instance of fragment PageFragment.
     */
    // TODO: Rename and change types and number of parameters
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
        TextView textView = (TextView) view.findViewById(R.id.fragmentView);

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);

        ListView listView = (ListView) view.findViewById(R.id.listViewTabs);

        //If Transactions page, hide FAB
        if (mPage == 2) {
            fab.hide();

            AsyncTask<String, Void, JSONArray> restCall = new CallApis().execute(Utils.server_transact);

            listOfTransactions = new ListViewRow [] {
                    new ListViewRow(R.drawable.ic_attach_money_black_36dp, "Koi Palace", "You paid $25 on 23/08/2015", "1")
            };

            adapter = new ListViewRowAdapter(getActivity(), R.layout.list_view_item_row, listOfTransactions);
            listView.setAdapter(adapter);

            if (listView.getCount() == 0) {
                textView.setText("You have no transactions yet!");
            } else {
                textView.setVisibility(View.GONE);
            }
        } else {
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getContext(), SearchActivity.class);
                    startActivity(intent);
                }
            });

            if (listView.getCount() == 0) {
                textView.setText("You have no reservations yet!");
            } else {
                textView.setText("UH WTF");
                //textView.setVisibility(View.GONE);
            }
        }
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
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
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }
}
