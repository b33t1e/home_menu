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

public class DishAdapter extends ArrayAdapter<Dish> {
    private int resourceId;

    public DishAdapter(Context context,int textViewResourceId, List<Dish> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }

    class DishViewHolder {
        ImageView dishImage;
        TextView dishName;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Dish dish = getItem(position);
        View view;
        DishViewHolder dishViewHolder;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
            dishViewHolder = new DishViewHolder();
            dishViewHolder.dishImage = (ImageView) view.findViewById(R.id.dish_image);
            dishViewHolder.dishName = (TextView) view.findViewById(R.id.dish_name);
            view.setTag(dishViewHolder);
        }
        else {
                view = convertView;
                dishViewHolder = (DishViewHolder) view.getTag();
        }
        if (dish.getImageId() != -1){
            dishViewHolder.dishImage.setImageResource(dish.getImageId());
        }
        else {
            byte[] images=dish.getImageBitmap();
            Bitmap bitmap= BitmapFactory.decodeByteArray(images,0,images.length);
            dishViewHolder.dishImage.setImageBitmap(bitmap);
        }
        dishViewHolder.dishName.setText(dish.getName());
        return view;
    }
}
