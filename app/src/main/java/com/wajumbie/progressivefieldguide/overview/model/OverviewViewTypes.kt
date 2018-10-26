package com.wajumbie.progressivefieldguide.overview.model

sealed class OverviewViewTypes{
    class Polls: OverviewViewTypes()
    class Favorability: OverviewViewTypes()
    class RegisterToVote: OverviewViewTypes()
}