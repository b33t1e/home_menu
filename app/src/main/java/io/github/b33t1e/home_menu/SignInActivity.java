package io.github.b33t1e.home_menu;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignInActivity extends BaseActivity {
    private EditText editText_user;
    private EditText editText_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        Button button_login = (Button) findViewById(R.id.loginButton);
        Button button_cancle = (Button) findViewById(R.id.cancleButton);
        editText_user = (EditText) findViewById(R.id.usernameView);
        editText_password = (EditText) findViewById(R.id.passwordView);
        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userText = editText_user.getText().toString();
                String passwordText = editText_password.getText().toString();
                if (userText.equals("root") && passwordText.equals("toor")) {
                        back_ctrl_activity.actionStart(SignInActivity.this);
                    }
                else {
                    Toast.makeText(SignInActivity.this,"用户名或密码错误!!!登录失败!!!",Toast.LENGTH_SHORT).show();
                }

            }
        });
        button_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, SignInActivity.class);
        context.startActivity(intent);
    }
}
