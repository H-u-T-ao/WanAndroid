package com.fengjiaxing.wanandroid.application;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.fengjiaxing.wanandroid.R;
import com.fengjiaxing.wanandroid.application.contacts.LoginContacts;
import com.fengjiaxing.wanandroid.application.presenter.LoginPresenter;
import com.fengjiaxing.wanandroid.storage.shared.LoginInformationShared;
import com.fengjiaxing.wanandroid.util.TaskBarSetting;

/**
 * @description 我板块的登录活动
 * */
public class LoginActivity extends AppCompatActivity implements LoginContacts.ILoginActivity {

    private EditText userName;

    private final Intent intent = new Intent();

    private LoginContacts.ILoginPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mPresenter = new LoginPresenter(this);

        intent.putExtra("userName", "点击此处以登录");
        setResult(RESULT_CANCELED, intent);

        userName = findViewById(R.id.login_user_name);
        ImageView back = findViewById(R.id.iv_login_back);
        TextView register = findViewById(R.id.tv_login_to_register);
        EditText password = findViewById(R.id.et_login_password);
        Button login = findViewById(R.id.btn_login_login);

        back.setOnClickListener(v -> finish());
        register.setOnClickListener(v -> {
            Intent registerIntent = new Intent(this, RegisterActivity.class);
            startActivity(registerIntent);
        });

        login.setOnClickListener(v -> this.mPresenter.login(userName.getText().toString(), password.getText().toString()));

        register.setOnClickListener(v -> {
            Intent intent = new Intent(this, RegisterActivity.class);
            startActivity(intent);
        });
    }

    @Override
    public void onStart() {
        TextView bar = findViewById(R.id.tv_login_task_bar);
        TaskBarSetting.hideAll(this, bar);
        super.onStart();
    }

    @Override
    public void loginSuccess() {
        runOnUiThread(() -> Toast.makeText(this, "登陆成功", Toast.LENGTH_SHORT).show());
        Intent intent = new Intent(this, LoginInformationShared.class);
        startService(intent);
        intent.removeExtra("userName");
        intent.putExtra("userName", userName.getText().toString());
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void loginFailure() {
        runOnUiThread(() -> Toast.makeText(this, "账号或密码错误，请重试", Toast.LENGTH_SHORT).show());
    }

    @Override
    public void requestFailure() {
        runOnUiThread(() -> Toast.makeText(this, R.string.time_out_text, Toast.LENGTH_SHORT).show());
    }

    @Override
    public void onDestroy() {
        mPresenter = null;
        super.onDestroy();
    }
}
