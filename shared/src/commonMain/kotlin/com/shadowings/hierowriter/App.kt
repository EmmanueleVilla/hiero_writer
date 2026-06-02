package com.shadowings.hierowriter

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.boundsInWindow
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shadowings.hierowriter.ui.AppTheme
import com.shadowings.hierowriter.ui.titleFontFamily
import kotlinx.coroutines.delay

private data class FamousName(
    val label: String,
    val mdcCode: String,
    val translation: String,
    val isRoyal: Boolean
)

@Composable
fun App() {
    var darkThemeOverride by remember { mutableStateOf<Boolean?>(null) }
    val systemDark = isSystemInDarkTheme()
    val isDark = darkThemeOverride ?: systemDark

    AppTheme(darkTheme = isDark) {
        Scaffold(
            modifier = Modifier.fillMaxSize()
        ) { paddingValues ->
            var inputText by rememberSaveable { mutableStateOf("F35-R4:X1*Q3") }
            var isRtl by rememberSaveable { mutableStateOf(false) }
            var isCartouche by rememberSaveable { mutableStateOf(false) }
            var glyphSize by rememberSaveable { mutableStateOf(64f) }
            var componentBounds by remember { mutableStateOf<Rect?>(null) }
            var statusMessage by remember { mutableStateOf("") }

            val triggerExport = {
                val bounds = componentBounds
                if (bounds != null && bounds.width > 0 && bounds.height > 0 && !bounds.left.isNaN() && !bounds.top.isNaN()) {
                    val sanitized = inputText.take(15)
                        .replace(":", "_")
                        .replace("*", "_")
                        .replace("-", "_")
                    exportImage(
                        x = bounds.left,
                        y = bounds.top,
                        width = bounds.width,
                        height = bounds.height,
                        fileName = "hiero-$sanitized.png"
                    )
                    statusMessage = "Exporting PNG..."
                } else {
                    statusMessage = "Error: Invalid preview layout bounds"
                }
            }

            val famousNames = remember {
                listOf(
                    FamousName("Tutankhamun", "M17-Y5:N35-G43B-S34", "King Tutankhamun", true),
                    FamousName("Neferhotep", "F35-R4:X1*Q3", "Neferhotep (nfrḥtp)", false),
                    FamousName("Ptah", "Q3:X1-V28-G7", "God Ptah (ptḥ)", false),
                    FamousName("Inpu (Anubis)", "M17-N35:Q3-C7", "God Anubis (inpw)", false),
                    FamousName("Scriba", "Y3-A1", "Scribe (sš)", false),
                    FamousName("Pane", "X1:X2", "Bread (t)", false),
                    FamousName("Birra", "V28-N35:N29-X1:W22", "Beer (ḥnqt)", false),
                    FamousName("Vino", "M17-D21:Q3-W22:Z2", "Wine (irp)", false),
                    FamousName("Acqua", "N35:N35:N35", "Water (mw)", false)
                )
            }

            LaunchedEffect(statusMessage) {
                if (statusMessage.isNotEmpty()) {
                    delay(3000)
                    statusMessage = ""
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .background(MaterialTheme.colorScheme.background)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                Brush.horizontalGradient(
                                    listOf(
                                        Color(0xFF6D5E0F),
                                        Color(0xFFAC9852),
                                        Color(0xFF6D5E0F)
                                    )
                                )
                            )
                            .padding(horizontal = 24.dp, vertical = 16.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    text = "𓋹 HieroWriter 𓋹",
                                    color = Color.White,
                                    fontSize = 24.sp,
                                    fontWeight = FontWeight.Bold,
                                    fontFamily = titleFontFamily()
                                )
                                Text(
                                    text = "Ancient Egyptian Hieroglyphic MdC Compositor",
                                    color = Color.White.copy(alpha = 0.85f),
                                    fontSize = 12.sp
                                )
                            }

                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Text(
                                    text = "Dark Mode",
                                    color = Color.White,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Medium
                                )
                                Switch(
                                    checked = isDark,
                                    onCheckedChange = { darkThemeOverride = it },
                                    colors = SwitchDefaults.colors(
                                        checkedThumbColor = Color(0xFFFFF9EE),
                                        checkedTrackColor = Color(0xFF3A3000),
                                        uncheckedThumbColor = Color(0xFF6D5E0F),
                                        uncheckedTrackColor = Color(0xFFCDC6B4)
                                    )
                                )
                            }
                        }
                    }

                    Column(
                        modifier = Modifier
                            .widthIn(max = 900.dp)
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // Adaptive layout: Settings left, Preview right. Below size limit: Settings top, Preview bottom.
                        BoxWithConstraints(modifier = Modifier.fillMaxWidth()) {
                            val isWidescreen = maxWidth > 720.dp

                            if (isWidescreen) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                                ) {
                                    Box(modifier = Modifier.weight(1.1f)) {
                                        InputPanel(
                                            inputText = inputText,
                                            onInputTextChange = { inputText = it },
                                            isRtl = isRtl,
                                            onRtlChange = { isRtl = it },
                                            isCartouche = isCartouche,
                                            onCartoucheChange = { isCartouche = it },
                                            glyphSize = glyphSize,
                                            onGlyphSizeChange = { glyphSize = it }
                                        )
                                    }
                                    Box(modifier = Modifier.weight(1f)) {
                                        PreviewPanel(
                                            inputText = inputText,
                                            isRtl = isRtl,
                                            isCartouche = isCartouche,
                                            glyphSize = glyphSize,
                                            onBoundsPositioned = { componentBounds = it },
                                            onExport = triggerExport
                                        )
                                    }
                                }
                            } else {
                                Column(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalArrangement = Arrangement.spacedBy(16.dp)
                                ) {
                                    InputPanel(
                                        inputText = inputText,
                                        onInputTextChange = { inputText = it },
                                        isRtl = isRtl,
                                        onRtlChange = { isRtl = it },
                                        isCartouche = isCartouche,
                                        onCartoucheChange = { isCartouche = it },
                                        glyphSize = glyphSize,
                                        onGlyphSizeChange = { glyphSize = it }
                                    )
                                    PreviewPanel(
                                        inputText = inputText,
                                        isRtl = isRtl,
                                        isCartouche = isCartouche,
                                        glyphSize = glyphSize,
                                        onBoundsPositioned = { componentBounds = it },
                                        onExport = triggerExport
                                    )
                                }
                            }
                        }

                        HelpPanel()

                        ExamplesPanel(
                            famousNames = famousNames,
                            onApply = { mdc, royal ->
                                inputText = mdc
                                isCartouche = royal
                                statusMessage = "Loaded: $mdc"
                            }
                        )
                    }
                }

                // Toast overlay
                if (statusMessage.isNotEmpty()) {
                    Box(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(bottom = 32.dp)
                            .background(
                                Brush.horizontalGradient(
                                    listOf(Color(0xFF6D5E0F), Color(0xFFAC9852))
                                ),
                                shape = RoundedCornerShape(24.dp)
                            )
                            .padding(horizontal = 24.dp, vertical = 12.dp)
                    ) {
                        Text(
                            text = statusMessage,
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun InputPanel(
    inputText: String,
    onInputTextChange: (String) -> Unit,
    isRtl: Boolean,
    onRtlChange: (Boolean) -> Unit,
    isCartouche: Boolean,
    onCartoucheChange: (Boolean) -> Unit,
    glyphSize: Float,
    onGlyphSizeChange: (Float) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
        ),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Compositor Settings",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )

            OutlinedTextField(
                value = inputText,
                onValueChange = onInputTextChange,
                label = { Text("Manuel de Codage (MdC) Code") },
                placeholder = { Text("e.g. F35-R4:X1*Q3") },
                modifier = Modifier.fillMaxWidth(),
                trailingIcon = {
                    if (inputText.isNotEmpty()) {
                        IconButton(onClick = { onInputTextChange("") }) {
                            Text("✕", fontWeight = FontWeight.Bold)
                        }
                    }
                },
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.outline
                )
            )

            // Direction and Cartouche
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // RTL Checkbox Card
                Card(
                    modifier = Modifier
                        .weight(1f)
                        .clickable { onRtlChange(!isRtl) },
                    colors = CardDefaults.cardColors(
                        containerColor = if (isRtl) MaterialTheme.colorScheme.primaryContainer else Color.Transparent
                    ),
                    border = BorderStroke(
                        1.dp,
                        if (isRtl) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outlineVariant
                    )
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Checkbox(
                            checked = isRtl,
                            onCheckedChange = onRtlChange,
                            colors = CheckboxDefaults.colors(checkedColor = MaterialTheme.colorScheme.primary)
                        )
                        Spacer(Modifier.width(4.dp))
                        Text(
                            text = "Right-to-Left (RTL)",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Medium,
                            color = if (isRtl) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSurface
                        )
                    }
                }

                // Cartouche Checkbox Card
                Card(
                    modifier = Modifier
                        .weight(1f)
                        .clickable { onCartoucheChange(!isCartouche) },
                    colors = CardDefaults.cardColors(
                        containerColor = if (isCartouche) MaterialTheme.colorScheme.primaryContainer else Color.Transparent
                    ),
                    border = BorderStroke(
                        1.dp,
                        if (isCartouche) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outlineVariant
                    )
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Checkbox(
                            checked = isCartouche,
                            onCheckedChange = onCartoucheChange,
                            colors = CheckboxDefaults.colors(checkedColor = MaterialTheme.colorScheme.primary)
                        )
                        Spacer(Modifier.width(4.dp))
                        Text(
                            text = "Enclose Cartouche",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Medium,
                            color = if (isCartouche) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }

            // Glyph size slider
            Column(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Glyph Dimension",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Medium
                    )
                    Text(
                        text = "${glyphSize.toInt()} dp",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
                Slider(
                    value = glyphSize,
                    onValueChange = onGlyphSizeChange,
                    valueRange = 32f..128f,
                    colors = SliderDefaults.colors(
                        thumbColor = MaterialTheme.colorScheme.primary,
                        activeTrackColor = MaterialTheme.colorScheme.primary
                    )
                )
            }
        }
    }
}

@Composable
private fun PreviewPanel(
    inputText: String,
    isRtl: Boolean,
    isCartouche: Boolean,
    glyphSize: Float,
    onBoundsPositioned: (Rect) -> Unit,
    onExport: () -> Unit
) {
    // Papyrus light theme styling is forced for the preview canvas so that the black tint hieroglyphs are always readable and look like standard parchment documents.
    val tabletBg = Color(0xFFFFF1D6)
    val tabletBorder = Color(0xFFCBB285)

    Card(
        modifier = Modifier.fillMaxWidth(),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Tablet Preview",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            // Stone/Papyrus Canvas
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 180.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(tabletBg)
                    .border(BorderStroke(2.dp, tabletBorder), RoundedCornerShape(12.dp))
                    .padding(24.dp),
                contentAlignment = Alignment.Center
            ) {
                if (inputText.trim().isEmpty()) {
                    Text(
                        text = "Enter a valid code to render hieroglyphs\n(e.g., F35-R4:X1*Q3)",
                        textAlign = TextAlign.Center,
                        color = Color.Black.copy(alpha = 0.5f),
                        fontSize = 14.sp
                    )
                } else {
                    // Exportable container
                    Box(
                        modifier = Modifier
                            .background(Color.Transparent)
                            .padding(8.dp)
                            .onGloballyPositioned { layoutCoordinates ->
                                onBoundsPositioned(layoutCoordinates.boundsInWindow())
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        MdCWord(
                            word = inputText.trim(),
                            rtl = isRtl,
                            cart = isCartouche,
                            size = glyphSize.dp,
                            tint = Color.Black // Force black tint in preview
                        )
                    }
                }
            }

            Button(
                onClick = onExport,
                enabled = inputText.trim().isNotEmpty(),
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                ),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("Export as PNG", fontWeight = FontWeight.Bold, fontSize = 15.sp)
            }
        }
    }
}

@Composable
private fun ExamplesPanel(
    famousNames: List<FamousName>,
    onApply: (String, Boolean) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "HieroWriter Examples",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )

            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                val chunked = famousNames.chunked(3)
                chunked.forEach { rowItems ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        rowItems.forEach { name ->
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.2f))
                                    .border(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.6f), RoundedCornerShape(8.dp))
                                    .clickable {
                                        onApply(name.mdcCode, name.isRoyal)
                                    }
                                    .padding(vertical = 12.dp, horizontal = 4.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Text(
                                        text = name.label,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 13.sp,
                                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                                        textAlign = TextAlign.Center,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                    Spacer(Modifier.height(2.dp))
                                    Text(
                                        text = name.translation,
                                        fontSize = 9.sp,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
                                        textAlign = TextAlign.Center,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                }
                            }
                        }
                        if (rowItems.size < 3) {
                            repeat(3 - rowItems.size) {
                                Spacer(modifier = Modifier.weight(1f))
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun HelpPanel() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(
                text = "Quick MdC Syntax Guide",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )

            HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)

            Text(
                text = "Hieroglyphic blocks are composed using symbols and layout codes:",
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("•", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                Text(
                    text = "A - B : Joins blocks horizontally (left to right)",
                    fontSize = 12.sp,
                    fontFamily = FontFamily.SansSerif
                )
            }

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("•", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                Text(
                    text = "A : B : Arranges blocks vertically (top to bottom)",
                    fontSize = 12.sp,
                    fontFamily = FontFamily.SansSerif
                )
            }

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("•", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                Text(
                    text = "A * B : Places symbols side by side inside a vertical group",
                    fontSize = 12.sp,
                    fontFamily = FontFamily.SansSerif
                )
            }

            Text(
                text = "Example: F35-R4:X1*Q3 renders F35, followed by R4 stacked on top of the pair X1 and Q3.",
                fontSize = 11.sp,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Medium
            )
        }
    }
}