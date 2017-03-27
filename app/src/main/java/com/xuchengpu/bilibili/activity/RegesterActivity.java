package com.xuchengpu.bilibili.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xuchengpu.bilibili.R;
import com.xuchengpu.bilibili.utils.ConstantUtils;
import com.xuchengpu.bilibili.utils.RequestMethod;
import com.xuchengpu.bilibili.utils.TransferData;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RegesterActivity extends AppCompatActivity {

    @BindView(R.id.iv_head_topiccenter)
    ImageView ivHeadTopiccenter;
    @BindView(R.id.center_name)
    TextView centerName;
    @BindView(R.id.et_register_number)
    EditText etRegisterNumber;
    @BindView(R.id.et_register_name)
    EditText etRegisterName;
    @BindView(R.id.et_register_pwd)
    EditText etRegisterPwd;
    @BindView(R.id.et_register_pwdagain)
    EditText etRegisterPwdagain;
    @BindView(R.id.btn_register)
    Button btnRegister;
    private String number;
    private String name;
    private String pwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regester);
        ButterKnife.bind(this);
        initListener();
    }

    private void initListener() {
        ivHeadTopiccenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                number = etRegisterNumber.getText().toString().trim();
                name = etRegisterName.getText().toString().trim();
                pwd = etRegisterPwd.getText().toString().trim();
                String pwdAgain = etRegisterPwdagain.getText().toString().trim();
                if (TextUtils.isEmpty(number) || TextUtils.isEmpty(name) || TextUtils.isEmpty(pwd) || TextUtils.isEmpty(pwdAgain)) {
                    Toast.makeText(RegesterActivity.this, "各项不能有一个为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!pwd.equals(pwdAgain)) {
                    Toast.makeText(RegesterActivity.this, "前后密码需一致", Toast.LENGTH_SHORT).show();
                }
                //保存到map映射中
                Map<String, String> map = new HashMap<String, String>();
                map.put("name", name);
                map.put("password", pwd);
                map.put("phone", number);


                RequestMethod.getDataFromNet(ConstantUtils.REGISTER, map, new TransferData() {
                    @Override
                    public void onsucess(String data) {
                        JSONObject parseObject = JSON.parseObject(data);
                        boolean success = parseObject.getBoolean("isExist");
                        if (success) {
                            Toast.makeText(RegesterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();

                            finish();
                        } else {
                            Toast.makeText(RegesterActivity.this, "该用户已经注册过", Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void failure(String data) {

                    }
                });
            }
        });
    }
}
