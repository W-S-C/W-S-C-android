package com.example.weather_community_android.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviderKt;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.weather_community_android.R;
import com.example.weather_community_android.databinding.ActivityLoginBinding;
import com.example.weather_community_android.network.ApiService;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import io.reactivex.Scheduler;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class LoginActivity2 extends AppCompatActivity {

    EditText etId;
    EditText etPwd;
    Button btnLogin;
    private ViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btnLogin = findViewById(R.id.btn_login);
        etId = findViewById(R.id.et_id);
        etPwd = findViewById(R.id.et_pwd);

//        JSONObject input = new JSONObject();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = etId.getText().toString();
                String pwd = etPwd.getText().toString();
                JsonObject inputJson = new JsonObject();
                inputJson.addProperty("userName", id);
                inputJson.addProperty("pwd", pwd);
//                login(inputJson);

            }
        });
    }

//    private void login(JsonObject input) {
//
//        ApiService.INSTANCE.getLoginService().login(input)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(
//                        it -> {
//                            Toast.makeText(getApplicationContext(), "로그인 성공", Toast.LENGTH_SHORT).show();
//                            Intent intent = new Intent(this,MainActivity.class);
//                            intent.putExtra("token", it);
//                            Log.e("수빈: 토큰 ", it);
//                            startActivity(intent);
//                            finish();
//                        }, it->{
//                            Log.e("수빈: 로그인", it.getMessage());
//                            Toast.makeText(getApplicationContext(), it.getMessage(),Toast.LENGTH_SHORT).show();
//                        }
//                );
//
//    }
}