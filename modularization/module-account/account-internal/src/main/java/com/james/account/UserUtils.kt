package com.james.account

import com.james.account_api.UserUtils

object UserUtilsImpl: UserUtils {

    override fun isLogin(): Boolean = true

    override fun login(account: String, password: String) {

    }
}