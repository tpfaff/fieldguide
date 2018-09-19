package com.example.tyler.myapplication.overview

sealed class OverviewViewTypes{
    class Polls: OverviewViewTypes()
    class Favorability: OverviewViewTypes()
    class RegisterToVote: OverviewViewTypes()
}