package com.fengjiaxing.wanandroid.application;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.fengjiaxing.wanandroid.R;
import com.fengjiaxing.wanandroid.application.contacts.RegisterContacts;
import com.fengjiaxing.wanandroid.application.presenter.RegisterPresenter;
import com.fengjiaxing.wanandroid.util.TaskBarSetting;

/**
 * @description 我板块的注册活动
 * */
public class RegisterActivity extends AppCompatActivity implements RegisterContacts.IRegisterActivity {

    private RegisterContacts.IRegisterPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mPresenter = new RegisterPresenter(this);

        ImageView registerBackToLogin = findViewById(R.id.register_back);
        Button registerButton = findViewById(R.id.btn_register_register);
        EditText userName = findViewById(R.id.et_register_name);
        EditText password = findViewById(R.id.et_register_password);
        EditText rePassword = findViewById(R.id.et_register_repassword);

        registerBackToLogin.setOnClickListener(v -> finish());
        registerButton.setOnClickListener(v -> {
            if (userName.getText().toString().isEmpty()) {
                Toast.makeText(this, "用户名不能为空", Toast.LENGTH_LONG).show();
            } else if (password.getText().toString().length() < 6 || rePassword.getText().toString().length() < 6) {
                Toast.makeText(this, "密码长度不能小于6位", Toast.LENGTH_LONG).show();
            } else if (!password.getText().toString().equals(rePassword.getText().toString())) {
                Toast.makeText(this, "两次输入的密码不一致", Toast.LENGTH_LONG).show();
            } else {
                mPresenter.register(userName.getText().toString(),
                        password.getText().toString(),
                        rePassword.getText().toString());
            }
        });
    }

    @Override
    public void registerSuccess() {
        runOnUiThread(() -> Toast.makeText(this, "注册成功", Toast.LENGTH_LONG).show());
        finish();
    }

    @Override
    public void registerFailure() {
        runOnUiThread(() -> Toast.makeText(this, "注册失败:用户名已被注册", Toast.LENGTH_LONG).show());
    }

    @Override
    public void requestFailure() {
        runOnUiThread(() -> Toast.makeText(this, R.string.time_out_text, Toast.LENGTH_LONG).show());
    }

    @Override
    public void onStart() {
        TextView bar = findViewById(R.id.tv_register_task_bar);
        TaskBarSetting.hideAll(this, bar);
        super.onStart();
    }

    @Override
    public void onDestroy() {
        mPresenter = null;
        super.onDestroy();
    }
}
