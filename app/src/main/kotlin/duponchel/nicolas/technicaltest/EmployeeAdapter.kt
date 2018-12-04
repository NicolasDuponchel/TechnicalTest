package duponchel.nicolas.technicaltest

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import duponchel.nicolas.technicaltest.model.Employee


class EmployeeAdapter(private val context: Context) :
    RecyclerView.Adapter<EmployeeAdapter.ViewHolder>() {
    private val employees = mutableListOf<Employee>()

    fun setupItems(employees: List<Employee>) = with(this.employees) {
        clear()
        addAll(employees)
        notifyItemChanged(-1) //I've got a little bug with notifyDataSetChange() due to DragManagerAdapter. It's the only thing I found to correct it quickly
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        return ViewHolder(EmployeeView(context))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        return holder.customView.setupView(employees[position])
    }

    override fun getItemCount() = employees.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val customView: EmployeeView = view as EmployeeView
    }

}