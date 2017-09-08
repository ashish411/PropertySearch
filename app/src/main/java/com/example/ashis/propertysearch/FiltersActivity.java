package com.example.ashis.propertysearch;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ashis.propertysearch.data.PropertyContract;
import com.example.ashis.propertysearch.data.PropertyDbHelper;

public class FiltersActivity extends AppCompatActivity implements View.OnClickListener{
    private LinearLayout mPktSelectorLayout,mPlotSelectorLayout,mSectorSelectorLayout,mFloorSelectorLayout,
            mAreaSelectorLayout,mPriceSelectorLayout,mPostedBySelectorLayout,
            mLocationSelectorLayout,mBedroomSelector,mSocietySelector,mPlotFloorSelector;
    private Button mClearAllBtn,mApplyBtn;
    private SQLiteDatabase mDb;
    private TextView mPkyValueTxt,mPlotValueTxt,mSectorValueTxt,mFloorValueTxt,mPostedByValue,
            mAreaValueTxt,mPriceValueTxt,mLocationValueTxt,mBedroomValue,mSocietyValue,mPlotFloorValue;
    private ArrayAdapter<String> mAdapter;
    private StringBuilder stringBuilder;
    private String queryString;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filters);

        mPlotFloorSelector=(LinearLayout)findViewById(R.id.plotFloorSelectorLayout);
        mSocietySelector=(LinearLayout)findViewById(R.id.societySelectorLayout);
        mBedroomSelector=(LinearLayout)findViewById(R.id.bedroomSelectorLayout);
        mLocationSelectorLayout=(LinearLayout)findViewById(R.id.locationSelectorLayout);
        mPktSelectorLayout=(LinearLayout)findViewById(R.id.pktSelectorLayout);
        mPlotSelectorLayout=(LinearLayout)findViewById(R.id.plotSelectorLayout);
        mSectorSelectorLayout=(LinearLayout)findViewById(R.id.sectorSelectorLayout);
        mFloorSelectorLayout=(LinearLayout)findViewById(R.id.floorSelectorLayout);
        mAreaSelectorLayout=(LinearLayout)findViewById(R.id.areaSelectorLayout);
        mPriceSelectorLayout=(LinearLayout)findViewById(R.id.priceSelectorLayout);
        mPostedBySelectorLayout = (LinearLayout)findViewById(R.id.postedBySelectorLayout);

        mClearAllBtn=(Button)findViewById(R.id.clearAllBtn);
        mApplyBtn=(Button)findViewById(R.id.appyFilterBtn);

        mPlotFloorValue=(TextView)findViewById(R.id.plotFloorValue);
        mSocietyValue=(TextView)findViewById(R.id.societyValue);
        mBedroomValue=(TextView)findViewById(R.id.bedroomValue);
        mLocationValueTxt=(TextView)findViewById(R.id.locationValue);
        mPostedByValue = (TextView)findViewById(R.id.postedByValue);
        mPkyValueTxt=(TextView)findViewById(R.id.pktValue);
        mPlotValueTxt=(TextView)findViewById(R.id.plotValue);
        mSectorValueTxt=(TextView)findViewById(R.id.sectorValue);
        mFloorValueTxt=(TextView)findViewById(R.id.floorValue);
        mAreaValueTxt=(TextView)findViewById(R.id.areaValue);
        mPriceValueTxt=(TextView)findViewById(R.id.priceValue);

        mAdapter = new ArrayAdapter<String>(this,android.R.layout.select_dialog_singlechoice);

        stringBuilder=new StringBuilder();
        queryString=" select * from " + PropertyContract.PropertyEntry.TABLE_NAME + " where ";

        stringBuilder.append(queryString);

        PropertyDbHelper dbHelper = new PropertyDbHelper(this);

        mDb=dbHelper.getReadableDatabase();


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
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.pktSelectorLayout:
                String pktTitle = "Select Pocket";
                if (mAdapter!=null){
                    mAdapter.clear();
                }
                final String pktQuery = "select distinct "+ PropertyContract.PropertyEntry.COLUMN_PKT + " from "+ PropertyContract.PropertyEntry.TABLE_NAME + " ; ";
                Cursor cursorPkt = mDb.rawQuery(pktQuery,null);
                if (cursorPkt.moveToFirst()){
                    do {

                        mAdapter.add(cursorPkt.getString(cursorPkt.getColumnIndex(PropertyContract.PropertyEntry.COLUMN_PKT)));

                    }while (cursorPkt.moveToNext());
                }
                showDialog(pktTitle,1,mAdapter);
                break;
            case R.id.plotSelectorLayout:
                String plotTitle = "Select Plot";
                if (mAdapter!=null){
                    mAdapter.clear();
                }
                final String plotQuery = "select distinct "+ PropertyContract.PropertyEntry.COLUMN_PLOT + " from "+ PropertyContract.PropertyEntry.TABLE_NAME + " ; ";
                Cursor cursorPlot = mDb.rawQuery(plotQuery,null);
                if (cursorPlot.moveToFirst()){
                    do {

                        mAdapter.add(cursorPlot.getString(cursorPlot.getColumnIndex(PropertyContract.PropertyEntry.COLUMN_PLOT)));

                    }while (cursorPlot.moveToNext());
                }
                showDialog(plotTitle,2,mAdapter);
                break;
            case R.id.sectorSelectorLayout:
                String sectorTitle = "Select Sector/Location";
                if (mAdapter!=null){
                    mAdapter.clear();
                }
                final String sectorQuery = "select distinct "+ PropertyContract.PropertyEntry.COLUMN_SECTOR + " from "+ PropertyContract.PropertyEntry.TABLE_NAME + " ; ";
                Cursor cursorSector = mDb.rawQuery(sectorQuery,null);
                if (cursorSector.moveToFirst()){
                    do {

                        mAdapter.add(cursorSector.getString(cursorSector.getColumnIndex(PropertyContract.PropertyEntry.COLUMN_SECTOR)));

                    }while (cursorSector.moveToNext());
                }
                showDialog(sectorTitle,3,mAdapter);
                break;
            case R.id.floorSelectorLayout:
                String floorTitle = "Select Floor";
                if (mAdapter!=null){
                    mAdapter.clear();
                }
                final String floorQuery = "select distinct "+ PropertyContract.PropertyEntry.COLUMN_FLOOR + " from "+ PropertyContract.PropertyEntry.TABLE_NAME + " ; ";
                Cursor cursorFloor = mDb.rawQuery(floorQuery,null);
                if (cursorFloor.moveToFirst()){
                    do {

                        mAdapter.add(cursorFloor.getString(cursorFloor.getColumnIndex(PropertyContract.PropertyEntry.COLUMN_FLOOR)));

                    }while (cursorFloor.moveToNext());
                }
                showDialog(floorTitle,4,mAdapter);
                break;
            case R.id.areaSelectorLayout:
                String areaTitle = "Select Area";
                if (mAdapter!=null){
                    mAdapter.clear();
                }
                final String areaQuery = "select distinct "+ PropertyContract.PropertyEntry.COLUMN_AREA + " from "+ PropertyContract.PropertyEntry.TABLE_NAME + " ; ";
                Cursor cursorArea = mDb.rawQuery(areaQuery,null);
                if (cursorArea.moveToFirst()){
                    do {

                        mAdapter.add(cursorArea.getString(cursorArea.getColumnIndex(PropertyContract.PropertyEntry.COLUMN_AREA)));

                    }while (cursorArea.moveToNext());
                }
                showDialog(areaTitle,5,mAdapter);
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
                String postedTitle = "Posted By";
                if (mAdapter!=null){
                    mAdapter.clear();
                }
                mAdapter.add("Dealer");
                mAdapter.add("Mine");
                showDialog(postedTitle,7,mAdapter);
                break;
            case R.id.locationSelectorLayout:
                String locationTitle = "Select Location";
                if (mAdapter!=null){
                    mAdapter.clear();
                }
                final String locationQuery = "select distinct "+ PropertyContract.PropertyEntry.COLUMN_LOCATION
                        + " from "+ PropertyContract.PropertyEntry.TABLE_NAME + ""+ " ; ";
                Cursor cursorLocation = mDb.rawQuery(locationQuery,null);
                if (cursorLocation.moveToFirst()){
                    do {

                        mAdapter.add(cursorLocation.getString(cursorLocation.getColumnIndex(PropertyContract.PropertyEntry.COLUMN_LOCATION)));

                    }while (cursorLocation.moveToNext());
                }
                showDialog(locationTitle,8,mAdapter);
                break;
            case R.id.bedroomSelectorLayout:
                String bedroomTitle = "Bedroom";
                if (mAdapter!=null){
                    mAdapter.clear();
                }
                final String bedroomQuery = "select distinct "+ PropertyContract.PropertyEntry.COLUMN_BEDROOM
                        + " from "+ PropertyContract.PropertyEntry.TABLE_NAME + ""+ " ; ";
                Cursor cursorBedroom = mDb.rawQuery(bedroomQuery,null);
                if (cursorBedroom.moveToFirst()){
                    do {

                        mAdapter.add(cursorBedroom.getString(cursorBedroom.getColumnIndex(PropertyContract.PropertyEntry.COLUMN_BEDROOM)));

                    }while (cursorBedroom.moveToNext());
                }
                showDialog(bedroomTitle,9,mAdapter);
                break;
            case R.id.societySelectorLayout:
                String societyTitle = "Society";
                if (mAdapter!=null){
                    mAdapter.clear();
                }
                final String societyQuery = "select distinct "+ PropertyContract.PropertyEntry.COLUMN_SOCIETY
                        + " from "+ PropertyContract.PropertyEntry.TABLE_NAME + ""+ " ; ";
                Cursor cursorSociety = mDb.rawQuery(societyQuery,null);
                if (cursorSociety.moveToFirst()){
                    do {

                        mAdapter.add(cursorSociety.getString(cursorSociety.getColumnIndex(PropertyContract.PropertyEntry.COLUMN_SOCIETY)));

                    }while (cursorSociety.moveToNext());
                }
                showDialog(societyTitle,10,mAdapter);
                break;
            case R.id.plotFloorSelectorLayout:
                String plotFLoorTitle = "Plot or Floor";
                if (mAdapter!=null)
                    mAdapter.clear();

                mAdapter.add("Plot");
                mAdapter.add("Floor");
                showDialog(plotFLoorTitle,11,mAdapter);

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
                intent.putExtra("areaValue",mAreaValueTxt.getText().toString());
                intent.putExtra("floorValue",mFloorValueTxt.getText().toString());
                intent.putExtra("pktValue",mPkyValueTxt.getText().toString());
                intent.putExtra("plotValue",mPlotValueTxt.getText().toString());
                intent.putExtra("priceValue",mPriceValueTxt.getText().toString());
                intent.putExtra("sectorValue",mSectorValueTxt.getText().toString());
                intent.putExtra("resultString",stringBuilder.toString());
                setResult(RESULT_OK,intent);
                finish();
        }
    }

    private void showPriceDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final View dialogView = LayoutInflater.from(this).inflate(R.layout.content_price_filter,null);
       //Button submitBtn = (Button)findViewById(R.id.submitBtn);

        builder.setView(dialogView);
        final android.support.design.widget.TextInputEditText minPrice = (android.support.design.widget.TextInputEditText)dialogView.findViewById(R.id.minPrice);
        final android.support.design.widget.TextInputEditText maxPrice = (android.support.design.widget.TextInputEditText)dialogView.findViewById(R.id.maxPrice);

        builder.setTitle("Set Price");
        builder.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


                String max = maxPrice.getText().toString();
                String min = minPrice.getText().toString();
                if (max.equals("") || min.equals("")){
                    Toast.makeText(getApplicationContext(),"empty fields",Toast.LENGTH_SHORT).show();
                    return;
                }

                stringBuilder.append(" "+ PropertyContract.PropertyEntry.COLUMN_PRICE+" >= \'"+ min).
                        append("\' and "+ PropertyContract.PropertyEntry.COLUMN_PRICE+" <= \'"+max+"\' and ");
                mPriceValueTxt.setText("Rs "+min + " To Rs. "+max);
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


    private void showDialog(String pktTitle, final int flag, final ArrayAdapter<String> mAdapter) {


        AlertDialog.Builder builder = new AlertDialog.Builder(this);


        builder.setAdapter(mAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String pktString = mAdapter.getItem(which);

                dialog.dismiss();

                if (!pktString.equals("") || pktString!=null){
                    switch (flag){
                        case 1:mPkyValueTxt.setText(pktString);
                            stringBuilder.append(" "+PropertyContract.PropertyEntry.COLUMN_PKT + " = \'").append(pktString).append("' and");
                            break;
                        case 2:mPlotValueTxt.setText(pktString);
                            stringBuilder.append(" "+PropertyContract.PropertyEntry.COLUMN_PLOT + " = \'").append(pktString).append("' and");
                            break;
                        case 3:mSectorValueTxt.setText(pktString);
                            stringBuilder.append(" "+PropertyContract.PropertyEntry.COLUMN_SECTOR + " = \'").append(pktString).append("' and");
                            break;
                        case 4:mFloorValueTxt.setText(pktString);
                            stringBuilder.append(" "+PropertyContract.PropertyEntry.COLUMN_FLOOR + " = \'").append(pktString).append("' and");
                            break;
                        case 5:mAreaValueTxt.setText(pktString);
                            stringBuilder.append(" "+PropertyContract.PropertyEntry.COLUMN_AREA + " = \'").append(pktString).append("' and");
                            break;
                        case 6:mPriceValueTxt.setText(pktString);
                            stringBuilder.append( " "+PropertyContract.PropertyEntry.COLUMN_PRICE + " = \'").append(pktString).append("' and");
                            break;
                        case 7:mPostedByValue.setText(pktString);
                            if (pktString.equals("Dealer")){
                                stringBuilder.append( " "+PropertyContract.PropertyEntry.COLUMN_DEALER + " = 1").append(" and");
                            } else {
                                stringBuilder.append( " "+PropertyContract.PropertyEntry.COLUMN_DEALER + " = 0 ").append(" and");
                            }
                            break;
                        case 8:mLocationValueTxt.setText(pktString);
                            stringBuilder.append( " "+PropertyContract.PropertyEntry.COLUMN_LOCATION + " = \'").append(pktString).append("' and");
                            break;
                        case 9:mBedroomValue.setText(pktString);
                            stringBuilder.append( " "+PropertyContract.PropertyEntry.COLUMN_BEDROOM + " = \'").append(pktString).append("' and");
                            break;
                        case 10:mSocietyValue.setText(pktString);
                            stringBuilder.append( " "+PropertyContract.PropertyEntry.COLUMN_SOCIETY + " = \'").append(pktString).append("' and");
                            break;
                        case 11:mPlotFloorValue.setText(pktString);
                            if (pktString.equals("Plot"))
                                stringBuilder.append(" "+PropertyContract.PropertyEntry.COLUMN_FLOOR + " =\'Plot' and");
                            else
                                stringBuilder.append(" not "+ PropertyContract.PropertyEntry.COLUMN_FLOOR + " =\'Plot\' and");

                    }
                }
            }
        });

        builder.show();
    }
}
