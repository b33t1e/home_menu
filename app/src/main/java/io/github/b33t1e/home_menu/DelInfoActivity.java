package io.github.b33t1e.home_menu;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.litepal.crud.DataSupport;

import java.io.ByteArrayOutputStream;
import java.util.List;

public class DelInfoActivity extends BaseActivity {

    private TextView TopText;
    private EditText editTextFirst;
    private boolean isMenuInfoActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_del_info);
        TopText = (TextView) findViewById(R.id.DelInfoTopText);
        editTextFirst = (EditText) findViewById(R.id.DelInfoFirst);
        Button buttonCancle = (Button) findViewById(R.id.DelInfoCancel);
        Button buttonOk = (Button) findViewById(R.id.DelInfoOk);
        Intent intent = getIntent();
        String StartClassName = intent.getStringExtra("StartClass");
        if (StartClassName.equals("MenuInfoActivity")) {
            TopText.setText("菜谱信息删除");
            editTextFirst.setHint("删除的菜名：");
            isMenuInfoActivity = true;
        }
        else if (StartClassName.equals("CollocationInfoActivity")) {
            TopText.setText("营养搭配信息删除");
            editTextFirst.setHint("删除的食物名称：");
            isMenuInfoActivity = false;
        }
        else {
            Toast.makeText(DelInfoActivity.this,"Not Supported Error !!!",Toast.LENGTH_SHORT).show();
        }
        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Name = editTextFirst.getText().toString();
                if (isMenuInfoActivity) {
                    DataSupport.deleteAll(Dish.class, "name = ?", Name);
                } else {
                    DataSupport.deleteAll(Food.class, "name = ?", Name);
                }
                Toast.makeText(DelInfoActivity.this,"成功删除!!!",Toast.LENGTH_SHORT).show();
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
            Intent intent = new Intent(context, DelInfoActivity.class);
            intent.putExtra("StartClass","MenuInfoActivity");
            context.startActivity(intent);
        }
        else if (context.getClass().getSimpleName().toString().equals("CollocationInfoActivity")) {
            Intent intent = new Intent(context, DelInfoActivity.class);
            intent.putExtra("StartClass","CollocationInfoActivity");
            context.startActivity(intent);
        }
        else {
            Intent intent = new Intent(context, DelInfoActivity.class);
            intent.putExtra("StartClass","NotSupported");
            context.startActivity(intent);
        }
    }
}
