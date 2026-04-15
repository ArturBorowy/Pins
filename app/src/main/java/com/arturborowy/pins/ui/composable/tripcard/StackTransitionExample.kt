package com.arturborowy.pins.ui.composable.tripcard

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex

@Composable
fun StackTransitionExample() {
    var isExpanded by remember { mutableStateOf(false) }

    SharedTransitionLayout {
        AnimatedContent(
            targetState = isExpanded,
            label = "SheetToStack"
        ) { expanded ->
            if (expanded) {
                // STATE A: The Expanded Bottom Sheet
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    Card(
                        modifier = Modifier
                            .sharedBounds(
                                sharedContentState = rememberSharedContentState(key = "my_card"),
                                animatedVisibilityScope = this@AnimatedContent
                            )
                            .fillMaxWidth()
                            .height(400.dp) // Large sheet size
                            .clickable { isExpanded = false },
                        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
                    ) {
                        Text("I am a Bottom Sheet. Tap to shrink.")
                    }
                }
            } else {
                // STATE B: The Stacked Card
                Column(Modifier.padding(16.dp)) {
                    Card(
                        Modifier
                            .fillMaxWidth()
                            .height(80.dp)
                    ) { Text("Card 1 (Top)") }

                    // Here is our transforming card in position #2!
                    Card(
                        modifier = Modifier
                            .sharedBounds(
                                sharedContentState = rememberSharedContentState(key = "my_card"),
                                animatedVisibilityScope = this@AnimatedContent
                            )
                            .fillMaxWidth()
                            .height(80.dp) // Small stacked size
                            .offset(y = (-20).dp) // Slide slightly under Card 1
                            .zIndex(-1f)
                            .clickable { isExpanded = true },
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("I am Card #2 in the stack. Tap to expand.")
                    }

                    Card(
                        Modifier
                            .fillMaxWidth()
                            .height(80.dp)
                            .offset(y = (-40).dp)
                            .zIndex(-2f)
                    ) {
                        Text("Card 3 (Bottom)")
                    }
                }
            }
        }
    }
}
