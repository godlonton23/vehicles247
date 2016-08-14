package com.godlontonconsulting.vehicles247.model;

import android.os.Parcel;
import android.os.Parcelable;



public class ParcelableData implements Parcelable {


    public String id;
    public String year;
    public String title;
    public String default_image;
    public String price;

    public ParcelableData() {

    }

    ParcelableData(Parcel source) {
        readFromParcel(source);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(id);
        dest.writeString(year);
        dest.writeString(title);
        dest.writeString(default_image);
        dest.writeString(price);
    }

    private void readFromParcel(Parcel parcel) {
        id = parcel.readString();
        year = parcel.readString();
        title = parcel.readString();
        default_image = parcel.readString();
        price = parcel.readString();
    }

    // Method to recreate a Question from a Parcel
    public static Creator<ParcelableData> CREATOR = new Creator<ParcelableData>() {

        @Override
        public ParcelableData createFromParcel(Parcel source) {
            return new ParcelableData(source);
        }

        @Override
        public ParcelableData[] newArray(int size) {
            return new ParcelableData[size];
        }

    };

    @Override
    public int describeContents() {
        return 0;
    }


}
