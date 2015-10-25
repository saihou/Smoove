package hackathon.money2020.smoove;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import java.util.ArrayList;
import java.util.Calendar;

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
                String item = ((TextView) view.findViewById(R.id.txtTitle)).getText().toString();
                Toast.makeText(getBaseContext(), item, Toast.LENGTH_SHORT).show();
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
            addToListView();
            Toast.makeText(getBaseContext(), "Searching for " + searchPhrase + "!", Toast.LENGTH_SHORT).show();
        } else if (searchPhrase.length() > 0){
            Toast.makeText(getBaseContext(), "Type something longer damn it", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getBaseContext(), "You gotta type something", Toast.LENGTH_SHORT).show();
            txtSearch.clearFocus();
        }
    }

    public void addToListView() {
        //listOfRestaurants = GETJSONOBJECTFROMSERVER
        //hardcode for now
        listOfRestaurants = new ListViewRow[] {
                new ListViewRow(android.R.drawable.ic_menu_gallery, "Denny's", "A fast food restaurant that seems good but I haven't got the chance to eat"),
                new ListViewRow(android.R.drawable.ic_menu_add, "Koi Palace", "Bloody expensive and bloody good Chinese food")
        };
        adapter = new ListViewRowAdapter(this, R.layout.list_view_item_row, listOfRestaurants);
        listView.setAdapter(adapter);
    }
}
