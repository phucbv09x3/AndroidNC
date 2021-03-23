package com.example.androidnc.data.model


data class CoinModel(
     var id: Int,
     var name: String,
     var symbol: String,
     var slug: String,
     var quote: QuoteModel
) {
}