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

public class dcsp_activity extends BaseActivity {

    private List<Dish> dishList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dcsp);
        //initDishes();
        getDishes();
        DishAdapter adapter = new DishAdapter(dcsp_activity.this,R.layout.dish_item,dishList);
        ListView listView = (ListView) findViewById(R.id.DcspList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Dish dish = dishList.get(position);
                FoodIntroActivity.actionStart(dcsp_activity.this, dish.getName(), dish.getImageId(), dish.getImageBitmap(), dish.getIntro(), dish.getContent());
            }
        });

    }

    private void getDishes() {
        List<Dish> dishes = DataSupport.findAll(Dish.class);
        if (dishes.isEmpty()) {
            Log.d("dcsp_activity","dishList init error!!!");
        }
        else {
            for (Dish dish: dishes) {
                dishList.add(dish);
            }
        }
    }

    /*private void initDishes() {
        for (int i = 0; i<24; i++) {
            Dish testDish = new Dish("油炸大便",R.drawable.back_img,"这个菜不错!!!你热吃.", "大便\t油");
            dishList.add(testDish);
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
                SignInActivity.actionStart(dcsp_activity.this);
                break;
            case R.id.finish:
                ActivityCollector.finishAll();
                break;
            default:
        }
        return true;
    }
    public static void actionStart(Context context) {
        Intent intent = new Intent(context, dcsp_activity.class);
        context.startActivity(intent);
    }
}
