package com.example.ashis.propertysearch;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.textfield.TextInputEditText;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.text.InputType;
import android.text.method.KeyListener;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ashis.propertysearch.data.PropertyContract;
import com.example.ashis.propertysearch.data.PropertyDbHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AddPropertyActivity extends AppCompatActivity {

    private TextInputEditText pktEditText, plotEditTxt, sectorEditTxt,
            areaEditTxt, priceEditTxt, notesEditTxt, mLocationEditText, mBedroomEditText,
            mSocietyEditText, mRemarksEditText;
    private AutoCompleteTextView  mDealerNameEditTxt;
    private Button mSaveBtn;
    private String mIsImp = " ";
    private SQLiteDatabase mDb;
    private Switch mSwitchDealer, mIsImpSwitch;
    private int dealerValue;
    private KeyListener originalKeyListner;
    private Spinner mColorSpinner;
    int mColourValue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_property);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        PropertyDbHelper helper = new PropertyDbHelper(this);
        mDb = helper.getReadableDatabase();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        ArrayAdapter<String> mAdapter = new ArrayAdapter<String>(AddPropertyActivity.this,
                android.R.layout.simple_dropdown_item_1line, new String[]{"Plot"});

        mColorSpinner = (Spinner)findViewById(R.id.spinner);

        String[] colour = {"Select Color",
                            "Red",
                            "Green",
                            "Blue",
                            "Yellow"};

        final List<String> mColorList = new ArrayList<>(Arrays.asList(colour));

        final ArrayAdapter<String> mColorAdapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_spinner_dropdown_item,mColorList){
            @Override
            public boolean isEnabled(int position) {

                if (position == 0){
                    return false;
                }
                else {
                    return true;
                }

            }

            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if(position == 0){
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                }
                else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };

        mColorSpinner.setAdapter(mColorAdapter);

        mColorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                mColourValue=position;


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        final ArrayAdapter<String> mDealerNameAdapter = new ArrayAdapter<String>(AddPropertyActivity.this,
                android.R.layout.simple_list_item_1);
        Cursor cursorDealerName = getDealerNames();
        if (cursorDealerName.getCount() > 0) {
            cursorDealerName.moveToFirst();
            do {
                mDealerNameAdapter.add(cursorDealerName.getString(cursorDealerName.getColumnIndex(PropertyContract.PropertyEntry.COLUMN_DEALER_NAME)));
            } while (cursorDealerName.moveToNext());
        }
        SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd");
        final String cuurentDate = sdFormat.format(new Date());


        mDealerNameEditTxt = (AutoCompleteTextView) findViewById(R.id.editDealerName);
        mRemarksEditText = (TextInputEditText) findViewById(R.id.editRemarks);
        mSocietyEditText = (TextInputEditText) findViewById(R.id.editSociety);
        mLocationEditText = (TextInputEditText) findViewById(R.id.editLocation);
        mSwitchDealer = (Switch) findViewById(R.id.switchDealer);
        mIsImpSwitch = (Switch) findViewById(R.id.importantProp);
        pktEditText = (TextInputEditText) findViewById(R.id.editPkt);
        plotEditTxt = (TextInputEditText) findViewById(R.id.editPlot);
        sectorEditTxt = (TextInputEditText) findViewById(R.id.editSector);
        areaEditTxt = (TextInputEditText) findViewById(R.id.editSize);
        priceEditTxt = (TextInputEditText) findViewById(R.id.editPrice);
        notesEditTxt = (TextInputEditText) findViewById(R.id.editNotes);
        mSaveBtn = (Button) findViewById(R.id.save_btn);
        originalKeyListner = mDealerNameEditTxt.getKeyListener();

        mDealerNameEditTxt.setEnabled(false);

        mSwitchDealer.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mDealerNameEditTxt.setEnabled(true);
                    dealerValue = 1;
                    mDealerNameEditTxt.setKeyListener(originalKeyListner);
                    mDealerNameEditTxt.requestFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.showSoftInput(mDealerNameEditTxt, InputMethodManager.SHOW_IMPLICIT);
                    mDealerNameEditTxt.setAdapter(mDealerNameAdapter);
                    mDealerNameEditTxt.setThreshold(-1);
                } else {
                    mDealerNameEditTxt.setText("");
                    mDealerNameEditTxt.setEnabled(false);
                    dealerValue = 0;
                }
            }
        });

        mIsImpSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mIsImp = "Important";
                } else {
                    mIsImp = "Not Important";
                }
            }
        });

        final PropertyDbHelper mDbHelper = new PropertyDbHelper(this);

        final int flagId = getIntent().getIntExtra("flag", 100);

        if (flagId != 100) {
            if (flagId == 1)
                mSaveBtn.setText("Update");
            else if (flagId == 2)
                mSaveBtn.setText("Save");
            final int rowId = getIntent().getIntExtra("rowId", 200);
            if (rowId != 200) {
                Cursor cursor = getPropertyDetail(rowId);
                cursor.moveToFirst();
                Toast.makeText(this, cursor.getString(cursor.getColumnIndex(PropertyContract.PropertyEntry.COLUMN_DATE)), Toast.LENGTH_SHORT).show();
                pktEditText.setText(cursor.getString(cursor.getColumnIndex(PropertyContract.PropertyEntry.COLUMN_PKT)));
                plotEditTxt.setText(cursor.getString(cursor.getColumnIndex(PropertyContract.PropertyEntry.COLUMN_PLOT)));
                sectorEditTxt.setText(cursor.getString(cursor.getColumnIndex(PropertyContract.PropertyEntry.COLUMN_SECTOR)));
                areaEditTxt.setText(cursor.getString(cursor.getColumnIndex(PropertyContract.PropertyEntry.COLUMN_AREA)));
                notesEditTxt.setText(cursor.getString(cursor.getColumnIndex(PropertyContract.PropertyEntry.COLUMN_NOTES)));
                priceEditTxt.setText(cursor.getString(cursor.getColumnIndex(PropertyContract.PropertyEntry.COLUMN_PRICE)));
                mLocationEditText.setText(cursor.getString(cursor.getColumnIndex(PropertyContract.PropertyEntry.COLUMN_LOCATION)));
                mRemarksEditText.setText(cursor.getString(cursor.getColumnIndex(PropertyContract.PropertyEntry.COLUMN_REMARKS)));
                mDealerNameEditTxt.setText(cursor.getString(cursor.getColumnIndex(PropertyContract.PropertyEntry.COLUMN_DEALER_NAME)));
                mColorSpinner.setSelection(cursor.getInt(cursor.getColumnIndex(PropertyContract.PropertyEntry.COLUMN_COLOR)),true);
                int count = cursor.getInt(cursor.getColumnIndex(PropertyContract.PropertyEntry.COLUMN_DEALER));
                if (count == 1) {
                    mSwitchDealer.setChecked(true);
                } else if (count == 0) {
                    mSwitchDealer.setChecked(false);
                }
                String impProp = cursor.getString(cursor.getColumnIndex(PropertyContract.PropertyEntry.COLUMN_IS_IMPORTANT));

                if (impProp.equals("Important"))
                    mIsImpSwitch.setChecked(true);
                else
                    mIsImpSwitch.setChecked(false);

                mSaveBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String pktString = pktEditText.getText().toString();
                        String plotString = plotEditTxt.getText().toString();
                        String areaString = areaEditTxt.getText().toString();
                        String priceString = priceEditTxt.getText().toString();
                        String notesString = notesEditTxt.getText().toString();
                        String sectorString = sectorEditTxt.getText().toString();
                        String locationString = mLocationEditText.getText().toString();
                        String bedroomString = mBedroomEditText.getText().toString();
                        String socityString = mSocietyEditText.getText().toString();
                        String remarkString = mRemarksEditText.getText().toString();
                        String dealerNameString = mDealerNameEditTxt.getText().toString();
                        String selectionQury = PropertyContract.PropertyEntry.COLUMN_SECTOR + " =? AND " +
                                PropertyContract.PropertyEntry.COLUMN_PKT + " =? AND " +
                                PropertyContract.PropertyEntry.COLUMN_PLOT + " =? AND " +
                                PropertyContract.PropertyEntry.COLUMN_AREA + " =? AND " +
                                PropertyContract.PropertyEntry.COLUMN_NOTES + " =? AND " +
                                PropertyContract.PropertyEntry.COLUMN_REMARKS + " =? AND " +
                                PropertyContract.PropertyEntry.COLUMN_LOCATION + " =? AND " +
                                PropertyContract.PropertyEntry.COLUMN_PRICE + " =? AND " +
                                PropertyContract.PropertyEntry.COLUMN_DEALER_NAME + " =? AND " +
                                PropertyContract.PropertyEntry.COLUMN_DATE + " =? AND " +
                                PropertyContract.PropertyEntry.COLUMN_IS_IMPORTANT + " =? AND "+
                                PropertyContract.PropertyEntry.COLUMN_COLOR+" =?";

                        String[] selectionArgsQuery = new String[]{sectorString, pktString, plotString,
                                areaString,bedroomString, notesString, remarkString,
                                socityString, locationString, priceString, dealerNameString};

                        Cursor cursor = mDb.query(PropertyContract.PropertyEntry.TABLE_NAME, null
                                , selectionQury, selectionArgsQuery, null, null, null);

                        if (cursor.getCount() > 0) {
                            Toast.makeText(AddPropertyActivity.this, "Property Exist already", Toast.LENGTH_SHORT).show();
                            return;
                        }




                        ContentValues values = new ContentValues();
                        values.put(PropertyContract.PropertyEntry.COLUMN_AREA, areaString);
                        values.put(PropertyContract.PropertyEntry.COLUMN_NOTES, notesString);
                        values.put(PropertyContract.PropertyEntry.COLUMN_PKT, pktString);
                        values.put(PropertyContract.PropertyEntry.COLUMN_PLOT, plotString);
                        values.put(PropertyContract.PropertyEntry.COLUMN_PRICE, priceString);
                        values.put(PropertyContract.PropertyEntry.COLUMN_SECTOR, sectorString);
                        values.put(PropertyContract.PropertyEntry.COLUMN_DEALER, dealerValue);
                        values.put(PropertyContract.PropertyEntry.COLUMN_LOCATION, locationString);
                        values.put(PropertyContract.PropertyEntry.COLUMN_REMARKS, remarkString);
                        values.put(PropertyContract.PropertyEntry.COLUMN_DEALER_NAME, dealerNameString);
                        values.put(PropertyContract.PropertyEntry.COLUMN_DATE, cuurentDate);
                        values.put(PropertyContract.PropertyEntry.COLUMN_IS_IMPORTANT, mIsImp);
                        values.put(PropertyContract.PropertyEntry.COLUMN_COLOR,mColourValue);


                        if (flagId == 1) {
                            int updatedRow = mDb.update(PropertyContract.PropertyEntry.TABLE_NAME, values, PropertyContract.PropertyEntry._ID + " = " + rowId, null);
                            Toast.makeText(getApplicationContext(), "Property Updated Successfully", Toast.LENGTH_SHORT).show();
                        } else if (flagId == 2) {

                            long insertedRow = mDb.insert(PropertyContract.PropertyEntry.TABLE_NAME, null, values);
                            Toast.makeText(getApplicationContext(), "Property Added Successfully", Toast.LENGTH_SHORT).show();

                        }
                        Intent intent = new Intent();
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                });

            }


        } else {

            mSaveBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    String pktString = pktEditText.getText().toString();
                    String plotString = plotEditTxt.getText().toString();
                    String areaString = areaEditTxt.getText().toString();
                    String priceString = priceEditTxt.getText().toString();
                    String notesString = notesEditTxt.getText().toString();
                    String sectorString = sectorEditTxt.getText().toString();
                    String locationString = mLocationEditText.getText().toString();
                    String remarkString = mRemarksEditText.getText().toString();
                    String dealerNameString = mDealerNameEditTxt.getText().toString();
                    if (sectorString.isEmpty() ||plotString.isEmpty() ||
                            areaString.isEmpty() || priceString.isEmpty()) {
                        Toast.makeText(AddPropertyActivity.this, "Please Enter all the details", Toast.LENGTH_SHORT).show();
                        return;
                    }


                    if (locationString.isEmpty()) {
                        locationString = "Delhi";
                    }

                    mDb = mDbHelper.getWritableDatabase();

                    if (pktString.isEmpty() && plotString.isEmpty() && areaString.isEmpty()
                            && priceString.isEmpty() && notesString.isEmpty() &&
                            sectorString.isEmpty() && locationString.isEmpty() &&remarkString.isEmpty()) {
                        Toast.makeText(getApplicationContext(), "Please enter Property Detail", Toast.LENGTH_SHORT).show();
                        return;
                    }


                    String selectionQury = PropertyContract.PropertyEntry.COLUMN_SECTOR + " =? AND " +
                            PropertyContract.PropertyEntry.COLUMN_PKT + " =? AND " +
                            PropertyContract.PropertyEntry.COLUMN_PLOT + " =? AND " +
                            PropertyContract.PropertyEntry.COLUMN_AREA + " =? AND " +
                            PropertyContract.PropertyEntry.COLUMN_NOTES + " =? AND " +
                            PropertyContract.PropertyEntry.COLUMN_REMARKS + " =? AND " +
                            PropertyContract.PropertyEntry.COLUMN_LOCATION + " =? AND " +
                            PropertyContract.PropertyEntry.COLUMN_PRICE + " =? AND " +
                            PropertyContract.PropertyEntry.COLUMN_DEALER_NAME + " =? AND " +
                            PropertyContract.PropertyEntry.COLUMN_DATE + " =? AND " +
                            PropertyContract.PropertyEntry.COLUMN_IS_IMPORTANT + " =?";


                    String[] selectionArgsQuery = new String[]{sectorString, pktString, plotString,
                            areaString, priceString, dealerNameString};

                    Cursor cursor = mDb.query(PropertyContract.PropertyEntry.TABLE_NAME, null
                            , selectionQury, selectionArgsQuery, null, null, null);

                    if (cursor.getCount() > 0) {
                        Toast.makeText(AddPropertyActivity.this, "Property Exist already", Toast.LENGTH_SHORT).show();
                        return;
                    }


                    ContentValues values = new ContentValues();
                    values.put(PropertyContract.PropertyEntry.COLUMN_AREA, areaString);
                    values.put(PropertyContract.PropertyEntry.COLUMN_NOTES, notesString);
                    values.put(PropertyContract.PropertyEntry.COLUMN_PKT, pktString);
                    values.put(PropertyContract.PropertyEntry.COLUMN_PLOT, plotString);
                    values.put(PropertyContract.PropertyEntry.COLUMN_PRICE, priceString);
                    values.put(PropertyContract.PropertyEntry.COLUMN_SECTOR, sectorString);
                    values.put(PropertyContract.PropertyEntry.COLUMN_DEALER, dealerValue);
                    values.put(PropertyContract.PropertyEntry.COLUMN_LOCATION, locationString);
                    values.put(PropertyContract.PropertyEntry.COLUMN_REMARKS, remarkString);
                    values.put(PropertyContract.PropertyEntry.COLUMN_DEALER_NAME, dealerNameString);
                    values.put(PropertyContract.PropertyEntry.COLUMN_DATE, cuurentDate);
                    values.put(PropertyContract.PropertyEntry.COLUMN_IS_IMPORTANT, mIsImp);
                    values.put(PropertyContract.PropertyEntry.COLUMN_COLOR,mColourValue);

                    long id = mDb.insert(PropertyContract.PropertyEntry.TABLE_NAME, null, values);
                    Toast.makeText(getApplicationContext(), "Property Added Successfully", Toast.LENGTH_SHORT).show();

                    Log.i(AddPropertyActivity.class.getSimpleName(), "row no: " + id + dealerValue);

                    Intent intent = new Intent();
                    intent.putExtra("rowInsertedId", id);
                    setResult(RESULT_OK, intent);

                    finish();
                }
            });

        }

    }

    private Cursor getDealerNames() {
        return mDb.query(true, PropertyContract.PropertyEntry.TABLE_NAME, new String[]{PropertyContract.PropertyEntry.COLUMN_DEALER_NAME},
                null, null, null, null, PropertyContract.PropertyEntry.COLUMN_DEALER_NAME, null);
    }


    private Cursor getPropertyDetail(int rowId) {
        return mDb.query(PropertyContract.PropertyEntry.TABLE_NAME, null, PropertyContract.PropertyEntry._ID + "=" + rowId, null, null, null
                , null, null);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
