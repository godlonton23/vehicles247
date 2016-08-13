package com.godlontonconsulting.vehicles247;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DataAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
	//
	ArrayList<VehicleData> vehicleList;
	Activity activity;

	public DataAdapter(Activity activity,ArrayList<VehicleData> objects) {
		this.activity=activity;
		this.vehicleList = objects;
	}

	public static class ViewHolder extends RecyclerView.ViewHolder {
		public ViewHolder(View v) {
			super(v);
		}
	}

	//
	public class VehiclesViewHolder extends ViewHolder {

		@BindView(R.id.ivImage) ImageView imageview;
		@BindView(R.id.tvTitle) TextView tvTitle ;
		@BindView(R.id.tvPrice) TextView tvPrice;
		@BindView(R.id.tvYear) TextView tvYear;

		public VehiclesViewHolder(View view) {
			super(view);
			ButterKnife.bind(this, view);
		}
	}
	//
	@Override
	public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
		//
		View v;
		//
		v =  LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row, viewGroup, false);
		return new VehiclesViewHolder(v);
		//
	}

	@Override
	public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {
		//
		final VehiclesViewHolder holder = (VehiclesViewHolder) viewHolder;
		holder.imageview.setImageResource(R.drawable.ic_imageholder);
		final String imagePath=vehicleList.get(position).getDefault_image();
		Picasso.with(activity).load(imagePath).resize(540, 0).networkPolicy(NetworkPolicy.OFFLINE, NetworkPolicy.NO_CACHE).placeholder(R.drawable.ic_imageholder).into(holder.imageview, new com.squareup.picasso.Callback() {
			@Override
			public void onSuccess() {
				Picasso.with(activity).load(imagePath).resize(540, 0).placeholder(R.drawable.ic_imageholder).into(holder.imageview);
			}

			@Override
			public void onError() {
				Picasso.with(activity).load(imagePath).resize(540, 0).placeholder(R.drawable.ic_imageholder).into(holder.imageview);
			}
		});

		holder.tvTitle.setText(vehicleList.get(position).getTitle());
		holder.tvPrice.setText("Price: "+vehicleList.get(position).getPrice()+" ZAR");
 		holder.tvYear.setText("Year: " + vehicleList.get(position).getYear());
	}

	@Override
	public int getItemCount() {
		return this.vehicleList.size();
	}

}