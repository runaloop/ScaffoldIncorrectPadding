@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.scaffoldincorrectpadding

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldSubcomposeInMeasureFix
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.scaffoldincorrectpadding.ui.theme.ScaffoldIncorrectPaddingTheme
import kotlinx.coroutines.delay

const val listSize = 50

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        ScaffoldSubcomposeInMeasureFix = true
        setContent {
            ScaffoldIncorrectPaddingTheme {
                var isLoading by remember {
                    mutableStateOf(true)
                }
                LaunchedEffect(key1 = Unit) {
                    delay(1000)
                    isLoading = false
                }
                if (isLoading) {
                    Text(modifier = Modifier.fillMaxSize(), text = "Loading")
                } else {
                    Scaffold(
                        modifier = Modifier.fillMaxSize(),
                        topBar = { TopBar() },
                    ) { innerPadding ->
                        Content(innerPadding)
                    }
                }
            }
        }
    }

    @Composable
    private fun TopBar() {
        val barHeight = 90.dp
        val paddings = WindowInsets.systemBars
            .only(WindowInsetsSides.Top)
            .asPaddingValues()
            .calculateTopPadding()
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Color.Red.copy(.2f),
                    shape = RoundedCornerShape(bottomEnd = 16.dp, bottomStart = 16.dp)
                )
                .padding(
                    top = paddings
                )
                .height(barHeight)
        ) { Text(text = "Top Title h: $barHeight, top paddings: $paddings\n ScaffoldSubcomposeInMeasureFix: $ScaffoldSubcomposeInMeasureFix ") }
    }
}


@Composable
fun Content(paddings: PaddingValues) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        repeat(listSize) {
            item {
                when (it) {
                    0 -> {
                        Text(
                            text = "=====> #$it paddings top: ${paddings.calculateTopPadding()}",
                            modifier = Modifier
                                .padding(top = paddings.calculateTopPadding())
                                .background(nextColor())
                        )
                    }

                    else -> {
                        Text(
                            text = "Hello #$it",
                            modifier = Modifier.background(nextColor())
                        )
                    }
                }
            }
        }
    }
}

var current = 0
fun nextColor(): Color {
    val colors = listOf(
        Color(0xFFEF9A9A), // Red
        Color(0xFFCE93D8), // Purple
        Color(0xFF9FA8DA), // Indigo
        Color(0xFF90CAF9), // Blue
        Color(0xFF80DEEA), // Teal
        Color(0xFFA5D6A7), // Green
        Color(0xFFFFF59D), // Yellow
        Color(0xFFFFCC80), // Orange
        Color(0xFFBCAAA4), // Brown
        // Add more colors as needed
    )

    return colors[current++ % colors.lastIndex]
}
