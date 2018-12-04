package duponchel.nicolas.technicaltest

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import duponchel.nicolas.technicaltest.utils.viewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val viewModel by lazy {
        viewModel { MainViewModel(SharedPrefRepo(this)) }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.employees.observe(this, Observer {
            it?.let {
                employee_view.setupView(it.first())
            }
        })
    }

}
