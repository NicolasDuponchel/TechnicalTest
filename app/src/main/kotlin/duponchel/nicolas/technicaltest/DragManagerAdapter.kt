package duponchel.nicolas.technicaltest

import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.support.v7.widget.helper.ItemTouchHelper.ACTION_STATE_IDLE


class DragManagerAdapter(val listener: Listener, dragDirs: Int, swipeDirs: Int) :
    ItemTouchHelper.SimpleCallback(dragDirs, swipeDirs) {
    private var isOrderChanged: Boolean = false //also sorry about this variable boolean so statefull :P

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        listener.onEmployeePlaceChanged(viewHolder.adapterPosition, target.adapterPosition)
        isOrderChanged = true
        return true
    }

    override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
        super.onSelectedChanged(viewHolder, actionState)
        if (isSwippingFinish(actionState)) {
            listener.onFinishSwipping()
            isOrderChanged = false
        }
    }

    private fun isSwippingFinish(actionState: Int) = actionState == ACTION_STATE_IDLE && isOrderChanged

    override fun onSwiped(p0: RecyclerView.ViewHolder, p1: Int) = Unit

    class Listener(
        val onEmployeePlaceChanged: (fromPosition: Int, toPosition: Int) -> Unit,
        val onFinishSwipping: () -> Unit
    )
}