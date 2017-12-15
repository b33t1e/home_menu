package io.github.b33t1e.home_menu;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MenuInfoActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_info);
        Button button_add = (Button) findViewById(R.id.MenuAdd);
        Button button_del = (Button) findViewById(R.id.MenuDel);
        Button button_change = (Button) findViewById(R.id.MenuChange);
        Button button_select = (Button) findViewById(R.id.MenuSelect);
        button_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddInfoActivity.actionStart(MenuInfoActivity.this);
            }
        });
        button_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DelInfoActivity.actionStart(MenuInfoActivity.this);
            }
        });
        button_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChangeInfo0Activity.actionStart(MenuInfoActivity.this);
            }
        });
        button_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SelectInfoActivity.actionStart(MenuInfoActivity.this);
            }
        });
    }
    public static void actionStart(Context context) {
        Intent intent = new Intent(context, MenuInfoActivity.class);
        context.startActivity(intent);
    }
}
