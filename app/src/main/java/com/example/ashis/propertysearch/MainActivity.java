package com.example.ashis.propertysearch;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ashis.propertysearch.data.PropertyContract;
import com.example.ashis.propertysearch.data.PropertyDbHelper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements RecyclerViewAdapter.ListItemClickListener {

    private RecyclerView mRecyclerView;
    private SQLiteDatabase mDb;
    //  private List<Property> mList;
    private RecyclerViewAdapter mAdapter;
    private Cursor cursor;
    private TextView mFilterTxt,textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //  mList=new ArrayList<>();

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mFilterTxt = (TextView) findViewById(R.id.filterTextView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(mRecyclerView.getContext(), DividerItemDecoration.VERTICAL));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        textView = (TextView)findViewById(R.id.emptyText);


        cursor = getAllProperties();
        mAdapter = new RecyclerViewAdapter(cursor, this, this);
        mRecyclerView.setAdapter(mAdapter);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddPropertyActivity.class);
                startActivityForResult(intent, 666);
            }
        });
        mFilterTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(MainActivity.this,FiltersActivity.class));
                startActivityForResult(new Intent(MainActivity.this, FiltersActivity.class), 100);

            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                long id = (long) viewHolder.itemView.getTag();

                boolean isDeleted = removeProperty(id);

                Log.i(MainActivity.class.getSimpleName(), "Row deleted: " + isDeleted);
                mAdapter.swapCursor(getAllProperties());
            }
        }).attachToRecyclerView(mRecyclerView);


    }

    private boolean removeProperty(long id) {
        return mDb.delete(PropertyContract.PropertyEntry.TABLE_NAME, PropertyContract.PropertyEntry._ID + " = " + id, null) > 0;
    }


    private Cursor getAllProperties() {
        PropertyDbHelper dbHelper = new PropertyDbHelper(this);
        mDb = dbHelper.getReadableDatabase();
        return mDb.query(PropertyContract.PropertyEntry.TABLE_NAME, null, null, null, null, null, null, null);
    }


    @Override
    public void onItemClick(int pos) {
        Intent intent = new Intent(this, ShowProperty.class);
        intent.putExtra("flag", 1);
        intent.putExtra("rowId", pos);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_screen_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==R.id.saveDb){
            saveDataBase();
        }
        return super.onOptionsItemSelected(item);
    }

    private void saveDataBase() {

        StringBuilder builder = new StringBuilder();
        Cursor cursor = getAllProperties();
        cursor.moveToFirst();
        String outString;


        do {
            int id = cursor.getInt(cursor.getColumnIndex(PropertyContract.PropertyEntry._ID));
            String sector = cursor.getString(cursor.getColumnIndex(PropertyContract.PropertyEntry.COLUMN_SECTOR));
            String size = cursor.getString(cursor.getColumnIndex(PropertyContract.PropertyEntry.COLUMN_AREA));
            String pkt = cursor.getString(cursor.getColumnIndex(PropertyContract.PropertyEntry.COLUMN_PKT));
            String floor = cursor.getString(cursor.getColumnIndex(PropertyContract.PropertyEntry.COLUMN_FLOOR));
            String flatNo = cursor.getString(cursor.getColumnIndex(PropertyContract.PropertyEntry.COLUMN_PLOT));
            String bedroom = cursor.getString(cursor.getColumnIndex(PropertyContract.PropertyEntry.COLUMN_BEDROOM));
            String dealerName = cursor.getString(cursor.getColumnIndex(PropertyContract.PropertyEntry.COLUMN_DEALER_NAME));
            String price = cursor.getString(cursor.getColumnIndex(PropertyContract.PropertyEntry.COLUMN_PRICE));
            String notes = cursor.getString(cursor.getColumnIndex(PropertyContract.PropertyEntry.COLUMN_NOTES));
            String location = cursor.getString(cursor.getColumnIndex(PropertyContract.PropertyEntry.COLUMN_LOCATION));
            String remarks = cursor.getString(cursor.getColumnIndex(PropertyContract.PropertyEntry.COLUMN_REMARKS));
            String societyName = cursor.getString(cursor.getColumnIndex(PropertyContract.PropertyEntry.COLUMN_SOCIETY));
            builder.append(id + "\t\t\tSector: ").append(sector).append("\t\t\t\t\t Size: ").append(size).append("\n")
                    .append("\t\t\tPocket: ").append(pkt).append("\t\t\t\t\t Floor: ").append(floor).append("\n")
                    .append("\t\t\tFlat No: ").append(flatNo).append("\t\t\t\t\t Bedroom: ").append(bedroom).append(" bhk\n")
                    .append("\t\t\tDealer Name: ").append(dealerName).append("\t\t\t\t\t Price: Rs").append(price).append(" lac\n")
                    .append("\t\t\tNotes: ").append(notes).append("\t\t\t\t\t Location: ").append(location).append("\n")
                    .append("\t\t\tRemarks: ").append(remarks).append("\t\t\t\t\t Society Name: ").append(societyName)
                    .append("\n\t\t\t\t\t"+"---------------x----------------"+"\t\t\n");

        }while (cursor.moveToNext());

        outString = builder.toString();
        if (isStoragePermissionGranted()){
            File dir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS),"propertiesDb/");
            //dir.mkdir();
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            String formattedString = dateFormat.format(calendar.getTime());



            if (dir.mkdir())
                Toast.makeText(this,"Data stored Successfully!",Toast.LENGTH_SHORT).show();
            File file = new File(dir,"PropDb " +formattedString+".txt");
            try {
                FileOutputStream oStream = new FileOutputStream(file);
                oStream.write(outString.getBytes());
                oStream.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    public  boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v(MainActivity.class.getSimpleName(),"Permission is granted");
                return true;
            } else {

                Log.v(MainActivity.class.getSimpleName(),"Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.v(MainActivity.class.getSimpleName(),"Permission is granted");
            return true;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {
            if (resultCode == RESULT_OK) {
//                String pktSTring = data.getStringExtra("pktValue");
//                String plotString = data.getStringExtra("plotValue");
//                String sectorString = data.getStringExtra("sectorValue");
//                String floorString = data.getStringExtra("floorValue");
//                String areaString = data.getStringExtra("areaValue");
//                String priceString = data.getStringExtra("priceValue");
//                Log.i(MainActivity.class.getSimpleName(),pktSTring+" "+plotString+" "+sectorString+" "+floorString+" "+areaString+" "+priceString);
//
//                String query = "select * from "+ PropertyContract.PropertyEntry.TABLE_NAME+
//                        " where " + PropertyContract.PropertyEntry.COLUMN_PKT + " = \""+ pktSTring+
//                        "\" and " + PropertyContract.PropertyEntry.COLUMN_PLOT + " = \""+plotString+
//                        "\" and " + PropertyContract.PropertyEntry.COLUMN_SECTOR + " = \""+sectorString+
//                        "\" and " + PropertyContract.PropertyEntry.COLUMN_FLOOR + " = \""+floorString+
//                        "\" and " + PropertyContract.PropertyEntry.COLUMN_AREA + " = \""+areaString+
//                        "\" and " + PropertyContract.PropertyEntry.COLUMN_PRICE + " = \""+priceString +"\" ;";
//
//                Log.i(MainActivity.class.getSimpleName(),query);
//
//                Cursor cursor = mDb.rawQuery(query,null);

                String queryResult = data.getStringExtra("resultString");


                String s = queryResult.substring(0, (queryResult.length() - 4)) + ";";
                Log.i(MainActivity.class.getSimpleName(), s);

                Cursor cursor = mDb.rawQuery(s, null);
                Log.i(MainActivity.class.getSimpleName(), "cursor count:" + cursor.getCount());
                if (cursor.getCount() == 0) {
                    mRecyclerView.setVisibility(View.GONE);
                    textView.setVisibility(View.VISIBLE);
                }
                else {
                    textView.setVisibility(View.GONE);
                    mRecyclerView.setVisibility(View.VISIBLE);

                    if (cursor.moveToFirst()) {
                        do {
                            Log.i(MainActivity.class.getSimpleName(), cursor.getString(cursor.getColumnIndex(PropertyContract.PropertyEntry.COLUMN_NOTES)));
                        } while (cursor.moveToNext());
                        mAdapter.swapCursor(cursor);
                        //sssasa
                        mAdapter.notifyDataSetChanged();
                    }
                }
            }
        } else if (requestCode == 666) {
            if (resultCode == RESULT_OK) {
                Cursor newCursor = getAllProperties();
                if (newCursor.moveToFirst()) {
                    mAdapter.swapCursor(newCursor);
                    mAdapter.notifyDataSetChanged();
                }
            }
        }
//        else if (requestCode == 900){
//                if (resultCode==RESULT_OK ||resultCode==RESULT_CANCELED) {
//                    Cursor newCursor = getAllProperties();
//                    if (newCursor.moveToFirst()) {
//                        mAdapter.swapCursor(newCursor);
//                        mAdapter.notifyDataSetChanged();
//                    }
//                }
//        }
    }
}
