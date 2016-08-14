package com.godlontonconsulting.vehicles247;
import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.godlontonconsulting.vehicles247.adapter.DataAdapter;
import com.godlontonconsulting.vehicles247.model.ParcelableData;


public class LikedVehiclesActivity extends AppCompatActivity {

    private DataAdapter adapter;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liked);

        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        toolbar.setTitle("Liked Vehicles");

        setSupportActionBar(toolbar);
        //
        //Bundle b = getIntent().getExtras();
        //ArrayList<ParcelableData> myIdList =  b.getParcelable("idArray");

        ArrayList<ParcelableData> myIdList = MainActivity.idList;

        if (myIdList.size()<=0){
            Toast.makeText(getApplicationContext(), "No liked vehicles chosen. Go back and choose a few cars.", Toast.LENGTH_LONG).show();
        }

        RecyclerView recView = (RecyclerView) findViewById(R.id.list);
        DefaultItemAnimator itemAnimator = new DefaultItemAnimator();
        recView.setHasFixedSize(true);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(LikedVehiclesActivity.this);
        recView.setLayoutManager(mLinearLayoutManager);
        recView.setItemAnimator(itemAnimator);
        adapter = new DataAdapter(LikedVehiclesActivity.this,myIdList);
        recView.setAdapter(adapter);
    }
    //
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_mainbarliked, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_Home:
                Intent intent = new Intent(LikedVehiclesActivity.this, MainActivity.class);
                LikedVehiclesActivity.this.finish();
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    //
}
