import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mobdeve.caretracker.R
import com.squareup.picasso.Picasso

class TestResultViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val testType: TextView = itemView.findViewById(R.id.test_type)
    val testDate: TextView = itemView.findViewById(R.id.test_date_data)
    val resultStatus: TextView = itemView.findViewById(R.id.result_status_details_data)
    val keyFindings: TextView = itemView.findViewById(R.id.key_findings_details_data)
    val testComponents: TextView = itemView.findViewById(R.id.test_components_details_data)
    val comments: TextView = itemView.findViewById(R.id.comments_data)
    val imageresult: ImageView = itemView.findViewById(R.id.image_result)
    val recordnumber: TextView = itemView.findViewById(R.id.result_counter)

    fun bindData(model: TestResultModel) {
        this.recordnumber.text = "Test Result #" + model.recordNo.toString()
        this.testType.text = model.testType
        this.testDate.text = model.testDate
        this.testComponents.text = model.testComponents
        this.resultStatus.text = model.resultStatus
        this.comments.text = model.comments
        this.keyFindings.text = model.keyFindings
        if (model.imageResId.isNotEmpty()) {
            Picasso.get().load(model.imageResId).into(this.imageresult)
        }
    }
}
