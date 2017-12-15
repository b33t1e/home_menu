package io.github.b33t1e.home_menu;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

public class yydp_activity extends BaseActivity {

    private List<Food> foodList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yydp);
        getFood();
        //initFood();
        FoodAdapter adapter = new FoodAdapter(yydp_activity.this,R.layout.food_item,foodList);
        ListView listView = (ListView) findViewById(R.id.YydpList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Food food = foodList.get(position);
                CollocationIntroActivity.actionStart(yydp_activity.this, food.getName(), food.getImageId(),food.getImageBitmap(),food.getIntro(), food.getNotTogether());
            }
        });

    }

    private void getFood() {
        List<Food> temp_foodList = DataSupport.findAll(Food.class);
        if (temp_foodList.isEmpty()) {
            Log.d("yydp_activity","foodList init error!!!");
        }
        else {
            for (Food food: temp_foodList) {
                foodList.add(food);
            }
        }
    }
    /*private void initFood() {
        for (int i = 0; i<24; i++) {
            Food testFood = new Food("大便",R.drawable.back_img,"大便可软可硬，居家旅行必备!!!", "豆浆");
            foodList.add(testFood);
        }
    }*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.back_ctrl,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.back_ctrl:
                SignInActivity.actionStart(yydp_activity.this);
                break;
            case R.id.finish:
                ActivityCollector.finishAll();
                break;
            default:
        }
        return true;
    }
    public static void actionStart(Context context) {
        Intent intent = new Intent(context, yydp_activity.class);
        context.startActivity(intent);
    }
}
