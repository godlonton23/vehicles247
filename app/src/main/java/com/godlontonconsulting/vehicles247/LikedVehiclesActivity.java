package com.godlontonconsulting.vehicles247;
import java.util.ArrayList;
import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;


public class LikedVehiclesActivity extends Activity {

    private DataAdapter adapter;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liked);

        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        toolbar.setTitle("Vehicles 24/7 - Liked Vehicles");
        toolbar.setNavigationIcon(R.drawable.heart);
        //
        //Bundle b = getIntent().getExtras();
        //ArrayList<ParcelableData> myIdList =  b.getParcelable("idArray");

        ArrayList<ParcelableData> myIdList = MainActivity.idList;

        RecyclerView recView = (RecyclerView) findViewById(R.id.list);
        DefaultItemAnimator itemAnimator = new DefaultItemAnimator();
        recView.setHasFixedSize(true);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(LikedVehiclesActivity.this);
        recView.setLayoutManager(mLinearLayoutManager);
        recView.setItemAnimator(itemAnimator);
        adapter = new DataAdapter(LikedVehiclesActivity.this,myIdList);
        recView.setAdapter(adapter);
    }
}
