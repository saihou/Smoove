package hackathon.money2020.smoove;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class SearchActivity extends AppCompatActivity {
    ListView listView;
    ImageButton btnSearch;
    EditText txtSearch;
    ListViewRow[] listOfRestaurants;
    ListViewRowAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        listView = (ListView) findViewById(R.id.listView);
        listOfRestaurants = new ListViewRow [] {};
        adapter = new ListViewRowAdapter(this, R.layout.list_view_item_row, listOfRestaurants);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String merchantName = ((TextView) view.findViewById(R.id.txtTitle)).getText().toString();
                String merchantId = ((TextView) view.findViewById(R.id.merchantId)).getText().toString();

                Intent intent = new Intent(getBaseContext(), ReservationsActivity.class);
                intent.putExtra("merchant_id", merchantId);
                intent.putExtra("merchant_name", merchantName);
                startActivity(intent);
            }
        });

        txtSearch = (EditText) findViewById(R.id.searchBar);
        txtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    performSearch();
                    return true;
                }
                return false;
            }
        });

        btnSearch = (ImageButton) findViewById(R.id.searchButton);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performSearch();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);
        getSupportActionBar().setTitle(getString(R.string.title_activity_search));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if (id == android.R.id.home) {
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void performSearch() {
        ///******///******////*****////*****/////****///*////*///
        ///*////////*////////*/////*///*////*///*///////*////*///
        ///******///******///*******///******///*///////******///
        ////////*///*////////*/////*///*/*//////*///////*////*///
        ///******///******///*/////*///*//***////****///*////*///
        String searchPhrase = txtSearch.getText().toString().trim();

        if (searchPhrase.length() > 2) {
            //search
            AsyncTask<String, Void, JSONArray> restCall = new CallApis(this).execute(Utils.server_restaurant);
            Toast.makeText(getBaseContext(), "Please wait...", Toast.LENGTH_SHORT).show();
        } else if (searchPhrase.length() > 0){
            Toast.makeText(getBaseContext(), "Type something longer damn it", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getBaseContext(), "You gotta type something", Toast.LENGTH_SHORT).show();
            txtSearch.clearFocus();
        }
    }

    public void setListOfRestaurants(JSONArray obj) {
        if (obj == null) return;
        Log.e("SearchA", obj.toString());

        listOfRestaurants = new ListViewRow[obj.length()];

        for (int i = 0; i < listOfRestaurants.length; i++) {
            JSONObject jsonObj = obj.optJSONObject(i);
            if (jsonObj!=null) {
                String merchant_id = jsonObj.optString("merchant_id");
                String title = jsonObj.optString("company_name");
                String desc = jsonObj.optString("desc");
                listOfRestaurants[i] = new ListViewRow(android.R.drawable.ic_menu_gallery, title, desc, merchant_id);
            } else {
                Log.e("SearchA", "Error");
            }
            }
    }

    public void updateUi() {
        adapter = new ListViewRowAdapter(this, R.layout.list_view_item_row, listOfRestaurants);
        listView.setAdapter(adapter);
    }
}
