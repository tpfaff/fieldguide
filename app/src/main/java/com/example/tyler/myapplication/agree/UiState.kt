package com.example.tyler.myapplication.agree

sealed class UiState(){
    class Loading: UiState()
    class ListReady(val list: List<PollModel?>): UiState()
    class Error: UiState()
}