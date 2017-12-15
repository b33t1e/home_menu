package io.github.b33t1e.home_menu;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class CollocationInfoActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collocation_info);
        Button button_add = (Button) findViewById(R.id.CollocationAdd);
        Button button_del = (Button) findViewById(R.id.CollocationDel);
        Button button_change = (Button) findViewById(R.id.CollocationChange);
        Button button_select = (Button) findViewById(R.id.CollocationSelect);
        button_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddInfoActivity.actionStart(CollocationInfoActivity.this);
            }
        });
        button_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DelInfoActivity.actionStart(CollocationInfoActivity.this);
            }
        });
        button_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChangeInfo0Activity.actionStart(CollocationInfoActivity.this);
            }
        });
        button_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SelectInfoActivity.actionStart(CollocationInfoActivity.this);
            }
        });
    }
    public static void actionStart(Context context) {
        Intent intent = new Intent(context, CollocationInfoActivity.class);
        context.startActivity(intent);
    }
}
