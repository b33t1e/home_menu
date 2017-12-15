package io.github.b33t1e.home_menu;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;

public class CollocationIntroActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collocation_intro);
        Button CollocationIntro_button = (Button) findViewById(R.id.CollocationIntro_button);
        TextView CollocationIntro_intro = (TextView) findViewById(R.id.CollocationIntro_intro);
        TextView CollocationIntro_notTogether = (TextView) findViewById(R.id.CollocationIntro_notTogether);
        Intent intent = getIntent();
        if (intent.getIntExtra("imageId",-1) != -1) {
            CollocationIntro_button.setBackgroundResource(intent.getIntExtra("imageId",-1));
        }
        else {
            byte [] bis=intent.getByteArrayExtra("imageBitmap");
            Bitmap ImageBitmap= BitmapFactory.decodeByteArray(bis, 0, bis.length);
            Drawable drawable = new BitmapDrawable(ImageBitmap);
            CollocationIntro_button.setBackgroundDrawable(drawable);
        }
        CollocationIntro_button.setText(intent.getStringExtra("name"));
        CollocationIntro_intro.setText(intent.getStringExtra("intro"));
        CollocationIntro_notTogether.setText(intent.getStringExtra("notTogether"));
    }
    public static void actionStart(Context context, String Name, int ImageId, byte[] ImageBitmap, String Intro, String NotTogether) {
        Intent intent = new Intent(context, CollocationIntroActivity.class);
        intent.putExtra("name", Name);
        intent.putExtra("imageId", ImageId);
        intent.putExtra("imageBitmap",ImageBitmap);
        intent.putExtra("intro", Intro);
        intent.putExtra("notTogether",NotTogether);
        context.startActivity(intent);
    }
}
