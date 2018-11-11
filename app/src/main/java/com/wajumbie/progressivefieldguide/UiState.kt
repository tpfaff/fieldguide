package com.wajumbie.progressivefieldguide

sealed class UiState() {
    class Loading : UiState()
    class ListReady(val list: List<Any>) : UiState()
    class WebPage(val url: String) : UiState()
    class Share(val shareText: String): UiState()
    class Error : UiState()
}