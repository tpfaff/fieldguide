package com.example.tyler.myapplication.overview.model

sealed class OverviewViewTypes{
    class Polls: OverviewViewTypes()
    class Favorability: OverviewViewTypes()
    class RegisterToVote: OverviewViewTypes()
}