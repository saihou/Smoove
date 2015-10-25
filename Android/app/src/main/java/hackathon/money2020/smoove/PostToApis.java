package hackathon.money2020.smoove;

import android.os.AsyncTask;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by sai on 10/25/15.
 */
public class PostToApis extends AsyncTask<String, Void, String> {
    JSONObject jsonObj;

    public PostToApis(JSONObject json) {
        jsonObj = json;
    }
    @Override
    protected String doInBackground(String... urlString) {

        try {
            URL url = new URL(urlString[0]);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            OutputStream os = conn.getOutputStream();
            System.out.println(jsonObj.toString().getBytes());
            os.write(jsonObj.toString().getBytes());
            os.flush();

            if (conn.getResponseCode() != HttpURLConnection.HTTP_CREATED) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));

            String output;
            System.out.println("Output from Server .... \n");
            while ((output = br.readLine()) != null) {
                System.out.println(output);
            }

            conn.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "String";
    }

    @Override
    protected void onPostExecute(String result) {
    }
}
