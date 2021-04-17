package com.james.market

import android.content.Context
import android.content.Intent
import com.james.market_api.MarketApiService

object MarketApiServiceImpl : MarketApiService {

    override fun getMarketActivityIntent(context: Context): Intent =
        MarketActivity.getIntent(context)

}