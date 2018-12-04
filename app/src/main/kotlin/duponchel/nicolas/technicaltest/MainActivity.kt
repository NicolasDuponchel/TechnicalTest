package duponchel.nicolas.technicaltest

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.helper.ItemTouchHelper
import android.support.v7.widget.helper.ItemTouchHelper.*
import duponchel.nicolas.technicaltest.MainViewModel.LoadingStatus.LOADING
import duponchel.nicolas.technicaltest.MainViewModel.LoadingStatus.NOT_LOADING
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
            it?.let { employeeAdapter.setupItems(it) }
        })

        viewModel.loadingStatus.observe(this, Observer {
            when (it) {
                LOADING -> main_loader.show()
                NOT_LOADING -> main_loader.hide()
            }
        })
    }

    private fun setupRecyclerView() = with(recycler_employees_view) {
        layoutManager = LinearLayoutManager(this@MainActivity)
        employeeAdapter = EmployeeAdapter(this@MainActivity)
        adapter = employeeAdapter
        val listener = DragManagerAdapter.Listener(
            onEmployeePlaceChanged = { fromPosition, toPosition ->
                viewModel.onEmployeePlaceChanged(
                    employeeAdapter,
                    fromPosition,
                    toPosition
                )
            },
            onFinishSwipping = { viewModel.onFinishSwipping() }
        )
        val callback =
            DragManagerAdapter(
                listener = listener,
                dragDirs = UP.or(DOWN),
                swipeDirs = LEFT.or(RIGHT)
            )
        ItemTouchHelper(callback).attachToRecyclerView(this)
    }

}
