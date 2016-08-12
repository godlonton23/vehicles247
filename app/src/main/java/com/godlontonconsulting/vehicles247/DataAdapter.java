package com.godlontonconsulting.vehicles247;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.util.ArrayList;

//import butterknife.BindView;
//import butterknife.ButterKnife;

public class DataAdapter extends ArrayAdapter<VehicleData> {
	//
	ArrayList<VehicleData> vehicleList;
	LayoutInflater vi;
	int Resource;
	ViewHolder holder;

	public DataAdapter(Context context, int resource, ArrayList<VehicleData> objects) {
		super(context, resource, objects);
		vi = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		Resource = resource;
		vehicleList = objects;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// convert view = design
		View v = convertView;
		if (v == null) {
			holder = new ViewHolder();
			v = vi.inflate(Resource, null);
			holder.imageview = (ImageView) v.findViewById(R.id.ivImage);
			holder.tvTitle = (TextView) v.findViewById(R.id.tvTitle);
			holder.tvPrice = (TextView) v.findViewById(R.id.tvPrice);
			holder.tvYear = (TextView) v.findViewById(R.id.tvYear);
			v.setTag(holder);
		} else {
			holder = (ViewHolder) v.getTag();
		}
		holder.imageview.setImageResource(R.drawable.ic_imageholder);
		new DownloadImageTask(holder.imageview).execute(vehicleList.get(position).getDefault_image());
		holder.tvTitle.setText(vehicleList.get(position).getTitle());
		holder.tvPrice.setText("Price: "+vehicleList.get(position).getPrice()+" ZAR");
 		holder.tvYear.setText("Year: " + vehicleList.get(position).getYear());
		return v;

	}

	static class ViewHolder {
		public ImageView imageview;
		public TextView tvTitle;
		public TextView tvPrice;
		public TextView tvYear;

		// TODO  butterknife //
//		@BindView(R.id.ivImage) TextView imageview;
//		@BindView(R.id.tvTitle) TextView tvTitle;
//
//		public ViewHolder(View view) {
//			ButterKnife.bind(this, view);
//		}
	}

	private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
		ImageView bmImage;

		public DownloadImageTask(ImageView bmImage) {
			this.bmImage = bmImage;
		}

		protected Bitmap doInBackground(String... urls) {
			String urldisplay = urls[0];
			Bitmap mIcon11 = null;
			try {
				InputStream in = new java.net.URL(urldisplay).openStream();
				mIcon11 = BitmapFactory.decodeStream(in);
			} catch (Exception e) {
				Log.e("Error", e.getMessage());
				e.printStackTrace();
			}
			return mIcon11;
		}

		protected void onPostExecute(Bitmap result) {
			bmImage.setImageBitmap(result);
		}

	}
}