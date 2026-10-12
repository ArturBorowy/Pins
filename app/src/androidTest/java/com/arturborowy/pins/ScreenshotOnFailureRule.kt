package com.arturborowy.pins

import android.graphics.Bitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.test.captureToImage
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.onRoot
import androidx.test.services.storage.TestStorage
import org.junit.rules.TestWatcher
import org.junit.runner.Description

class ScreenshotOnFailureRule(
    private val composeTestRuleProvider: () -> ComposeTestRule
) : TestWatcher() {

    override fun failed(e: Throwable?, description: Description?) {
        runCatching {
            val composeTestRule = composeTestRuleProvider()

            val bitmap = composeTestRule.onRoot()
                .captureToImage()
                .asAndroidBitmap()

            if (description == null) {
                println("Failed to capture screenshot on test failure: description is null")
            } else {
                val className = description.className.substringAfterLast('.')
                val methodName = description.methodName
                val fileName = "${className}_${methodName}_failure.png"

                TestStorage().openOutputFile(fileName).use { outputStream ->
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
                }

                println("Failure screenshot captured: $fileName")
            }
        }.onFailure { captureFailureThrowable ->
            println(
                "Failed to capture screenshot on test failure, message: " +
                        "${captureFailureThrowable.message}"
            )
        }
    }
}
