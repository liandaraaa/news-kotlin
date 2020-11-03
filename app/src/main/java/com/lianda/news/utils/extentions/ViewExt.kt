package com.lianda.news.utils.extentions

import android.widget.Toast
import com.lianda.news.base.BaseActivity

fun BaseActivity.showToast(message:String){
    Toast.makeText(this,message, Toast.LENGTH_SHORT).show()
}