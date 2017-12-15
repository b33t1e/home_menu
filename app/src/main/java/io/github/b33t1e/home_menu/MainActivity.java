package io.github.b33t1e.home_menu;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import org.litepal.crud.DataSupport;
import org.litepal.tablemanager.Connector;

import java.util.List;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Connector.getDatabase();
        List<Dish> dishes = DataSupport.findAll(Dish.class);
        if (dishes.isEmpty()) {
            // init dishes
            //Log.d("MainActivity","init dishes!!!");
            for (int i = 0; i<24; i++){
                Dish dish = new Dish();
                dish.setName("油炸大便");
                dish.setImageId(R.drawable.back_img);
                dish.setImageBitmap(null);
                dish.setIntro("这个菜不错!!!你热吃.");
                dish.setContent("大便\t油");
                dish.save();
            }
        }
        List<Food> temp_foodList = DataSupport.findAll(Food.class);
        if (temp_foodList.isEmpty()) {
            // init dishes
            //Log.d("MainActivity","init food!!!");
            for (int i = 0; i<24; i++){
                Food food = new Food();
                food.setName("大便");
                food.setImageId(R.drawable.back_img);
                food.setImageBitmap(null);
                food.setIntro("大便可软可硬，居家旅行必备!!!");
                food.setNotTogether("大便\t油");
                food.save();
            }
        }
        Button button_dcsp = (Button) findViewById(R.id.dcsp);
        Button button_xdcp = (Button) findViewById(R.id.xdcp);
        Button button_yydp = (Button) findViewById(R.id.yydp);
        Button button_ts = (Button) findViewById(R.id.ts);
        button_dcsp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dcsp_activity.actionStart(MainActivity.this);
            }
        });
        button_xdcp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xdcp_activity.actionStart(MainActivity.this);

            }
        });
        button_yydp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                yydp_activity.actionStart(MainActivity.this);

            }
        });
        button_ts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ts_activity.actionStart(MainActivity.this);

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.back_ctrl,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.back_ctrl:
                SignInActivity.actionStart(MainActivity.this);
                break;
            case R.id.finish:
                ActivityCollector.finishAll();
                break;
            default:
        }
        return true;
    }
    public static void actionStart(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }
}
