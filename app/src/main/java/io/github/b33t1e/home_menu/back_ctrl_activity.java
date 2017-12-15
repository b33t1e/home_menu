package io.github.b33t1e.home_menu;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import static io.github.b33t1e.home_menu.R.id.CollocationInfo;
import static io.github.b33t1e.home_menu.R.id.MenuInfo;


public class back_ctrl_activity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_back_ctrl);
        Button button_MenuInfo = (Button) findViewById(MenuInfo);
        button_MenuInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MenuInfoActivity.actionStart(back_ctrl_activity.this);
            }
        });
        Button button_CollocationInfo = (Button) findViewById(CollocationInfo);
        button_CollocationInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CollocationInfoActivity.actionStart(back_ctrl_activity.this);
            }
        });
    }

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, back_ctrl_activity.class);
        context.startActivity(intent);
    }
}
