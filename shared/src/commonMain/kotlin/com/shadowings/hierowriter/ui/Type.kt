package com.shadowings.hierowriter.ui


import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp
import hierowriter.shared.generated.resources.GoogleSans
import hierowriter.shared.generated.resources.NotoSansEgyptianHieroglyphs_Regular
import hierowriter.shared.generated.resources.Res
import hierowriter.shared.generated.resources.Roboto_Condensed_Medium
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.Font

@OptIn(ExperimentalResourceApi::class)
@Composable
fun bodyFontFamily() = FontFamily(
    Font(Res.font.GoogleSans)
)

@OptIn(ExperimentalResourceApi::class)
@Composable
fun displayFontFamily() = FontFamily(
    Font(Res.font.Roboto_Condensed_Medium)
)

@OptIn(ExperimentalResourceApi::class)
@Composable
fun titleFontFamily() = FontFamily(
    Font(Res.font.NotoSansEgyptianHieroglyphs_Regular)
)


// Default Material 3 typography values
val baseline = Typography()

@Composable
fun appTypography() = Typography(
    displayLarge = baseline.displayLarge.copy(fontFamily = displayFontFamily()),
    displayMedium = baseline.displayMedium.copy(fontFamily = displayFontFamily()),
    displaySmall = baseline.displaySmall.copy(fontFamily = displayFontFamily()),
    headlineLarge = baseline.headlineLarge.copy(fontFamily = displayFontFamily()),
    headlineMedium = baseline.headlineMedium.copy(fontFamily = displayFontFamily()),
    headlineSmall = baseline.headlineSmall.copy(fontFamily = displayFontFamily()),
    titleLarge = baseline.titleLarge.copy(fontFamily = titleFontFamily()),
    titleMedium = baseline.titleMedium.copy(fontFamily = displayFontFamily()),
    titleSmall = baseline.titleSmall.copy(fontFamily = displayFontFamily()),
    bodyLarge = baseline.bodyLarge.copy(fontFamily = bodyFontFamily(), fontSize = 18.sp),
    bodyMedium = baseline.bodyMedium.copy(fontFamily = bodyFontFamily()),
    bodySmall = baseline.bodySmall.copy(fontFamily = bodyFontFamily()),
    labelLarge = baseline.labelLarge.copy(fontFamily = bodyFontFamily()),
    labelMedium = baseline.labelMedium.copy(fontFamily = bodyFontFamily()),
    labelSmall = baseline.labelSmall.copy(fontFamily = bodyFontFamily()),
)


