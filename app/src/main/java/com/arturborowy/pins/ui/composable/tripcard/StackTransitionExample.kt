package com.arturborowy.pins.ui.composable.tripcard

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import kotlin.random.Random

data class CardData(val id: String, val title: String, val color: Color)

private fun generateRandomColor() = Color(
    Random.nextInt(256),
    Random.nextInt(256),
    Random.nextInt(256)
)

@Composable
fun StackTransitionExample(
    cards: List<CardData> = remember {
        listOf(
            CardData("1", "Card 1 (Front)", generateRandomColor()),
            CardData("2", "Card 2 (Middle)", generateRandomColor()),
            CardData("3", "Card 3 (Back)", generateRandomColor())
        )
    }
) {
    var expandedCardId by remember { mutableStateOf<String?>(null) }

    SharedTransitionLayout {
        AnimatedContent(
            targetState = expandedCardId,
            label = "SheetToStack",
            transitionSpec = {
                EnterTransition.None togetherWith ExitTransition.None
            }
        ) { targetId ->
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
                if (targetId != null) {
                    val card = cards.find { it.id == targetId }
                    if (card != null) {
                        ExpandedSheet(
                            card = card,
                            onCollapse = { expandedCardId = null },
                            animatedVisibilityScope = this@AnimatedContent,
                            sharedTransitionScope = this@SharedTransitionLayout
                        )
                    }
                } else {
                    StackedCards(
                        cards = cards,
                        onExpand = { expandedCardId = it.id },
                        animatedVisibilityScope = this@AnimatedContent,
                        sharedTransitionScope = this@SharedTransitionLayout
                    )
                }
            }
        }
    }
}

@Composable
private fun ExpandedSheet(
    card: CardData,
    onCollapse: () -> Unit,
    animatedVisibilityScope: AnimatedVisibilityScope,
    sharedTransitionScope: SharedTransitionScope
) {
    val density = LocalDensity.current
    val slideDist = with(density) { 200.dp.toPx() }

    with(sharedTransitionScope) {
        Card(
            modifier = Modifier
                .sharedBounds(
                    sharedContentState = rememberSharedContentState(key = card.id),
                    animatedVisibilityScope = animatedVisibilityScope,
                    boundsTransform = { initialBounds, targetBounds ->
                        keyframes {
                            durationMillis = 600
                            initialBounds at 0 using FastOutSlowInEasing
                            val slideOutBounds = initialBounds.translate(0f, -slideDist)
                            slideOutBounds at 300 using FastOutSlowInEasing
                            targetBounds at 600 using FastOutSlowInEasing
                        }
                    }
                )
                .fillMaxWidth()
                .height(450.dp)
                .clickable { onCollapse() },
            shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
            colors = CardDefaults.cardColors(containerColor = card.color)
        ) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Expanded: ${card.title}\nTap to collapse", color = Color.White)
            }
        }
    }
}

@Composable
private fun StackedCards(
    cards: List<CardData>,
    onExpand: (CardData) -> Unit,
    animatedVisibilityScope: AnimatedVisibilityScope,
    sharedTransitionScope: SharedTransitionScope
) {
    val density = LocalDensity.current
    val slideDist = with(density) { 200.dp.toPx() }

    with(sharedTransitionScope) {
        Box(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .height(300.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            cards.asReversed().forEachIndexed { index, card ->
                val zIndex = (index + 1).toFloat()
                val offsetMultiplier = cards.size - 1 - index

                Card(
                    modifier = Modifier
                        .offset(y = (-30 * offsetMultiplier).dp)
                        .sharedBounds(
                            sharedContentState = rememberSharedContentState(key = card.id),
                            animatedVisibilityScope = animatedVisibilityScope,
                            boundsTransform = { initialBounds, targetBounds ->
                                keyframes {
                                    durationMillis = 600
                                    initialBounds at 0 using FastOutSlowInEasing
                                    val slideInBounds = targetBounds.translate(0f, -slideDist)
                                    slideInBounds at 300 using FastOutSlowInEasing
                                    targetBounds at 600 using FastOutSlowInEasing
                                }
                            }
                        )
                        .fillMaxWidth(1f - (0.05f * offsetMultiplier))
                        .height(120.dp)
                        .zIndex(zIndex)
                        .clickable { onExpand(card) }
                        .shadow(8.dp, RoundedCornerShape(12.dp)),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = card.color)
                ) {
                    Box(
                        Modifier
                            .fillMaxSize()
                            .padding(top = if (offsetMultiplier > 0) 8.dp else 0.dp),
                        contentAlignment = if (offsetMultiplier > 0) Alignment.TopCenter else Alignment.Center
                    ) {
                        Text(card.title, color = Color.White)
                    }
                }
            }
        }
    }
}
