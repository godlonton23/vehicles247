package com.godlontonconsulting.vehicles247;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.net.ParseException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;


public class LikedVehiclesActivity extends Activity {

    ArrayList<VehicleData> vehicleList;
    ArrayList<String> myIdList = new ArrayList<String>();

    DataAdapter adapter;

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liked);
        vehicleList = new ArrayList<VehicleData>();
        new JSONAsyncTask().execute("http://empty-bush-3943.getsandbox.com/listings");

        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        //
        toolbar.setTitle("Vehicles 24/7 - Liked Vehicles");

        toolbar.setNavigationIcon(R.drawable.heart);
        //
        Bundle b=this.getIntent().getExtras();
        myIdList=b.getStringArrayList("array");

        ListView listview = (ListView)findViewById(R.id.list);
        adapter = new DataAdapter(getApplicationContext(), R.layout.row,vehicleList);

        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position,
                                    long id) {
                // TODO Auto-generated method stub
                Toast.makeText(getApplicationContext(), vehicleList.get(position).getTitle(), Toast.LENGTH_LONG).show();
            }
        });
    }


    class JSONAsyncTask extends AsyncTask<String, Void, Boolean> {

        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(LikedVehiclesActivity.this);
            dialog.setMessage("Loading, please wait");
            dialog.setTitle("Connecting server");
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
                        //if (myIdList.get(i).equals(vehicleList.get(i).getId())){
                        JSONObject object = jarray.getJSONObject(i);
                        VehicleData dataObj = new VehicleData();
                        dataObj.setTitle(object.getString("title"));
                        dataObj.setId(object.getString("id"));
                        dataObj.setPrice(object.getString("price"));
                        dataObj.setYear(object.getString("year"));
                        dataObj.setDefault_image(object.getString("default_image"));
                        vehicleList.add(dataObj);


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
            adapter.notifyDataSetChanged();
            if(result == false)
                Toast.makeText(getApplicationContext(), "Unable to fetch data from server", Toast.LENGTH_LONG).show();

        }
    }
}
