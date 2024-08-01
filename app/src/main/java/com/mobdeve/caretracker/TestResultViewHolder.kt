// TestResultViewHolder.kt
import android.app.Activity
import android.content.Intent
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.mobdeve.caretracker.MyFirestoreReferences
import com.mobdeve.caretracker.PatientModel
import com.mobdeve.caretracker.PatientViewActivity
import com.mobdeve.caretracker.R

class TestResultViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val testType: TextView = itemView.findViewById(R.id.test_type)
    val testDate: TextView = itemView.findViewById(R.id.test_date_data)
    val resultStatus: TextView = itemView.findViewById(R.id.result_status_details_data)
    val keyFindings: TextView = itemView.findViewById(R.id.key_findings_details_data)
    val testComponents: TextView = itemView.findViewById(R.id.test_components_details_data)
    val comments: TextView = itemView.findViewById(R.id.comments_data)
    val imageresult: ImageView = itemView.findViewById(R.id.image_result)

    fun bindData(model: TestResultModel) {
        this.testType.text = model.testType
        this.testDate.text = model.testDate
        this.testComponents.text = model.testComponents
        this.resultStatus.text = model.resultStatus
        this.comments.text = model.comments
        this.keyFindings.text = model.keyFindings
        this.imageresult.setImageResource(R.drawable.default_photo)
    }
}
