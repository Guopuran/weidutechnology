package tech.wd.com.communication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import tech.wd.com.common.util.ContextUtil;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (ContextUtil.getContext!=null){
            Toast.makeText(this, "已经有了", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "还没有", Toast.LENGTH_SHORT).show();

        }
    }
}
