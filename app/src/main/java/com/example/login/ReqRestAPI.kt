package com.example.login

import android.content.Context
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley

class ReqRestAPI constructor(context: Context){
    companion object{
        @Volatile
        private var INSTANCE: ReqRestAPI?=null
        fun getInstance(context: Context)= INSTANCE?: synchronized(this){
            INSTANCE?: ReqRestAPI(context).also { INSTANCE=it }

        }
    }

    val requestQueue:RequestQueue by lazy {
        Volley.newRequestQueue(context.applicationContext)
    }
    fun <T> addToRequestQueue(req: Request <T>){
        requestQueue.add(req)
    }


}