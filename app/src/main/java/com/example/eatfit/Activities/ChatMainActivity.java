package com.example.eatfit.Activities;

import static androidx.core.content.ContextCompat.startActivity;

import static com.parse.Parse.getApplicationContext;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.ColorInt;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import com.example.eatfit.R;
import com.google.android.material.snackbar.Snackbar;
import com.thebluealliance.spectrum.SpectrumDialog;

import java.util.Random;


import retrofit2.http.GET;

public class ChatMainActivity extends AppCompatActivity {

     String COLOUR_PICKER_TAG = "Avatar Colour Picker";
    public static final String SHARED_PREFS_FILE = "NearbyChatPreferences";
    public static final String USERNAME_KEY = "username";
    public static final String AVATAR_COLOUR_KEY = "avatar_colour";
    public static Context mainContext;
    public static SharedPreferences sharedPreferences;

    private EditText mUsernameField;
    private ImageButton mGenerateUsernameButton;
    private ImageButton mPickAvatarColourButton;
    private Button mEnterChatButton;
    private static Integer sCurrentAvatarColour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set up the viw and toolbar
        setContentView(R.layout.activity_chat_main);

        // Get the main context
        mainContext = getApplicationContext();

        // Get the buttons and fields from the view
        mUsernameField = findViewById(R.id.username_field);
        mGenerateUsernameButton =  findViewById(R.id.button_username_generate);
        mPickAvatarColourButton = findViewById(R.id.button_avatar_colour_picker);
        mEnterChatButton =  findViewById(R.id.button_enter_chat);

        // Get any saved username and avatar colour from the shared preference file
        sharedPreferences = getSharedPreferences(SHARED_PREFS_FILE, Context.MODE_PRIVATE);
        String username = sharedPreferences.getString(USERNAME_KEY, "");
        String avatarColour = sharedPreferences.getString(AVATAR_COLOUR_KEY, "");

        // Set the username field if the user had saved one previously
        if (!username.isEmpty()){
            mUsernameField.setText(username);
            mUsernameField.setSelection(mUsernameField.getText().length());
        }


        sCurrentAvatarColour = ContextCompat.getColor(getApplicationContext(), R.color.md_pink_500);


        if (!avatarColour.isEmpty() && !avatarColour.equals("")){
            try{
                // Try and parse the colour into a colour int
                sCurrentAvatarColour = Color.parseColor(avatarColour);
                // Set the background colour of the mPickAvatarColourButton to the users preference
                GradientDrawable buttonBackgroundShape = (GradientDrawable) mPickAvatarColourButton.getBackground();
                buttonBackgroundShape.setColor(sCurrentAvatarColour);
            } catch (IllegalArgumentException e){
                // Otherwise use the default md_pink_500 colour from the resources
                sCurrentAvatarColour = ContextCompat.getColor(getApplicationContext(), R.color.md_pink_500);
            }
        }

        // Generate a random username when the mGenerateUsernameButton is clicked
        mGenerateUsernameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mUsernameField.setText(generateUsername());
                mUsernameField.setSelection(mUsernameField.getText().length());
                // Hide the soft keyboard if it was up
                hideSoftKeyboard(ChatMainActivity.this, view);
            }
        });

        // Launch a colour picker and select the avatar colour when mPickAvatarColourButton is clicked
        mPickAvatarColourButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchColourPickerDialog(sCurrentAvatarColour);
                // Hide the soft keyboard if it was up
                hideSoftKeyboard(ChatMainActivity.this, view);
            }
        });

        // Enter the chat room when mEnterChatButton is clicked
        mEnterChatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get the username and sCurrentAvatarColour
                String username = mUsernameField.getText().toString();
                String avatarColour = Integer.toHexString(sCurrentAvatarColour);

                // If the username is empty prompt the user and don't enter the chat
                if (username.isEmpty() || username.equals(" ")){
                    Snackbar.make(mEnterChatButton, getString(R.string.error_empty_username), Snackbar.LENGTH_SHORT).show();
                } else {
                    // Ensure the avatarColour string starts with # prior to sending and saving
                    if (!avatarColour.startsWith("#")){
                        avatarColour = "#" + avatarColour;
                    }

                    // Save the username and sCurrentAvatarColour in the shared preferences
                    storeUsernameAndAvatarColour(username, avatarColour);

                    // Enter the chat with the username and avatarColour sent to the ChatActivity
                    Intent enterChatIntent = new Intent(ChatMainActivity.this, ChatActivity.class);
                    enterChatIntent.putExtra(USERNAME_KEY, username);
                    Log.d("username", username);
                    enterChatIntent.putExtra(AVATAR_COLOUR_KEY, avatarColour);
                    startActivity(enterChatIntent);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

        return super.onOptionsItemSelected(item);
    }

    /**
     * Generate a random username drawing from an array of moods and adjectives and animal names.
     * @return a String of the form "Mood/Adjective Animal"
     */
    private String generateUsername(){
        String[] moodsAndAdjectivesArray = getApplicationContext().getResources().getStringArray(R.array.moods_and_adjectives);
        String[] animalsArray = getApplicationContext().getResources().getStringArray(R.array.animals);
        return moodsAndAdjectivesArray[new Random().nextInt(moodsAndAdjectivesArray.length)] + " " +
                animalsArray[new Random().nextInt(animalsArray.length)];
    }

    /**
     * Launch the colour picker dialog using the Spectrum Colour picker
     * https://github.com/the-blue-alliance/spectrum
     */
    private void launchColourPickerDialog(Integer currentColour){

        new SpectrumDialog.Builder(getApplicationContext())
                .setColors(R.array.avatar_colours)
                .setSelectedColor(currentColour)
                .setDismissOnColorSelected(true)
                .setOnColorSelectedListener(new SpectrumDialog.OnColorSelectedListener() {
                    @Override public void onColorSelected(boolean positiveResult, @ColorInt int color) {
                        if (positiveResult) {
                            // Change the button colour to the selected colour.
                            GradientDrawable buttonBackgroundShape = (GradientDrawable) mPickAvatarColourButton.getBackground();
                            buttonBackgroundShape.setColor(color);
                            // Update the sCurrentAvatarColour
                            sCurrentAvatarColour = color;
                        }
                    }

                }).build().show(getSupportFragmentManager(),  COLOUR_PICKER_TAG);
    }

    /**
     * Hide the software keyboard from the view
     */
    private static void hideSoftKeyboard(Activity activity, View view){
        InputMethodManager mInputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        mInputMethodManager.hideSoftInputFromWindow(view.getApplicationWindowToken(), 0);
    }

    /**
     * Store the username and avatarColour selections in the SharedPreference file to persist the user's choices.
     */
    private void storeUsernameAndAvatarColour(String username, String avatarColour){
        // Store the values in the SharedPreferences file
        sharedPreferences.edit().putString(USERNAME_KEY, username).apply();
        sharedPreferences.edit().putString(AVATAR_COLOUR_KEY, avatarColour).apply();
    }
}