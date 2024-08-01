import com.mobdeve.caretracker.R

// TestResultDataGenerator.kt

class TestResultDataGenerator {
    fun generateSampleData(): List<TestResultModel> {
        return listOf(
            TestResultModel(
                testType = "CBC",
                testDate = "07/20/2024",
                resultStatus = "Normal",
                keyFindings = "All blood parameters including Hemoglobin, White Blood Cells, and Platelets are within the expected normal ranges. No signs of anemia or infection detected. The overall blood profile is healthy.",
                testComponents = "Hemoglobin: Measures the amount of oxygen-carrying protein in red blood cells.\nWhite Blood Cells: Indicates immune system health.\nPlatelets: Assesses blood clotting ability.",
                comments = "Continue with regular health check-ups. Maintain a balanced diet and exercise regularly to ensure ongoing health.",
                imageResId = R.drawable.cbc_result
            ),
            TestResultModel(
                testType = "X-Ray",
                testDate = "07/15/2024",
                resultStatus = "Abnormal",
                keyFindings = "The X-Ray images reveal a possible fracture in the left arm, specifically around the radius bone. There is also slight swelling noted in the surrounding soft tissues. Further imaging or an MRI may be required to confirm the extent of the injury.",
                testComponents = "X-Ray Images: Radiographic images showing the structure of bones.\nInterpretation: Analysis of the images to diagnose any abnormalities.",
                comments = "Consult with an orthopedic specialist for a detailed evaluation. Follow recommended treatments and possibly additional imaging if necessary.",
                imageResId = R.drawable.xray_result
            ),
            TestResultModel(
                testType = "Blood Test",
                testDate = "07/18/2024",
                resultStatus = "Normal",
                keyFindings = "Cholesterol levels are within the recommended ranges, with LDL (bad cholesterol) and HDL (good cholesterol) being balanced. Glucose levels are stable, indicating no current issues with blood sugar regulation. Triglycerides are also within normal limits.",
                testComponents = "Cholesterol Levels: Total cholesterol, LDL, HDL.\nGlucose Levels: Blood sugar levels measured after fasting.\nTriglycerides: Blood fat levels that contribute to cardiovascular health.",
                comments = "The results suggest a healthy metabolic profile. Continue with a diet low in saturated fats and sugars. Regular physical activity is encouraged to maintain cardiovascular health.",
                imageResId = R.drawable.blood_test_result
            ),
            TestResultModel(
                testType = "CT Scan",
                testDate = "07/10/2024",
                resultStatus = "Abnormal",
                keyFindings = "CT Scan indicates the presence of a mass in the liver, which could be indicative of a benign or malignant lesion. Additional imaging, such as an MRI, and possibly a biopsy are needed for a definitive diagnosis. No other abnormalities were observed in the surrounding organs.",
                testComponents = "CT Images: Detailed cross-sectional images of the body.\nRadiology Report: Comprehensive report on the findings, including potential implications and recommendations.",
                comments = "It is crucial to follow up with a hepatologist or oncologist for further assessment and treatment options. Regular monitoring may be necessary to track any changes in the mass.",
                imageResId = R.drawable.ct_scan_result
            ),
            TestResultModel(
                testType = "Urinalysis",
                testDate = "07/12/2024",
                resultStatus = "Normal",
                keyFindings = "Urinalysis results show clear, pale yellow urine with no signs of infection or significant abnormalities. The pH level is slightly acidic, which is typical. No protein or glucose detected, indicating normal kidney function and no issues with diabetes.",
                testComponents = "Urine Color: Indicates hydration status and possible health issues.\npH: Acidity level of urine, which can vary with diet and health conditions.\nSpecific Gravity: Measures urine concentration, reflecting hydration status.\nProtein Levels: High levels could indicate kidney problems.\n",
                comments = "Maintain adequate hydration and continue with routine urinalysis to ensure continued renal health. Consult a healthcare provider if any symptoms or concerns arise.",
                imageResId = R.drawable.urinalysis_result
            )
        )
    }
}
