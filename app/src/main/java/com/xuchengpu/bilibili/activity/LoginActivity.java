package com.xuchengpu.bilibili.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xuchengpu.bilibili.R;
import com.xuchengpu.bilibili.bean.UserInfo;
import com.xuchengpu.bilibili.utils.CacheUtils;
import com.xuchengpu.bilibili.utils.ConstantUtils;
import com.xuchengpu.bilibili.utils.RequestMethod;
import com.xuchengpu.bilibili.utils.TransferData;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {


    @BindView(R.id.iv_head_topiccenter)
    ImageView ivHeadTopiccenter;
    @BindView(R.id.center_name)
    TextView centerName;
    @BindView(R.id.iv_icon_left)
    ImageView ivIconLeft;
    @BindView(R.id.iv_icon_centre)
    ImageView ivIconCentre;
    @BindView(R.id.iv_icon_right)
    ImageView ivIconRight;
    @BindView(R.id.logo_ll)
    RelativeLayout logoLl;
    @BindView(R.id.et_username)
    EditText etUsername;
    @BindView(R.id.delete_username)
    ImageButton deleteUsername;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.login_ll)
    LinearLayout loginLl;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.login_regitster_tv)
    TextView loginRegitsterTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        initListener();

    }

    private void initListener() {


    }

    @OnClick({R.id.btn_login, R.id.login_regitster_tv,R.id.iv_head_topiccenter})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                String phone = etUsername.getText().toString().trim();
                String passWord = etPassword.getText().toString().trim();
                if (TextUtils.isEmpty(phone) || TextUtils.isEmpty(passWord)) {
                    Toast.makeText(LoginActivity.this, "用户名或密码不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                //保存到map映射中
                Map<String, String> map = new HashMap<String, String>();
                map.put("phone", phone);
                map.put("password", passWord);

                RequestMethod.getDataFromNet(ConstantUtils.LOGIN, map, new TransferData() {
                    @Override
                    public void onsucess(String data) {
                        JSONObject parseObject = JSON.parseObject(data);
                        boolean success = parseObject.getBoolean("success");
                        if (success) {
                            UserInfo userInfo = JSON.parseObject(data, UserInfo.class);
                            //sp保存数据
                            CacheUtils.saveUserInfo(userInfo);
                            //数据库保存

                            //跳转界面
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(LoginActivity.this, "账号或密码错误", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void failure(String data) {

                    }
                });


                break;
            case R.id.login_regitster_tv:
                //跳转界面
                Intent intent = new Intent(LoginActivity.this, RegesterActivity.class);
                startActivity(intent);
                break;
            case R.id.iv_head_topiccenter:
               finish();
                break;
        }
    }
}
