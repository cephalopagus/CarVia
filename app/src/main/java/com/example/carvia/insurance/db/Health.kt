package com.example.carvia.insurance.db

data class Health(
    var uid:String?=null,
    var name:String?=null,
    var birth:String?=null,
    var phone: String? =null,
    var email: String? =null,
    var pasport: String? =null,
    var pasport_date: String? =null,
    var pasport_house: String? =null,
    var pasport_id: String? =null,
    var address: String? =null,
    val date_order: String?=null,
    val date_order_end: String?=null,
    var price: Float? =null,
    var id: String? =null)
