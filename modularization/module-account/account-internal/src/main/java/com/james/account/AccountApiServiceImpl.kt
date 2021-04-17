package com.james.account

import com.james.account_api.AccountApiService
import com.james.account_api.UserUtils

object AccountApiServiceImpl: AccountApiService {

    override fun getUserUtils(): UserUtils = UserUtilsImpl
}