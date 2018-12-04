package duponchel.nicolas.technicaltest.utils

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.support.v7.app.AppCompatActivity

/*********** inline function to avoid making VMFactory ***********/
inline fun <reified T : ViewModel> AppCompatActivity.viewModel(crossinline factory: () -> T): T {
    val vmFactory = object : ViewModelProvider.Factory {
        override fun <U : ViewModel> create(modelClass: Class<U>): U = factory() as U
    }
    return ViewModelProviders.of(this, vmFactory)
        .get(T::class.java)
}


/****************** SHARED PREFERENCES ******************/

fun Context.putInSharedPreference(sharedPreferenceName: String, name: String, value: String) =
    getSharedPreferences(sharedPreferenceName, Context.MODE_PRIVATE)
        .edit()
        .putString(name, value)
        .apply()

fun Context.getInSharedPreference(
    sharedPreferenceName: String,
    name: String,
    defaultValue: String? = null
): String? =
    getSharedPreferences(sharedPreferenceName, Context.MODE_PRIVATE)
        .getString(name, defaultValue)

fun Context.removeInSharedPreference(sharedPreferenceName: String, name: String) =
    getSharedPreferences(sharedPreferenceName, Context.MODE_PRIVATE)
        .edit()
        .remove(name)
        .apply()

fun Context.containsInSharedPreference(sharedPreferenceName: String, key: String) =
    getSharedPreferences(sharedPreferenceName, Context.MODE_PRIVATE)
        .contains(key)