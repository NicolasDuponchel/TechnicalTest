package duponchel.nicolas.technicaltest.api

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


object EmployeeApiServiceFactory {

    fun createService() : EmployeeApiService = Retrofit.Builder()
        .baseUrl("http://perenono.free.fr/test_mobile_gopro/")
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(EmployeeApiService::class.java)
}