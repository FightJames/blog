package com.james.lib_modulemanager

interface Module {

    // pair api and internal service instance.
    fun getPair(): Pair<Class<*>, Any>
}