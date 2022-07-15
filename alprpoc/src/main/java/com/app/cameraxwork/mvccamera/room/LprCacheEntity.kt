package com.app.cameraxwork.mvccamera.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "lpr_records")
class LprCacheEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    var id: String,

    @ColumnInfo(name = "vehicle_image")
    var vehicleImage: String,

    @ColumnInfo(name = "selected_data")
    var selectedData: String,

    @ColumnInfo(name = "output_value")
    var outputValue: String,

    @ColumnInfo(name = "ocr_value")
    var ocrValue: String
)