package tech.wd.com.login.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTouch;
import tech.wd.com.common.apis.Apis;
import tech.wd.com.common.base.BaseActivity;
import tech.wd.com.common.util.RsaCoder;
import tech.wd.com.common.util.ToastUtil;
import tech.wd.com.login.R;
import tech.wd.com.login.R2;
import tech.wd.com.login.bean.LoginBean;

public class LoginActivity extends BaseActivity {
    //手机号
    @BindView(R2.id.activity_login_edit_phone)
    EditText activity_login_edit_phone;
    //密码
    @BindView(R2.id.activity_login_edit_pwd)
    EditText activity_login_edit_pwd;
    //快速注册
    @BindView(R2.id.activity_login_text_register)
    TextView activity_login_text_register;
    //登录
    @BindView(R2.id.activity_login_button_login)
    Button activity_login_button_login;
    //微信
    @BindView(R2.id.activity_login_img_wechat)
    ImageView activity_login_img_wechat;
    //人脸识别
    @BindView(R2.id.activity_login_img_face)
    ImageView activity_login_img_face;

    SharedPreferences mSharedPreferences;

    @Override
    protected void initData() {
        mSharedPreferences = getSharedPreferences("keji",MODE_PRIVATE);
    }
    //显示或隐藏密码
    @OnTouch(R2.id.activity_login_img_eyes)
    public boolean onTouch(View v, MotionEvent event){
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                activity_login_edit_pwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                break;
            case MotionEvent.ACTION_UP:
                activity_login_edit_pwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                break;
        }
        return true;
    }
    @OnClick({R2.id.activity_login_text_register,R2.id.activity_login_button_login,R2.id.activity_login_img_wechat,R2.id.activity_login_img_face})
    public void onClick(View view){
        if (view.getId() == R.id.activity_login_text_register){
            startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
        }else if (view.getId() == R.id.activity_login_button_login){
            String phone = activity_login_edit_phone.getText().toString().trim();
            String pwd = activity_login_edit_pwd.getText().toString().trim();
            String regex = "^(13[0-9]|14[5|7]|15[0|1|2|3|5|6|7|8|9]|16[1|6]|18[0|1|2|3|5|6|7|8|9])\\d{8}$";
            boolean matches = Pattern.matches(regex, phone);

            if (phone.isEmpty() || pwd.isEmpty()){

                Toast.makeText(LoginActivity.this,R.string.login_phone_pwd_isEmpty,Toast.LENGTH_SHORT).show();

            }else if (!matches){
                Toast.makeText(LoginActivity.this,R.string.edit_phone_error,Toast.LENGTH_SHORT).show();

            }else{

                try {
                    String s = RsaCoder.encryptByPublicKey(pwd);
                    Map<String, String> map = new HashMap<>();
                    map.put("phone",phone);
                    map.put("pwd",s);
                    postRequest(Apis.URL_LOGIN,map,LoginBean.class);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

        }else if (view.getId() == R.id.activity_login_img_wechat){

        }else if (view.getId() == R.id.activity_login_img_face){

        }
    }
    @Override
    protected void success(Object object) {
        if (object instanceof LoginBean){
            LoginBean loginBean = (LoginBean) object;
            String message = loginBean.getMessage();
            if (message.equals("登录成功")){
                LoginBean.ResultBean resultBean = loginBean.getResult();
                String sessionId = resultBean.getSessionId();
                int userId = resultBean.getUserId();

                SharedPreferences.Editor edit = mSharedPreferences.edit();  //保存用户的sessionId,userId

                edit.putString("sessionId",sessionId);
                edit.putString("userId",userId+"");
                edit.commit();
                ToastUtil.showToast(LoginActivity.this,"登录成功");
            }
        }
    }

    @Override
    protected void failed(String error) {
        ToastUtil.showToast(LoginActivity.this,error);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_login;
    }
}
