package com.example.eatfit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PersonActivity extends AppCompatActivity {
    LinearLayout logout;
    public ChipNavigationBar bottomNavigationView;
    public static int RESULT_LOAD_CAMERA_IMAGE = 2;
    public static int RESULT_LOAD_GALLERY_IMAGE = 1;
    EditText etUsername;
    EditText etFirstname;
    EditText etLastname;
    EditText etPassword;
    Button updateBtn;
    ImageView imgPhoto;
    public String mCurrentPhotoPath;
    public File cameraImageFile;
    TextView userName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);

        ParseUser currentUser = ParseUser.getCurrentUser();

        bottomNavigationView = findViewById(R.id.bottom_navigator);
        imgPhoto = findViewById(R.id.imgPhoto);
        etUsername = findViewById(R.id.etUsername);
        etFirstname = findViewById(R.id.etFirstname);
        etLastname = findViewById(R.id.etLastname);
        etPassword = findViewById(R.id.etPassword);
        updateBtn = findViewById(R.id.updateBtn);
        userName = findViewById(R.id.userName);
        String name = currentUser.getString("f_name")+
               " "+ currentUser.getString("l_name");

        userName.setText(name);
        bottomNavigationView.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int id) {
                Activity activity = null;
                switch (id){
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        overridePendingTransition(0,0);
                        break;
                    case R.id.search:
                        startActivity(new Intent(getApplicationContext(), SearchActivity.class));
                        overridePendingTransition(0,0);
                        break;
                    case R.id.person:
                        break;
                }
            }


        });

        imgPhoto.setOnClickListener(chooseImageListener);
//        imgPhoto.setOnClickListener(uploadListener);

            updateBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String username = etUsername.getText().toString();
                    String firstname = etFirstname.getText().toString();
                    String lastname = etLastname.getText().toString();
                    String password = etPassword.getText().toString();


                    if (!username.isEmpty()) {
                        currentUser.put("username", username);
                    }
                    if (!firstname.isEmpty()) {
                        currentUser.put("f_name", firstname);
                    }
                    if (!lastname.isEmpty()) {
                        currentUser.put("l_name", lastname);
                    }
                    if (!password.isEmpty()) {
                        currentUser.put("password", password);
                    }


                    currentUser.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            setProgressBarIndeterminateVisibility(false);
                            if (e == null) {
                                Toast.makeText(PersonActivity.this, "user saved!", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                }
            });




            logout = findViewById(R.id.logout);
            logout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ParseUser.logOut();
                    Intent i = new Intent(PersonActivity.this, LoginActivity.class);
                    startActivity(i);
                }
            });

        }
    @Override
    public void onActivityResult (int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == PersonActivity.RESULT_OK) {

            if (requestCode == RESULT_LOAD_GALLERY_IMAGE && null != data) {
                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                Cursor cursor = getContentResolver().query(selectedImage,filePathColumn, null, null, null);
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                mCurrentPhotoPath = cursor.getString(columnIndex);
                cursor.close();

            } else if (requestCode == RESULT_LOAD_CAMERA_IMAGE) {
                mCurrentPhotoPath = cameraImageFile.getAbsolutePath();

            }

            File image = new File(mCurrentPhotoPath);
            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            Bitmap bitmap = BitmapFactory.decodeFile(image.getAbsolutePath(), bmOptions);
            imgPhoto.setImageBitmap(bitmap);
        }
    }
    public File createImageFile () throws IOException {

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = timeStamp + "_";

        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File folder = new File(storageDir.getAbsolutePath() + "/PlayIOFolder");

        if (!folder.exists()) {
            folder.mkdir();
        }

        cameraImageFile = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                folder      /* directory */
        );

        return cameraImageFile;

    }
    View.OnClickListener chooseImageListener =  new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            dialogChooseFrom();

        }
    };

    View.OnClickListener uploadListener =  new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            byte[] image = null;

            try {
                image = readInFile(mCurrentPhotoPath);
            }
            catch(Exception e) {
                e.printStackTrace();

            }

            // Create the ParseFile
            ParseFile file = new ParseFile("picturePath", image);
            // Upload the image into Parse Cloud
            file.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    if(e!=null){
                        Toast.makeText(PersonActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                }
            });

            ParseObject imgupload = new ParseObject("User");

            imgupload.put("Image", "picturePath");

            imgupload.put("ImageFile", file);

            imgupload.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    if(e==null){

                    }else {
                        Toast.makeText(PersonActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }
    };

    public void dialogChooseFrom(){

        final CharSequence[] items={"From Gallery","From Camera"};

        AlertDialog.Builder chooseDialog =new AlertDialog.Builder(this);
        chooseDialog.setTitle("Pick your choice").setItems(items, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                if(items[which].equals("From Gallery")){

                    Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(galleryIntent, RESULT_LOAD_GALLERY_IMAGE);

                } else {

                    try {
                        Intent cameraIntent = new Intent();
                        cameraIntent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
                        File photoFile = createImageFile();
                        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT,Uri.fromFile(photoFile));
                        startActivityForResult(cameraIntent, RESULT_LOAD_CAMERA_IMAGE);

                    } catch (IOException e) {

                        e.printStackTrace();
                    }
                }
            }
        });

        chooseDialog.show();
    }

    public byte[] readInFile(String path) throws IOException {

        byte[] data = null;
        File file = new File(path);
        InputStream input_stream = new BufferedInputStream(new FileInputStream(file));
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        data = new byte[16384]; // 16K
        int bytes_read;

        while ((bytes_read = input_stream.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, bytes_read);
        }

        input_stream.close();
        return buffer.toByteArray();
    }

}



