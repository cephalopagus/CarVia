package com.example.carvia.insurance.db

import android.text.Editable

data class Osago(
    var uid:String?=null,
    var name:String?=null,
    var phone: String? =null,
    var type_auto:String?=null,
    var foreign_auto: String? =null,
    var experience: String? =null,
    var period: String? =null,
    var price: Float? =null )