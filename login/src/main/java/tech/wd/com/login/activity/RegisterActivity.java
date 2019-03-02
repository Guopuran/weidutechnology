package tech.wd.com.login.activity;


import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import tech.wd.com.login.bean.RegisterBean;

public class RegisterActivity extends BaseActivity {

    //昵称
    @BindView(R2.id.activity_register_edit_name)
    EditText activity_register_edit_name;
    //手机号
    @BindView(R2.id.activity_register_edit_phone)
    EditText activity_register_edit_phone;
    //密码
    @BindView(R2.id.activity_register_edit_pwd)
    EditText activity_register_edit_pwd;
    //短信注册
    @BindView(R2.id.activity_register_text)
    TextView activity_register_text;
    //注册
    @BindView(R2.id.activity_register_button)
    Button activity_register_button;
    @Override
    protected void initData() {
        isSlide(false);
    }

    //显示或隐藏密码
    @OnTouch(R2.id.activity_register_img_eyes)
    public boolean onTouch(View v, MotionEvent event){
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                activity_register_edit_pwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                break;
            case MotionEvent.ACTION_UP:
                activity_register_edit_pwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                break;
        }
        return true;
    }
    @OnClick({R2.id.activity_register_button,R2.id.activity_register_text})
    public void onClick(View view){
         if (view.getId() == R.id.activity_register_button){
            String name = activity_register_edit_name.getText().toString().trim();
            String phone = activity_register_edit_phone.getText().toString().trim();
            String pwd = activity_register_edit_pwd.getText().toString().trim();
            String regex = "^(13[0-9]|14[5|7]|15[0|1|2|3|5|6|7|8|9]|16[1|6]|18[0|1|2|3|5|6|7|8|9])\\d{8}$";
            boolean matches = Pattern.matches(regex, phone);

            if (name.isEmpty() || phone.isEmpty() || pwd.isEmpty()){

                Toast.makeText(RegisterActivity.this,R.string.login_phone_pwd_isEmpty,Toast.LENGTH_SHORT).show();

            }else if (!matches){
                Toast.makeText(RegisterActivity.this,R.string.edit_phone_error,Toast.LENGTH_SHORT).show();

            }else{
                try {
                    String s = RsaCoder.encryptByPublicKey(pwd);
                    Map<String, String> map = new HashMap<>();
                    map.put("name",name);
                    map.put("phone",phone);
                    map.put("pwd",s);
                    postRequest(Apis.URL_REGISTER,map,RegisterBean.class);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

        }else if (view.getId() == R.id.activity_register_text){

        }
    }
    @Override
    protected void success(Object object) {
        if (object instanceof RegisterBean){
            RegisterBean registerBean  = (RegisterBean) object;
            String message = registerBean.getMessage();
            if (message.equals("注册成功")){
                ToastUtil.showToast(RegisterActivity.this,registerBean.getMessage());
                finish();
            }else {
                ToastUtil.showToast(RegisterActivity.this,registerBean.getMessage());
            }
        }
    }

    @Override
    protected void failed(String error) {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_register;
    }
}
