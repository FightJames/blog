package com.james.modularization

import android.app.Application
import com.james.account.AccountModule
import com.james.lib_modulemanager.ModuleManager
import com.james.market.MarketModule

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        ModuleManager.register(listOf(AccountModule.getPair(), MarketModule.getPair()))
    }
}