package com.maloo.paggingapplication.data.model

data class PassengersResponse(
    val `data`: List<Passenger>,
    val totalPages: Int,
    val totalPassengers: Int
)