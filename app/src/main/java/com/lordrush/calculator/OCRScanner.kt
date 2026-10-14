package com.lordrush.calculator

import android.graphics.Bitmap
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions

object OCRScanner {

    fun parseReport(bitmap: Bitmap, onResult: (Stats) -> Unit) {
        val image = InputImage.fromBitmap(bitmap, 0)
        val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)

        recognizer.process(image)
            .addOnSuccessListener { visionText ->
                val stats = Stats()
                val text = visionText.text

                val percentRegex = Regex("""\+(\d+[\.,]?\d*)%""")
                val matches = percentRegex.findAll(text).map { 
                    it.groupValues[1].replace(",", ".").toDoubleOrNull() ?: 0.0 
                }.toList()

                if (matches.size >= 12) {
                    stats.atkInf = matches[0]
                    stats.defInf = matches[1]
                    stats.letInf = matches[2]
                    stats.hpInf  = matches[3]

                    stats.atkLan = matches[4]
                    stats.defLan = matches[5]
                    stats.effLan = matches[6]
                    stats.hpLan  = matches[7]

                    stats.atkArc = matches[8]
                    stats.defArc = matches[9]
                    stats.letArc = matches[10]
                    stats.hpArc  = matches[11]
                }

                val countRegex = Regex("""(\d{1,3}\s?\d{3}|\d+)""")
                val numbers = countRegex.findAll(text).map {
                    it.value.replace(" ", "").toIntOrNull() ?: 0
                }.filter { it > 100 }.toList()

                if (numbers.isNotEmpty()) {
                    stats.countInf = numbers.getOrElse(0) { 0 }
                    stats.countLan = numbers.getOrElse(1) { 0 }
                    stats.countArc = numbers.getOrElse(2) { 0 }
                }

                onResult(stats)
            }
            .addOnFailureListener {
                onResult(Stats())
            }
    }
}
