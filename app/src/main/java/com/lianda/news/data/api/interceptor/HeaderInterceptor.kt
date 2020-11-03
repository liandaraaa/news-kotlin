package com.lianda.news.data.api.interceptor

import android.app.Application
import com.lianda.news.BuildConfig
import com.lianda.news.data.preference.AppPreference
import com.lianda.news.utils.extentions.hasNetwork
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import java.util.HashMap

class HeaderInterceptor (
    private val application: Application,
    private val headers:HashMap<String,String>,
    val preference: AppPreference,
    val basicAuth:String):Interceptor{

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = mapHeaders(chain)
        return chain.proceed(request)
    }

    private fun mapHeaders(chain: Interceptor.Chain):Request{
        val original = chain.request()
        val requestBuilder = original.newBuilder()
            .addHeader("Authorization",basicAuth)

        if (hasNetwork(application))
            requestBuilder.addHeader("Cache-Control", "public, max-age=" + 5).build()
        else
           requestBuilder.addHeader("Cache-Control", "public, only-if-cached, max-stale=" + 60*60*2).build()


        for ((key, value) in headers) {
            requestBuilder.addHeader(key, value)
        }
        return requestBuilder.build()
    }


}