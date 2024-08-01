// TestResult.kt
data class TestResultModel(
    val testType: String,
    val testDate: String,
    val resultStatus: String,
    val keyFindings: String,
    val testComponents: String,
    val comments: String,
    val imageResId: Int?
)
