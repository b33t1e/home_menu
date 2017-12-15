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

public class ChangeInfo0Activity extends BaseActivity {

    private TextView TopText;
    private EditText editTextFirst;
    private boolean isMenuInfoActivity;
    private String StartClassName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_info0);
        TopText = (TextView) findViewById(R.id.ChangeInfo0TopText);
        editTextFirst = (EditText) findViewById(R.id.ChangeInfo0First);
        Button buttonCancle = (Button) findViewById(R.id.ChangeInfo0Cancel);
        Button buttonOk = (Button) findViewById(R.id.ChangeInfo0Ok);
        Intent intent = getIntent();
        StartClassName = intent.getStringExtra("StartClass");
        if (StartClassName.equals("MenuInfoActivity")) {
            TopText.setText("菜谱信息修改");
            editTextFirst.setHint("修改的菜名：");
            isMenuInfoActivity = true;
        }
        else if (StartClassName.equals("CollocationInfoActivity")) {
            TopText.setText("营养搭配信息修改");
            editTextFirst.setHint("修改的食物名称：");
            isMenuInfoActivity = false;
        }
        else {
            Toast.makeText(ChangeInfo0Activity.this,"Not Supported Error !!!",Toast.LENGTH_SHORT).show();
        }
        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Name = editTextFirst.getText().toString();
                if (isMenuInfoActivity) {
                    List<Dish> dishes = DataSupport.where("name = ?", Name).find(Dish.class);
                    if (dishes.isEmpty()) {
                        Toast.makeText(ChangeInfo0Activity.this,"您要查询的菜不存在，请重新输入菜名!!!",Toast.LENGTH_SHORT).show();
                    }
                    else {
                        for (Dish dish: dishes) {
                            //FoodIntroActivity.actionStart(ChangeInfo0Activity.this, dish.getName(), dish.getImageId(), dish.getImageBitmap(), dish.getIntro(), dish.getContent());
                            ChangeInfoActivity.actionStart(ChangeInfo0Activity.this,StartClassName,dish.getName());
                            break;
                        }
                    }
                } else {
                    List<Food> foodList = DataSupport.where("name = ?", Name).find(Food.class);
                    if (foodList.isEmpty()) {
                        Toast.makeText(ChangeInfo0Activity.this,"您要查询的食物不存在，请重新输入菜名!!!",Toast.LENGTH_SHORT).show();
                    }
                    else {
                        for (Food food: foodList) {
                            //CollocationIntroActivity.actionStart(ChangeInfo0Activity.this, food.getName(), food.getImageId(),food.getImageBitmap(),food.getIntro(), food.getNotTogether());
                            ChangeInfoActivity.actionStart(ChangeInfo0Activity.this,StartClassName,food.getName());
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
            Intent intent = new Intent(context, ChangeInfo0Activity.class);
            intent.putExtra("StartClass","MenuInfoActivity");
            context.startActivity(intent);
        }
        else if (context.getClass().getSimpleName().toString().equals("CollocationInfoActivity")) {
            Intent intent = new Intent(context, ChangeInfo0Activity.class);
            intent.putExtra("StartClass","CollocationInfoActivity");
            context.startActivity(intent);
        }
        else {
            Intent intent = new Intent(context, ChangeInfo0Activity.class);
            intent.putExtra("StartClass","NotSupported");
            context.startActivity(intent);
        }
    }
}