package com.lianda.news.utils.di

import android.app.Application
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import com.lianda.news.BuildConfig
import com.lianda.news.data.api.interceptor.HeaderInterceptor
import com.lianda.news.data.preference.AppPreference
import com.lianda.news.utils.extentions.hasNetwork
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import java.text.SimpleDateFormat
import java.util.concurrent.TimeUnit

val networkModule = module {
    single { provideBasicAuth() }

//    single(named(HEADER_INTERCEPTOR_NAME)) { provideHeaderInterceptor(get(), get(), get()) }

    single { provideHttpCache(get()) }

    single { provideGsonConverterFactory() }

    single { provideLoggingInterceptor() }

    single { provideOkHttpClient(get(), get(), get(), get()) }

    single { provideRetrofit(get(), get()) }
}

fun provideBasicAuth(): String {
    val today = Calendar.getInstance().time
    val dateFormat = SimpleDateFormat("dd")
    return Credentials.basic(
        BuildConfig.USERNAME_URL,
        BuildConfig.PASSWORD_URL + dateFormat.format(today)
    )
}

fun provideHttpCache(application: Application): Cache {
    val cacheSize = 10 * 1024 * 1024
    return Cache(application.cacheDir, cacheSize.toLong())
}

fun provideGsonConverterFactory(): GsonConverterFactory {
    return GsonConverterFactory.create()
}

fun provideRetrofit(
    okHttpClient: OkHttpClient,
    gsonConverterFactory: GsonConverterFactory
): Retrofit {
    return Retrofit.Builder().baseUrl(BuildConfig.SERVER_URL)
        .client(okHttpClient)
        .addConverterFactory(gsonConverterFactory)
        .build()
}

fun provideOkHttpClient(
    cache: Cache, application: Application, loggingInterceptor: HttpLoggingInterceptor, basicAuth: String
): OkHttpClient {

    val spec = ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS).tlsVersions(TlsVersion.TLS_1_2)
        .cipherSuites(
            CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256,
            CipherSuite.TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256,
            CipherSuite.TLS_DHE_RSA_WITH_AES_128_GCM_SHA256
        ).build()

    return OkHttpClient.Builder()
        .connectionSpecs(Collections.singletonList(spec))
        .cache(cache)
        .readTimeout(5, TimeUnit.MINUTES)
        .connectTimeout(5, TimeUnit.MINUTES)
        .addInterceptor { chain ->
            val newRequest = chain.request().newBuilder()
                .addHeader("Authorization",basicAuth)
                .build()
            chain.proceed(newRequest)
        }
        .addInterceptor  {chain ->
            var request = chain.request()

            request = if (hasNetwork(application))
                request.newBuilder().header("Cache-Control", "public, max-age=" + 5).build()
            else
                request.newBuilder().header("Cache-Control", "public, only-if-cached, max-stale=" + 60*60*2).build()

            chain.proceed(request)


        }
        .addInterceptor(loggingInterceptor)
        .build()
}

fun provideHeaderInterceptor(
    application: Application,
    preference: AppPreference,
    basicAuth:String
): Interceptor {
    val headers = HashMap<String, String>()

    return HeaderInterceptor(
        application,
        headers,
        preference,
        basicAuth
    )
}

fun provideLoggingInterceptor(): HttpLoggingInterceptor {
    val logging = HttpLoggingInterceptor()
    if (BuildConfig.DEBUG) {
        logging.level = HttpLoggingInterceptor.Level.BODY
    }
    return logging
}
