package duponchel.nicolas.technicaltest

import android.content.Context
import duponchel.nicolas.technicaltest.model.EmployeeResponse
import duponchel.nicolas.technicaltest.utils.fromJson
import duponchel.nicolas.technicaltest.utils.getInSharedPreference
import duponchel.nicolas.technicaltest.utils.putInSharedPreference
import duponchel.nicolas.technicaltest.utils.toJson


class SharedPrefRepo(val context: Context) {
    companion object {
        private const val EMPLOYEES_PREF_NAME = "employees_sharedPref"
        private const val EMPLOYEES_KEY = "employees_key"
    }

    fun saveEmployee(employees: EmployeeResponse) =
        context.putInSharedPreference(EMPLOYEES_PREF_NAME, EMPLOYEES_KEY, employees.toJson())

    fun employee(): EmployeeResponse? = context.getInSharedPreference(EMPLOYEES_PREF_NAME, EMPLOYEES_KEY)?.fromJson()
}