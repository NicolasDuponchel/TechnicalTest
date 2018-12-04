package duponchel.nicolas.technicaltest

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import duponchel.nicolas.technicaltest.api.EmployeeApiServiceFactory
import duponchel.nicolas.technicaltest.model.Employee
import duponchel.nicolas.technicaltest.model.Employees
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers


class MainViewModel(private val sharedPrefRepo: SharedPrefRepo) : ViewModel() {
    private val api by lazy { EmployeeApiServiceFactory.createService() }

    private val _employees = MutableLiveData<List<Employee>>()
    val employees: LiveData<List<Employee>> = _employees

    init {
        sharedPrefRepo.employee()
            ?.let {
                _employees.postValue(it.employees)
            }
            ?: fetchEmployees()
    }

    private fun fetchEmployees() = api.employee()
        .subscribeOn(Schedulers.io())
        .observeOn(Schedulers.io())
        .subscribeBy(
            onSuccess = {
                Log.d("employees", "$it")
                sharedPrefRepo.saveEmployee(Employees(it))
                _employees.postValue(it)
            },
            onError = { Log.e("employees", "${it.message}") }
        )

    fun onEmployeePlaceChanged(): () -> Unit {
        TODO("Update employees list here in live data")

        if (fromPosition < toPosition) {
            for (i in fromPosition until toPosition) {
                employees[i] = employees.set(i + 1, employees[i])
            }
        } else {
            for (i in fromPosition..toPosition + 1) {
                employees[i] = employees.set(i - 1, employees[i])
            }
        }

        notifyItemMoved(fromPosition, toPosition)
    }
}