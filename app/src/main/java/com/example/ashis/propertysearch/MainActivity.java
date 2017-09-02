package com.example.ashis.propertysearch;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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

import java.util.ArrayList;
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
    protected void onResume() {
        super.onResume();
        Cursor cursor = getAllProperties();
        mAdapter.swapCursor(cursor);
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
