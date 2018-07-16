package com.techapp.james.gson

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import java.lang.reflect.Type

class ConvertFamily : JsonDeserializer<Family> {
    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): Family {
        var family: JsonObject = json!!.asJsonObject.get("family").asJsonObject
        var father = family.get("Father").asString
        var son = family.get("Son").asString
        var mother = family.get("Mother").asString
        return Family(father, son, mother)
    }
}