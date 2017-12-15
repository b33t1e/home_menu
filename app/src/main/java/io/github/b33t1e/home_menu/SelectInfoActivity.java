package io.github.b33t1e.home_menu;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.litepal.crud.DataSupport;

import java.util.List;

public class SelectInfoActivity extends BaseActivity {

    private TextView TopText;
    private EditText editTextFirst;
    private boolean isMenuInfoActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_info);
        TopText = (TextView) findViewById(R.id.SelectInfoTopText);
        editTextFirst = (EditText) findViewById(R.id.SelectInfoFirst);
        Button buttonCancle = (Button) findViewById(R.id.SelectInfoCancel);
        Button buttonOk = (Button) findViewById(R.id.SelectInfoOk);
        Intent intent = getIntent();
        String StartClassName = intent.getStringExtra("StartClass");
        if (StartClassName.equals("MenuInfoActivity")) {
            TopText.setText("菜谱信息查询");
            editTextFirst.setHint("查询的菜名：");
            isMenuInfoActivity = true;
        }
        else if (StartClassName.equals("CollocationInfoActivity")) {
            TopText.setText("营养搭配信息查询");
            editTextFirst.setHint("查询的食物名称：");
            isMenuInfoActivity = false;
        }
        else {
            Toast.makeText(SelectInfoActivity.this,"Not Supported Error !!!",Toast.LENGTH_SHORT).show();
        }
        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Name = editTextFirst.getText().toString();
                if (isMenuInfoActivity) {
                    List<Dish> dishes = DataSupport.where("name = ?", Name).find(Dish.class);
                    if (dishes.isEmpty()) {
                        Toast.makeText(SelectInfoActivity.this,"您要查询的菜不存在，请重新输入菜名!!!",Toast.LENGTH_SHORT).show();
                    }
                    else {
                        for (Dish dish: dishes) {
                            FoodIntroActivity.actionStart(SelectInfoActivity.this, dish.getName(), dish.getImageId(), dish.getImageBitmap(), dish.getIntro(), dish.getContent());
                            break;
                        }
                    }
                } else {
                    List<Food> foodList = DataSupport.where("name = ?", Name).find(Food.class);
                    if (foodList.isEmpty()) {
                        Toast.makeText(SelectInfoActivity.this,"您要查询的食物不存在，请重新输入菜名!!!",Toast.LENGTH_SHORT).show();
                    }
                    else {
                        for (Food food: foodList) {
                            CollocationIntroActivity.actionStart(SelectInfoActivity.this, food.getName(), food.getImageId(),food.getImageBitmap(),food.getIntro(), food.getNotTogether());
                            break;
                        }
                    }
                }
            }
        });
        buttonCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    public static void actionStart(Context context) {
        if (context.getClass().getSimpleName().toString().equals("MenuInfoActivity")) {
            Intent intent = new Intent(context, SelectInfoActivity.class);
            intent.putExtra("StartClass","MenuInfoActivity");
            context.startActivity(intent);
        }
        else if (context.getClass().getSimpleName().toString().equals("CollocationInfoActivity")) {
            Intent intent = new Intent(context, SelectInfoActivity.class);
            intent.putExtra("StartClass","CollocationInfoActivity");
            context.startActivity(intent);
        }
        else {
            Intent intent = new Intent(context, SelectInfoActivity.class);
            intent.putExtra("StartClass","NotSupported");
            context.startActivity(intent);
        }
    }
}
