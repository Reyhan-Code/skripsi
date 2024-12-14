package com.dicoding.capstone.view.result

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.capstone.R
import com.dicoding.capstone.databinding.ActivityResultBinding
import com.dicoding.capstone.helper.ImageClassifierHelper
import org.tensorflow.lite.task.vision.classifier.Classifications

class ResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val imageUri = intent.getStringExtra(EXTRA_IMAGE_URI)
        if (imageUri != null) {
            val image = Uri.parse(imageUri)
            displayImage(image)

            val imageClassifierHelper = ImageClassifierHelper(
                context = this,
                classifierListener = object : ImageClassifierHelper.ClassifierListener {
                    override fun onError(errorMessage: String) {
                        Log.d(TAG, "Error: $errorMessage")
                    }

                    override fun onResults(results: List<Classifications>?, inferenceTime: Long) {
                        results?.let { Results(it) }
                    }
                }
            )
            imageClassifierHelper.classifyStaticImage(image)
        } else {
            Log.e(TAG, "No image URI provided")
            finish()
        }

    }

    private fun Results(results: List<Classifications>) {
        val topResult = results[0]
        val label = topResult.categories[0].label
        val score = topResult.categories[0].score
        val index = topResult.categories[0].index

        fun Float.formatToString(): String {
            return String.format("%.2f%%", this * 100)
        }

        val resultText = when (index) {
            0 -> "Non Edible"
            1 -> "Non Edible"
            2 -> "Edible"
            3 -> "Edible"
            4 ->"Edible"
            5 ->"Non Edible"
            6 -> "Non Edible"
            7 -> "Non Edible"
            8 -> "Edible"
            9 -> "Edible"
            else -> "unknown"
        }

        val descFungus = when (index){
            0 -> getString(R.string.index0)
            1 -> getString(R.string.index1)
            2 -> getString(R.string.index2)
            3 -> getString(R.string.index3)
            4 -> getString(R.string.index4)
            5 -> getString(R.string.index5)
            6 -> getString(R.string.index6)
            7 -> getString(R.string.index7)
            8 -> getString(R.string.index8)
            9 -> getString(R.string.index9)
            10 -> getString(R.string.index10)
            11 -> getString(R.string.index11)
            12 -> getString(R.string.index12)
            13 -> getString(R.string.index13)
            14 -> getString(R.string.index14)
            15 -> getString(R.string.index15)
            16 -> getString(R.string.index16)
            17 -> getString(R.string.index17)
            18 -> getString(R.string.index18)
            19 -> getString(R.string.index19)
            else -> "unknown"
        }

        val displayMushroomImage = when (index){
            0 -> R.drawable.amanita_phalloides
            1 -> "Non Edible"
            2 -> "Edible"
            3 -> "Edible"
            4 ->"Edible"
            5 ->"Non Edible"
            6 -> "Non Edible"
            7 -> "Non Edible"
            8 -> "Edible"
            9 -> "Edible"
            else -> "unknown"
        }


        with(binding) {
            resultToxic.text = resultText
            resultName.text = label
            resultPersentase.text = score.formatToString()
            resultDes.text = descFungus

            displayMushroomImage.let {
                resultImg.setImageResource(it as Int)
            }

            Log.d("DebugLog", "resultText: $resultText")
            Log.d("DebugLog", "label: $label")
            Log.d("DebugLog", "score: ${score.formatToString()}")
            Log.d("DebugLog", "descFungus: $descFungus")
        }
    }

    private fun displayImage(uri: Uri) {
        Log.d(TAG, "Displaying image: $uri")
        with(binding) {
            resultImage.setImageURI(uri)
            resultImageBackground.setImageURI(uri)
        }
    }

    companion object {
        const val EXTRA_IMAGE_URI = "extra_image_uri"
        const val TAG = "Result_Activity"
    }
}