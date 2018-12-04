package duponchel.nicolas.technicaltest

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.helper.ItemTouchHelper
import android.support.v7.widget.helper.ItemTouchHelper.*
import duponchel.nicolas.technicaltest.utils.viewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var employeeAdapter: EmployeeAdapter

    private val viewModel by lazy {
        viewModel { MainViewModel(SharedPrefRepo(this)) }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupRecyclerView()
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.employees.observe(this, Observer {
            it?.let {
                with(employeeAdapter) {
                    setupItems(it)
                    notifyDataSetChanged()
                }
            }
        })
    }

    private fun setupRecyclerView() = with(recycler_employees_view) {
        layoutManager = LinearLayoutManager(this@MainActivity)
        employeeAdapter = EmployeeAdapter(this@MainActivity, viewModel.onEmployeePlaceChanged())
        adapter = employeeAdapter
        val callback = DragManagerAdapter(viewModel.onEmployeePlaceChanged(), UP.or(DOWN), LEFT.or(RIGHT))
        ItemTouchHelper(callback).attachToRecyclerView(this)
    }

}
