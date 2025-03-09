package com.example.survey.utils

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

abstract class StateViewModel<T: Any>(
    initialValue: T
): ViewModel() {
    private val _state = MutableStateFlow(initialValue)
    val state: StateFlow<T> = _state.asStateFlow()

    protected fun updateState(alteration: T.() -> T) {
        _state.update(alteration)
    }
}