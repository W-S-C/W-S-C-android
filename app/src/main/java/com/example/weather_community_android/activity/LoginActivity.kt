package com.example.weather_community_android.activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.weather_community_android.R
import com.example.weather_community_android.databinding.ActivityLoginBinding
import com.example.weather_community_android.model.LoginDAO
import com.example.weather_community_android.model.LoginEntity
import com.example.weather_community_android.model.LoginViewModel
import com.example.weather_community_android.network.ApiService
import com.example.weather_community_android.repository.UserRepository
import com.google.gson.JsonObject
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_login.*

import kotlinx.android.synthetic.main.activity_login.*
import java.lang.Exception

//import kotlinx.android.synthetic.main.activity_login.et_id

class LoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding
    private lateinit var keyboardVisibilityUtils: KeyboardVisibilityUtils
    private var backTime: Long = 0
    private lateinit var loginVm : LoginViewModel
    private lateinit var loginDAO: LoginDAO
    lateinit var viewModelFactory : ViewModelProvider.AndroidViewModelFactory
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
//        val binding: ActivityLoginBinding = DataBindingUtil.setContentView(this,R.layout.activity_login)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this
        setContentView(binding.root)
        initActivity()
        keyboardVisibilityUtils = KeyboardVisibilityUtils(window,
                onShowKeyboard = { keyboardHeight ->
                    sv_login.run {
                        smoothScrollTo(scrollX, scrollY +keyboardHeight)
                    }
                })

        btn_login.setOnClickListener {
//            val id = et_id.text.toString()
//            val pwd = et_pwd.text.toString()
//            val inputJson = JsonObject()
//            inputJson.addProperty("userName", id)
//            inputJson.addProperty("pwd", pwd)
//            login(inputJson)
            login()


        }

    }
    private fun initActivity() {
        initViewModel()
    }
    private fun initViewModel() {
        loginVm = ViewModelProvider(this).get(LoginViewModel::class.java)


    }

    private fun login() {
        val inputData = LoginEntity(binding.id.toString(), binding.pwd.toString())
        val json = JsonObject()
        json.addProperty("userName", inputData.id)
        json.addProperty("pwd", inputData.pwd)
        loginVm.login(json)


//        if (loginDAO.statusCode == 200) {
//            val intent = Intent(this@LoginActivity, MainActivity::class.java)
//            intent.putExtra("token", loginDAO.data)
//            println(loginDAO.data)
//            startActivity(intent)
//            finish()
//        }
//        else{
////            Toast.makeText(applicationContext, "dd", Toast.LENGTH_SHORT).show()
//            throw Exception("로그인 실패")
//
//        }

    }
//    @SuppressLint("CheckResult")
//    fun login(input: JsonObject) {
//        ApiService.loginService.login(input)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe({
//                    Toast.makeText(applicationContext, "로그인 성공", Toast.LENGTH_SHORT).show()
//                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
//                    intent.putExtra("token", it)
//                    startActivity(intent)
//                    finish()
//                }, {
//                    Toast.makeText(applicationContext, "$it", Toast.LENGTH_SHORT).show()
//                    Log.e("수빈", it.toString())
//                })
//    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount == 0) {
            if (System.currentTimeMillis() - backTime < 2000) {
                finish()
            }
            Toast.makeText(this, "종료하시려면 다시한번 눌러주세요.", Toast.LENGTH_SHORT).show()
            backTime = System.currentTimeMillis()
        } else {
            supportFragmentManager.popBackStack()
        }
    }

}