package com.simplified.qr_barcode_scanner.screens.home.components

import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.StartOffset
import androidx.compose.animation.core.StartOffsetType
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateValue
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun AnimatedLoader(
    modifier: Modifier = Modifier,
    size: Dp = 80.dp
) {
    val blockCount = 8
    val blockWidth = size * 0.1f
    val spacing = size * 0.025f // Adjusted for a closer look to the original

    Box(
        modifier = modifier.size(size),
        contentAlignment = Alignment.Center
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(spacing),
            verticalAlignment = Alignment.CenterVertically
        ) {
            for (i in 1..blockCount) {
                LoaderBlock(
                    delay = i * 100,
                    size = size,
                    blockWidth = blockWidth,
                )
            }
        }
    }
}

@Composable
private fun LoaderBlock(
    delay: Int,
    size: Dp,
    blockWidth: Dp,
) {
    val infiniteTransition = rememberInfiniteTransition(label = "loader_block_transition")

    val scaleY by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                durationMillis = 1200
                1f at 0 using CubicBezierEasing(0.5f, 0f, 0.5f, 1f)
                2.5f at (durationMillis * 0.2).toInt() using CubicBezierEasing(0.5f, 0f, 0.5f, 1f)
                1f at (durationMillis * 0.4).toInt() using CubicBezierEasing(0.5f, 0f, 0.5f, 1f)
                1f at durationMillis
            },
            repeatMode = RepeatMode.Restart,
            initialStartOffset = StartOffset(delay, StartOffsetType.Delay)
        ), label = "loader_block_scale"
    )

    val glowRadius by infiniteTransition.animateValue(
        initialValue = size * 0.1f, // Base glow
        targetValue = size * 0.1f, // Target is same as initial for this animation setup
        typeConverter = Dp.VectorConverter,
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                durationMillis = 1200
                val baseGlow = size * 0.1f
                val peakGlow = size * 0.15f
                baseGlow at 0 using CubicBezierEasing(0.5f, 0f, 0.5f, 1f)
                peakGlow at (durationMillis * 0.2).toInt() using CubicBezierEasing(
                    0.5f,
                    0f,
                    0.5f,
                    1f
                )
                baseGlow at (durationMillis * 0.4).toInt() using CubicBezierEasing(
                    0.5f,
                    0f,
                    0.5f,
                    1f
                )
                baseGlow at durationMillis
            },
            repeatMode = RepeatMode.Restart,
            initialStartOffset = StartOffset(delay, StartOffsetType.Delay)
        ), label = "loader_block_glow"
    )

    Box(contentAlignment = Alignment.Center) {
        val blockModifier = Modifier
            .graphicsLayer {
                this.scaleY = scaleY
                this.transformOrigin = TransformOrigin.Center
            }
            .size(width = blockWidth, height = blockWidth)
            .background(Color.White)

        // Glow layer
        Box(
            modifier = blockModifier.blur(radius = glowRadius)
        )

        // Solid block layer
        Box(modifier = blockModifier)
    }
}

@Preview(showBackground = true)
@Composable
fun AnimatedLoaderPreview() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        contentAlignment = Alignment.Center
    ) {
        AnimatedLoader(size = 120.dp)
    }
}
