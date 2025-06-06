package com.calcitron.ui.components

import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.calcitron.NumberBase

@Composable
fun ConversionExplanation(
    input: String,
    fromBase: NumberBase,
    conversions: Map<NumberBase, String>,
    modifier: Modifier = Modifier
) {
    if (input.isNotEmpty() && !conversions.isNullOrEmpty()) {
        Card(
            modifier = modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.7f)
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = "Conversion Steps",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 12.dp)
                )

                conversions.forEach { (targetBase, result) ->
                    val explanation = buildExplanation(input, fromBase, targetBase, result)
                    Text(
                        text = explanation,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(vertical = 4.dp)
                    )
                }
            }
        }
    }
}

private fun buildExplanation(input: String, fromBase: NumberBase, toBase: NumberBase, result: String): String {
    val fromBaseValue = when (fromBase) {
        NumberBase.BINARY -> 2
        NumberBase.OCTAL -> 8
        NumberBase.DECIMAL -> 10
        NumberBase.HEXADECIMAL -> 16
    }
    
    val toBaseValue = when (toBase) {
        NumberBase.BINARY -> 2
        NumberBase.OCTAL -> 8
        NumberBase.DECIMAL -> 10
        NumberBase.HEXADECIMAL -> 16
    }

    return buildAnnotatedString {
        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
            append("${fromBase.name} → ${toBase.name}:\n")
        }
        
        when {
            // Binary to Decimal
            fromBase == NumberBase.BINARY && toBase == NumberBase.DECIMAL -> {
                val steps = input.reversed().mapIndexed { index, digit ->
                    val value = digit.toString().toInt() * (1 shl index)
                    "$digit × 2^$index = $value"
                }
                append(steps.joinToString(" + "))
                append(" = $result")
            }
            
            // Decimal to Binary
            fromBase == NumberBase.DECIMAL && toBase == NumberBase.BINARY -> {
                var num = input.toInt()
                val steps = mutableListOf<String>()
                while (num > 0) {
                    steps.add("$num ÷ 2 = ${num/2} R${num%2}")
                    num /= 2
                }
                append(steps.joinToString("\n"))
                append("\nReading remainders from bottom to top: $result")
            }
            
            // Binary to Octal
            fromBase == NumberBase.BINARY && toBase == NumberBase.OCTAL -> {
                val paddedInput = input.padStart((input.length + 2) / 3 * 3, '0')
                val groups = paddedInput.chunked(3)
                val steps = groups.map { group ->
                    "$group = ${group.toInt(2)}"
                }
                append("Group in threes: ${groups.joinToString(" ")}\n")
                append(steps.joinToString(" → "))
                append("\nResult: $result")
            }
            
            // Hexadecimal to Binary
            fromBase == NumberBase.HEXADECIMAL && toBase == NumberBase.BINARY -> {
                val steps = input.map { digit ->
                    val binary = digit.toString().toInt(16).toString(2).padStart(4, '0')
                    "$digit = $binary"
                }
                append(steps.joinToString("\n"))
                append("\nCombined: $result")
            }
            
            // General case
            else -> {
                append("Convert $input (base $fromBaseValue) to base $toBaseValue\n")
                append("Result: $result")
            }
        }
    }.toString()
} 