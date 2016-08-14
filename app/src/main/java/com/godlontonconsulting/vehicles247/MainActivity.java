package com.godlontonconsulting.vehicles247;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ParseException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.godlontonconsulting.vehicles247.model.ParcelableData;
import com.godlontonconsulting.vehicles247.model.VehicleData;
import com.godlontonconsulting.vehicles247.tindercard.FlingCardListener;
import com.godlontonconsulting.vehicles247.tindercard.SwipeFlingAdapterView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements FlingCardListener.ActionDownInterface, View.OnClickListener {

    public static MyAppAdapter myAppAdapter;
    public static ViewHolder viewHolder;
    private ArrayList<VehicleData> al;
    public static ArrayList<ParcelableData> idList = new ArrayList<ParcelableData>();

    private SwipeFlingAdapterView flingContainer;

    private Toolbar toolbar;
    private LinearLayout lnrConProblem;
    private Button btnReload;

    public static void removeBackground() {
        viewHolder.background.setVisibility(View.GONE);
        myAppAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        flingContainer = (SwipeFlingAdapterView) findViewById(R.id.frame);

        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        toolbar.setTitle("Vehicles 24/7");
        setSupportActionBar(toolbar);

        lnrConProblem = (LinearLayout) findViewById(R.id.lnrConProblem);
        btnReload = (Button) findViewById(R.id.btnReload);
        btnReload.setOnClickListener(this);

        al = new ArrayList<VehicleData>();
        fetchData();

        myAppAdapter = new MyAppAdapter(al, MainActivity.this);
        flingContainer.setAdapter(myAppAdapter);
        flingContainer.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {
            @Override
            public void removeFirstObjectInAdapter() {

            }

            @Override
            public void onLeftCardExit(Object dataObject) {
                //
                ParcelableData dataObj = new ParcelableData();
                dataObj.title=al.get(0).getTitle();
                dataObj.year=al.get(0).getYear();
                dataObj.default_image=al.get(0).getDefault_image();
                dataObj.price=al.get(0).getPrice();
                dataObj.id=al.get(0).getId();
                idList.add(dataObj);
                //
                al.remove(0);
                myAppAdapter.notifyDataSetChanged();
            }

            @Override
            public void onRightCardExit(Object dataObject) {

                al.remove(0);
                myAppAdapter.notifyDataSetChanged();
            }

            @Override
            public void onAdapterAboutToEmpty(int itemsInAdapter) {

            }

            @Override
            public void onScroll(float scrollProgressPercent) {

                View view = flingContainer.getSelectedView();
                view.findViewById(R.id.background).setAlpha(0);
                view.findViewById(R.id.item_swipe_right_indicator).setAlpha(scrollProgressPercent < 0 ? -scrollProgressPercent : 0);
                view.findViewById(R.id.item_swipe_left_indicator).setAlpha(scrollProgressPercent > 0 ? scrollProgressPercent : 0);
            }
        });


        flingContainer.setOnItemClickListener(new SwipeFlingAdapterView.OnItemClickListener() {
            @Override
            public void onItemClicked(int itemPosition, Object dataObject) {

                View view = flingContainer.getSelectedView();
                view.findViewById(R.id.background).setAlpha(0);

                myAppAdapter.notifyDataSetChanged();
            }
        });

    }
    //
    @Override
    public void onClick(View v) {
            fetchData();
    }
    //
    public void fetchData(){
        lnrConProblem.setVisibility(View.GONE);
        new JSONAsyncTask().execute("http://empty-bush-3943.getsandbox.com/listings");
    }

    // Menu icons are inflated just as they were with actionbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_mainbarhome, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_Liked:
                Intent intent = new Intent(MainActivity.this, LikedVehiclesActivity.class);
               //intent.putExtra("idArray", idList);
                MainActivity.this.finish();
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onActionDownPerform() {
        Log.e("action", "bingo");
    }

    public static class ViewHolder {
        public static FrameLayout background;
        public TextView DataText;
        public ImageView cardImage;
        public TextView txtCarTitle;
    }

    public class MyAppAdapter extends BaseAdapter {


        public List<VehicleData> vehicleList;
        public Context context;

        private MyAppAdapter(List<VehicleData> apps, Context context) {
            this.vehicleList = apps;
            this.context = context;
        }

        @Override
        public int getCount() {
            return vehicleList.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            View rowView = convertView;

            if (rowView == null) {

                LayoutInflater inflater = getLayoutInflater();
                rowView = inflater.inflate(R.layout.item, parent, false);
                // configure view holder
                viewHolder = new ViewHolder();
                viewHolder.txtCarTitle = (TextView) rowView.findViewById(R.id.txtCarTitle);
                viewHolder.background = (FrameLayout) rowView.findViewById(R.id.background);
                viewHolder.cardImage = (ImageView) rowView.findViewById(R.id.cardImage);
                viewHolder.DataText = (TextView) rowView.findViewById(R.id.vehText);
                rowView.setTag(viewHolder);

            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.DataText.setText("Year: "+al.get(position).getYear());
            viewHolder.txtCarTitle.setText(al.get(position).getTitle());
            Glide.with(MainActivity.this).load(al.get(position).getDefault_image()).into(viewHolder.cardImage);

            return rowView;
        }
    }
    //
    class JSONAsyncTask extends AsyncTask<String, Void, Boolean> {

        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(MainActivity.this);
            dialog.setMessage("Loading, please wait");
            dialog.setTitle("Connecting to server.");
            dialog.show();
            dialog.setCancelable(false);
        }

        @Override
        protected Boolean doInBackground(String... urls) {
            try {

                //------------------>>
                HttpGet httppost = new HttpGet(urls[0]);
                HttpClient httpclient = new DefaultHttpClient();
                HttpResponse response = httpclient.execute(httppost);

                // StatusLine stat = response.getStatusLine();
                int status = response.getStatusLine().getStatusCode();

                if (status == 200) {
                    HttpEntity entity = response.getEntity();
                    String data = EntityUtils.toString(entity);


                    JSONObject jsono = new JSONObject(data);
                    JSONArray jarray = jsono.getJSONArray("data");

                    for (int i = 0; i < jarray.length(); i++) {
                        JSONObject object = jarray.getJSONObject(i);

                        VehicleData dataObj = new VehicleData();

                        dataObj.setTitle(object.getString("title"));
                        dataObj.setYear(object.getString("year"));
                        dataObj.setPrice(object.getString("price"));
                        dataObj.setDefault_image(object.getString("default_image"));
                        dataObj.setId(object.getString("id"));

                        al.add(dataObj);
                    }
                    return true;
                }

                //------------------>>

            } catch (ParseException e1) {
                e1.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return false;
        }

        protected void onPostExecute(Boolean result) {
            dialog.cancel();
            myAppAdapter.notifyDataSetChanged();
            if(result == false) {
                Toast.makeText(getApplicationContext(), "Unable to fetch data from server. Connection problem - please try again.", Toast.LENGTH_LONG).show();
                lnrConProblem.setVisibility(View.VISIBLE);
            }
        }
    }
}
