package io.github.b33t1e.home_menu;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;

public class FoodIntroActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_intro);
        ImageView FoodIntro_image = (ImageView) findViewById(R.id.FoodIntro_image);
        TextView FoodIntro_name = (TextView) findViewById(R.id.FoodIntro_name);
        TextView FoodIntro_intro = (TextView) findViewById(R.id.FoodIntro_intro);
        TextView FoodIntro_content = (TextView) findViewById(R.id.FoodIntro_content);
        Intent intent = getIntent();
        if (intent.getIntExtra("imageId",-1) != -1) {
            FoodIntro_image.setImageResource(intent.getIntExtra("imageId",-1));
        }
        else {
            byte[] bis = intent.getByteArrayExtra("imageBitmap");
            Bitmap ImageBitmap = BitmapFactory.decodeByteArray(bis, 0, bis.length);
            FoodIntro_image.setImageBitmap(ImageBitmap);
        }
        FoodIntro_name.setText(intent.getStringExtra("name"));
        FoodIntro_intro.setText(intent.getStringExtra("intro"));
        FoodIntro_content.setText(intent.getStringExtra("content"));
    }
    public static void actionStart(Context context, String FoodName, int ImageId, byte[] ImageBitmap, String FoodIntro, String Content) {
        Intent intent = new Intent(context, FoodIntroActivity.class);
        intent.putExtra("name", FoodName);
        intent.putExtra("imageId", ImageId);
        intent.putExtra("imageBitmap",ImageBitmap);
        intent.putExtra("intro", FoodIntro);
        intent.putExtra("content",Content);
        context.startActivity(intent);
    }
}
