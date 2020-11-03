package com.lianda.news.utils.di

import com.lianda.news.data.db.AppDatabase
import org.koin.dsl.module

val dbModule = module {
    single { AppDatabase.database(get()) }

    single { get<AppDatabase>().orderDao() }
}