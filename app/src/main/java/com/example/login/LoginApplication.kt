package com.example.login

import android.app.Application

class LoginApplication : Application(){
    companion object{

        lateinit var reqRestAPI: ReqRestAPI
    }

    override fun onCreate() {
        super.onCreate()

        //Volley

        reqRestAPI= ReqRestAPI.getInstance(this)
    }
}