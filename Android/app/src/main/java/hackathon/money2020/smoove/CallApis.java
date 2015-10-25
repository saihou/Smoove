package hackathon.money2020.smoove;

import android.app.Activity;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.util.Log;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by sai on 10/24/15.
 */
public class CallApis extends AsyncTask<String, Void, JSONArray> {

    Activity callingActivity;
    Fragment callingFragment;
    public CallApis(Activity activity) {
        callingActivity = activity;
        callingFragment = null;
    }
    public CallApis(Fragment fragment) {
        callingFragment = fragment;
        callingActivity = null;
    }
    public CallApis() {
    }
    @Override
    protected JSONArray doInBackground(String... urlString) {

        String resultToDisplay = "";
        JSONArray json;

        // HTTP Get
        try {
            URL url = new URL(urlString[0]);
            Log.d("TAG", "Connecting to ... " + url.toString());
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null)
                resultToDisplay += inputLine;
            in.close();

            json = new JSONArray(resultToDisplay);
        } catch (Exception e ) {
            System.out.println(e.getMessage());
            return null;
        }
        return json;
    }

    @Override
    protected void onPostExecute(JSONArray result) {
        if (callingActivity != null) {
            if (callingActivity instanceof SearchActivity) {
                SearchActivity activity = (SearchActivity) callingActivity;
                activity.setListOfRestaurants(result);
                activity.updateUi();
            }
        }
        if (callingFragment != null && callingFragment instanceof TabbedFragment) {
            TabbedFragment fragment = (TabbedFragment) callingFragment;
            if (fragment.mPage == 2) {
                fragment.setListOfTransactions(result);
            } else {
                fragment.setListOfReservations(result);
            }
            fragment.updateUi();
        }
    }
}