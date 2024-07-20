// TestResultAdapter.kt
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mobdeve.caretracker.R

class TestResultAdapter(private val testResults: List<TestResultModel>) : RecyclerView.Adapter<TestResultViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TestResultViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.test_results_recycleritem, parent, false)
        return TestResultViewHolder(view)
    }

    override fun onBindViewHolder(holder: TestResultViewHolder, position: Int) {
        val testResult = testResults[position]
        holder.testType.text = testResult.testType
        holder.testDate.text = testResult.testDate
        holder.resultStatus.text = testResult.resultStatus
        holder.keyFindings.text = testResult.keyFindings
        holder.testComponents.text = testResult.testComponents
        holder.comments.text = testResult.comments
    }

    override fun getItemCount(): Int {
        return testResults.size
    }
}
