package com.james.account

import com.james.account_api.AccountApiService
import com.james.lib_modulemanager.Module

object AccountModule : Module{

    override fun getPair(): Pair<Class<*>, Any>
    = Pair(AccountApiService::class.java, AccountApiServiceImpl)

}