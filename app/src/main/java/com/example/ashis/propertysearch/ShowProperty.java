package com.example.ashis.propertysearch;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.ashis.propertysearch.data.PropertyContract;
import com.example.ashis.propertysearch.data.PropertyDbHelper;

/**
 *   int flagId = getIntent().getIntExtra("flag",100);

 if (flagId != 100){
 PropertyDbHelper helper = new PropertyDbHelper(this);
 mDb=helper.getReadableDatabase();
 int rowId = getIntent().getIntExtra("rowId",200);
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
 *
 */

public class ShowProperty extends AppCompatActivity {

    private TextView mSectorTxt,mFlatNoTxt,mPktTxt,mLocationTxt,mBedroomTxt,mPriceTxt,mFloorTxt,mSizeTxt,
            mNotesTxt,mRemarksTxt,mSocietyNameTxt,mPostedByTxt;
    private SQLiteDatabase mDb;
    private Cursor cursor;
    private int rowId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_property);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mPostedByTxt = (TextView) findViewById(R.id.postedByValue);
        mSocietyNameTxt = (TextView) findViewById(R.id.societyName);
        mSectorTxt = (TextView) findViewById(R.id.sectorValue);
        mFlatNoTxt = (TextView) findViewById(R.id.flatNoValue);
        mPktTxt = (TextView) findViewById(R.id.pktValue);
        mLocationTxt = (TextView) findViewById(R.id.locationValue);
        mBedroomTxt = (TextView) findViewById(R.id.bedroomValue);
        mPriceTxt = (TextView) findViewById(R.id.priceValue);
        mFloorTxt = (TextView) findViewById(R.id.floorValue);
        mSizeTxt = (TextView) findViewById(R.id.sizeValue);
        mNotesTxt = (TextView) findViewById(R.id.notesValue);
        mRemarksTxt = (TextView) findViewById(R.id.remarksValue);


        int flagId = getIntent().getIntExtra("flag", 100);

        if (flagId != 100) {
            PropertyDbHelper helper = new PropertyDbHelper(this);
            mDb = helper.getReadableDatabase();
            rowId = getIntent().getIntExtra("rowId", 200);
            if (rowId != 200) {
                cursor = getPropertyDetail(rowId);
                cursor.moveToFirst();
                mPktTxt.setText(cursor.getString(cursor.getColumnIndex(PropertyContract.PropertyEntry.COLUMN_PKT)));
                mFlatNoTxt.setText(cursor.getString(cursor.getColumnIndex(PropertyContract.PropertyEntry.COLUMN_PLOT)));
                mSectorTxt.setText(cursor.getString(cursor.getColumnIndex(PropertyContract.PropertyEntry.COLUMN_SECTOR)));
                mFloorTxt.setText(cursor.getString(cursor.getColumnIndex(PropertyContract.PropertyEntry.COLUMN_FLOOR)));
                mSizeTxt.setText(cursor.getString(cursor.getColumnIndex(PropertyContract.PropertyEntry.COLUMN_AREA)));
                mNotesTxt.setText(cursor.getString(cursor.getColumnIndex(PropertyContract.PropertyEntry.COLUMN_NOTES)));
                mPriceTxt.setText(cursor.getString(cursor.getColumnIndex(PropertyContract.PropertyEntry.COLUMN_PRICE)));
                mLocationTxt.setText(cursor.getString(cursor.getColumnIndex(PropertyContract.PropertyEntry.COLUMN_LOCATION)));
                mBedroomTxt.setText(cursor.getString(cursor.getColumnIndex(PropertyContract.PropertyEntry.COLUMN_BEDROOM)));
                mSocietyNameTxt.setText(cursor.getString(cursor.getColumnIndex(PropertyContract.PropertyEntry.COLUMN_SOCIETY)));
                mRemarksTxt.setText(cursor.getString(cursor.getColumnIndex(PropertyContract.PropertyEntry.COLUMN_REMARKS)));
                int count = cursor.getInt(cursor.getColumnIndex(PropertyContract.PropertyEntry.COLUMN_DEALER));
                if (count == 1) {
                    mPostedByTxt.setText(cursor.getString(cursor.getColumnIndex(PropertyContract.PropertyEntry.COLUMN_DEALER_NAME)));
                } else if (count == 0) {
                    mPostedByTxt.setText("Me");
                }
            }
        }
    }


    private Cursor getPropertyDetail(int rowId) {
        return mDb.query(PropertyContract.PropertyEntry.TABLE_NAME,null, PropertyContract.PropertyEntry._ID+"="+rowId,null,null,null
                ,null,null);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return super.onCreateOptionsMenu(menu);
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home:
                Intent intent1 = new Intent();
              //  setResult(RESULT_OK,intent1);
                finish();
                break;
            case R.id.action_edit:
                Intent intent = new Intent(this,AddPropertyActivity.class);
                intent.putExtra("flag",1);
                intent.putExtra("rowId",rowId);
                startActivity(intent);
                break;
            case R.id.action_addMore:
                Intent intentAddMore = new Intent(this,AddPropertyActivity.class);
                intentAddMore.putExtra("flag",2);
                intentAddMore.putExtra("rowId",rowId);
                startActivity(intentAddMore);
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent1 = new Intent();
       // setResult(RESULT_OK,intent1);
        finish();
    }
}
