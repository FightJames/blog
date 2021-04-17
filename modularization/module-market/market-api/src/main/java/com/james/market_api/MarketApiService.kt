package com.james.market_api

import android.content.Context
import android.content.Intent

interface MarketApiService {

    fun getMarketActivityIntent(context: Context): Intent
}