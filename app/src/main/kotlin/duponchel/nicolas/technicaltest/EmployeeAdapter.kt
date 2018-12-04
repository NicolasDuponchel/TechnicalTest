package duponchel.nicolas.technicaltest

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import duponchel.nicolas.technicaltest.model.Employee


class EmployeeAdapter(val context: Context) : RecyclerView.Adapter<EmployeeAdapter.ViewHolder>() {
    private val employees = mutableListOf<Employee>()

    fun setupItems(employees: List<Employee>) = with(this.employees) {
        clear()
        addAll(employees)
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        return ViewHolder(EmployeeView(context))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        return holder.customView.setupView(employees[position])
    }

    override fun getItemCount() = employees.size

    fun swapItems(fromPosition: Int, toPosition: Int) {
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

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val customView: EmployeeView = view as EmployeeView
    }

}