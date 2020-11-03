package com.lianda.news.utils.extentions

import android.content.Context
import android.provider.Settings

fun getDeviceId(context: Context):String{
    return Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
}