package appdorks.tk.teamironchamps54thbranchrajendernagar;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddMember extends AppCompatActivity
{

    public static final String TAG = "AddMember";
    public static final int REQUEST_CODE = 1100;

    private ImageButton mImageButton;
    private ImageView mImageView;
    private Bitmap imageBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_member);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mImageButton = findViewById(R.id.imgBtn_photo);
        mImageView = findViewById(R.id.img_photo);

        mImageButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                /*Snackbar.make(v, "photo to be captured here", Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show();*/
                dispatchTakePictureIntent();
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_save);
        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onRestart()
    {
        super.onRestart();
        startActivity(new Intent(getApplicationContext(), AddMember.class));
    }

    private void dispatchTakePictureIntent()
    {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null)
        {
            startActivityForResult(takePictureIntent, REQUEST_CODE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK)
        {
            Toast.makeText(this, "image captured", Toast.LENGTH_SHORT).show();
            Log.i(TAG, "onActivityResult: image captured via CameraIntent");

            Bundle extras = data.getExtras();
            if (extras == null)
            {
                Toast.makeText(this, "bundle is empty", Toast.LENGTH_SHORT).show();
                Log.e(TAG, "onActivityResult: bundle returned empty");
            }
            else
            {
                imageBitmap = (Bitmap) extras.get("data");
                Log.i(TAG, "onActivityResult: bundle received with data");

                //mImageButton.setVisibility(View.INVISIBLE);
                //mImageView.setVisibility(View.VISIBLE);
                mImageView.setImageBitmap(imageBitmap);

            }



            /*TODO: start camera intent,
             capture image,
              receive the result,
               set the imageView as visible,
                set the image to imageview,
                 set the imagebutton as invisible,
                  save the image to external storage*/
        }
        else
        {
            Toast.makeText(this, "some error occurred", Toast.LENGTH_SHORT).show();
        }
    }

    String mCurrentPhotoPath;

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    /*TODO: write logs for events*/

}
