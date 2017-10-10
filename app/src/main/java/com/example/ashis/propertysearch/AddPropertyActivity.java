package com.example.ashis.propertysearch;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.text.method.KeyListener;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.example.ashis.propertysearch.data.PropertyContract;
import com.example.ashis.propertysearch.data.PropertyDbHelper;

public class AddPropertyActivity extends AppCompatActivity {

    private android.support.design.widget.TextInputEditText pktEditText,plotEditTxt, sectorEditTxt,
            areaEditTxt,priceEditTxt,notesEditTxt,mLocationEditText,mBedroomEditText,
            mSocietyEditText,mRemarksEditText;
    private AutoCompleteTextView floorEditTxt,mDealerNameEditTxt;
    private Button mSaveBtn;
    private SQLiteDatabase mDb;
    private Switch mSwitchDealer;
    private int dealerValue;
    private KeyListener originalKeyListner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_property);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        PropertyDbHelper helper = new PropertyDbHelper(this);
        mDb=helper.getReadableDatabase();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        ArrayAdapter<String> mAdapter = new ArrayAdapter<String>(AddPropertyActivity.this,
                android.R.layout.simple_dropdown_item_1line,new String[]{"Plot"});

        final ArrayAdapter<String> mDealerNameAdapter = new ArrayAdapter<String>(AddPropertyActivity.this,
                android.R.layout.simple_list_item_1);
        Cursor cursorDealerName = getDealerNames();
        cursorDealerName.moveToFirst();
            do {
                mDealerNameAdapter.add(cursorDealerName.getString(cursorDealerName.getColumnIndex(PropertyContract.PropertyEntry.COLUMN_DEALER_NAME)));
            }while (cursorDealerName.moveToNext());


        mDealerNameEditTxt=(AutoCompleteTextView)findViewById(R.id.editDealerName);
        mRemarksEditText=(TextInputEditText)findViewById(R.id.editRemarks);
        mSocietyEditText=(TextInputEditText)findViewById(R.id.editSociety);
        mBedroomEditText = (TextInputEditText)findViewById(R.id.editBedroom);
        mLocationEditText=(android.support.design.widget.TextInputEditText)findViewById(R.id.editLocation);
        mSwitchDealer = (Switch)findViewById(R.id.switchDealer);
        pktEditText=(android.support.design.widget.TextInputEditText)findViewById(R.id.editPkt);
        plotEditTxt=(android.support.design.widget.TextInputEditText)findViewById(R.id.editPlot);
        sectorEditTxt =(android.support.design.widget.TextInputEditText)findViewById(R.id.editSector);
        floorEditTxt=(AutoCompleteTextView)findViewById(R.id.editFloor);
        areaEditTxt=(android.support.design.widget.TextInputEditText)findViewById(R.id.editSize);
        priceEditTxt=(android.support.design.widget.TextInputEditText)findViewById(R.id.editPrice);
        notesEditTxt=(android.support.design.widget.TextInputEditText)findViewById(R.id.editNotes);
        mSaveBtn=(Button)findViewById(R.id.save_btn);
        floorEditTxt.setAdapter(mAdapter);
        floorEditTxt.setThreshold(-1);
        originalKeyListner = mDealerNameEditTxt.getKeyListener();

        mDealerNameEditTxt.setEnabled(false);

        mSwitchDealer.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    mDealerNameEditTxt.setEnabled(true);
                    dealerValue = 1;
                    mDealerNameEditTxt.setKeyListener(originalKeyListner);
                    mDealerNameEditTxt.requestFocus();
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
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

        final PropertyDbHelper mDbHelper = new PropertyDbHelper(this);

        final int flagId = getIntent().getIntExtra("flag",100);

        if (flagId != 100){
            if (flagId==1)
                mSaveBtn.setText("Update");
            else if (flagId==2)
                mSaveBtn.setText("Save");
            final int rowId = getIntent().getIntExtra("rowId",200);
            if (rowId!=200){
                Cursor cursor = getPropertyDetail(rowId);
                cursor.moveToFirst();
                pktEditText.setText(cursor.getString(cursor.getColumnIndex(PropertyContract.PropertyEntry.COLUMN_PKT)));
                plotEditTxt.setText(cursor.getString(cursor.getColumnIndex(PropertyContract.PropertyEntry.COLUMN_PLOT)));
                sectorEditTxt.setText(cursor.getString(cursor.getColumnIndex(PropertyContract.PropertyEntry.COLUMN_SECTOR)));
                floorEditTxt.setText(cursor.getString(cursor.getColumnIndex(PropertyContract.PropertyEntry.COLUMN_FLOOR)));
                areaEditTxt.setText(cursor.getString(cursor.getColumnIndex(PropertyContract.PropertyEntry.COLUMN_AREA)));
                notesEditTxt.setText(cursor.getString(cursor.getColumnIndex(PropertyContract.PropertyEntry.COLUMN_NOTES)));
                priceEditTxt.setText(cursor.getString(cursor.getColumnIndex(PropertyContract.PropertyEntry.COLUMN_PRICE)));
                mLocationEditText.setText(cursor.getString(cursor.getColumnIndex(PropertyContract.PropertyEntry.COLUMN_LOCATION)));
                mBedroomEditText.setText(cursor.getString(cursor.getColumnIndex(PropertyContract.PropertyEntry.COLUMN_BEDROOM)));
                mSocietyEditText.setText(cursor.getString(cursor.getColumnIndex(PropertyContract.PropertyEntry.COLUMN_SOCIETY)));
                mRemarksEditText.setText(cursor.getString(cursor.getColumnIndex(PropertyContract.PropertyEntry.COLUMN_REMARKS)));
                mDealerNameEditTxt.setText(cursor.getString(cursor.getColumnIndex(PropertyContract.PropertyEntry.COLUMN_DEALER_NAME)));
                int count = cursor.getInt(cursor.getColumnIndex(PropertyContract.PropertyEntry.COLUMN_DEALER));
                if (count == 1){
                    mSwitchDealer.setChecked(true);
                }
                else if (count == 0){
                    mSwitchDealer.setChecked(false);
                }

                mSaveBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String pktString = pktEditText.getText().toString();
                        String plotString = plotEditTxt.getText().toString();
                        String floorString = floorEditTxt.getText().toString();
                        String areaString = areaEditTxt.getText().toString();
                        String priceString = priceEditTxt.getText().toString();
                        String notesString = notesEditTxt.getText().toString();
                        String sectorString = sectorEditTxt.getText().toString();
                        String locationString = mLocationEditText.getText().toString();
                        String bedroomString = mBedroomEditText.getText().toString();
                        String socityString = mSocietyEditText.getText().toString();
                        String remarkString = mRemarksEditText.getText().toString();
                        String dealerNameString = mDealerNameEditTxt.getText().toString();
                        String selectionQury = PropertyContract.PropertyEntry.COLUMN_SECTOR +" =? AND "+
                                PropertyContract.PropertyEntry.COLUMN_PKT +" =? AND "+
                                PropertyContract.PropertyEntry.COLUMN_PLOT +" =? AND "+
                                PropertyContract.PropertyEntry.COLUMN_AREA +" =? AND "+
                                PropertyContract.PropertyEntry.COLUMN_FLOOR +" =? AND "+
                                PropertyContract.PropertyEntry.COLUMN_BEDROOM +" =? AND "+
                                PropertyContract.PropertyEntry.COLUMN_NOTES +" =? AND "+
                                PropertyContract.PropertyEntry.COLUMN_REMARKS +" =? AND "+
                                PropertyContract.PropertyEntry.COLUMN_SOCIETY +" =? AND "+
                                PropertyContract.PropertyEntry.COLUMN_LOCATION +" =? AND "+
                                PropertyContract.PropertyEntry.COLUMN_PRICE +" =? AND "+
                                PropertyContract.PropertyEntry.COLUMN_DEALER_NAME+" =?";

                        String[] selectionArgsQuery = new String[]{sectorString,pktString,plotString,
                                areaString,floorString,bedroomString,notesString,remarkString,
                                socityString,locationString,priceString,dealerNameString};

                        Cursor cursor = mDb.query(PropertyContract.PropertyEntry.TABLE_NAME,null
                                ,selectionQury,selectionArgsQuery,null,null,null);

                        if (cursor.getCount()>0){
                            Toast.makeText(AddPropertyActivity.this,"Property Exist already",Toast.LENGTH_SHORT).show();
                            return;
                        }


                        if (locationString.isEmpty()){
                            locationString="Delhi";
                        }









                        ContentValues values = new ContentValues();
                        values.put(PropertyContract.PropertyEntry.COLUMN_AREA, areaString);
                        values.put(PropertyContract.PropertyEntry.COLUMN_FLOOR, floorString);
                        values.put(PropertyContract.PropertyEntry.COLUMN_NOTES, notesString);
                        values.put(PropertyContract.PropertyEntry.COLUMN_PKT, pktString);
                        values.put(PropertyContract.PropertyEntry.COLUMN_PLOT, plotString);
                        values.put(PropertyContract.PropertyEntry.COLUMN_PRICE,priceString);
                        values.put(PropertyContract.PropertyEntry.COLUMN_SECTOR, sectorString);
                        values.put(PropertyContract.PropertyEntry.COLUMN_DEALER,dealerValue);
                        values.put(PropertyContract.PropertyEntry.COLUMN_LOCATION,locationString);
                        values.put(PropertyContract.PropertyEntry.COLUMN_BEDROOM,bedroomString);
                        values.put(PropertyContract.PropertyEntry.COLUMN_SOCIETY,socityString);
                        values.put(PropertyContract.PropertyEntry.COLUMN_REMARKS,remarkString);
                        values.put(PropertyContract.PropertyEntry.COLUMN_DEALER_NAME,dealerNameString);

                        if (flagId==1) {
                            int updatedRow = mDb.update(PropertyContract.PropertyEntry.TABLE_NAME, values, PropertyContract.PropertyEntry._ID + " = " + rowId, null);
                            Toast.makeText(getApplicationContext(),"Property Updated Successfully",Toast.LENGTH_SHORT).show();
                        }
                        else if (flagId==2){

                            long insertedRow = mDb.insert(PropertyContract.PropertyEntry.TABLE_NAME,null,values);
                            Toast.makeText(getApplicationContext(),"Property Added Successfully",Toast.LENGTH_SHORT).show();

                        }
                            Intent intent = new Intent();
                        setResult(RESULT_OK,intent);
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
                    String floorString = floorEditTxt.getText().toString();
                    String areaString = areaEditTxt.getText().toString();
                    String priceString = priceEditTxt.getText().toString();
                    String notesString = notesEditTxt.getText().toString();
                    String sectorString = sectorEditTxt.getText().toString();
                    String locationString = mLocationEditText.getText().toString();
                    String bedroomString = mBedroomEditText.getText().toString();
                    String socityString = mSocietyEditText.getText().toString();
                    String remarkString = mRemarksEditText.getText().toString();
                    String dealerNameString = mDealerNameEditTxt.getText().toString();
                    if (sectorString.isEmpty()||pktString.isEmpty()||plotString.isEmpty()||
                            areaString.isEmpty()||floorString.isEmpty()||
                            priceString.isEmpty()){
                        Toast.makeText(AddPropertyActivity.this,"Please Enter all the details",Toast.LENGTH_SHORT).show();
                        return;
                    }

                    mDb = mDbHelper.getWritableDatabase();

                    if (pktString.isEmpty() && plotString.isEmpty() && floorString.isEmpty() &&
                            areaString.isEmpty() && priceString.isEmpty() && notesString.isEmpty() &&
                            sectorString.isEmpty() && locationString.isEmpty()  &&
                            socityString.isEmpty() && remarkString.isEmpty()){
                        Toast.makeText(getApplicationContext(),"Please enter Property Detail",Toast.LENGTH_SHORT).show();
                        return;
                    }


                   String selectionQury = PropertyContract.PropertyEntry.COLUMN_SECTOR +" =? AND "+
                            PropertyContract.PropertyEntry.COLUMN_PKT +" =? AND "+
                            PropertyContract.PropertyEntry.COLUMN_PLOT +" =? AND "+
                            PropertyContract.PropertyEntry.COLUMN_AREA +" =? AND "+
                            PropertyContract.PropertyEntry.COLUMN_FLOOR +" =? AND "+
                            PropertyContract.PropertyEntry.COLUMN_BEDROOM +" =? AND "+
                            PropertyContract.PropertyEntry.COLUMN_NOTES +" =? AND "+
                            PropertyContract.PropertyEntry.COLUMN_REMARKS +" =? AND "+
                            PropertyContract.PropertyEntry.COLUMN_SOCIETY +" =? AND "+
                            PropertyContract.PropertyEntry.COLUMN_LOCATION +" =? AND "+
                            PropertyContract.PropertyEntry.COLUMN_PRICE +" =? AND "+
                            PropertyContract.PropertyEntry.COLUMN_DEALER_NAME+" =?";


                    String[] selectionArgsQuery = new String[]{sectorString,pktString,plotString,
                            areaString,floorString,bedroomString,priceString,dealerNameString};

                    Cursor cursor = mDb.query(PropertyContract.PropertyEntry.TABLE_NAME,null
                    ,selectionQury,selectionArgsQuery,null,null,null);

                    if (cursor.getCount()>0){
                        Toast.makeText(AddPropertyActivity.this,"Property Exist already",Toast.LENGTH_SHORT).show();
                        return;
                    }


                    ContentValues values = new ContentValues();
                    values.put(PropertyContract.PropertyEntry.COLUMN_AREA, areaString);
                    values.put(PropertyContract.PropertyEntry.COLUMN_FLOOR, floorString);
                    values.put(PropertyContract.PropertyEntry.COLUMN_NOTES, notesString);
                    values.put(PropertyContract.PropertyEntry.COLUMN_PKT, pktString);
                    values.put(PropertyContract.PropertyEntry.COLUMN_PLOT, plotString);
                    values.put(PropertyContract.PropertyEntry.COLUMN_PRICE,priceString);
                    values.put(PropertyContract.PropertyEntry.COLUMN_SECTOR, sectorString);
                    values.put(PropertyContract.PropertyEntry.COLUMN_DEALER,dealerValue);
                    values.put(PropertyContract.PropertyEntry.COLUMN_LOCATION,locationString);
                    values.put(PropertyContract.PropertyEntry.COLUMN_BEDROOM,bedroomString);
                    values.put(PropertyContract.PropertyEntry.COLUMN_SOCIETY,socityString);
                    values.put(PropertyContract.PropertyEntry.COLUMN_REMARKS,remarkString);
                    values.put(PropertyContract.PropertyEntry.COLUMN_DEALER_NAME,dealerNameString);
                    long id = mDb.insert(PropertyContract.PropertyEntry.TABLE_NAME, null, values);
                    Toast.makeText(getApplicationContext(),"Property Added Successfully",Toast.LENGTH_SHORT).show();

                    Log.i(AddPropertyActivity.class.getSimpleName(), "row no: " + id + dealerValue);

                    Intent intent = new Intent();
                    intent.putExtra("rowInsertedId",id);
                    setResult(RESULT_OK,intent);

                    finish();
                }
            });

        }

    }

    private Cursor getDealerNames(){
        return mDb.query(true, PropertyContract.PropertyEntry.TABLE_NAME,new String[]{PropertyContract.PropertyEntry.COLUMN_DEALER_NAME},
                null,null,null,null, PropertyContract.PropertyEntry.COLUMN_DEALER_NAME,null);
    }


    private Cursor getPropertyDetail(int rowId) {
        return mDb.query(PropertyContract.PropertyEntry.TABLE_NAME,null, PropertyContract.PropertyEntry._ID+"="+rowId,null,null,null
        ,null,null);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
