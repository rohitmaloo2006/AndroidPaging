package com.maloo.paggingapplication.data.model

data class Passenger(
    val __v: Int,
    val _id: String,
    val airline: List<Airline>,
    val name: String,
    val trips: Int
)