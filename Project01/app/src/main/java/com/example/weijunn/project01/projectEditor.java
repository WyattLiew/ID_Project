package com.example.weijunn.project01;

import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NavUtils;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.weijunn.project01.sqlitedata.PendingContract;
import com.example.weijunn.project01.sqlitedata.ProjectDbHelper;
import com.example.weijunn.project01.sqlitedata.Untils;
import com.example.weijunn.project01.MainActivity;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class projectEditor extends AppCompatActivity {

    EditText mProjectLocation, mContactName, mContactNumber, mDefect1, mDefect2, mDefect3, mPendingComment;

    ProjectDbHelper mDbHelper;
    private Spinner mProjectTypeSpinner;

    //Camera
    ImageView projectImage;
    Integer REQUEST_CAMERA = 1, SELECT_FILE = 0;
    public static final int REQUEST_PERMISSION = 200;
    String imageFilePath ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_editor);

        mDbHelper = new ProjectDbHelper(this);

        mProjectLocation = (EditText) findViewById(R.id.edit_project_location);
        mContactName =(EditText) findViewById(R.id.edit_client_name);
        mContactNumber =(EditText) findViewById(R.id.edit_client_contact);
        mDefect1 =(EditText) findViewById(R.id.defect_1);
        mDefect2 =(EditText) findViewById(R.id.defect_2);
        mDefect3 =(EditText) findViewById(R.id.defect_3);
        mPendingComment =(EditText) findViewById(R.id.pending_comment);

        mProjectTypeSpinner = (Spinner) findViewById(R.id.spinner_projectType);

        setupSpinner();

        projectImage = (ImageView) findViewById(R.id.project_img);

        //Check permission

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_PERMISSION);
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.floatingActionButton2);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SelectImage();
            }
        });

    }

    private void SelectImage() {

        final CharSequence[] items = {"Camera", "Gallery", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(projectEditor.this);
        builder.setTitle("Add Image");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (items[which].equals("Camera")) {

                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    if (intent.resolveActivity(getPackageManager()) != null) {
                        File photoFile = null;
                        try{
                            photoFile = createImageFile();
                        }
                        catch (IOException e){
                            e.printStackTrace();
                        }
                        if(photoFile!=null) {
                            Uri photoUri = FileProvider.getUriForFile(getApplicationContext(), "com.example.weijunn.project01.provider", photoFile);
                            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                            startActivityForResult(intent, REQUEST_CAMERA);
                        }
                    }

                } else if (items[which].equals("Gallery")) {
                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    startActivityForResult(intent.createChooser(intent,"Select File"),SELECT_FILE);
                    //startActivityForResult(intent, SELECT_FILE);

                } else if (items[which].equals("Cancel")) {
                    dialog.dismiss();

                }
            }
        });
        builder.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {

            if (requestCode == REQUEST_CAMERA) {

                //Bundle bundle = data.getExtras();
                //final Bitmap bmp = (Bitmap) bundle.get("data");
                //projectImage.setImageBitmap(bmp);

                projectImage.setImageURI(Uri.parse(imageFilePath));

            } else if (requestCode == SELECT_FILE) {
                Uri selectImageUri = data.getData();
                projectImage.setImageURI(selectImageUri);
            }
        }
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmsss", Locale.getDefault())
                .format(new Date());
        String imageFileName = "IMG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName, ".jpg", storageDir);
        imageFilePath = image.getAbsolutePath();

        return image;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == REQUEST_PERMISSION && grantResults.length > 0 ){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this,"Thanks for granting Permission", Toast.LENGTH_SHORT).show();;
            }
        }
    }
/** Insert data Spare
    private void insert_Pending(){
        String locationString = mProjectLocation.getText().toString().trim();
        String conNameString = mContactName.getText().toString().trim();
        String conNumString = mContactNumber.getText().toString().trim();
        int conNumInt = Integer.parseInt(conNumString);
        String defect1String = mDefect1.getText().toString().trim();
        String defect2String = mDefect2.getText().toString().trim();
        String defect3String = mDefect3.getText().toString().trim();
        String penCommentString = mPendingComment.getText().toString().trim();
        Bitmap imgBitmap = ((BitmapDrawable)projectImage.getDrawable()).getBitmap();

        ProjectDbHelper mDbHelper = new ProjectDbHelper(this);
        //Get the database in write mode
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

         //Create a ContentValues object where column names are the keys,
         // and Toto's pending attributes are the values

        ContentValues values = new ContentValues();
        values.put(ProjectDbHelper.COLUMN_PROJECT_LOCATION, locationString);
        values.put(ProjectDbHelper.COLUMN_CONTACT_NAME, conNameString);
        values.put(ProjectDbHelper.COLUMN_CONTACT_NUMBER, conNumInt);
        values.put(ProjectDbHelper.COLUMN_DEFECT_1, defect1String);
        values.put(ProjectDbHelper.COLUMN_DEFECT_2, defect2String);
        values.put(ProjectDbHelper.COLUMN_DEFECT_3, defect3String);
        values.put(ProjectDbHelper.COLUMN_DEFECT_COMMENTS, penCommentString);
        values.put(ProjectDbHelper.COLUMN_DEFECT_IMG,imgBitmap);

        // Insert a new row for Toto in the database, returning the ID of that new row.
        long dbInsert = db.insert(ProjectDbHelper.TABLE_NAME_PENDING,null,values);

        if(dbInsert != -1){
            Toast.makeText(this,"New row added, new row id: " + dbInsert, Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this,"Error ..",Toast.LENGTH_SHORT).show();
        }

        db.close();

    }
 **/

    private void setupSpinner() {
        // Create adapter for spinner. The list options are from the String array it will use
        // the spinner will use the default layout
        ArrayAdapter projectTypeSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.array_projectType_options, android.R.layout.simple_spinner_item);

        // Specify dropdown layout style - simple list view with 1 item per line
        projectTypeSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        // Apply the adapter to the spinner
        mProjectTypeSpinner.setAdapter(projectTypeSpinnerAdapter);

        // Set the integer mSelected to the constant values
        mProjectTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)) {
                    if (selection.equals(R.string.project_type1)) {
                        // type 1
                    }else if (selection.equals(R.string.project_type2)){
                        // type 2
                    } else {
                        // type 3
                    }
                }
            }

            // Because AdapterView is an abstract class, onNothingSelected must be defined
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                 // Unknown
            }

        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_editor.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_editor, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Save" menu option
            case R.id.action_save:
                //Create dialog to send email / store data
                final CharSequence[] items = {"Save and email", "Save only", "Cancel"};
                AlertDialog.Builder builder = new AlertDialog.Builder(projectEditor.this);
                builder.setTitle("Select options");
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (items[which].equals("Save and email")) {

                           // wait

                        } else if (items[which].equals("Save only")) {
                            String locationString = mProjectLocation.getText().toString().trim();
                            String conNameString = mContactName.getText().toString().trim();
                            String conNumString = mContactNumber.getText().toString().trim();
                            int conNumInt = Integer.parseInt(conNumString);
                            String defect1String = mDefect1.getText().toString().trim();
                            String defect2String = mDefect2.getText().toString().trim();
                            String defect3String = mDefect3.getText().toString().trim();
                            String penCommentString = mPendingComment.getText().toString().trim();
                            Bitmap imgBitmap = ((BitmapDrawable)projectImage.getDrawable()).getBitmap();
                            mDbHelper.insert_pending(locationString,conNameString,conNumInt,defect1String,defect2String,defect3String,penCommentString, Untils.getBytes(imgBitmap));
                            finish();

                        } else if (items[which].equals("Cancel")) {
                            dialog.dismiss();
                        }
                    }
                });
                builder.show();
                return true;
            // Respond to a click on the "Delete" menu option
            case R.id.action_delete:
                // Do nothing for now
                return true;
            // Respond to a click on the "Up" arrow button in the app bar
            case android.R.id.home:
                // Navigate back to parent activity (CatalogActivity)
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
