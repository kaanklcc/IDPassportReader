package com.example.idpassportreader.model

data class MatchResponse(
      val result: String,
       val liveness_score: Double,
       val match_confidence: Double
)
