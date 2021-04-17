package com.james.modularization

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.james.lib_modulemanager.ModuleManager
import com.james.market.MarketActivity
import com.james.market_api.MarketApiService

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<Button>(R.id.go_to_market).setOnClickListener {
            startActivity(
                ModuleManager.getService<MarketApiService>(MarketApiService::class.java)
                    .getMarketActivityIntent(this)
            )
        }
    }
}