package com.example.eatfit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;

public class SearchActivity extends AppCompatActivity {
    public BottomNavigationView bottomNavigationView;
    Spinner sp_type;
    ImageView findBtn;
    Location myLocation;
    GoogleMap mMap;
    SupportMapFragment supportMapFragment;
    FusedLocationProviderClient fusedLocationProviderClient;
    double currentLat = 0, currentLong = 0;
    public int integer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        sp_type = findViewById(R.id.sp_type);
        findBtn = findViewById(R.id.findBtn);

        supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.google_map);

        //initializing type of place
        String[] placeList = {"atm","movie_theater", "restaurant"};
        //initialize array of name of place
        String[] placeName = {"ATM", "Movie theater", "Restaurant"};
        //put adapter on spinner
        sp_type.setAdapter(new ArrayAdapter<>(SearchActivity.this, android.R.layout.simple_spinner_dropdown_item, placeName));

        //initialize fused location
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        //check permission
        if (ActivityCompat.checkSelfPermission(SearchActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED) {
            //WHEN granted call method
            getCurrrentLocation();
        } else {
            //if permission denied ask for it
            ActivityCompat.requestPermissions(SearchActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
        }

        findBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                integer = sp_type.getSelectedItemPosition();
                //get selected position of spinner
                int i = sp_type.getSelectedItemPosition();


                //initialize url
                String url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json" + //url
                        "?location=" + currentLat + "," + currentLong + "&radius=5000" + //nearby radius
                        "&types=" + placeList[i] +
                        "&sensor=true" +
                        "&key=" + getResources().getString(R.string.google_map_key);


                //place task method to download json data
                new PlaceTask().execute(url);

            }
        });


        bottomNavigationView = findViewById(R.id.bottom_navigator);
        bottomNavigationView.setSelectedItemId(R.id.home);


        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        Toast.makeText(SearchActivity.this, "home!", Toast.LENGTH_SHORT).show();
                        return true;

                    case R.id.search:
                        return true;

                    case R.id.person:
                        startActivity(new Intent(getApplicationContext(), PersonActivity.class));
                        Toast.makeText(SearchActivity.this, "person!", Toast.LENGTH_SHORT).show();

                        return true;

                }
                return false;
            }
        });


    }

    public void getCurrrentLocation() {
        //Initialize task Location
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            return;
//        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if(location != null){
                    //when location is valid
                    //get lat
                    currentLat = location.getLatitude();

                    //get current long
                    currentLong = location.getLongitude();
                    //SYNC MAp
                    supportMapFragment.getMapAsync(new OnMapReadyCallback() {
                        @Override
                        public void onMapReady(@NonNull GoogleMap googleMap) {
                            //when map is ready
                            mMap = googleMap;
                            //zoom to current location on map
                            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                                    new LatLng(currentLat,currentLong), 13

                            ));
                            BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.location);
                            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(currentLat,currentLong),13));
                            MarkerOptions options = new MarkerOptions();
                            //set position
                            options.position(new LatLng(currentLat, currentLong));
                            //set title
                            options.title("you");
                            options.icon(icon);
                            //add marker on map
                            mMap.addMarker(options);
                        }
                    });

                }
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 44) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //WHEN PERMISSION IS GRANTED
                getCurrrentLocation();

            }
        }
    }

    private class PlaceTask extends AsyncTask<String,Integer,String> {

        @Override
        protected String doInBackground(String... strings) {
            String data = null;
            //initialize data
            try {
               data = downloadUrl(strings[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return data;
        }

        @Override
        protected void onPostExecute(String s) {
            //Execute parser task
            new ParserTask().execute(s);
        }
    }

    private String downloadUrl(String string) throws IOException {
        //initialize url
        URL url = new URL(string);
        //initialize connection
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        //connect
        connection.connect();
        //Initialize input stream
        InputStream stream = connection.getInputStream();
        //initialize buffer reader
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        //initialize string builder
        StringBuilder builder = new StringBuilder();
        //initialize string variable
        String line = "";
        while((line = reader.readLine()) != null){
            //append line
            builder.append(line);
        }
        //Get append data
        String data = builder.toString();
        //close reader and return data
        reader.close();
        return data;
    }

    private class ParserTask extends AsyncTask<String, Integer, List<HashMap<String, String>>> {

        @Override
        protected List<HashMap<String, String>> doInBackground(String... strings) {
            //create json parser class
            JsonParserr jsonParserr = new JsonParserr();

            //initalize hash map list
            List<HashMap<String,String>> mapList = null;
            //initialize json object
            JSONObject object = null;
            try {
                 object = new JSONObject(strings[0]);
                 //parse json object
                mapList = jsonParserr.parseResult(object);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            //return map list
            return mapList;

        }

        @Override
        protected void onPostExecute(List<HashMap<String, String>> hashMaps) {
            mMap.clear();
            //add yo hashmap

            for (int i=0; i<hashMaps.size();i++){
                //initialize hashmap
                HashMap<String,String> hashMapList = hashMaps.get(i);
                //get latitude and lng
                double lat = Double.parseDouble(hashMapList.get("lat"));
                double lng  = Double.parseDouble(hashMapList.get("lng"));

                //get name
                String name = hashMapList.get("name");
                //join lat and long
                LatLng latLng = new LatLng(lat, lng);

                //initialize marker options
                MarkerOptions options = new MarkerOptions();
                //set position
                options.position(latLng);
                //set title
                options.title(name);

                //add marker on map
                mMap.addMarker(options);
                BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.location);
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(currentLat,currentLong),13));
                MarkerOptions newoptions = new MarkerOptions();
                //set position
                newoptions.position(new LatLng(currentLat, currentLong));
                //set title
                newoptions.title("you");
                newoptions.icon(icon);
                //add marker on map
                mMap.addMarker(newoptions);


                    mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                        @Override

                        public  boolean onMarkerClick(@NonNull Marker marker) {
                            if (integer==2 ) {
                                String markerList = marker.getTitle();
                                Intent i = new Intent(SearchActivity.this, DetailsActivity.class);
                                i.putExtra("title", markerList);
                                startActivity(i);
                            }
                            return false;
                        }

                    });














            }
        }
    }
//    @Override
//    public void onLocationChanged(Location location){
//        myLocation = location;
//        if (myLocation!=null){
//            currentLat = location.getLatitude();
//            currentLong = location.getLongitude();
//
//            BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.location);
//            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(currentLat,currentLong),13));
//            MarkerOptions options = new MarkerOptions();
//            //set position
//            options.position(new LatLng(currentLat, currentLong));
//            //set title
//            options.title("you");
//            options.icon(icon);
//            //add marker on map
//            mMap.addMarker(options);
//
//            getNearby();
//        }
//
//    }
//
//    private void getNearby() {
//    }
}