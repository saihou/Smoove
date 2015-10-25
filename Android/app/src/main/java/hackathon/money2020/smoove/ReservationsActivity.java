package hackathon.money2020.smoove;


import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class ReservationsActivity extends AppCompatActivity {

    String merchantId;
    String merchantName;

    TextView restaurantName;
    TextView date;
    TextView time;
    TextView pax;

    Button cancel;
    Button confirm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservations);

        merchantId = getIntent().getStringExtra("merchant_id");
        merchantName = getIntent().getStringExtra("merchant_name");

        restaurantName = (TextView) findViewById(R.id.restaurantName);
        restaurantName.setText(merchantName);

        date = (TextView) findViewById(R.id.date);
        time = (TextView) findViewById(R.id.time);
        pax = (TextView) findViewById(R.id.pax);
        cancel = (Button) findViewById(R.id.btnCancel);
        confirm = (Button) findViewById(R.id.btnConfirm);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_reservations, menu);
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

    public void showTimePickerDialog(View v) {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getFragmentManager(), "timePicker");
    }

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getFragmentManager(), "datePicker");
    }

    public void cancel(View v) {
        this.finish();
    }

    public void confirmReservation(View v) {
        String currentTimeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
        String numPax = pax.getText().toString();
        String userId = "1";
        String reservationTime = date.getText().toString() + " " + time.getText().toString();

        HashMap<String, String> params = new HashMap<String,String>();
        params.put("user_id", userId);
        params.put("merchant_id", merchantId);
        params.put("pax", numPax);
        params.put("current_time_stamp", currentTimeStamp);
        params.put("reservation_time", reservationTime);

        JSONObject jsonObj = new JSONObject(params);
        System.out.println(jsonObj.toString());
        new PostToApis(jsonObj).execute(Utils.server_post_reservation);

        Toast.makeText(this, "Your request has been sent to the server", Toast.LENGTH_LONG).show();

        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish(); // call this to finish the current activity
    }
}
