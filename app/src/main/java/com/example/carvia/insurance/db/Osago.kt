package com.example.carvia.insurance.db

import android.text.Editable

data class Osago(
    var uid:String?=null,
    var name:String?=null,
    var phone: String? =null,
    var type_auto:String?=null,
    var diagnostic_card:String?=null,
    var foreign_auto: String? =null,
    var experience: String? =null,
    var period: String? =null,
    val date_order: String?=null,
    val date_order_end: String?=null,
    var price: Float? =null,
    var id: String? =null)
