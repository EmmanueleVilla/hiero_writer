package com.shadowings.hierowriter.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val correctBgLight = Color(0xFF58cc02)
private val correctBgDark = Color(0xFF3E8B2A)
val correctBg: Color
	@Composable get() = if (LocalIsDarkMode.current) correctBgDark else correctBgLight

private val correctBorderLight = Color(0xFF2e8c00)
private val correctBorderDark = Color(0xFF8BDD63)
val correctBorder: Color
	@Composable get() = if (LocalIsDarkMode.current) correctBorderDark else correctBorderLight

private val wrongBgLight = Color(0xFFff4b4b)
private val wrongBgDark = Color(0xFF8E3636)
val wrongBg: Color
	@Composable get() = if (LocalIsDarkMode.current) wrongBgDark else wrongBgLight

private val wrongBorderLight = Color(0xFFe92929)
private val wrongBorderDark = Color(0xFFFF7D7D)
val wrongBorder: Color
	@Composable get() = if (LocalIsDarkMode.current) wrongBorderDark else wrongBorderLight

val neutralBlack = Color.Black
val neutralWhite = Color.White
val neutralRed = Color.Red
val neutralCyan = Color.Cyan
val testModeCorrect = Color.Green

val hieroglyphTint: Color
	@Composable get() = if (LocalIsDarkMode.current) neutralWhite else neutralBlack

private val quizSelectionPendingBgLight = Color(0xFFd0dfea)
private val quizSelectionPendingBgDark = Color(0xFF2D3A47)
val quizSelectionPendingBg: Color
	@Composable get() = if (LocalIsDarkMode.current) quizSelectionPendingBgDark else quizSelectionPendingBgLight

private val quizSelectionCorrectBgLight = Color(0xFFd9ead0)
private val quizSelectionCorrectBgDark = Color(0xFF284432)
val quizSelectionCorrectBg: Color
	@Composable get() = if (LocalIsDarkMode.current) quizSelectionCorrectBgDark else quizSelectionCorrectBgLight

private val quizSelectionWrongBgLight = Color(0xFFead0d0)
private val quizSelectionWrongBgDark = Color(0xFF4A2D2D)
val quizSelectionWrongBg: Color
	@Composable get() = if (LocalIsDarkMode.current) quizSelectionWrongBgDark else quizSelectionWrongBgLight

private val quizInputCorrectBorderLight = Color(0xFF4CAF50)
private val quizInputCorrectBorderDark = Color(0xFF81C784)
val quizInputCorrectBorder: Color
	@Composable get() = if (LocalIsDarkMode.current) quizInputCorrectBorderDark else quizInputCorrectBorderLight

private val quizInputWrongBorderLight = Color(0xFFF44336)
private val quizInputWrongBorderDark = Color(0xFFE57373)
val quizInputWrongBorder: Color
	@Composable get() = if (LocalIsDarkMode.current) quizInputWrongBorderDark else quizInputWrongBorderLight

private val quizInputCorrectBgLight = Color(0xFFE8F5E9)
private val quizInputCorrectBgDark = Color(0xFF1F3325)
val quizInputCorrectBg: Color
	@Composable get() = if (LocalIsDarkMode.current) quizInputCorrectBgDark else quizInputCorrectBgLight

private val quizInputWrongBgLight = Color(0xFFFFEBEE)
private val quizInputWrongBgDark = Color(0xFF3A2224)
val quizInputWrongBg: Color
	@Composable get() = if (LocalIsDarkMode.current) quizInputWrongBgDark else quizInputWrongBgLight

private val disabledNeutralBgLight = Color(0xFFE0E0E0)
private val disabledNeutralBgDark = Color(0xFF2F2F2F)
val disabledNeutralBg: Color
	@Composable get() = if (LocalIsDarkMode.current) disabledNeutralBgDark else disabledNeutralBgLight

private val disabledNeutralBorderLight = Color(0xFFBDBDBD)
private val disabledNeutralBorderDark = Color(0xFF4A4A4A)
val disabledNeutralBorder: Color
	@Composable get() = if (LocalIsDarkMode.current) disabledNeutralBorderDark else disabledNeutralBorderLight

private val disabledNeutralTextLight = Color(0xFF757575)
private val disabledNeutralTextDark = Color(0xFF9E9E9E)
val disabledNeutralText: Color
	@Composable get() = if (LocalIsDarkMode.current) disabledNeutralTextDark else disabledNeutralTextLight

private val lessonPerfectContainerLight = Color(0xFFFFFDE7)
private val lessonPerfectContainerDark = Color(0xFF3F3A1A)
val lessonPerfectContainer: Color
	@Composable get() = if (LocalIsDarkMode.current) lessonPerfectContainerDark else lessonPerfectContainerLight

private val lessonLockedContainerLight = Color(0xFFF2F2F2)
private val lessonLockedContainerDark = Color(0xFF262626)
val lessonLockedContainer: Color
	@Composable get() = if (LocalIsDarkMode.current) lessonLockedContainerDark else lessonLockedContainerLight

private val lessonLockedTintLight = Color(0xFF888888)
private val lessonLockedTintDark = Color(0xFF9A9A9A)
val lessonLockedTint: Color
	@Composable get() = if (LocalIsDarkMode.current) lessonLockedTintDark else lessonLockedTintLight

private val lessonLockedButtonBorderLight = Color(0xFFB0B0B0)
private val lessonLockedButtonBorderDark = Color(0xFF5A5A5A)
val lessonLockedButtonBorder: Color
	@Composable get() = if (LocalIsDarkMode.current) lessonLockedButtonBorderDark else lessonLockedButtonBorderLight

private val lessonHintTextLight = Color(0xFF856404)
private val lessonHintTextDark = Color(0xFFC7A15C)
val lessonHintText: Color
	@Composable get() = if (LocalIsDarkMode.current) lessonHintTextDark else lessonHintTextLight

private val keyboardEnabledContainerLight = Color(0xFFEEEEEE)
private val keyboardEnabledContainerDark = Color(0xFF2D2D2D)
val keyboardEnabledContainer: Color
	@Composable get() = if (LocalIsDarkMode.current) keyboardEnabledContainerDark else keyboardEnabledContainerLight

private val keyboardDisabledContainerLight = Color(0xFFF5F5F5)
private val keyboardDisabledContainerDark = Color(0xFF232323)
val keyboardDisabledContainer: Color
	@Composable get() = if (LocalIsDarkMode.current) keyboardDisabledContainerDark else keyboardDisabledContainerLight

private val modalScrimLight = Color.Black.copy(alpha = 0.5f)
private val modalScrimDark = Color.Black.copy(alpha = 0.65f)
val modalScrim: Color
	@Composable get() = if (LocalIsDarkMode.current) modalScrimDark else modalScrimLight

private val overlayScrimLight = Color.Black.copy(alpha = 0.4f)
private val overlayScrimDark = Color.Black.copy(alpha = 0.55f)
val overlayScrim: Color
	@Composable get() = if (LocalIsDarkMode.current) overlayScrimDark else overlayScrimLight

val skinPalette = listOf(
	Color(0xFFf6d7c3),
	Color(0xFFeac2a0),
	Color(0xFFe0b08a),
	Color(0xFFd39a73),
	Color(0xFFc6865a),
	Color(0xFFb7774b),
	Color(0xFF9c623e),
	Color(0xFF7c4a2f),
	Color(0xFF5c3623),
	Color(0xFF3b2216)
)

val hairPalette = listOf(
	Color(0xFFF5E6B3),
	Color(0xFFE8D28A),
	Color(0xFFD8B96A),
	Color(0xFFB08968),
	Color(0xFF8D6B4F),
	Color(0xFF6F4E37),
	Color(0xFFC65A2E),
	Color(0xFFA43A1E),
	Color(0xFF2C1B12),
	Color(0xFF0F0F0F),
	Color(0xFF7A4BFF),
	Color(0xFF2EC4B6),
	Color(0xFFFF4FA3)
)

val eyesPalette = listOf(
	Color(0xFF4A2C1A),
	Color(0xFF6B3E26),
	Color(0xFF8C6A2F),
	Color(0xFFB68A3A),
	Color(0xFF3A6B3A),
	Color(0xFF2F8F6B),
	Color(0xFF3A5F8F),
	Color(0xFF5A8FD8),
	Color(0xFF6E7B8B),
	Color(0xFF1A1A1A),
	Color(0xFF7A4BFF),
	Color(0xFFFF4F7A)
)

val accessoryPalette = listOf(
	Color(0xFF009b77),
	Color(0xFF9966cc),
	Color(0xFFb87333),
	Color(0xFFff6f61),
	Color(0xFFd4af37),
	Color(0xFF2b2b2b),
	Color(0xFFe0115f),
	Color(0xFF0f52ba),
	Color(0xFFc0c0c0),
	Color(0xFF40e0d0),
)

val hatPalette = listOf(
	Color(0xFFFBE56D),
	Color(0xFFFFDB58),
	Color(0xFFE8D3A1),
	Color(0xFFD8C08A),
	Color(0xFFC19A6B),
	Color(0xFF8B5A2B),
	Color(0xFF5C3A21),
	Color(0xFF6B7A3A),
	Color(0xFF2F5D3A),
	Color(0xFF008080),
	Color(0xFF2C3E66),
	Color(0xFFDC143C),
	Color(0xFF7A1E2C),
	Color(0xFF3A3A3A),
	Color(0xFF1A1A1A),
)

val mouthPalette = listOf(
	Color(0xFFF2A7A0),
	Color(0xFFE58C84),
	Color(0xFFD46A6A),
	Color(0xFFB94A48),
	Color(0xFF7A2E2E)
)

val backgroundPalette = listOf(
	Color(0xFF534600),
	Color(0xFF3F4A1F),
	Color(0xFF4A2F1F),
	Color(0xFF2F3F4A),
	Color(0xFF4A1F3A),
	Color(0xFF3A3A3A),
	Color(0xFF8A7A2A),
	Color(0xFF7A8A4A),
	Color(0xFF8A5A3A),
	Color(0xFF6A7FA0),
	Color(0xFFBFAE5A),
	Color(0xFFD9CC8A),
	Color(0xFFE8E2B0),
)

val primaryLight = Color(0xFF6D5E0F)
val onPrimaryLight = Color(0xFFFFFFFF)
val primaryContainerLight = Color(0xFFf8e287)
val onPrimaryContainerLight = Color(0xFF534600)
val secondaryLight = Color(0xFF665E40)
val onSecondaryLight = Color(0xFFFFFFFF)
val secondaryContainerLight = Color(0xFFEEE2BC)
val onSecondaryContainerLight = Color(0xFF4E472A)
val tertiaryLight = Color(0xFF43664E)
val onTertiaryLight = Color(0xFFFFFFFF)
val tertiaryContainerLight = Color(0xFFC5ECCE)
val onTertiaryContainerLight = Color(0xFF2C4E38)
val errorLight = Color(0xFFBA1A1A)
val onErrorLight = Color(0xFFFFFFFF)
val errorContainerLight = Color(0xFFFFDAD6)
val onErrorContainerLight = Color(0xFF93000A)
val backgroundLight = Color(0xFFFFF9EE)
val onBackgroundLight = Color(0xFF1E1B13)
val surfaceLight = Color(0xFFFFF9EE)
val onSurfaceLight = Color(0xFF1E1B13)
val surfaceVariantLight = Color(0xFFEAE2D0)
val onSurfaceVariantLight = Color(0xFF4B4739)
val outlineLight = Color(0xFF7C7767)
val outlineVariantLight = Color(0xFFCDC6B4)
val scrimLight = Color(0xFF000000)
val inverseSurfaceLight = Color(0xFF333027)
val inverseOnSurfaceLight = Color(0xFFF7F0E2)
val inversePrimaryLight = Color(0xFFDBC66E)
val surfaceDimLight = Color(0xFFE0D9CC)
val surfaceBrightLight = Color(0xFFFFF9EE)
val surfaceContainerLowestLight = Color(0xFFFFFFFF)
val surfaceContainerLowLight = Color(0xFFFAF3E5)
val surfaceContainerLight = Color(0xFFF4EDDF)
val surfaceContainerHighLight = Color(0xFFEEE8DA)
val surfaceContainerHighestLight = Color(0xFFE8E2D4)


val primaryDark = Color(0xFFAC9852)
val onPrimaryDark = Color(0xFF3A3000)
val primaryContainerDark = Color(0xFF534600)
val onPrimaryContainerDark = Color(0xFFCCB967)
val secondaryDark = Color(0xFFB8AD8A)
val onSecondaryDark = Color(0xFF363016)
val secondaryContainerDark = Color(0xFF4E472A)
val onSecondaryContainerDark = Color(0xFFD4C8A6)
val tertiaryDark = Color(0xFFA9D0B3)
val onTertiaryDark = Color(0xFF143723)
val tertiaryContainerDark = Color(0xFF2C4E38)
val onTertiaryContainerDark = Color(0xFFC5ECCE)
val errorDark = Color(0xFFFFB4AB)
val onErrorDark = Color(0xFF690005)
val errorContainerDark = Color(0xFF93000A)
val onErrorContainerDark = Color(0xFFFFDAD6)
val backgroundDark = Color(0xFF15130B)
val onBackgroundDark = Color(0xFFE8E2D4)
val surfaceDark = Color(0xFF15130B)
val onSurfaceDark = Color(0xFFE8E2D4)
val surfaceVariantDark = Color(0xFF4B4739)
val onSurfaceVariantDark = Color(0xFFCDC6B4)
val outlineDark = Color(0xFF969080)
val outlineVariantDark = Color(0xFF4B4739)
val scrimDark = Color(0xFF000000)
val inverseSurfaceDark = Color(0xFFE8E2D4)
val inverseOnSurfaceDark = Color(0xFF333027)
val inversePrimaryDark = Color(0xFF685A12)
val surfaceDimDark = Color(0xFF15130B)
val surfaceBrightDark = Color(0xFF3C3930)
val surfaceContainerLowestDark = Color(0xFF100E07)
val surfaceContainerLowDark = Color(0xFF1E1B13)
val surfaceContainerDark = Color(0xFF222017)
val surfaceContainerHighDark = Color(0xFF2D2A21)
val surfaceContainerHighestDark = Color(0xFF38352B)

