package com.example.ashis.propertysearch;

import android.content.Intent;
import androidx.fragment.app.FragmentManager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class FilterList extends AppCompatActivity {

    private ListView mListView;
    private EditText searchQryEditTxt;
    private ArrayList<String> mList;
    private ArrayAdapter<String> mAdapter;
    private int flag=-1;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.filter_list_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==R.id.addFilter){
            Intent intent = new Intent();

            SparseBooleanArray checked = mListView.getCheckedItemPositions();
            ArrayList<String> selectedItems = new ArrayList<>();

            for (int i=0;i<checked.size();i++){
                int position = checked.keyAt(i);

                if (checked.valueAt(i)){
                    selectedItems.add(mAdapter.getItem(position));
                }
            }

            String[] outPutStrArr = new String[selectedItems.size()];

            for (int i=0;i<selectedItems.size();i++){
                outPutStrArr[i]="'"+selectedItems.get(i)+"'";
            }

            intent.putExtra("flag",flag);
            intent.putExtra("list",outPutStrArr);
            setResult(RESULT_OK,intent);
            finish();
        }

        if (item.getItemId()==android.R.id.home){
            finish();
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_list);
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        mList = getIntent().getStringArrayListExtra("list");
        flag = getIntent().getIntExtra("flag",0);
        String title = getIntent().getStringExtra("title");

        setTitle(title);

        mListView = (ListView)findViewById(R.id.filterResult);
        searchQryEditTxt=(EditText)findViewById(R.id.searchText);
        mListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);


        mAdapter = new ArrayAdapter<String>(this,android.R.layout.select_dialog_multichoice,
                mList);


        mListView.setAdapter(mAdapter);

        searchQryEditTxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                mAdapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });




    }


}
