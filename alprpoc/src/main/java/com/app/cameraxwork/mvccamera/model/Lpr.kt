package com.app.cameraxwork.mvccamera.model

import java.util.*

data class Lpr(
    var vehicleImage: String,
    var selectedData: String,
    var outputValue: String,
    var ocrValue: String,
    var id: String = UUID.randomUUID().toString()
){
    override fun toString(): String {
        return "Lpr(vehicleImage='$vehicleImage', selectedData='$selectedData', outputValue='$outputValue', ocrValue='$ocrValue', id='$id')"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Lpr

        if (vehicleImage != other.vehicleImage) return false
        if (selectedData != other.selectedData) return false
        if (outputValue != other.outputValue) return false
        if (ocrValue != other.ocrValue) return false
        if (id != other.id) return false

        return true
    }


}