package duponchel.nicolas.technicaltest.utils

import com.google.gson.Gson
import com.google.gson.GsonBuilder

val gson: Gson by lazy {
    GsonBuilder().create()
}

inline fun <reified T> String.fromJson(): T = gson.fromJson<T>(this, T::class.java)
inline fun <reified T> T.toJson(): String = gson.toJson(this)