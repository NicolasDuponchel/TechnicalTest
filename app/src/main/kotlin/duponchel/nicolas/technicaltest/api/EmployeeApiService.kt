package duponchel.nicolas.technicaltest.api

import duponchel.nicolas.technicaltest.model.Employee
import io.reactivex.Single
import retrofit2.http.GET


interface EmployeeApiService {

    @GET("employees.json")
    fun employee(): Single<List<Employee>>

}