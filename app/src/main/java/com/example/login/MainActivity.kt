package com.example.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.example.login.databinding.ActivityMainBinding
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    private lateinit var mBinding:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        mBinding.swType.setOnCheckedChangeListener { button, checked ->
            button.text= if(checked) getString(R.string.main_type_login)
            else getString(R.string.main_type_register)

            mBinding.btnLogin.text=button.text
        }

        mBinding.btnLogin.setOnClickListener {
            login()
        }
    }
    private fun login(){
        val typeMethod= if (mBinding.swType.isChecked) Constanst.LOGIN_PATH else Constanst.REGISTER_PATH
        val url= Constanst.BASE_URL+ Constanst.API_PATH+ typeMethod

        val email= mBinding.etEmail.text.toString().trim()
        val password= mBinding.etPassword.text.toString().trim()

        val jsonParams=JSONObject()
        if (email.isNotEmpty()){
            jsonParams.put(Constanst.EMAIL_PARAM, email)
        }
        if (password.isNotEmpty()){
            jsonParams.put(Constanst.PASSWORD_PARAM,password)
        }

        val jsonObjectRequest= object :JsonObjectRequest(Request.Method.POST, url, jsonParams,{ response->
            Log.i("response", response.toString())

            //extraer valores
            val id=response.optString(Constanst.ID_PROPERTY,Constanst.ERROR_VALUE)
            val token= response.optString(Constanst.TOKEN_PROPERTY,Constanst.ERROR_VALUE)

            val result=if(id.equals(Constanst.ERROR_VALUE))"${Constanst.TOKEN_PROPERTY}:$token"
            else "${Constanst.ID_PROPERTY}:$id, ${Constanst.TOKEN_PROPERTY}:$token"
            updateUI(result)

        },{
            it.printStackTrace()
            if(it.networkResponse.statusCode==400){
                updateUI(getString(R.string.main_error_server))
            }

        }){
            override fun getHeaders(): MutableMap<String, String> {
                val params= HashMap<String,String>()
                    params["Content-Type"]="application/json"
                    return params
                }
        }

        LoginApplication.reqRestAPI.addToRequestQueue(jsonObjectRequest)

        //updateUI(":3")
    }
    private fun updateUI(result:String){
        mBinding.tvResult.visibility= View.VISIBLE
        mBinding.tvResult.text= result
    }
}