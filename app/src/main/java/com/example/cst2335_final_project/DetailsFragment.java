package com.example.cst2335_final_project;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.fragment.app.Fragment;

public class DetailsFragment extends Fragment {
    private Bundle dataFromActivity;
    private long id;
    private String title;
    private String date;
    private byte[] bytedImage;
    private Bitmap image;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        dataFromActivity = getArguments();
        id = dataFromActivity.getLong(ActivityFavourites.ITEM_ID);
        title = dataFromActivity.getString(ActivityFavourites.TEXT_TITLE);
        date = dataFromActivity.getString(ActivityFavourites.TEXT_DATE);
//        image = dataFromActivity.getParcelable(ActivityFavourites.BITMAP_IMAGE);
//        bytedImage = dataFromActivity.getByteArray(ActivityFavourites.BYTE_IMAGE);
//        image = Converter.getImage(bytedImage);
        View result = inflater.inflate(R.layout.fragment_details, container, false);
        TextView titleText = (TextView) result.findViewById(R.id.TextView_Fragment_Title);
        TextView dateText = (TextView) result.findViewById(R.id.TextView_Fragment_Date);
        TextView idView = (TextView)result.findViewById(R.id.TextView_Fragment_ID);
//        ImageView picView = (ImageView) result.findViewById(R.id.Image_Fragment_Image);
        idView.setText(getString(R.string.Show_ID)+" "+id);
        titleText.setText(getString(R.string.Show_Text_Title)+" "+title);
        dateText.setText(getString(R.string.Show_Text_Date)+" "+date);
//        picView.setImageBitmap(image);


        Button hideButton = (Button)result.findViewById(R.id.Button_Fragment_Hide_Button);
        hideButton.setOnClickListener( clk -> {
            EmptyActivity parent = (EmptyActivity) getActivity();
            Intent backToActivityFavourites = new Intent();
            backToActivityFavourites.putExtra(ActivityFavourites.ITEM_ID, dataFromActivity.getLong(ActivityFavourites.ITEM_ID ));
            parent.setResult(ActivityFavourites.RESULT_OK, backToActivityFavourites);
            parent.finish();
        });

        return result;
    }
}
