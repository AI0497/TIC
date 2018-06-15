package appdorks.tk.teamironchamps54thbranchrajendernagar;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
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
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddMember extends AppCompatActivity
{

    public static final String TAG = "AddMember";
    public static final int REQUEST_CODE = 1100;

    private ImageButton mImageButton;
    private ImageView mImageView;
    private Bitmap imageBitmap;

    private String mStringFirstName, mStringLastName;

    /*private String mWeightPrefix = "kg";*/

    private long mLongPhone, mLongEmergencyPhone;

    private int mIntGender, mIntHeightIn, mIntHeightFt, mIntWeight, mIntDate, mIntMonth, mIntYear, mIntFees;

    private int mYear, mMonth, mDay, mHour, mMinute;

    private EditText mEditFirstName, mEditLastName, mEditHeightIn, mEditHeightFt, mEditWeight,
            mEditPhone, mEditEmergencyPhone, mEditStartDate, mEditFees;

    private RadioButton mGenderMale, mGenderFemale;

    private RadioGroup mRadioGroup;

    private ImageButton mBtnStartDate;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_member);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // editTexts for first and last name
        mEditFirstName = findViewById(R.id.et_first_name);
        mEditLastName = findViewById(R.id.et_last_name);

        // radioGroup for gender radioButtons
        mRadioGroup = findViewById(R.id.radiogroup);

        // radioButtons for gender
        mGenderMale = findViewById(R.id.rb_gender_male);
        mGenderFemale = findViewById(R.id.rb_gender_female);

        // editTexts for height in feet and inches
        mEditHeightFt = findViewById(R.id.et_height_ft);
        mEditHeightIn = findViewById(R.id.et_height_in);

        // editText for weight
        mEditWeight = findViewById(R.id.et_weight);

        // editText for phone number
        mEditPhone = findViewById(R.id.et_phone);

        // editText for emergency phone number
        mEditEmergencyPhone = findViewById(R.id.et_emergency_phone);

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

        mBtnStartDate = findViewById(R.id.imgBtn_start_date);

        mBtnStartDate.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                final Calendar calendar = Calendar.getInstance();

                mYear = calendar.get(Calendar.YEAR);
                mMonth = calendar.get(Calendar.MONTH);
                mDay = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(AddMember.this,
                        new DatePickerDialog.OnDateSetListener()
                {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth)
                    {
                        mIntDate = dayOfMonth;
                        mIntMonth = month;
                        mIntYear = year;
                        mEditStartDate.setText(dayOfMonth + "/" + (month + 1) + "/" + year);

                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        mEditStartDate = findViewById(R.id.et_start_date);

        mEditFees = findViewById(R.id.et_fees);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_save);
        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                retrieveValues();


                // before actually entering the values in the database, let the user verify them by showing them in a dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(AddMember.this);
                builder.setMessage("Please check the details \n" +
                        "Name: " + mStringFirstName + " " + mStringLastName + "\n" +
                        "Gender: " + ((mIntGender == 0)? "Male": (mIntGender == 4) ? "NA" : "Female" ) + "\n" +
                        "Height: " +  mIntHeightFt + " ft " + mIntHeightIn + " in " + "\n" +
                        "Weight: " + mIntWeight + " kgs" + "\n" +
                        "Phone: " + "+91-" + mLongPhone + "\n" +
                        "Emergency Phone No: " + "+91-" + mLongEmergencyPhone + "\n" +
                        "Joining Date: " + mIntDate + "/" + mIntMonth + "/" + mIntYear + "\n" +
                        "Fees: ₹" + mIntFees  )
                        .setPositiveButton(R.string.add_member_form_dialog_save, new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {
                                /*Toast.makeText(AddNewMember.this, "ok clicked", Toast.LENGTH_SHORT).show();*/
                                if (mStringFirstName.isEmpty() || mStringLastName.isEmpty() ||
                                        mIntGender == 4 || mLongPhone == 0 || mLongEmergencyPhone == 0
                                        || mIntDate == 0 || mIntFees == 0)
                                {
                                    Toast.makeText(AddMember.this, "please fill all values first", Toast.LENGTH_SHORT).show();
                                }
                                else
                                {
                                    /*// create content values
                                    ContentValues contentValues = new ContentValues();
                                    contentValues.put(MemberContract.MemberEntry.COLUMN_NAME_FIRST_NAME, mStringFirstName);
                                    contentValues.put(MemberContract.MemberEntry.COLUMN_NAME_LAST_NAME, mStringLastName);
                                    contentValues.put(MemberContract.MemberEntry.COLUMN_NAME_GENDER, mIntGender);
                                    contentValues.put(MemberContract.MemberEntry.COLUMN_NAME_WEIGHT, mIntWeight);
                                    contentValues.put(MemberContract.MemberEntry.COLUMN_NAME_HEIGHT_FT, mIntHeightFt);
                                    contentValues.put(MemberContract.MemberEntry.COLUMN_NAME_HEIGHT_IN, mIntHeightIn);
                                    contentValues.put(MemberContract.MemberEntry.COLUMN_NAME_PHONE_NUMBER, mLongPhone);
                                    contentValues.put(MemberContract.MemberEntry.COLUMN_NAME_EMERGENCY_CONTACT, mLongEmergencyPhone);
                                    contentValues.put(MemberContract.MemberEntry.COLUMN_NAME_MONTH_START_DATE, mIntDate);
                                    contentValues.put(MemberContract.MemberEntry.COLUMN_NAME_FEES_AMOUNT, mIntFees);

                                    // TODO save the values to the database
                                    MemberDBHelper memberDBHelper = new MemberDBHelper(AddNewMember.this);

                                    SQLiteDatabase sqLiteDatabase = memberDBHelper.getWritableDatabase();


                                    sqLiteDatabase.insert(MemberContract.MemberEntry.TABLE_NAME, null, contentValues);

                                    Toast.makeText(AddNewMember.this, mStringFirstName + " successfully added!", Toast.LENGTH_SHORT).show();

                                    finish();

                                    //MemberDBHelper memberDBHelper = new MemberDBHelper(this);
                                    //db.insert(MemberContract.MemberEntry.TABLE_NAME, null, contentValues);*/

                                }
                            }
                        }).setNegativeButton(R.string.add_member_form_dialog_discard, new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        /*Toast.makeText(AddNewMember.this, "no clicked", Toast.LENGTH_SHORT).show();*/
                    }
                });

                AlertDialog dialog = builder.create();

                dialog.show();

            }
        });

    }

    private void retrieveValues()
    {
        // TODO remove error toasts and make visual validation cues
        // check for null values
        // retrieve all the details from the form

        // retrieve first name
        if (mEditFirstName.getText().toString().isEmpty())
        {
            /*Toast.makeText(this, "please enter the first name", Toast.LENGTH_SHORT).show();*/
            mStringFirstName = "NA";
        }
        else
        {
            mStringFirstName = mEditFirstName.getText().toString().trim();
        }

        // retrieve last name
        if (mEditLastName.getText().toString().isEmpty())
        {
            /*Toast.makeText(this, "please enter the last name", Toast.LENGTH_SHORT).show();*/
            mStringLastName = "NA";
        }
        else
        {
            mStringLastName = mEditLastName.getText().toString().trim();
        }

        // retrieve gender
        if (mGenderMale.isChecked())
        {
            mIntGender = 0; // 0 is code for male
        }
        else if (mGenderFemale.isChecked())
        {
            mIntGender = 1; // 1 is code for female
        }
        else
        {
            /*Toast.makeText(this, "select a gender", Toast.LENGTH_SHORT).show();*/
            mIntGender = 4;
        }

        // retrieve height
        if (mEditHeightFt.getText().toString().isEmpty())
        {
            /*Toast.makeText(this, "please enter height in feet", Toast.LENGTH_SHORT).show();*/
            mIntHeightFt = 0;
        }
        else
        {
            mIntHeightFt = Integer.parseInt(mEditHeightFt.getText().toString().trim());
        }

        if (mEditHeightIn.getText().toString().isEmpty())
        {
            /*Toast.makeText(this, "please enter height in inches", Toast.LENGTH_SHORT).show();*/
            mIntHeightIn = 0;
        }
        else
        {
            mIntHeightIn = Integer.parseInt(mEditHeightIn.getText().toString().trim());
        }

        // retrieve weight
        if (mEditWeight.getText().toString().isEmpty())
        {
            /*Toast.makeText(this, "please enter weight", Toast.LENGTH_SHORT).show();*/
            mIntWeight = 0;
        }
        else
        {
            mIntWeight = Integer.parseInt(mEditWeight.getText().toString().trim());
        }

        // retrieve phone number
        if (mEditPhone.getText().toString().isEmpty())
        {
            /*Toast.makeText(this, "please enter phone number", Toast.LENGTH_SHORT).show();*/
            mLongPhone = 0000000000;
        }
        else
        {
            mLongPhone = Long.parseLong(mEditPhone.getText().toString().trim());
        }

        // retrieve emergency phone
        if (mEditEmergencyPhone.getText().toString().isEmpty())
        {
            /*Toast.makeText(this, "please enter emergency contact number", Toast.LENGTH_SHORT).show();*/
            mLongEmergencyPhone = 0000000000;
        }
        else
        {
            mLongEmergencyPhone = Long.parseLong(mEditEmergencyPhone.getText().toString().trim());
        }

        // retrieve start date - done in the DatePickerDialog's onClickListener method

        // retrieve fees
        if (mEditFees.getText().toString().isEmpty())
        {
            /*Toast.makeText(this, "please enter fees", Toast.LENGTH_SHORT).show();*/
            mIntFees = 00;
        }
        else
        {
            mIntFees = Integer.parseInt(mEditFees.getText().toString().trim());
        }

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

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
    }

    /*TODO: write logs for events*/

}
