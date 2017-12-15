package io.github.b33t1e.home_menu;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Administrator on 2017/12/6.
 */

public class FoodAdapter extends ArrayAdapter<Food> {
    private int resourceId;

    public FoodAdapter(Context context,int textViewResourceId, List<Food> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }

    class FoodViewHolder {
        ImageView foodImage;
        TextView foodName;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Food food = getItem(position);
        View view;
        FoodViewHolder foodViewHolder;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
            foodViewHolder = new FoodViewHolder();
            foodViewHolder.foodImage = (ImageView) view.findViewById(R.id.food_image);
            foodViewHolder.foodName = (TextView) view.findViewById(R.id.food_name);
            view.setTag(foodViewHolder);
        }
        else {
            view = convertView;
            foodViewHolder = (FoodViewHolder) view.getTag();
        }
        if (food.getImageId() != -1) {
            foodViewHolder.foodImage.setImageResource(food.getImageId());
        } else {
            byte[] images=food.getImageBitmap();
            Bitmap bitmap= BitmapFactory.decodeByteArray(images,0,images.length);
            foodViewHolder.foodImage.setImageBitmap(bitmap);
        }
        foodViewHolder.foodName.setText(food.getName());
        return view;
    }
}
