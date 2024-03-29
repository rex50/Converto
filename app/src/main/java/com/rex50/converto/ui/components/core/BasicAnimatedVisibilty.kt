package com.rex50.converto.ui.components.core

import androidx.compose.animation.*
import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.TweenSpec
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun AnimatedVisibilityBox(
    modifier: Modifier = Modifier,
    isVisible: Boolean = true,
    defaultTransitionDuration: Int = 300,
    defaultTransitionDelay: Int = 0,
    defaultTransitionEasing: Easing = FastOutLinearInEasing,
    enterTransition: EnterTransition? = null,
    exitTransition: ExitTransition? = null,
    content: @Composable AnimatedVisibilityScope.() -> Unit
) {
    Box(
        modifier = modifier
    ) {
        AnimatedVisibility(
            visible = isVisible,
            enter = enterTransition ?: fadeIn(
                animationSpec = TweenSpec(defaultTransitionDuration, defaultTransitionDelay, defaultTransitionEasing)
            ),
            exit = exitTransition ?: fadeOut(
                animationSpec = TweenSpec(defaultTransitionDuration, defaultTransitionDelay, defaultTransitionEasing)
            )
        ) {
            content()
        }
    }
}