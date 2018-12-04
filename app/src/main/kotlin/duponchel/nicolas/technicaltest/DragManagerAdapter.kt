package duponchel.nicolas.technicaltest

import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper


class DragManagerAdapter(
    val onEmployeePlaceChanged: () -> Pair<Int, Int>,
    dragDirs: Int,
    swipeDirs: Int
) :
    ItemTouchHelper.SimpleCallback(dragDirs, swipeDirs) {

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
//        adapter.swapItems(viewHolder.adapterPosition, target.adapterPosition)
        onEmployeePlaceChanged()
        return true
    }

    override fun onSwiped(p0: RecyclerView.ViewHolder, p1: Int) {
    }

}