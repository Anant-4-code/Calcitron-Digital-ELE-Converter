package com.calcitron.ui.components

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.calcitron.NumberBase
import com.calcitron.ui.theme.*
import com.calcitron.ui.viewmodel.ConverterViewModel
import kotlinx.coroutines.delay
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Share
import androidx.compose.foundation.clickable
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.ui.graphics.graphicsLayer
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.ui.text.TextStyle
import kotlin.math.pow
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.border
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.size
import androidx.compose.ui.res.painterResource
import com.calcitron.R
import androidx.compose.foundation.Image
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalClipboardManager
import android.content.Intent
import android.widget.Toast
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.ExperimentalFoundationApi

@Composable
private fun AboutDialog(onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                "About Calcitron",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth()
            )
        },
        text = {
            Column(
                modifier = Modifier
                    .padding(top = 8.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // About Section
                Text(
                    "About",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    "Developed by: Anant S. Rai",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.Medium
                    )
                )
                Text(
                    "Email: anantrai0809@gmail.com",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    "Version: 1.0.0",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    "A modern number system converter app for Android",
                    style = MaterialTheme.typography.bodyMedium
                )

                Divider(
                    modifier = Modifier.padding(vertical = 8.dp),
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f)
                )

                // Privacy Policy Section
                Text(
                    "Privacy Policy",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    "Last updated: ${java.time.LocalDate.now()}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
                Spacer(modifier = Modifier.height(4.dp))
                
                Text(
                    "Data Collection",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    "Calcitron does not collect, store, or transmit any personal data. All calculations are performed locally on your device.",
                    style = MaterialTheme.typography.bodyMedium
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                Text(
                    "Clipboard Access",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    "The app only accesses your clipboard when you explicitly use the paste feature. Clipboard data is only used for number conversion and is not stored or transmitted.",
                    style = MaterialTheme.typography.bodyMedium
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                Text(
                    "Third-Party Services",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    "This app does not use any third-party services or analytics tools.",
                    style = MaterialTheme.typography.bodyMedium
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                Text(
                    "Updates",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    "This privacy policy may be updated from time to time. Any changes will be reflected in the 'Last updated' date.",
                    style = MaterialTheme.typography.bodyMedium
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                Text(
                    "Contact",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    "If you have any questions about this Privacy Policy, please contact us at anantrai0809@gmail.com",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = onDismiss,
                colors = ButtonDefaults.textButtonColors(
                    contentColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Text("Close", fontWeight = FontWeight.Medium)
            }
        },
        containerColor = MaterialTheme.colorScheme.surface,
        titleContentColor = MaterialTheme.colorScheme.primary,
        textContentColor = MaterialTheme.colorScheme.onSurface,
        shape = RoundedCornerShape(16.dp),
        tonalElevation = 8.dp,
        modifier = Modifier.heightIn(max = 600.dp)
    )
}

@Composable
private fun BlinkingCursor(color: Color) {
    val alpha by rememberInfiniteTransition().animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(500),
            repeatMode = RepeatMode.Reverse
        )
    )
    
    Text(
        text = "|",
        color = color.copy(alpha = alpha),
        fontSize = 20.sp,
        modifier = Modifier.padding(horizontal = 2.dp)
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun NumberSystemConverter() {
    val viewModel: ConverterViewModel = viewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()
    var lastBase by remember { mutableStateOf(state.currentBase) }
    var isKeyboardVisible by remember { mutableStateOf(true) }
    var showAboutDialog by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val clipboardManager = LocalClipboardManager.current
    
    // Color transition based on number system
    val baseChangeTransition = updateTransition(state.currentBase, label = "baseChange")
    val themeColor by baseChangeTransition.animateColor(
        label = "themeColor",
        transitionSpec = { spring(stiffness = Spring.StiffnessLow) }
    ) { base ->
        when (base) {
            NumberBase.BINARY -> Color(0xFF00BFA5)    // Teal
            NumberBase.DECIMAL -> Color(0xFF2979FF)   // Blue
            NumberBase.OCTAL -> Color(0xFFFF6D00)     // Orange
            NumberBase.HEXADECIMAL -> Color(0xFFD500F9) // Purple
        }
    }

    val surfaceColor by baseChangeTransition.animateColor(
        label = "surfaceColor",
        transitionSpec = { spring(stiffness = Spring.StiffnessLow) }
    ) { base ->
        when (base) {
            NumberBase.BINARY -> Color(0xFF00BFA5).copy(alpha = 0.1f)
            NumberBase.DECIMAL -> Color(0xFF2979FF).copy(alpha = 0.1f)
            NumberBase.OCTAL -> Color(0xFFFF6D00).copy(alpha = 0.1f)
            NumberBase.HEXADECIMAL -> Color(0xFFD500F9).copy(alpha = 0.1f)
        }
    }
    
    // Handle back button press
    BackHandler(enabled = isKeyboardVisible) {
        isKeyboardVisible = false
    }

    if (showAboutDialog) {
        AboutDialog(onDismiss = { showAboutDialog = false })
    }
    
    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.background,
                        surfaceColor
                    )
                )
            )
    ) {
        val isLandscape = maxWidth > maxHeight

        if (isLandscape) {
            // Landscape layout
            Row(modifier = Modifier.fillMaxSize()) {
                // Content area
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .padding(16.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .verticalScroll(rememberScrollState())
                    ) {
                        // Title and Logo Row
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 24.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Number System Converter",
                                style = MaterialTheme.typography.headlineMedium,
                                fontWeight = FontWeight.Bold,
                                color = themeColor,
                                modifier = Modifier.weight(1f)
                            )
                            
                            IconButton(
                                onClick = { showAboutDialog = true },
                                modifier = Modifier.padding(start = 8.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Info,
                                    contentDescription = "About",
                                    tint = themeColor
                                )
                            }
                        }

                        // Input Field with keyboard toggle
                        OutlinedTextField(
                            value = state.input,
                            onValueChange = { },
                            label = { 
                                Text(
                                    "Enter ${state.currentBase.name.lowercase()} number",
                                    color = if (state.isError) MaterialTheme.colorScheme.error else themeColor
                                )
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(4.dp))
                                .background(surfaceColor)
                                .combinedClickable(
                                    onClick = { isKeyboardVisible = true },
                                    onLongClick = {
                                        clipboardManager.getText()?.let { clipboardText ->
                                            // Only accept valid characters for the current base
                                            val validText = when (state.currentBase) {
                                                NumberBase.BINARY -> clipboardText.text.filter { c -> c in "01" }
                                                NumberBase.OCTAL -> clipboardText.text.filter { c -> c in "01234567" }
                                                NumberBase.DECIMAL -> clipboardText.text.filter { c -> c in "0123456789" }
                                                NumberBase.HEXADECIMAL -> clipboardText.text.filter { c -> 
                                                    c.uppercaseChar() in "0123456789ABCDEF" 
                                                }
                                            }
                                            if (validText.isNotEmpty()) {
                                                viewModel.onInputChange(validText)
                                                Toast.makeText(
                                                    context,
                                                    "Text pasted and filtered for ${state.currentBase.name.lowercase()} format",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                            } else {
                                                Toast.makeText(
                                                    context,
                                                    "No valid ${state.currentBase.name.lowercase()} numbers in clipboard",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                            }
                                        }
                                    }
                                ),
                            shape = RoundedCornerShape(4.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = if (state.isError) MaterialTheme.colorScheme.error else themeColor,
                                unfocusedBorderColor = if (state.isError) MaterialTheme.colorScheme.error.copy(alpha = 0.5f) else themeColor.copy(alpha = 0.5f),
                                cursorColor = themeColor,
                                focusedTextColor = MaterialTheme.colorScheme.onSurface,
                                unfocusedTextColor = MaterialTheme.colorScheme.onSurface
                            ),
                            textStyle = MaterialTheme.typography.bodyLarge.copy(textAlign = TextAlign.End),
                            readOnly = true,
                            isError = state.isError,
                            supportingText = if (state.isError) {
                                { Text(state.errorMessage, color = MaterialTheme.colorScheme.error) }
                            } else null,
                            trailingIcon = {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    if (isKeyboardVisible) {
                                        BlinkingCursor(if (state.isError) MaterialTheme.colorScheme.error else themeColor)
                                    }
                                    IconButton(
                                        onClick = { isKeyboardVisible = !isKeyboardVisible }
                                    ) {
                                        Icon(
                                            imageVector = if (isKeyboardVisible) Icons.Default.KeyboardArrowDown else Icons.Default.KeyboardArrowUp,
                                            contentDescription = if (isKeyboardVisible) "Hide Keyboard" else "Show Keyboard",
                                            tint = themeColor
                                        )
                                    }
                                }
                            }
                        )

                        // Base Selection
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 16.dp),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            NumberBase.values().forEach { base ->
                                var isPressed by remember { mutableStateOf(false) }
                                val scale by animateFloatAsState(
                                    targetValue = if (isPressed) 0.95f else 1f,
                                    animationSpec = spring(
                                        dampingRatio = Spring.DampingRatioMediumBouncy,
                                        stiffness = Spring.StiffnessLow
                                    )
                                )

                                val baseColor = when (base) {
                                    NumberBase.BINARY -> Color(0xFF00BFA5)
                                    NumberBase.DECIMAL -> Color(0xFF2979FF)
                                    NumberBase.OCTAL -> Color(0xFFFF6D00)
                                    NumberBase.HEXADECIMAL -> Color(0xFFD500F9)
                                }
                                
                                OutlinedButton(
                                    onClick = { 
                                        isPressed = true
                                        viewModel.onBaseChange(base)
                                    },
                                    modifier = Modifier.scale(scale),
                                    shape = RoundedCornerShape(4.dp),
                                    colors = ButtonDefaults.outlinedButtonColors(
                                        containerColor = if (base == state.currentBase) 
                                            baseColor.copy(alpha = 0.2f)
                                        else 
                                            MaterialTheme.colorScheme.surface,
                                        contentColor = if (base == state.currentBase)
                                            baseColor
                                        else
                                            MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                                    ),
                                    border = ButtonDefaults.outlinedButtonBorder.copy(
                                        width = if (base == state.currentBase) 2.dp else 1.dp
                                    )
                                ) {
                                    Text(
                                        text = base.name.substring(0, 3),
                                        fontWeight = if (base == state.currentBase) FontWeight.Bold else FontWeight.Normal
                                    )
                                }
                                
                                LaunchedEffect(isPressed) {
                                    if (isPressed) {
                                        delay(100)
                                        isPressed = false
                                    }
                                }
                            }
                        }

                        // Conversion Results
                        AnimatedVisibility(
                            visible = state.conversions.isNotEmpty(),
                            enter = fadeIn() + expandVertically(),
                            exit = fadeOut() + shrinkVertically()
                        ) {
                            Column {
                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 8.dp),
                                    shape = RoundedCornerShape(16.dp),
                                    colors = CardDefaults.cardColors(
                                        containerColor = surfaceColor
                                    )
                                ) {
                                    Column(
                                        modifier = Modifier
                                            .padding(16.dp)
                                            .fillMaxWidth()
                                    ) {
                                        Text(
                                            text = "Conversions",
                                            style = MaterialTheme.typography.titleMedium,
                                            color = themeColor,
                                            fontWeight = FontWeight.Bold,
                                            modifier = Modifier.padding(bottom = 8.dp)
                                        )

                                        state.conversions.forEach { (base, value) ->
                                            AnimatedVisibility(
                                                visible = true,
                                                enter = slideInHorizontally() + fadeIn(),
                                                modifier = Modifier.padding(vertical = 4.dp)
                                            ) {
                                                Row(
                                                    modifier = Modifier.fillMaxWidth(),
                                                    horizontalArrangement = Arrangement.SpaceBetween
                                                ) {
                                                    Text(
                                                        text = "${base.name}:",
                                                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                                                    )
                                                    Text(
                                                        text = value,
                                                        color = MaterialTheme.colorScheme.onSurface,
                                                        fontWeight = FontWeight.Medium
                                                    )
                                                }
                                            }
                                        }
                                    }
                                }

                                // Add detailed conversion explanations
                                state.conversions.forEach { (toBase, result) ->
                                    if (toBase != state.currentBase) {
                                        ConversionExplanation(
                                            input = state.input,
                                            fromBase = state.currentBase,
                                            toBase = toBase,
                                            result = result,
                                            modifier = Modifier.padding(top = 8.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                // Keyboard area in landscape
                AnimatedVisibility(
                    visible = isKeyboardVisible,
                    enter = slideInHorizontally(initialOffsetX = { it }),
                    exit = slideOutHorizontally(targetOffsetX = { it }),
                    modifier = Modifier
                        .width(IntrinsicSize.Min)
                        .fillMaxHeight()
                ) {
                    Surface(
                        modifier = Modifier
                            .fillMaxHeight()
                            .width(320.dp), // Fixed width for landscape keyboard
                        color = surfaceColor,
                        shape = RoundedCornerShape(4.dp),
                        tonalElevation = 8.dp,
                        shadowElevation = 16.dp
                    ) {
                        CalculatorKeyboard(
                            currentBase = state.currentBase,
                            onKeyPress = { key -> viewModel.onInputChange(state.input + key) },
                            onDelete = { viewModel.onDelete() },
                            onClear = { viewModel.onClear() },
                            themeColor = themeColor
                        )
                    }
                }
            }
        } else {
            // Portrait layout (existing)
            Box(modifier = Modifier.fillMaxSize()) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .padding(bottom = if (isKeyboardVisible) 240.dp else 80.dp)
                        .verticalScroll(rememberScrollState())
                ) {
                    // Title and Logo Row
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 24.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Number System Converter",
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold,
                            color = themeColor,
                            modifier = Modifier.weight(1f)
                        )
                        
                        IconButton(
                            onClick = { showAboutDialog = true },
                            modifier = Modifier.padding(start = 8.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Info,
                                contentDescription = "About",
                                tint = themeColor
                            )
                        }
                    }

                    // Input Field with keyboard toggle
                    OutlinedTextField(
                        value = state.input,
                        onValueChange = { },
                        label = { 
                            Text(
                                "Enter ${state.currentBase.name.lowercase()} number",
                                color = if (state.isError) MaterialTheme.colorScheme.error else themeColor
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(4.dp))
                            .background(surfaceColor)
                            .combinedClickable(
                                onClick = { isKeyboardVisible = true },
                                onLongClick = {
                                    clipboardManager.getText()?.let { clipboardText ->
                                        // Only accept valid characters for the current base
                                        val validText = when (state.currentBase) {
                                            NumberBase.BINARY -> clipboardText.text.filter { c -> c in "01" }
                                            NumberBase.OCTAL -> clipboardText.text.filter { c -> c in "01234567" }
                                            NumberBase.DECIMAL -> clipboardText.text.filter { c -> c in "0123456789" }
                                            NumberBase.HEXADECIMAL -> clipboardText.text.filter { c -> 
                                                c.uppercaseChar() in "0123456789ABCDEF" 
                                            }
                                        }
                                        if (validText.isNotEmpty()) {
                                            viewModel.onInputChange(validText)
                                            Toast.makeText(
                                                context,
                                                "Text pasted and filtered for ${state.currentBase.name.lowercase()} format",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        } else {
                                            Toast.makeText(
                                                context,
                                                "No valid ${state.currentBase.name.lowercase()} numbers in clipboard",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }
                                    }
                                }
                            ),
                        shape = RoundedCornerShape(4.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = if (state.isError) MaterialTheme.colorScheme.error else themeColor,
                            unfocusedBorderColor = if (state.isError) MaterialTheme.colorScheme.error.copy(alpha = 0.5f) else themeColor.copy(alpha = 0.5f),
                            cursorColor = themeColor,
                            focusedTextColor = MaterialTheme.colorScheme.onSurface,
                            unfocusedTextColor = MaterialTheme.colorScheme.onSurface
                        ),
                        textStyle = MaterialTheme.typography.bodyLarge.copy(textAlign = TextAlign.End),
                        readOnly = true,
                        isError = state.isError,
                        supportingText = if (state.isError) {
                            { Text(state.errorMessage, color = MaterialTheme.colorScheme.error) }
                        } else null,
                        trailingIcon = {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                if (isKeyboardVisible) {
                                    BlinkingCursor(if (state.isError) MaterialTheme.colorScheme.error else themeColor)
                                }
                                IconButton(
                                    onClick = { isKeyboardVisible = !isKeyboardVisible }
                                ) {
                                    Icon(
                                        imageVector = if (isKeyboardVisible) Icons.Default.KeyboardArrowDown else Icons.Default.KeyboardArrowUp,
                                        contentDescription = if (isKeyboardVisible) "Hide Keyboard" else "Show Keyboard",
                                        tint = themeColor
                                    )
                                }
                            }
                        }
                    )

                    // Base Selection with animations
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        NumberBase.values().forEach { base ->
                            var isPressed by remember { mutableStateOf(false) }
                            val scale by animateFloatAsState(
                                targetValue = if (isPressed) 0.95f else 1f,
                                animationSpec = spring(
                                    dampingRatio = Spring.DampingRatioMediumBouncy,
                                    stiffness = Spring.StiffnessLow
                                )
                            )

                            val baseColor = when (base) {
                                NumberBase.BINARY -> Color(0xFF00BFA5)
                                NumberBase.DECIMAL -> Color(0xFF2979FF)
                                NumberBase.OCTAL -> Color(0xFFFF6D00)
                                NumberBase.HEXADECIMAL -> Color(0xFFD500F9)
                            }
                            
                            OutlinedButton(
                                onClick = { 
                                    isPressed = true
                                    viewModel.onBaseChange(base)
                                },
                                modifier = Modifier.scale(scale),
                                shape = RoundedCornerShape(4.dp),
                                colors = ButtonDefaults.outlinedButtonColors(
                                    containerColor = if (base == state.currentBase) 
                                        baseColor.copy(alpha = 0.2f)
                                    else 
                                        MaterialTheme.colorScheme.surface,
                                    contentColor = if (base == state.currentBase)
                                        baseColor
                                    else
                                        MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                                ),
                                border = ButtonDefaults.outlinedButtonBorder.copy(
                                    width = if (base == state.currentBase) 2.dp else 1.dp
                                )
                            ) {
                                Text(
                                    text = base.name.substring(0, 3),
                                    fontWeight = if (base == state.currentBase) FontWeight.Bold else FontWeight.Normal
                                )
                            }
                            
                            LaunchedEffect(isPressed) {
                                if (isPressed) {
                                    delay(100)
                                    isPressed = false
                                }
                            }
                        }
                    }

                    // Conversion Results with animations
                    AnimatedVisibility(
                        visible = state.conversions.isNotEmpty(),
                        enter = fadeIn() + expandVertically(),
                        exit = fadeOut() + shrinkVertically()
                    ) {
                        Column {
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp),
                                shape = RoundedCornerShape(16.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = surfaceColor
                                )
                            ) {
                                Column(
                                    modifier = Modifier
                                        .padding(16.dp)
                                        .fillMaxWidth()
                                ) {
                                    Text(
                                        text = "Conversions",
                                        style = MaterialTheme.typography.titleMedium,
                                        color = themeColor,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier.padding(bottom = 8.dp)
                                    )

                                    state.conversions.forEach { (base, value) ->
                                        AnimatedVisibility(
                                            visible = true,
                                            enter = slideInHorizontally() + fadeIn(),
                                            modifier = Modifier.padding(vertical = 4.dp)
                                        ) {
                                            Row(
                                                modifier = Modifier.fillMaxWidth(),
                                                horizontalArrangement = Arrangement.SpaceBetween
                                            ) {
                                                Text(
                                                    text = "${base.name}:",
                                                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                                                )
                                                Text(
                                                    text = value,
                                                    color = MaterialTheme.colorScheme.onSurface,
                                                    fontWeight = FontWeight.Medium
                                                )
                                            }
                                        }
                                    }
                                }
                            }

                            // Add detailed conversion explanations
                            state.conversions.forEach { (toBase, result) ->
                                if (toBase != state.currentBase) {
                                    ConversionExplanation(
                                        input = state.input,
                                        fromBase = state.currentBase,
                                        toBase = toBase,
                                        result = result,
                                        modifier = Modifier.padding(top = 8.dp)
                                    )
                                }
                            }
                        }
                    }
                }

                // Portrait keyboard with animation
                AnimatedVisibility(
                    visible = isKeyboardVisible,
                    enter = slideInVertically(initialOffsetY = { it }),
                    exit = slideOutVertically(targetOffsetY = { it }),
                    modifier = Modifier.align(Alignment.BottomCenter)
                ) {
                    Surface(
                        modifier = Modifier.fillMaxWidth(),
                        color = surfaceColor,
                        shape = RoundedCornerShape(4.dp),
                        tonalElevation = 8.dp,
                        shadowElevation = 16.dp
                    ) {
                        CalculatorKeyboard(
                            currentBase = state.currentBase,
                            onKeyPress = { key -> viewModel.onInputChange(state.input + key) },
                            onDelete = { viewModel.onDelete() },
                            onClear = { viewModel.onClear() },
                            themeColor = themeColor
                        )
                    }
                }
            }
        }

        // Base change animation
        LaunchedEffect(state.currentBase) {
            if (lastBase != state.currentBase) {
                lastBase = state.currentBase
            }
        }
    }
}

@Composable
fun CalculatorKeyboard(
    currentBase: NumberBase,
    onKeyPress: (String) -> Unit,
    onDelete: () -> Unit,
    onClear: () -> Unit,
    themeColor: Color
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(
                color = MaterialTheme.colorScheme.surface.copy(alpha = 0.95f),
                shape = RoundedCornerShape(4.dp)
            )
            .padding(8.dp)
    ) {
        // Operation buttons (Delete and Clear) at the top
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            // Delete button
            Surface(
                onClick = onDelete,
                modifier = Modifier
                    .weight(1f)
                    .height(48.dp)
                    .padding(horizontal = 4.dp),
                shape = RoundedCornerShape(4.dp),
                color = themeColor.copy(alpha = 0.15f),
                border = BorderStroke(
                    width = 1.dp,
                    color = themeColor.copy(alpha = 0.3f)
                )
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Icon(
                        imageVector = Icons.Filled.KeyboardArrowLeft,
                        contentDescription = "Delete",
                        modifier = Modifier.size(24.dp),
                        tint = themeColor
                    )
                }
            }

            // Clear button
            Surface(
                onClick = onClear,
                modifier = Modifier
                    .weight(1f)
                    .height(48.dp)
                    .padding(horizontal = 4.dp),
                shape = RoundedCornerShape(4.dp),
                color = themeColor.copy(alpha = 0.15f),
                border = BorderStroke(
                    width = 1.dp,
                    color = themeColor.copy(alpha = 0.3f)
                )
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Text(
                        text = "C",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = themeColor
                    )
                }
            }
        }

        // Number keys grid
        val rows = when (currentBase) {
            NumberBase.BINARY -> listOf(
                listOf("1"),
                listOf("0")
            )
            NumberBase.OCTAL -> listOf(
                listOf("7", "8", "9"),
                listOf("4", "5", "6"),
                listOf("1", "2", "3"),
                listOf("0")
            )
            NumberBase.DECIMAL -> listOf(
                listOf("7", "8", "9"),
                listOf("4", "5", "6"),
                listOf("1", "2", "3"),
                listOf("0")
            )
            NumberBase.HEXADECIMAL -> listOf(
                listOf("7", "8", "9"),
                listOf("4", "5", "6"),
                listOf("1", "2", "3"),
                listOf("A", "B", "C"),
                listOf("D", "E", "F"),
                listOf("0")
            )
        }

        rows.forEach { row ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 2.dp),
                horizontalArrangement = if (row.size < 3) Arrangement.Center else Arrangement.SpaceEvenly
            ) {
                row.forEach { key ->
                    var isPressed by remember { mutableStateOf(false) }
                    val scale by animateFloatAsState(
                        targetValue = if (isPressed) 0.95f else 1f,
                        animationSpec = spring(
                            dampingRatio = Spring.DampingRatioMediumBouncy,
                            stiffness = Spring.StiffnessLow
                        )
                    )

                    Surface(
                        onClick = {
                            isPressed = true
                            onKeyPress(key)
                        },
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp)
                            .padding(horizontal = 4.dp)
                            .scale(scale),
                        shape = RoundedCornerShape(4.dp),
                        color = themeColor.copy(alpha = 0.1f),
                        border = BorderStroke(
                            width = 1.dp,
                            color = themeColor.copy(alpha = 0.3f)
                        )
                    ) {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            Text(
                                text = key,
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold,
                                color = themeColor
                            )
                        }
                        }

                    LaunchedEffect(isPressed) {
                        if (isPressed) {
                            delay(100)
                            isPressed = false
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ConversionExplanation(
    input: String,
    fromBase: NumberBase,
    toBase: NumberBase,
    result: String,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val clipboardManager = LocalClipboardManager.current

    // Create formatted explanation text
    val explanationText = buildString {
        appendLine("Converting (${input})${getSubscript(fromBase)} to ${toBase.name.lowercase()} system")
        appendLine()
        
        when {
            fromBase != NumberBase.DECIMAL && toBase != fromBase -> {
                appendLine("Step 1: Convert to decimal")
                appendLine("For conversion of ($input) to decimal:")
                
                val decimalResult = calculateToDecimal(input, fromBase)
                decimalResult.steps.forEach { step ->
                    appendLine(step)
                }
                
                if (toBase != NumberBase.DECIMAL) {
                    appendLine()
                    appendLine("Step 2: Convert (${decimalResult.result})₁₀ to ${toBase.name.lowercase()}")
                    appendLine("Dividing by ${getBaseNumber(toBase)} repeatedly:")
                    
                    val finalSteps = calculateFromDecimal(decimalResult.result, toBase)
                    finalSteps.forEach { step ->
                        appendLine(step)
                    }
                }
            }
            fromBase == NumberBase.DECIMAL -> {
                appendLine("Converting decimal $input to ${toBase.name.lowercase()}:")
                appendLine("Dividing by ${getBaseNumber(toBase)} repeatedly:")
                
                val steps = calculateFromDecimal(input, toBase)
                steps.forEach { step ->
                    appendLine(step)
                }
            }
        }
        
        appendLine()
        appendLine("Final Result:")
        appendLine("(${input})${getSubscript(fromBase)} = ($result)${getSubscript(toBase)}")
    }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            // Title row with actions
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Step by Step Conversion",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.weight(1f)
                )
                
                // Action buttons
                Row(
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(start = 8.dp)
                ) {
                    // Copy button
                    IconButton(
                        onClick = {
                            clipboardManager.setText(AnnotatedString(explanationText))
                            Toast.makeText(context, "Explanation copied to clipboard", Toast.LENGTH_SHORT).show()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.ContentCopy,
                            contentDescription = "Copy explanation",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                    
                    // Share button
                    IconButton(
                        onClick = {
                            val sendIntent = Intent().apply {
                                action = Intent.ACTION_SEND
                                putExtra(Intent.EXTRA_TEXT, explanationText)
                                type = "text/plain"
                            }
                            val shareIntent = Intent.createChooser(sendIntent, "Share Conversion Steps")
                            context.startActivity(shareIntent)
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Share,
                            contentDescription = "Share explanation",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }

            // Explanation content
            when {
                fromBase != NumberBase.DECIMAL && toBase != fromBase -> {
                    Text(
                        text = "Step 1: Convert (${input})${getSubscript(fromBase)} to decimal system",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "For conversion of ($input) to the decimal number system, " +
                              "each coefficient is multiplied by the corresponding power " +
                              "of ${getBaseNumber(fromBase)} and added to obtain the decimal number.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
                    )
                    
                    val decimalResult = calculateToDecimal(input, fromBase)
                    decimalResult.steps.forEach { step ->
                        Text(
                            text = step,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.padding(vertical = 4.dp)
                        )
                    }

                    if (toBase != NumberBase.DECIMAL) {
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "Step 2: Convert (${decimalResult.result})${getSubscript(NumberBase.DECIMAL)} to ${toBase.name.lowercase()} system",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        
                        Spacer(modifier = Modifier.height(8.dp))
                        
                        Text(
                            text = "To convert a decimal number integer part (${decimalResult.result}) into " +
                                  "${toBase.name.lowercase()}, we divide the number by ${getBaseNumber(toBase)} and record the " +
                                  "remainder. Then divide the result of this division again by ${getBaseNumber(toBase)} " +
                                  "and so on until the result of the division is zero.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
                        )
                        
                        val finalSteps = calculateFromDecimal(decimalResult.result, toBase)
                        finalSteps.forEach { step ->
                            Text(
                                text = step,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurface,
                                modifier = Modifier.padding(vertical = 2.dp)
                            )
                        }
                    }
                }
                fromBase == NumberBase.DECIMAL -> {
                    Text(
                        text = "To convert a decimal number integer part ($input) into " +
                              "${toBase.name.lowercase()}, we divide the number by ${getBaseNumber(toBase)} and record the " +
                              "remainder. Then divide the result of this division again by ${getBaseNumber(toBase)} " +
                              "and so on until the result of the division is zero.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
                    )
                    
                    val steps = calculateFromDecimal(input, toBase)
                    steps.forEach { step ->
                        Text(
                            text = step,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.padding(vertical = 2.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "The complete result is:",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = "(${input})${getSubscript(fromBase)} = ($result)${getSubscript(toBase)}",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}

private fun getBaseNumber(base: NumberBase): Int {
    return when (base) {
        NumberBase.BINARY -> 2
        NumberBase.OCTAL -> 8
        NumberBase.DECIMAL -> 10
        NumberBase.HEXADECIMAL -> 16
    }
}

private fun getSubscript(base: NumberBase): String {
    return when (base) {
        NumberBase.BINARY -> "₂"
        NumberBase.OCTAL -> "₈"
        NumberBase.DECIMAL -> "₁₀"
        NumberBase.HEXADECIMAL -> "₁₆"
    }
}

private data class DecimalResult(
    val result: String,
    val steps: List<String>
)

private fun calculateToDecimal(input: String, fromBase: NumberBase): DecimalResult {
    val base = getBaseNumber(fromBase)
    val steps = mutableListOf<String>()
    var sum = 0
    val digits = input.reversed()
    val calculations = mutableListOf<String>()
    
    // Add a function to convert numbers to superscript
    fun toSuperscript(number: Int): String {
        return number.toString().map { digit ->
            when (digit) {
                '0' -> '⁰'
                '1' -> '¹'
                '2' -> '²'
                '3' -> '³'
                '4' -> '⁴'
                '5' -> '⁵'
                '6' -> '⁶'
                '7' -> '⁷'
                '8' -> '⁸'
                '9' -> '⁹'
                else -> digit
            }
        }.joinToString("")
    }
    
    digits.forEachIndexed { index, digit ->
        val value = when {
            digit.isDigit() -> digit.toString().toInt()
            else -> digit.uppercaseChar().code - 'A'.code + 10
        }
        val power = base.toDouble().pow(index).toInt()
        val termValue = value * power
        calculations.add("(${digit.uppercaseChar()}×${base}${toSuperscript(index)})")
        sum += termValue
    }
    
    steps.add("=$input")
    steps.add("=" + calculations.joinToString("+"))
    
    // Calculate intermediate sums
    val values = digits.mapIndexed { index, digit ->
        val value = when {
            digit.isDigit() -> digit.toString().toInt()
            else -> digit.uppercaseChar().code - 'A'.code + 10
        }
        value * base.toDouble().pow(index).toInt()
    }
    steps.add("=" + values.joinToString("+"))
    steps.add("=$sum")
    
    return DecimalResult(sum.toString(), steps)
}

private fun calculateFromDecimal(input: String, toBase: NumberBase): List<String> {
    val steps = mutableListOf<String>()
    var number = input.toInt()
    val base = getBaseNumber(toBase)
    
    while (number > 0) {
        val quotient = number / base
        val remainder = number % base
        val remainderStr = if (remainder < 10) remainder.toString() 
                          else ('A' + remainder - 10).toString()
        steps.add("$number ÷ $base = $quotient Remainder $remainderStr")
        number = quotient
    }
    
    steps.add("\nNow, we have to scan the remainder from the bottom. So,")
    steps.add("the integer part ${toBase.name.lowercase()} number is = ${
        steps.map { 
            it.substringAfterLast("Remainder ").trim()
        }.reversed().dropWhile { it.isEmpty() }.joinToString("")
    }")
    
    return steps
} 