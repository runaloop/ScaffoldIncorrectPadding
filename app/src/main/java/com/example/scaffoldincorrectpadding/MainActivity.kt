package com.example.scaffoldincorrectpadding

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import com.example.scaffoldincorrectpadding.ui.theme.ScaffoldIncorrectPaddingTheme
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    private fun isNightMode(context: Context): Boolean {
        val nightModeFlags =
            context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        return nightModeFlags == Configuration.UI_MODE_NIGHT_YES
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        WindowCompat.setDecorFitsSystemWindows(window, false)
        val controller = WindowCompat.getInsetsController(window, window.decorView)
        controller.isAppearanceLightStatusBars = isNightMode(this).not()
        val loadingWithScaffold = true
        setContent {
            ScaffoldIncorrectPaddingTheme {
                var loadingState by remember {
                    mutableStateOf(true)
                }
                LaunchedEffect(key1 = Unit) {
                    delay(1000)
                    loadingState = false
                }
                if (loadingState) {
                    if(loadingWithScaffold){
                        Scaffold(
                            modifier = Modifier.fillMaxSize(),
                            topBar = {}
                        ) { innerPadding ->
                            Text(modifier = Modifier
                                .fillMaxSize()
                                .padding(innerPadding), text = "Loading")

                        }
                    }else{
                        Text(modifier = Modifier
                            .fillMaxSize()
                            , text = "Loading")
                    }
                } else {
                    Scaffold(
                        modifier = Modifier.fillMaxSize(),
                        topBar = {
                            TopBar()
                        }
                    ) { innerPadding ->
                        Content(innerPadding)

                    }
                }
            }
        }
    }

    @Composable
    private fun TopBar() {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(false) {}
                .background(
                    Color.Red.copy(.2f),
                    shape = RoundedCornerShape(bottomEnd = 16.dp, bottomStart = 16.dp)
                )
                .padding(
                    top = WindowInsets.systemBars
                        .only(WindowInsetsSides.Top)
                        .asPaddingValues()
                        .calculateTopPadding() + 8.dp
                )
                .height(66.dp)
        ) {
            Text(text = "RTITLE")
        }
    }
}

@Composable
fun Content(paddings: PaddingValues) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        repeat(100) {
            item {
                val itemModifier = when (it) {
                    0 -> {
                        LaunchedEffect(key1 = paddings) {
                            println("🚒 pads: $paddings")
                        }
                        Modifier.padding(top = paddings.calculateTopPadding())
                    }

                    else -> Modifier
                }

                Text(
                    text = "Hello $it",
                    modifier = itemModifier.background(nextColor())
                )
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

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ScaffoldIncorrectPaddingTheme {
    }
}