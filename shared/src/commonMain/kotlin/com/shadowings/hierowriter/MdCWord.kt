package com.shadowings.hierowriter

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
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
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.shadowings.hierowriter.HieroRepository.resFromGardinerCode
import com.shadowings.hierowriter.ui.AppTheme
import com.shadowings.hierowriter.ui.hieroglyphTint
import com.shadowings.hierowriter.ui.neutralBlack
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
fun MdCWord(word: String, rtl: Boolean, cart: Boolean, size: Dp = 64.dp, tint: Color = hieroglyphTint) {
    val columns = if (!rtl) {
        word.split("-")
    } else {
        word.split("-").reversed()
    }

    Box(
        modifier = Modifier
            .border(
                width = if (cart) 2.dp else 0.dp,
                color = if (cart) tint else Color.Transparent,
                shape = RoundedCornerShape(
                    if (!rtl) 16.dp else 0.dp,
                    if (rtl) 16.dp else 0.dp,
                    if (rtl) 16.dp else 0.dp,
                    if (!rtl) 16.dp else 0.dp
                )
            )
            .padding(4.dp),
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

            println("totalWidth: $totalWidth, maxW: $maxW, measurables: ${measurables.size}")

            var finalHeight = defaultHeight

            // If the total width exceeds the available width, scale down the height
            if (maxW in 1..<totalWidth) {
                var scaleFactor = maxW.toFloat() / totalWidth
                // reduce the scale factor a bit to ensure it fits within the paddings
                scaleFactor *= 0.9f
                finalHeight = (defaultHeight * scaleFactor).toInt().coerceAtLeast(1)
            }

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
}

@Composable
private fun MdCColumn(columnWord: String, rtl: Boolean, size: Dp, tint: Color, modifier: Modifier = Modifier) {
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
                                .widthIn(max = size / cells.size)
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