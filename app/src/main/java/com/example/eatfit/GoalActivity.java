package com.example.eatfit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.os.Bundle;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class GoalActivity extends AppCompatActivity {
    AppCompatButton gWeight;
    AppCompatButton lWeight;
    AppCompatButton sMoney;
    String latitude;
    String longitude;
    String quote_ids;
    String charset = "UTF-8";

    public String BASE_URL = "https://mealme-4.mealme.ai";
    public String SEARCH_ENDPOINT = "/restaurants/search/store";
    public String MENU_ENDPOINT = "/restaurants/details/menu";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goal);

        String query = null;

        try {
            query = String.format("param1=%s&param2=%s",
                        URLEncoder.encode(latitude, charset),
                        URLEncoder.encode(longitude, charset),
                        URLEncoder.encode(quote_ids, charset));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        URL request_url = null;
        try {
            request_url = new URL(BASE_URL);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        HttpURLConnection http_conn = null;
        try {
            http_conn = (HttpURLConnection)request_url.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            request_url = new URL(BASE_URL+SEARCH_ENDPOINT);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        URLConnection connection = null;
        try {
            connection = new URL(SEARCH_ENDPOINT + "?" + query).openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
        connection.setRequestProperty("Accept-Charset", charset);
        try {
            InputStream response = connection.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }


//        print("Running...")
//    # Request a list of stores
//                search_parameters = A({"query": latitide, longitude})
//        search_resp = requests.get(f"{BASE_URL}{SEARCH_ENDPOINT}?{search_parameters}", headers=HEADERS)
//        if search_resp.status_code != 200:
//        print(f"Search API Failed with status {search_resp.status_code} and response: {search_resp.text}")
//        return {}
//        stores = search_resp.json().get("restaurants", [])
//        for store in stores:
//        for quote_id in store.get("quote_ids", []):
//            # Fetch menu for this quote
//                menu_parameters = urlencode({"quote_id": quote_id, "user_latitude": latitude, "user_longitude": longitude})
//        menu_resp = requests.get(f"{BASE_URL}{MENU_ENDPOINT}?{menu_parameters}", headers=HEADERS)
//        if menu_resp.status_code != 200:
//        print(f"Menu API Failed with status {menu_resp.status_code} and response: {menu_resp.text}")
//            else:
//                # Successfully found an available menu!
//        return menu_resp.json()
    }


}