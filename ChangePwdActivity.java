package com.newer.user;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.newer.com.newer.util.VolleyUtil;
import com.newer.driverschool.MainActivity;
import com.newer.driverschool.R;

import java.util.HashMap;
import java.util.Map;

/**
 * 修改密码活动
 */
public class ChangePwdActivity extends AppCompatActivity {

    //身份证号
    private EditText editText_pin;
    //旧密码
    private EditText editText_oldPwd;
    //新密码
    private EditText editText_NewPwd;

    private Button button_changPwd;

    String pin;
    String old;
    String newPwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pwd);

        initView();
    }

    private void initView() {
        editText_pin = (EditText) findViewById(R.id.editText_pin1);
        editText_oldPwd = (EditText) findViewById(R.id.editText_oldPwd1);
        editText_NewPwd = (EditText) findViewById(R.id.editText_oldPwd1);

        button_changPwd = (Button) findViewById(R.id.button_changPwd);

        button_changPwd.setOnClickListener(new ChangPwdListener());
    }

    /**
     * 修改密码监听器
     */
    private class ChangPwdListener implements View.OnClickListener {

        String TAG = "--ChangPwdListener--";

        @Override
        public void onClick(View v) {

            pin = editText_pin.getText().toString();
            old = editText_oldPwd.getText().toString();
            newPwd = editText_NewPwd.getText().toString();

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    if (null == pin) {

                        Log.d(TAG, "身份证号码不能为空！");

                    } else if (null == old) {

                        Log.d(TAG, "原密码不能为空！");

                    } else if (null == newPwd) {

                        Log.d(TAG, "新密码不能为空！");

                    } else {

                        //发送请求

                        String url = "http://192.168.191.1:8080/jiaxiaotong/servlet/AppChangeStudentPassword?pin=" + pin + "&password=" + old + "&passwordNew=" + newPwd;

                        StringRequest request = new StringRequest(
                                Request.Method.POST,
                                url,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String s) {
                                        //响应成功
                                        Log.d(TAG, "响应成功" + s);
                                        if (s.equals("true")) {
                                            Toast.makeText(getApplicationContext(), "密码修改成功！", Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                        } else {
                                            Toast.makeText(getApplicationContext(), "密码修改失败!", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError volleyError) {
                                        //响应失败
                                        Log.d(TAG, "响应失败");
                                    }
                                }
                        ) {
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {

                                //这里写要传的参数
                                Map<String, String> map = new HashMap<String, String>();

                                map.put("UserPin", pin);
                                map.put("UserOld", old);
                                map.put("UserNPwd", newPwd);

                                return map;
                            }
                        };

                        VolleyUtil.getInstance(getApplicationContext()).addRequest(request);
                    }

                }
            }, 100);

        }
    }
}
