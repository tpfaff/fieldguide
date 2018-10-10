package com.example.tyler.myapplication

sealed class UiState() {
    class Loading : UiState()
    class ListReady(val list: List<Any>) : UiState()
    class WebPage(val url: String) : UiState()
    class Error : UiState()
}