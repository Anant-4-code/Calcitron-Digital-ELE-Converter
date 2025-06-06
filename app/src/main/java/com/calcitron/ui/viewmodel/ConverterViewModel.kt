package com.calcitron.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.calcitron.NumberBase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class ConversionState(
    val input: String = "",
    val currentBase: NumberBase = NumberBase.DECIMAL,
    val conversions: Map<NumberBase, String> = emptyMap(),
    val isError: Boolean = false,
    val errorMessage: String = ""
)

class ConverterViewModel : ViewModel() {
    private val _state = MutableStateFlow(ConversionState())
    val state: StateFlow<ConversionState> = _state.asStateFlow()

    fun onBaseChange(newBase: NumberBase) {
        _state.update { currentState ->
            currentState.copy(
                currentBase = newBase,
                input = "",
                conversions = emptyMap()
            )
        }
    }

    fun onInputChange(newInput: String) {
        try {
            val currentState = _state.value
            val decimal = when (currentState.currentBase) {
                NumberBase.BINARY -> newInput.toLongOrNull(2)
                NumberBase.DECIMAL -> newInput.toLongOrNull(10)
                NumberBase.OCTAL -> newInput.toLongOrNull(8)
                NumberBase.HEXADECIMAL -> newInput.toLongOrNull(16)
            }

            if (decimal != null) {
                val conversions = NumberBase.values()
                    .filter { it != currentState.currentBase }
                    .associate { base ->
                        base to when (base) {
                            NumberBase.BINARY -> decimal.toString(2)
                            NumberBase.DECIMAL -> decimal.toString(10)
                            NumberBase.OCTAL -> decimal.toString(8)
                            NumberBase.HEXADECIMAL -> decimal.toString(16).uppercase()
                        }
                    }

                _state.update { 
                    it.copy(
                        input = newInput,
                        conversions = conversions,
                        isError = false,
                        errorMessage = ""
                    )
                }
            } else if (newInput.isEmpty()) {
                _state.update {
                    it.copy(
                        input = "",
                        conversions = emptyMap(),
                        isError = false,
                        errorMessage = ""
                    )
                }
            } else {
                _state.update {
                    it.copy(
                        input = newInput,
                        isError = true,
                        errorMessage = "Invalid number for ${currentState.currentBase.name.lowercase()} system"
                    )
                }
            }
        } catch (e: NumberFormatException) {
            _state.update {
                it.copy(
                    isError = true,
                    errorMessage = "Invalid number format"
                )
            }
        }
    }

    fun onDelete() {
        _state.value.input.let { currentInput ->
            if (currentInput.isNotEmpty()) {
                onInputChange(currentInput.dropLast(1))
            }
        }
    }

    fun onClear() {
        _state.update {
            it.copy(
                input = "",
                conversions = emptyMap(),
                isError = false,
                errorMessage = ""
            )
        }
    }
} 