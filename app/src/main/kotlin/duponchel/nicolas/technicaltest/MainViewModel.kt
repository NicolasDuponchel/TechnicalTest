package duponchel.nicolas.technicaltest

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import duponchel.nicolas.technicaltest.MainViewModel.LoadingStatus.LOADING
import duponchel.nicolas.technicaltest.MainViewModel.LoadingStatus.NOT_LOADING
import duponchel.nicolas.technicaltest.api.EmployeeApiServiceFactory
import duponchel.nicolas.technicaltest.model.Employee
import duponchel.nicolas.technicaltest.model.Employees
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers


class MainViewModel(private val sharedPrefRepo: SharedPrefRepo) : ViewModel() {
    private val api by lazy { EmployeeApiServiceFactory.createService() }

    enum class LoadingStatus { LOADING, NOT_LOADING }

    private val _employees = MutableLiveData<List<Employee>>()
    val employees: LiveData<List<Employee>> = _employees

    private val _loadingStatus = MutableLiveData<LoadingStatus>()
    val loadingStatus: LiveData<LoadingStatus> = _loadingStatus

    init {
        sharedPrefRepo.employee()
            ?.let {
                _employees.postValue(it.employees)
            }
            ?: fetchEmployees()
    }

    /**
     * This function should be written much more properly, in a functional kotlin lovely way.
     * Sorry about disgusting for loops ;P ... time was up
     */
    fun onEmployeePlaceChanged(adapter: EmployeeAdapter, fromPosition: Int, toPosition: Int) {
        val employees: MutableList<Employee> = _employees.value?.toMutableList() ?: mutableListOf()
        if (fromPosition < toPosition) {
            for (i in fromPosition until toPosition) {
                employees[i] = employees.set(i + 1, employees[i])
            }
        } else {
            for (i in fromPosition..toPosition + 1) {
                employees[i] = employees.set(i - 1, employees[i])
            }
        }
        adapter.notifyItemMoved(fromPosition, toPosition)
        _employees.postValue(employees)
    }

    fun onFinishSwipping() = _employees.value?.let { sharedPrefRepo.saveEmployee(Employees(it)) }

    private fun fetchEmployees() = api.employee()
        .subscribeOn(Schedulers.io())
        .observeOn(Schedulers.io())
        .doOnSubscribe { _loadingStatus.postValue(LOADING) }
        .doFinally { _loadingStatus.postValue(NOT_LOADING) }
        .subscribeBy(
            onSuccess = {
                Log.d("employees", "$it")
                _employees.postValue(it)
                saveEmployees()
            },
            onError = { Log.e("employees", "${it.message}") }
        )

    private fun saveEmployees() = _employees.value?.let { sharedPrefRepo.saveEmployee(Employees(it)) }
}