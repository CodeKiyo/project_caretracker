// TestResultAdapter.kt
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.mobdeve.caretracker.R

class TestResultAdapter(private val testResults: List<TestResultModel>) : RecyclerView.Adapter<TestResultViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TestResultViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.test_results_recycleritem, parent, false)
        return TestResultViewHolder(view)
    }

    override fun onBindViewHolder(holder: TestResultViewHolder, position: Int) {
        holder.bindData(this.testResults[position])
    }

    override fun getItemCount(): Int {
        return testResults.size
    }
    private fun showImageDialog(imageView: ImageView, imageResId: Int?) {
        val dialog = Dialog(imageView.context)
        dialog.setContentView(R.layout.dialog_image_view)
        val closeButton = dialog.findViewById<ImageButton>(R.id.close_button)
        val dialogImageView = dialog.findViewById<ImageView>(R.id.dialog_image)
        dialogImageView.setImageResource(imageResId ?: R.drawable.default_photo)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        closeButton.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }
}

