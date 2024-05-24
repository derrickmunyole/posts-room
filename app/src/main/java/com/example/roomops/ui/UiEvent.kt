package com.example.roomops.ui

sealed interface UiEvent {
    data object NavigateToHome : UiEvent
}