package com.arturborowy.pins.ui.composable.tripcard

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.EnterExitState
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
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
                val isExpanding = targetState != null
                (EnterTransition.None togetherWith ExitTransition.None).apply {
                    // Ensure the exiting stack stays on top of the entering sheet
                    // so shared elements in the overlay follow their Z-indices.
                    targetContentZIndex = if (isExpanding) -1f else 1f
                }
            }
        ) { targetId ->
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
                // StackedCards is rendered in both branches to keep them in the shared overlay
                StackedCards(
                    cards = cards,
                    onExpand = { expandedCardId = it.id },
                    animatedVisibilityScope = this@AnimatedContent,
                    sharedTransitionScope = this@SharedTransitionLayout,
                    hiddenCardId = targetId
                )

                if (targetId != null) {
                    val card = cards.find { it.id == targetId }
                    if (card != null) {
                        ExpandedSheet(
                            card = card,
                            cards = cards,
                            onCollapse = { expandedCardId = null },
                            animatedVisibilityScope = this@AnimatedContent,
                            sharedTransitionScope = this@SharedTransitionLayout
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ExpandedSheet(
    card: CardData,
    cards: List<CardData>,
    onCollapse: () -> Unit,
    animatedVisibilityScope: AnimatedVisibilityScope,
    sharedTransitionScope: SharedTransitionScope
) {
    val density = LocalDensity.current
    val slideDist = with(density) { 200.dp.toPx() }
    val initialZIndex = (cards.size - cards.indexOf(card)).toFloat()

    val zIndexInOverlay by animatedVisibilityScope.transition.animateFloat(
        label = "zIndexInOverlay",
        transitionSpec = {
            if (EnterExitState.PreEnter isTransitioningTo EnterExitState.Visible) {
                keyframes {
                    durationMillis = 1800
                    initialZIndex at 0
                    initialZIndex at 900 // Keep behind other cards for first half
                    10f at 1800
                }
            } else {
                keyframes {
                    durationMillis = 1800
                    10f at 0
                    initialZIndex at 900
                    initialZIndex at 1800
                }
            }
        }
    ) { state ->
        if (state == EnterExitState.Visible) 10f else initialZIndex
    }

    with(sharedTransitionScope) {
        Card(
            modifier = Modifier
                .sharedBounds(
                    sharedContentState = rememberSharedContentState(key = card.id),
                    animatedVisibilityScope = animatedVisibilityScope,
                    zIndexInOverlay = zIndexInOverlay,
                    boundsTransform = { initialBounds, targetBounds ->
                        keyframes {
                            durationMillis = 1800
                            initialBounds at 0 using FastOutSlowInEasing
                            val slideOutBounds = initialBounds.translate(0f, -slideDist)
                            slideOutBounds at 900 using FastOutSlowInEasing
                            targetBounds at 1800 using FastOutSlowInEasing
                        }
                    }
                )
                .fillMaxWidth()
                .aspectRatio(1f)
                .shadow(8.dp, RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
                .clickable { onCollapse() },
            shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
            colors = CardDefaults.cardColors(containerColor = card.color)
        ) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CardContent(card)
            }
        }
    }
}

@Composable
private fun StackedCards(
    cards: List<CardData>,
    onExpand: (CardData) -> Unit,
    animatedVisibilityScope: AnimatedVisibilityScope,
    sharedTransitionScope: SharedTransitionScope,
    hiddenCardId: String? = null
) {
    val density = LocalDensity.current
    val slideDist = with(density) { 200.dp.toPx() }

    with(sharedTransitionScope) {
        Box(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .height(500.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            cards.asReversed().forEachIndexed { index, card ->
                val zIndex = (index + 1).toFloat()
                val offsetMultiplier = cards.size - 1 - index

                // Only render if it's not the one currently expanded (it's rendered by ExpandedSheet)
                if (card.id != hiddenCardId) {
                    Card(
                        modifier = Modifier
                            .offset(x = (30 * offsetMultiplier).dp, y = (-30 * offsetMultiplier).dp)
                            .sharedBounds(
                                sharedContentState = rememberSharedContentState(key = card.id),
                                animatedVisibilityScope = animatedVisibilityScope,
                                zIndexInOverlay = zIndex,
                                boundsTransform = { initialBounds, targetBounds ->
                                    keyframes {
                                        durationMillis = 1800
                                        initialBounds at 0 using FastOutSlowInEasing
                                        val slideInBounds = targetBounds.translate(0f, -slideDist)
                                        slideInBounds at 900 using FastOutSlowInEasing
                                        targetBounds at 1800 using FastOutSlowInEasing
                                    }
                                }
                            )
                            .fillMaxWidth(0.5f)
                            .aspectRatio(1f)
                            .zIndex(zIndex)
                            .clickable(enabled = hiddenCardId == null) { onExpand(card) }
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
                            CardContent(card)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CardContent(card: CardData) {
    Column(Modifier.fillMaxSize()) {
        Row(
            Modifier
                .fillMaxWidth()
                .background(card.color)
                .padding(32.dp)
        ) {
            Text(card.title, color = Color.White)
        }
        Box(
            Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(16.dp)
        ) {
            Text(
                "WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW",
                color = Color.Black,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}
