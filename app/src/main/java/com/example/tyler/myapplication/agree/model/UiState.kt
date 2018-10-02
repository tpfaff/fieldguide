package com.example.tyler.myapplication.agree.model

sealed class UiState() {
    class Loading : UiState()
    class ListReady(val list: List<PollModel>) : UiState()
    class WebPage(val url: String) : UiState()
    class Error : UiState()
}