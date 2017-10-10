package com.example.ashis.propertysearch;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ashis.propertysearch.data.PropertyContract;
import com.example.ashis.propertysearch.data.PropertyDbHelper;

import java.util.ArrayList;
import java.util.List;

public class FiltersActivity extends AppCompatActivity implements View.OnClickListener {
    private LinearLayout mPktSelectorLayout, mPlotSelectorLayout, mSectorSelectorLayout, mFloorSelectorLayout,
            mAreaSelectorLayout, mPriceSelectorLayout, mPostedBySelectorLayout,mRemarksSelectorLayout,
            mLocationSelectorLayout, mBedroomSelector, mSocietySelector, mPlotFloorSelector;
    private Button mClearAllBtn, mApplyBtn;
    private SQLiteDatabase mDb;
    private TextView mPkyValueTxt, mPlotValueTxt, mSectorValueTxt, mFloorValueTxt, mPostedByValue,
            mAreaValueTxt, mPriceValueTxt, mLocationValueTxt, mBedroomValue, mSocietyValue,
            mPlotFloorValue,mRemarksValue;
    private ArrayAdapter<String> mAdapter;
    private StringBuilder stringBuilder;
    private String queryString;
    private FragmentManager fm;
    private ArrayList<String> pktList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filters);
        pktList = new ArrayList<>();

        mRemarksSelectorLayout=(LinearLayout)findViewById(R.id.remarksSelectorLayout);
        mPlotFloorSelector = (LinearLayout) findViewById(R.id.plotFloorSelectorLayout);
        mSocietySelector = (LinearLayout) findViewById(R.id.societySelectorLayout);
        mBedroomSelector = (LinearLayout) findViewById(R.id.bedroomSelectorLayout);
        mLocationSelectorLayout = (LinearLayout) findViewById(R.id.locationSelectorLayout);
        mPktSelectorLayout = (LinearLayout) findViewById(R.id.pktSelectorLayout);
        mPlotSelectorLayout = (LinearLayout) findViewById(R.id.plotSelectorLayout);
        mSectorSelectorLayout = (LinearLayout) findViewById(R.id.sectorSelectorLayout);
        mFloorSelectorLayout = (LinearLayout) findViewById(R.id.floorSelectorLayout);
        mAreaSelectorLayout = (LinearLayout) findViewById(R.id.areaSelectorLayout);
        mPriceSelectorLayout = (LinearLayout) findViewById(R.id.priceSelectorLayout);
        mPostedBySelectorLayout = (LinearLayout) findViewById(R.id.postedBySelectorLayout);

        mClearAllBtn = (Button) findViewById(R.id.clearAllBtn);
        mApplyBtn = (Button) findViewById(R.id.appyFilterBtn);

        mRemarksValue=(TextView)findViewById(R.id.remarksValue);
        mPlotFloorValue = (TextView) findViewById(R.id.plotFloorValue);
        mSocietyValue = (TextView) findViewById(R.id.societyValue);
        mBedroomValue = (TextView) findViewById(R.id.bedroomValue);
        mLocationValueTxt = (TextView) findViewById(R.id.locationValue);
        mPostedByValue = (TextView) findViewById(R.id.postedByValue);
        mPkyValueTxt = (TextView) findViewById(R.id.pktValue);
        mPlotValueTxt = (TextView) findViewById(R.id.plotValue);
        mSectorValueTxt = (TextView) findViewById(R.id.sectorValue);
        mFloorValueTxt = (TextView) findViewById(R.id.floorValue);
        mAreaValueTxt = (TextView) findViewById(R.id.areaValue);
        mPriceValueTxt = (TextView) findViewById(R.id.priceValue);

        mAdapter = new ArrayAdapter<String>(this, android.R.layout.select_dialog_singlechoice);

        stringBuilder = new StringBuilder();
        queryString = " select * from " + PropertyContract.PropertyEntry.TABLE_NAME + " where ";

        stringBuilder.append(queryString);

        PropertyDbHelper dbHelper = new PropertyDbHelper(this);

        mDb = dbHelper.getReadableDatabase();


        mLocationSelectorLayout.setOnClickListener(this);
        mPostedBySelectorLayout.setOnClickListener(this);
        mPktSelectorLayout.setOnClickListener(this);
        mPlotSelectorLayout.setOnClickListener(this);
        mSectorSelectorLayout.setOnClickListener(this);
        mFloorSelectorLayout.setOnClickListener(this);
        mAreaSelectorLayout.setOnClickListener(this);
        mPriceSelectorLayout.setOnClickListener(this);
        mApplyBtn.setOnClickListener(this);
        mClearAllBtn.setOnClickListener(this);
        mBedroomSelector.setOnClickListener(this);
        mPlotFloorSelector.setOnClickListener(this);
        mSocietySelector.setOnClickListener(this);
        mRemarksSelectorLayout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pktSelectorLayout:
                if (!pktList.isEmpty()) {
                    pktList.clear();
                }
                String pktSelect = "Select Pocket";

                final String pktQuery = "select distinct " + PropertyContract.PropertyEntry.COLUMN_PKT
                        + " from " + PropertyContract.PropertyEntry.TABLE_NAME + " ORDER BY " +
                        PropertyContract.PropertyEntry.COLUMN_PKT;
                Cursor cursorPkt = mDb.rawQuery(pktQuery, null);
                if (cursorPkt.moveToFirst()) {
                    do {

                        pktList.add(cursorPkt.getString(cursorPkt.getColumnIndex(PropertyContract.PropertyEntry.COLUMN_PKT)));

                    } while (cursorPkt.moveToNext());
                }
                showFilterDialog(pktList, 1,pktSelect);
                break;
            case R.id.plotSelectorLayout:
                if (!pktList.isEmpty()) {
                    pktList.clear();
                }
                String plotSelect = "Select Plot";
                final String plotQuery = "select distinct " + PropertyContract.PropertyEntry.COLUMN_PLOT
                        + " from " + PropertyContract.PropertyEntry.TABLE_NAME + " ORDER BY " +
                        PropertyContract.PropertyEntry.COLUMN_PLOT;
                Cursor cursorPlot = mDb.rawQuery(plotQuery, null);
                if (cursorPlot.moveToFirst()) {
                    do {

                        pktList.add(cursorPlot.getString(cursorPlot.getColumnIndex(PropertyContract.PropertyEntry.COLUMN_PLOT)));

                    } while (cursorPlot.moveToNext());
                }
                showFilterDialog(pktList, 2,plotSelect);
                break;
            case R.id.sectorSelectorLayout:
                if (!pktList.isEmpty()) {
                    pktList.clear();
                }
                String secSelect = "Select Sector";
                final String sectorQuery = "select distinct " + PropertyContract.PropertyEntry.COLUMN_SECTOR
                        + " from " + PropertyContract.PropertyEntry.TABLE_NAME + " ORDER BY " +
                        PropertyContract.PropertyEntry.COLUMN_SECTOR;
                Cursor cursorSector = mDb.rawQuery(sectorQuery, null);
                if (cursorSector.moveToFirst()) {
                    do {

                        pktList.add(cursorSector.getString(cursorSector.getColumnIndex(PropertyContract.PropertyEntry.COLUMN_SECTOR)));

                    } while (cursorSector.moveToNext());
                }
                showFilterDialog(pktList, 3,secSelect);
                break;
            case R.id.floorSelectorLayout:
                if (!pktList.isEmpty()) {
                    pktList.clear();
                }
                String floorSelect = "Select floor";

                final String floorQuery = "select distinct " + PropertyContract.PropertyEntry.COLUMN_FLOOR
                        + " from " + PropertyContract.PropertyEntry.TABLE_NAME + " ORDER BY " +
                        PropertyContract.PropertyEntry.COLUMN_FLOOR;
                Cursor cursorFloor = mDb.rawQuery(floorQuery, null);
                if (cursorFloor.moveToFirst()) {
                    do {

                        pktList.add(cursorFloor.getString(cursorFloor.getColumnIndex(PropertyContract.PropertyEntry.COLUMN_FLOOR)));

                    } while (cursorFloor.moveToNext());
                }
                showFilterDialog(pktList, 4,floorSelect);
                break;
            case R.id.areaSelectorLayout:
                if (!pktList.isEmpty()) {
                    pktList.clear();
                }
                String areSelect = "Select Area";
                final String areaQuery = "select distinct " + PropertyContract.PropertyEntry.COLUMN_AREA
                        + " from " + PropertyContract.PropertyEntry.TABLE_NAME + " ORDER BY " +
                        PropertyContract.PropertyEntry.COLUMN_AREA;
                Cursor cursorArea = mDb.rawQuery(areaQuery, null);
                if (cursorArea.moveToFirst()) {
                    do {

                        pktList.add(cursorArea.getString(cursorArea.getColumnIndex(PropertyContract.PropertyEntry.COLUMN_AREA)));

                    } while (cursorArea.moveToNext());
                }
                showFilterDialog(pktList, 5,areSelect);
                break;
            case R.id.priceSelectorLayout:
//                String priceTitle = "Select Price";
//                if (mAdapter!=null){
//                    mAdapter.clear();
//                }
//                final String priceQuery = "select distinct "+ PropertyContract.PropertyEntry.COLUMN_PRICE + " from "+ PropertyContract.PropertyEntry.TABLE_NAME + " ; ";
//                Cursor cursorPrice = mDb.rawQuery(priceQuery,null);
//                if (cursorPrice.moveToFirst()){
//                    do {
//
//                        mAdapter.add(cursorPrice.getString(cursorPrice.getColumnIndex(PropertyContract.PropertyEntry.COLUMN_PRICE)));
//
//                    }while (cursorPrice.moveToNext());
//                }
//                showDialog(priceTitle,6,mAdapter);
                showPriceDialog();
                break;
            case R.id.postedBySelectorLayout:
                if (!pktList.isEmpty()) {
                    pktList.clear();
                }
                String postedTitle = "Posted By";
                pktList.add("Mine");
                final String dealerNameQuery = "select distinct " + PropertyContract.PropertyEntry.COLUMN_DEALER_NAME +
                        " from " + PropertyContract.PropertyEntry.TABLE_NAME + " order by " +
                        PropertyContract.PropertyEntry.COLUMN_DEALER_NAME;
                Cursor cursorDealerName = mDb.rawQuery(dealerNameQuery, null);
                if (cursorDealerName.moveToFirst()) {
                    do {
                        pktList.add(cursorDealerName.getString(cursorDealerName.getColumnIndex(PropertyContract.PropertyEntry.COLUMN_DEALER_NAME)));
                    } while (cursorDealerName.moveToNext());
                }
                showFilterDialog(pktList, 7,postedTitle);
                break;
            case R.id.locationSelectorLayout:
                if (!pktList.isEmpty()) {
                    pktList.clear();
                }
                String locationSelect = "Select Location";
                final String locationQuery = "select distinct " + PropertyContract.PropertyEntry.COLUMN_LOCATION
                        + " from " + PropertyContract.PropertyEntry.TABLE_NAME + " ORDER BY " +
                        PropertyContract.PropertyEntry.COLUMN_LOCATION;
                Cursor cursorLocation = mDb.rawQuery(locationQuery, null);
                if (cursorLocation.moveToFirst()) {
                    do {

                        pktList.add(cursorLocation.getString(cursorLocation.getColumnIndex(PropertyContract.PropertyEntry.COLUMN_LOCATION)));

                    } while (cursorLocation.moveToNext());
                }
                showFilterDialog(pktList, 8,locationSelect);
                break;
            case R.id.bedroomSelectorLayout:
                if (!pktList.isEmpty()) {
                    pktList.clear();
                }
                String bedroomSelect = "Select No of Bedroom";
                final String bedroomQuery = "select distinct " + PropertyContract.PropertyEntry.COLUMN_BEDROOM
                        + " from " + PropertyContract.PropertyEntry.TABLE_NAME + " ORDER BY " +
                        PropertyContract.PropertyEntry.COLUMN_BEDROOM;
                Cursor cursorBedroom = mDb.rawQuery(bedroomQuery, null);
                if (cursorBedroom.moveToFirst()) {
                    do {

                        pktList.add(cursorBedroom.getString(cursorBedroom.getColumnIndex(PropertyContract.PropertyEntry.COLUMN_BEDROOM)));

                    } while (cursorBedroom.moveToNext());
                }
                showFilterDialog(pktList, 9,bedroomSelect);
                break;
            case R.id.societySelectorLayout:
                if (!pktList.isEmpty()) {
                    pktList.clear();
                }
                String societySelect = "Select Society Name:";
                final String societyQuery = "select distinct " + PropertyContract.PropertyEntry.COLUMN_SOCIETY
                        + " from " + PropertyContract.PropertyEntry.TABLE_NAME + " ORDER BY "
                        + PropertyContract.PropertyEntry.COLUMN_SOCIETY;
                Cursor cursorSociety = mDb.rawQuery(societyQuery, null);
                if (cursorSociety.moveToFirst()) {
                    do {

                        pktList.add(cursorSociety.getString(cursorSociety.getColumnIndex(PropertyContract.PropertyEntry.COLUMN_SOCIETY)));

                    } while (cursorSociety.moveToNext());
                }
                showFilterDialog(pktList, 10,societySelect);
                break;
            case R.id.plotFloorSelectorLayout:
                if (!pktList.isEmpty()) {
                    pktList.clear();
                }
                String plotFloorSelect = "Plot or Floor";
                pktList.add("Plot");
                pktList.add("Floor");
                showFilterDialog(pktList, 11,plotFloorSelect);
                break;
            case R.id.remarksSelectorLayout:
                if (!pktList.isEmpty()) {
                    pktList.clear();
                }
                String remarksSelect = "Select Society Name:";
                final String remarksQuery = "select distinct " + PropertyContract.PropertyEntry.COLUMN_REMARKS
                        + " from " + PropertyContract.PropertyEntry.TABLE_NAME + " ORDER BY "
                        + PropertyContract.PropertyEntry.COLUMN_REMARKS;
                Cursor cursorRemarks = mDb.rawQuery(remarksQuery, null);
                if (cursorRemarks.moveToFirst()) {
                    do {

                        pktList.add(cursorRemarks.getString(cursorRemarks.getColumnIndex(PropertyContract.PropertyEntry.COLUMN_REMARKS)));

                    } while (cursorRemarks.moveToNext());
                }
                showFilterDialog(pktList, 12,remarksSelect);
                break;
            case R.id.clearAllBtn:
                mAreaValueTxt.setText("");
                mFloorValueTxt.setText("");
                mPkyValueTxt.setText("");
                mPlotValueTxt.setText("");
                mPriceValueTxt.setText("");
                mSectorValueTxt.setText("");
                break;
            case R.id.appyFilterBtn:
                Intent intent = new Intent();
                intent.putExtra("areaValue", mAreaValueTxt.getText().toString());
                intent.putExtra("floorValue", mFloorValueTxt.getText().toString());
                intent.putExtra("pktValue", mPkyValueTxt.getText().toString());
                intent.putExtra("plotValue", mPlotValueTxt.getText().toString());
                intent.putExtra("priceValue", mPriceValueTxt.getText().toString());
                intent.putExtra("sectorValue", mSectorValueTxt.getText().toString());
                intent.putExtra("resultString", stringBuilder.toString());
                setResult(RESULT_OK, intent);
                finish();
        }
    }

    private void showFilterDialog(ArrayList<String> mList, int flag,String title) {

        Intent intent = new Intent(this, FilterList.class);
        intent.putStringArrayListExtra("list", mList);
        intent.putExtra("flag", flag);
        intent.putExtra("title",title);
        startActivityForResult(intent, 101);

    }

    private void showPriceDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final View dialogView = LayoutInflater.from(this).inflate(R.layout.content_price_filter, null);
        //Button submitBtn = (Button)findViewById(R.id.submitBtn);

        builder.setView(dialogView);
        final android.support.design.widget.TextInputEditText minPrice = (android.support.design.widget.TextInputEditText) dialogView.findViewById(R.id.minPrice);
        final android.support.design.widget.TextInputEditText maxPrice = (android.support.design.widget.TextInputEditText) dialogView.findViewById(R.id.maxPrice);

        builder.setTitle("Set Price");
        builder.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


                String max = maxPrice.getText().toString();
                String min = minPrice.getText().toString();
                if (max.equals("") || min.equals("")) {
                    Toast.makeText(getApplicationContext(), "empty fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                stringBuilder.append(" " + PropertyContract.PropertyEntry.COLUMN_PRICE + " >= \'" + min).
                        append("\' and " + PropertyContract.PropertyEntry.COLUMN_PRICE + " <= \'" + max + "\' and ");
                mPriceValueTxt.setText("Rs " + min + " To Rs. " + max);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog al = builder.create();
        al.show();

        // Cursor cursor = mDb.query(PropertyContract.PropertyEntry.TABLE_NAME,new String[PropertyContract.PropertyEntry.COLUMN_PKT])


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {

        super.onActivityResult(requestCode, resultCode, intent);

        if (requestCode == 101) {
            if (resultCode == RESULT_OK) {
                int flag = intent.getIntExtra("flag", 0);
                String[] data = intent.getStringArrayExtra("list");
                String joined = TextUtils.join(",",data);

                switch (flag) {
                    case 1:
                        stringBuilder.append(" "+PropertyContract.PropertyEntry.COLUMN_PKT+" IN (")
                            .append(joined).append(") and");
                        mPkyValueTxt.setText(joined);
                        break;
                    case 2:
                        stringBuilder.append(" "+PropertyContract.PropertyEntry.COLUMN_PLOT+" IN (")
                                .append(joined).append(") and");
                        mPlotValueTxt.setText(joined);
                        break;
                    case 3:
                        stringBuilder.append(" "+PropertyContract.PropertyEntry.COLUMN_SECTOR +" IN (")
                                .append(joined).append(") and");
                        mSectorValueTxt.setText(joined);
                        break;
                    case 4:
                        stringBuilder.append(" "+PropertyContract.PropertyEntry.COLUMN_FLOOR +" IN (")
                                .append(joined).append(") and");
                        mFloorValueTxt.setText(joined);
                        break;
                    case 5:

                        stringBuilder.append(" "+PropertyContract.PropertyEntry.COLUMN_AREA+" IN (")
                                .append(joined).append(") and");
                        mAreaValueTxt.setText(joined);
                        break;

                    case 7:
                        if (joined.contains("\'Mine\'")) {
                            stringBuilder.append(" " + PropertyContract.PropertyEntry.COLUMN_DEALER + " = 0").append(" and");
                        } else {
                            stringBuilder.append(" "+PropertyContract.PropertyEntry.COLUMN_DEALER_NAME  +" IN (")
                                    .append(joined).append(") and");
                        }
                        mPostedByValue.setText(joined);
                        break;
                    case 8:

                        stringBuilder.append(" "+PropertyContract.PropertyEntry.COLUMN_LOCATION  +" IN (")
                                .append(joined).append(") and");
                        mLocationValueTxt.setText(joined);
                        break;
                    case 9:

                        stringBuilder.append(" "+PropertyContract.PropertyEntry.COLUMN_BEDROOM  +" IN (")
                                .append(joined).append(") and");
                        mBedroomValue.setText(joined);
                        break;
                    case 10:

                        stringBuilder.append(" "+PropertyContract.PropertyEntry.COLUMN_SOCIETY  +" IN (")
                                .append(joined).append(") and");
                        mSocietyValue.setText(joined);
                        break;
                    case 11:
                        mPlotFloorValue.setText(joined);
                        if (joined.equals("\'Plot\'"))
                            stringBuilder.append(" " + PropertyContract.PropertyEntry.COLUMN_FLOOR + " =\'Plot' and");
                        else
                            stringBuilder.append(" not " + PropertyContract.PropertyEntry.COLUMN_FLOOR + " =\'Plot\' and");
                        break;
                    case 12:

                        stringBuilder.append(" "+PropertyContract.PropertyEntry.COLUMN_REMARKS+" IN (")
                                .append(joined).append(") and");
                        mRemarksValue.setText(joined);
                        break;
                }
            }

        }
    }
}


