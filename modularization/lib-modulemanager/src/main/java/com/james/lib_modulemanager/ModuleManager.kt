package com.james.lib_modulemanager

object ModuleManager {
    private val map = HashMap<Class<*>, Any>()

    fun register(vararg pairs:Pair<Class<*>, Any>) {
        for (pair in pairs) {
            map[pair.first] = pair.second
        }
    }

    fun register(pairs:List<Pair<Class<*>, Any>>) {
        for (pair in pairs) {
            map[pair.first] = pair.second
        }
    }

    fun<T> getService(input: Class<*>): T =
        map[input] as T
}