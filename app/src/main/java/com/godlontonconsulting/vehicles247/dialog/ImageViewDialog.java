package com.godlontonconsulting.vehicles247.dialog;

import android.app.Activity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;


import com.godlontonconsulting.vehicles247.R;
import com.godlontonconsulting.vehicles247.adapter.DataAdapter;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import it.sephiroth.android.library.imagezoom.ImageViewTouch;


public class ImageViewDialog extends CustomDialog implements View.OnClickListener {

    private ImageViewTouch imgImage;
    private ImageButton closeButton;

    private String vPath;
    private Activity vActivity;

    private FrameLayout imgLayout;


    public ImageViewDialog(Activity activity, String imagePath) {
        super(activity);
        setTitle("Image");
        this. vActivity = activity;
         this.vPath=imagePath;
        setContentView(R.layout.dialog_imageview);
        //
       init();
    }
    
    private void init() {
        //
        imgImage = (ImageViewTouch) findViewById(R.id.image);
        imgLayout = (FrameLayout) findViewById(R.id.imgLayout);
        closeButton= (ImageButton) findViewById(R.id.closeButton);

        closeButton.setOnClickListener(this);
        //
        Picasso.with(vActivity).load(vPath).resize(1024, 0).networkPolicy(NetworkPolicy.OFFLINE, NetworkPolicy.NO_CACHE).into(imgImage, new com.squareup.picasso.Callback() {
            @Override
            public void onSuccess() {
                Picasso.with(vActivity).load(vPath).resize(1024, 0).placeholder(R.drawable.imageholder).into(imgImage);
            }

            @Override
            public void onError() {
                Picasso.with(vActivity).load(vPath).resize(1024, 0).placeholder(R.drawable.imageholder).into(imgImage);
            }
        });
        //
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.closeButton:
                DataAdapter.imageClicked=false;
                this.dismiss();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        DataAdapter.imageClicked=false;
        this.dismiss();
    }
}