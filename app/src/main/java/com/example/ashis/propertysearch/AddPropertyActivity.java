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
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.example.ashis.propertysearch.data.PropertyContract;
import com.example.ashis.propertysearch.data.PropertyDbHelper;

public class AddPropertyActivity extends AppCompatActivity {

    private android.support.design.widget.TextInputEditText pktEditText,plotEditTxt, sectorEditTxt,
            floorEditTxt,areaEditTxt,priceEditTxt,notesEditTxt,mLocationEditText,mBedroomEditText,
            mSocietyEditText,mRemarksEditText;
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

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        mRemarksEditText=(TextInputEditText)findViewById(R.id.editRemarks);
        mSocietyEditText=(TextInputEditText)findViewById(R.id.editSociety);
        mBedroomEditText = (TextInputEditText)findViewById(R.id.editBedroom);
        mLocationEditText=(android.support.design.widget.TextInputEditText)findViewById(R.id.editLocation);
        mSwitchDealer = (Switch)findViewById(R.id.switchDealer);
        pktEditText=(android.support.design.widget.TextInputEditText)findViewById(R.id.editPkt);
        plotEditTxt=(android.support.design.widget.TextInputEditText)findViewById(R.id.editPlot);
        sectorEditTxt =(android.support.design.widget.TextInputEditText)findViewById(R.id.editSector);
        floorEditTxt=(android.support.design.widget.TextInputEditText)findViewById(R.id.editFloor);
        areaEditTxt=(android.support.design.widget.TextInputEditText)findViewById(R.id.editSize);
        priceEditTxt=(android.support.design.widget.TextInputEditText)findViewById(R.id.editPrice);
        notesEditTxt=(android.support.design.widget.TextInputEditText)findViewById(R.id.editNotes);
        mSaveBtn=(Button)findViewById(R.id.save_btn);

        originalKeyListner = notesEditTxt.getKeyListener();
        notesEditTxt.setKeyListener(null);

        mSwitchDealer.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    dealerValue = 1;
                    notesEditTxt.setKeyListener(originalKeyListner);
                    notesEditTxt.requestFocus();
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.showSoftInput(notesEditTxt, InputMethodManager.SHOW_IMPLICIT);
                } else {
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
            PropertyDbHelper helper = new PropertyDbHelper(this);
            mDb=helper.getReadableDatabase();
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
                        String selectionQury = PropertyContract.PropertyEntry.COLUMN_SECTOR +" =? AND "+
                                PropertyContract.PropertyEntry.COLUMN_PKT +" =? AND "+
                                PropertyContract.PropertyEntry.COLUMN_PLOT +" =? AND "+
                                PropertyContract.PropertyEntry.COLUMN_AREA +" =? AND "+
                                PropertyContract.PropertyEntry.COLUMN_FLOOR +" =? AND "+
                                PropertyContract.PropertyEntry.COLUMN_BEDROOM +" =? AND "+
                                PropertyContract.PropertyEntry.COLUMN_PRICE +" =?";

                        String[] selectionArgsQuery = new String[]{sectorString,pktString,plotString,
                                areaString,floorString,bedroomString,priceString};

                        Cursor cursor = mDb.query(PropertyContract.PropertyEntry.TABLE_NAME,null
                                ,selectionQury,selectionArgsQuery,null,null,null);

                        if (cursor.getCount()>0){
                            Toast.makeText(AddPropertyActivity.this,"Property Exist already",Toast.LENGTH_SHORT).show();
                            return;
                        }

                        if (pktString.isEmpty())
                            pktString="0";
                        if (plotString.isEmpty()){
                            plotString="0";
                        }
                        if (areaString.isEmpty())
                            areaString="0";
                        if (sectorString.isEmpty())
                            sectorString="0";
                        if (priceString.isEmpty())
                            priceString="0";
                        if (locationString.isEmpty()){
                            locationString="Delhi";
                        }

                        if (socityString.isEmpty()){
                            socityString="None";
                        }

                        if (bedroomString.isEmpty()){
                            bedroomString="Plot";
                        }

                        if(floorString.isEmpty()){
                            floorString="Plot";
                        }
                        if (floorString.equals("Plot")){

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

                    if (sectorString.isEmpty()||pktString.isEmpty()||plotString.isEmpty()||
                            areaString.isEmpty()||floorString.isEmpty()||bedroomString.isEmpty()||
                            priceString.isEmpty()){
                        Toast.makeText(AddPropertyActivity.this,"Please Enter all the details",Toast.LENGTH_SHORT).show();
                        return;
                    }

                    mDb = mDbHelper.getWritableDatabase();

                    if (pktString.isEmpty() && plotString.isEmpty() && floorString.isEmpty() &&
                            areaString.isEmpty() && priceString.isEmpty() && notesString.isEmpty() &&
                            sectorString.isEmpty() && locationString.isEmpty() && bedroomString.isEmpty() &&
                            socityString.isEmpty() && remarkString.isEmpty()){
                        Toast.makeText(getApplicationContext(),"Please enter Property Detail",Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (pktString.isEmpty())
                        pktString="0";
                    if (plotString.isEmpty()){
                        plotString="0";
                    }
                   if (areaString.isEmpty())
                        areaString="0";
                    if (sectorString.isEmpty())
                        sectorString="0";
                    if (priceString.isEmpty())
                        priceString="0";
                    if (locationString.isEmpty()){
                        locationString="Delhi";
                    }

                    if (socityString.isEmpty()){
                        socityString="None";
                    }

                    String selectionQury = PropertyContract.PropertyEntry.COLUMN_SECTOR +" =? AND "+
                            PropertyContract.PropertyEntry.COLUMN_PKT +" =? AND "+
                            PropertyContract.PropertyEntry.COLUMN_PLOT +" =? AND "+
                            PropertyContract.PropertyEntry.COLUMN_AREA +" =? AND "+
                            PropertyContract.PropertyEntry.COLUMN_FLOOR +" =? AND "+
                            PropertyContract.PropertyEntry.COLUMN_BEDROOM +" =? AND "+
                            PropertyContract.PropertyEntry.COLUMN_PRICE +" =?";

                    String[] selectionArgsQuery = new String[]{sectorString,pktString,plotString,
                            areaString,floorString,bedroomString,priceString};

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
