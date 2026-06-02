package com.shadowings.hierowriter

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.shadowings.hierowriter.ui.AppTheme
import hierowriter.shared.generated.resources.Res
import hierowriter.shared.generated.resources.compose_multiplatform
import org.jetbrains.compose.resources.painterResource

@Composable
@Preview
fun App() {
    AppTheme {
        Scaffold {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .safeContentPadding(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text("Hello, HieroWriter!")
                Image(
                    painterResource(Res.drawable.compose_multiplatform),
                    contentDescription = "Multiplatform Logo"
                )
            }
        }
    }
}