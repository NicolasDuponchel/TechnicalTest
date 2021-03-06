package duponchel.nicolas.technicaltest

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.LinearLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import duponchel.nicolas.technicaltest.model.Employee
import kotlinx.android.synthetic.main.employee_view.view.*


class EmployeeView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    init {
        ConstraintLayout.inflate(context, R.layout.employee_view, this)
        orientation = HORIZONTAL
        layoutParams = LayoutParams(MATCH_PARENT, WRAP_CONTENT)
    }

    fun setupView(employee: Employee): Unit = with(employee) {
        view_first_name.text = first_name
        view_last_name.text = name
        view_email.text = email_adress
        view_job_title.text = job_title

        Glide.with(context)
            .load(thumb_url)
            .apply(
                RequestOptions()
                    .centerCrop()
                    .circleCrop()
                    .placeholder(R.drawable.ic_default)
                    .error(R.drawable.ic_failed)
            )
            .into(view_img_employee)
    }
}