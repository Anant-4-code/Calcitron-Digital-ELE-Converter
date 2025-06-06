package com.calcitron.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.calcitron.NumberBase
import kotlinx.coroutines.delay

@Composable
fun CalculatorKeyboard(
    currentBase: NumberBase,
    onKeyPress: (String) -> Unit,
    onDelete: () -> Unit,
    onClear: () -> Unit
) {
    val keys = remember(currentBase) {
        when (currentBase) {
            NumberBase.BINARY -> listOf("0", "1")
            NumberBase.OCTAL -> (0..7).map { it.toString() }
            NumberBase.DECIMAL -> (0..9).map { it.toString() }
            NumberBase.HEXADECIMAL -> (0..9).map { it.toString() } + ('A'..'F').map { it.toString() }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        // Grid of number keys
        keys.chunked(4).forEach { rowKeys ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                rowKeys.forEach { key ->
                    AnimatedKeyButton(
                        text = key,
                        onClick = { onKeyPress(key) },
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = 6.dp)
                            .height(64.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.secondaryContainer,
                            contentColor = MaterialTheme.colorScheme.onSecondaryContainer
                        )
                    )
                }
                // Fill empty spaces in the last row if needed
                repeat(4 - rowKeys.size) {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }

        // Control buttons row
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 6.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            AnimatedKeyButton(
                text = "DEL",
                onClick = onDelete,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.errorContainer,
                    contentColor = MaterialTheme.colorScheme.onErrorContainer
                ),
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 6.dp)
                    .height(64.dp)
            )
            
            AnimatedKeyButton(
                text = "CLR",
                onClick = onClear,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.errorContainer,
                    contentColor = MaterialTheme.colorScheme.onErrorContainer
                ),
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 6.dp)
                    .height(64.dp)
            )
        }
    }
}

@Composable
private fun AnimatedKeyButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    colors: ButtonColors = ButtonDefaults.buttonColors()
) {
    var isPressed by remember { mutableStateOf(false) }
    
    // Scale animation
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.92f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        )
    )

    // Elevation animation
    val elevation by animateDpAsState(
        targetValue = if (isPressed) 2.dp else 6.dp,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        )
    )

    Button(
        onClick = {
            isPressed = true
            onClick()
        },
        modifier = modifier.scale(scale),
        colors = colors,
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = elevation,
            pressedElevation = 2.dp
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Text(
            text = text,
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp
        )
    }

    LaunchedEffect(isPressed) {
        if (isPressed) {
            delay(100)
            isPressed = false
        }
    }
} 