package com.james.account_api

interface UserUtils {

    fun isLogin(): Boolean

    fun login(account: String, password: String)
}