package com.shadowings.hierowriter

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.IntrinsicMeasurable
import androidx.compose.ui.layout.IntrinsicMeasureScope
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.LayoutModifier
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.MeasureResult
import androidx.compose.ui.layout.MeasureScope
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.shadowings.hierowriter.HieroRepository.resFromGardinerCode
import com.shadowings.hierowriter.ui.AppTheme
import com.shadowings.hierowriter.ui.hieroglyphTint
import org.jetbrains.compose.resources.painterResource

@Preview
@Preview(name = "Dark", uiMode = 0x20, showBackground = true)
@Composable
fun MdCWordPreview() {
    AppTheme {
        Surface {
            Row {
                MdCWord("N5:G39", rtl = false, cart = false)
                MdCWord("F35-R4:X1*Q3", rtl = false, cart = true)
            }
        }
    }
}

@Composable
fun MdCWord(
    word: String,
    rtl: Boolean,
    cart: Boolean,
    size: Dp = 64.dp,
    tint: Color = hieroglyphTint
) {
    val count = word.split("-").size
    val columns = if (!rtl) {
        word.split("-")
    } else {
        word.split("-").reversed()
    }
    if (count > 12) {
        val orderedColumns = if (!rtl) columns else columns.reversed()
        val firstHalf = orderedColumns.subList(0, count / 2).joinToString("-")
        val secondHalf = orderedColumns.subList(count / 2, count).joinToString("-")
        Column(
            horizontalAlignment = if (rtl) Alignment.End else Alignment.Start,
            verticalArrangement = Arrangement.Center
        ) {
            MdCWord(firstHalf, rtl, cart, size, tint)
            MdCWord(secondHalf, rtl, cart, size, tint)
        }
        return
    }

    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (cart && rtl) {
            Box(modifier = Modifier.width(3.dp).height(size + 16.dp).background(Color.Black))
        }
        Box(
            modifier = Modifier
                .border(
                    width = if (cart) 2.dp else 0.dp,
                    color = if (cart) tint else Color.Transparent,
                    shape = RoundedCornerShape(
                        if (cart) 24.dp else 0.dp,
                        if (cart) 24.dp else 0.dp,
                        if (cart) 24.dp else 0.dp,
                        if (cart) 24.dp else 0.dp
                    )
                )
                .padding(8.dp),
            contentAlignment = Alignment.Center
        ) {
            Layout(
                content = {
                    columns.forEach { columnWord ->
                        MdCColumn(columnWord = columnWord, rtl = rtl, size = size, tint = tint)
                    }
                }
            ) { measurables, constraints ->
                val defaultHeight = size.roundToPx()

                // 1. Calculate natural widths at default height using maxIntrinsicWidth (without measuring)
                val naturalWidths = measurables.map { measurable ->
                    measurable.maxIntrinsicWidth(defaultHeight)
                }
                val totalWidth = naturalWidths.sum()
                val maxW = constraints.maxWidth

                println("MDC_WORD: totalWidth: $totalWidth, maxW: $maxW, measurables: ${measurables.size}")

                var finalHeight = defaultHeight

                // If the total width exceeds the available width, scale down the height
                if (maxW in 1..<totalWidth) {
                    val scaleFactor = maxW.toFloat() / totalWidth
                    finalHeight = (defaultHeight * scaleFactor).toInt().coerceAtLeast(1)
                }

                println("MDC_WORD: finalHeight: $finalHeight with scaleFactor: ${finalHeight.toFloat() / defaultHeight} and expected totalWidth: ${(totalWidth * (finalHeight.toFloat() / defaultHeight)).toInt()}")

                // 3. Measure all measurables EXACTLY ONCE with the final height
                val placeables = measurables.map { measurable ->
                    measurable.measure(
                        Constraints(
                            minHeight = finalHeight,
                            maxHeight = finalHeight
                        )
                    )
                }

                val actualTotalWidth = placeables.sumOf { it.width }

                println("MDC_WORD: actualTotalWidth: $actualTotalWidth, finalHeight: $finalHeight")

                // 4. Layout placement
                layout(width = actualTotalWidth, height = finalHeight) {
                    var xPosition = 0
                    placeables.forEach { placeable ->
                        placeable.placeRelative(x = xPosition, y = 0)
                        xPosition += placeable.width
                    }
                }
            }
        }
        if (cart && !rtl) {
            Box(modifier = Modifier.width(2.dp).height(size).background(Color.Black))
        }
    }
}

@Composable
private fun MdCColumn(
    columnWord: String,
    rtl: Boolean,
    size: Dp,
    modifier: Modifier = Modifier,
    tint: Color = hieroglyphTint
) {
    val rows = columnWord.split(":")
    Column(
        modifier = modifier.fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        rows.forEach { rowWord ->
            val cells = rowWord.split("*")
            Row(
                modifier = Modifier
                    .weight(1f)
                    .wrapContentWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                val orderedCells = if (!rtl) cells else cells.reversed()
                orderedCells.forEach { code ->
                    resFromGardinerCode(code)?.let { res ->
                        Image(
                            modifier = Modifier
                                .fillMaxHeight()
                                .maxCellWidth(aspectRatio = rows.size.toFloat() / cells.size.toFloat())
                                .padding(2.dp)
                                .graphicsLayer {
                                    scaleX = if (!rtl) 1f else -1f
                                },
                            painter = painterResource(res),
                            contentScale = ContentScale.Fit,
                            contentDescription = "",
                            colorFilter = ColorFilter.tint(tint)
                        )
                    }
                }
            }
        }
    }
}

private fun Modifier.maxCellWidth(aspectRatio: Float): Modifier = this.then(
    MaxCellWidthModifier(aspectRatio)
)

private class MaxCellWidthModifier(val aspectRatio: Float) : LayoutModifier {
    override fun MeasureScope.measure(
        measurable: Measurable,
        constraints: Constraints
    ): MeasureResult {
        val height = constraints.maxHeight
        val maxW = (height * aspectRatio).toInt().coerceAtLeast(1)
        val placeable = measurable.measure(
            constraints.copy(maxWidth = constraints.maxWidth.coerceAtMost(maxW))
        )
        return layout(placeable.width, placeable.height) {
            placeable.placeRelative(0, 0)
        }
    }

    override fun IntrinsicMeasureScope.maxIntrinsicWidth(
        measurable: IntrinsicMeasurable,
        height: Int
    ): Int {
        val maxW = (height * aspectRatio).toInt().coerceAtLeast(1)
        return measurable.maxIntrinsicWidth(height).coerceAtMost(maxW)
    }

    override fun IntrinsicMeasureScope.minIntrinsicWidth(
        measurable: IntrinsicMeasurable,
        height: Int
    ): Int {
        val maxW = (height * aspectRatio).toInt().coerceAtLeast(1)
        return measurable.minIntrinsicWidth(height).coerceAtMost(maxW)
    }

    override fun IntrinsicMeasureScope.maxIntrinsicHeight(
        measurable: IntrinsicMeasurable,
        width: Int
    ): Int = measurable.maxIntrinsicHeight(width)

    override fun IntrinsicMeasureScope.minIntrinsicHeight(
        measurable: IntrinsicMeasurable,
        width: Int
    ): Int = measurable.minIntrinsicHeight(width)
}