package com.james.market

import com.james.lib_modulemanager.Module
import com.james.market_api.MarketApiService

object MarketModule: Module {

    override fun getPair(): Pair<Class<*>, Any> = Pair(MarketApiService::class.java, MarketApiServiceImpl)
}