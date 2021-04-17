package com.james.market

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.james.account_api.AccountApiService
import com.james.lib_modulemanager.ModuleManager

class MarketActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_market)
        // If user don't login, force him to login.
        val userUtils = ModuleManager.getService<AccountApiService>(AccountApiService::class.java)
            .getUserUtils()
        if (!userUtils.isLogin()) finish()


    }

    companion object {
        fun getIntent(context: Context) = Intent(context, MarketActivity::class.java)
    }
}