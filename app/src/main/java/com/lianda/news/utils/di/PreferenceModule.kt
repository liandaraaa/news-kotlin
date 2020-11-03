package com.lianda.news.utils.di

import com.lianda.news.data.preference.AppPreference
import com.lianda.news.data.preference.AppPreferenceImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val preferenceModule = module {
    single<AppPreference> { AppPreferenceImpl(androidContext()) }
}