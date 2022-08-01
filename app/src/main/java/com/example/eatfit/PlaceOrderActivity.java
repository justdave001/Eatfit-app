package com.example.eatfit;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;

import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;

public class PlaceOrderActivity extends AppCompatActivity {

     EditText inputName, inputEmail, inputAddress, inputCity, inputState, inputZip,inputCardNumber, inputCardExpiry, inputCardPin ;
     RecyclerView cartItemsRecyclerView;
    TextView tvSubtotalAmount, tvDeliveryChargeAmount, tvDeliveryCharge, tvTotalAmount, buttonPlaceYourOrder;
    private SwitchCompat switchDelivery;
    private boolean isDeliveryOn;
    PlaceOrderAdapter placeOrderAdapter;
    String body;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_order);
        String resname = getIntent().getStringExtra("resname");
        Restaurant restaurant = getIntent().getParcelableExtra("data");
        Log.d("final", restaurant.getMenus().toString());
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(resname);
        actionBar.setDisplayHomeAsUpEnabled(true);

        inputName = findViewById(R.id.inputName);
        inputEmail = findViewById(R.id.inputEmail);
        inputAddress = findViewById(R.id.inputAddress);
        inputCity = findViewById(R.id.inputCity);
        inputState = findViewById(R.id.inputState);
        inputZip = findViewById(R.id.inputZip);
        inputCardNumber = findViewById(R.id.inputCardNumber);
        inputCardExpiry = findViewById(R.id.inputCardExpiry);
        inputCardPin = findViewById(R.id.inputCardPin);
        tvSubtotalAmount = findViewById(R.id.tvSubtotalAmount);
        tvDeliveryChargeAmount = findViewById(R.id.tvDeliveryChargeAmount);
        tvDeliveryCharge = findViewById(R.id.tvDeliveryCharge);
        tvTotalAmount = findViewById(R.id.tvTotalAmount);
        buttonPlaceYourOrder = findViewById(R.id.buttonPlaceYourOrder);
        switchDelivery = findViewById(R.id.switchDelivery);

        cartItemsRecyclerView = findViewById(R.id.cartItemsRecyclerView);



        buttonPlaceYourOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPlaceOrderButtonClick(restaurant);
                List<Restaurant> add = restaurant.getMenus();
                Log.d("purchase", restaurant.getMenus().toString());
                String email = inputEmail.getText().toString();
                body = "Hi " +inputName.getText().toString()+", "+ "\n"+"\n"+"\n"+
                        "Thank you for your purchase at "
                        +resname+". Ordered items are: ";
                for (int i=0;i<=add.size()-1;i++){
                       body += "\n" +"    "+ add.get(i).getName()+": "+ "$"+add.get(i).getCost()+" X"+
                               add.get(i).getTotalInCart();
                }
                String ending = inputCardNumber.getText().toString();
                String substr=ending.substring(12,ending.length());
                body += "\n"+"Total: "+tvTotalAmount.getText().toString();
                body += "\n";
                body += "\n"+"paid for with card info ending in: "+ substr;

                String chooserTitle = "Eatfit";
                new Thread(new Runnable() {

                    @Override
                    public void run() {
                        try {
                            GMailSender sender = new GMailSender("3atfit@gmail.com",
                                    "tsivyypaphwkxgda");
                            sender.sendMail(resname+" "+"Order Confirmation", body,
                                    "Eatfit", email);
                        } catch (Exception e) {
                            Log.e("SendMail", e.getMessage(), e);
                        }
                    }

                }).start();


                ParseUser currentUser = ParseUser.getCurrentUser();
                ParseObject rname = new ParseObject("Restaurants");
                rname.put("user", currentUser);
                rname.put("res_name", resname);
                for (int i=0;i<=add.size()-1;i++){
                    rname.addUnique("menu_items", add.get(i).getName());
                }
                rname.saveInBackground();
                ParseQuery<ParseObject> query = ParseQuery.getQuery("Restaurants");
                query.whereEqualTo("user", currentUser);
                query.whereEqualTo("res_name", resname);
                query.findInBackground(new FindCallback<ParseObject>() {
                    @Override
                    public void done(List<ParseObject> objects, ParseException e) {
                        if (e==null){
                            for (ParseObject rname:objects){
                                if (rname.getString("res_name").equals(resname)
                                ) {
                                    for (int i=0;i<=add.size()-1;i++){
                                        rname.addUnique("menu_items", add.get(i).getName());
                                    }
                                    rname.saveInBackground();
                                }
                            }

                        }

                    }

                });

            }
        });

        switchDelivery.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    inputAddress.setVisibility(View.VISIBLE);
                    inputCity.setVisibility(View.VISIBLE);
                    inputState.setVisibility(View.VISIBLE);
                    inputZip.setVisibility(View.VISIBLE);
                    tvDeliveryChargeAmount.setVisibility(View.VISIBLE);
                    tvDeliveryCharge.setVisibility(View.VISIBLE);
                    isDeliveryOn = true;
                    calculateTotalAmount(restaurant);


                } else {
                    inputAddress.setVisibility(View.GONE);
                    inputCity.setVisibility(View.GONE);
                    inputState.setVisibility(View.GONE);
                    inputZip.setVisibility(View.GONE);
                    tvDeliveryChargeAmount.setVisibility(View.GONE);
                    tvDeliveryCharge.setVisibility(View.GONE);
                    isDeliveryOn = false;

                   calculateTotalAmount(restaurant);
                }
            }
        });
        initRecyclerView(restaurant);
        calculateTotalAmount(restaurant);
}



    private void calculateTotalAmount(Restaurant restaurant) {
        Double subTotalAmount = 0.0;
        for(Restaurant m : restaurant.getMenus()) {
            subTotalAmount += m.getCost() * m.getTotalInCart();
        }
        tvSubtotalAmount.setText("$"+String.format("%.2f", subTotalAmount));
        if(isDeliveryOn) {
            tvDeliveryChargeAmount.setText("$"+"20.00");
            subTotalAmount += 20.00;
        }
        tvTotalAmount.setText("$"+String.format("%.2f", subTotalAmount));
    }

    private void onPlaceOrderButtonClick(Restaurant restaurant) {
        if(TextUtils.isEmpty(inputName.getText().toString())) {
            inputName.setError("Please enter name ");
            return;
        } else if(isDeliveryOn && TextUtils.isEmpty(inputAddress.getText().toString())) {
            inputAddress.setError("Please enter address ");
            return;
        }else if(isDeliveryOn && TextUtils.isEmpty(inputCity.getText().toString())) {
            inputCity.setError("Please enter city ");
            return;
        }else if(isDeliveryOn && TextUtils.isEmpty(inputState.getText().toString())) {
            inputState.setError("Please enter zip ");
            return;
        }else if( TextUtils.isEmpty(inputCardNumber.getText().toString()) || inputCardNumber.getText().length() !=16){
            inputCardNumber.setError("Invalid card number ");
            return;
        }else if( TextUtils.isEmpty(inputCardExpiry.getText().toString())) {
            inputCardExpiry.setError("Please enter card expiry ");
            return;
        }else if( TextUtils.isEmpty(inputCardPin.getText().toString())) {
            inputCardPin.setError("Please enter card pin/cvv ");
            return;
        }

        //start success activity..
        String resname = getIntent().getStringExtra("resname");
        Intent i = new Intent(PlaceOrderActivity.this, OrderSuccessfulActivity.class);
        i.putExtra("resname", resname);
        startActivityForResult(i, 1000);
    }

    private void initRecyclerView(Restaurant restaurant) {
        cartItemsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        Log.e("menus",restaurant.getMenus().toString());
        placeOrderAdapter = new PlaceOrderAdapter(restaurant.getMenus());
        cartItemsRecyclerView.setAdapter(placeOrderAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if(requestCode == 1000) {
            setResult(Activity.RESULT_OK);
            finish();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home :
                finish();
            default:
                //do nothing
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setResult(Activity.RESULT_CANCELED);
        finish();
    }


}