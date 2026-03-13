package com.arturborowy.pins.ui

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.navigation.NavBackStackEntry

const val durationMs = 300

fun AnimatedContentTransitionScope<NavBackStackEntry>.enterAnimation(): EnterTransition {
    return defaultFadeIn() + slideIntoContainer(
        towards = AnimatedContentTransitionScope.SlideDirection.Start,
        animationSpec = tween(durationMs),
    )
}

fun AnimatedContentTransitionScope<NavBackStackEntry>.exitTransition(): ExitTransition {
    return defaultFadeOut() + slideOutOfContainer(
        towards = AnimatedContentTransitionScope.SlideDirection.Start,
        animationSpec = tween(durationMs),
    )
}

fun AnimatedContentTransitionScope<NavBackStackEntry>.popEnterTransition(): EnterTransition {
    return defaultFadeIn() + slideIntoContainer(
        towards = AnimatedContentTransitionScope.SlideDirection.End,
        animationSpec = tween(durationMs),
    )
}

fun AnimatedContentTransitionScope<NavBackStackEntry>.popExitTransition(): ExitTransition {
    return defaultFadeOut() + slideOutOfContainer(
        towards = AnimatedContentTransitionScope.SlideDirection.End,
        animationSpec = tween(durationMs),
    )
}

fun defaultFadeIn() = fadeIn(animationSpec = tween(durationMs, easing = LinearEasing))
fun defaultFadeOut() = fadeOut(animationSpec = tween(durationMs, easing = LinearEasing))
