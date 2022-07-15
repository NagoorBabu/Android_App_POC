package com.app.cameraxwork.mvccamera.room

import com.app.cameraxwork.mvccamera.model.Lpr
import com.app.cameraxwork.mvccamera.util.EntityMapper
import javax.inject.Inject

class CacheMapper
@Inject
constructor() : EntityMapper<LprCacheEntity, Lpr> {
    override fun mapFromEntity(entity: LprCacheEntity): Lpr {
        return Lpr(
            id = entity.id,
            vehicleImage = entity.vehicleImage,
            selectedData = entity.selectedData,
            outputValue = entity.outputValue,
            ocrValue = entity.ocrValue
        )
    }

    override fun mapToEntity(domainModel: Lpr): LprCacheEntity {
        return LprCacheEntity(
            id = domainModel.id,
            vehicleImage = domainModel.vehicleImage,
            selectedData = domainModel.selectedData,
            outputValue = domainModel.outputValue,
            ocrValue = domainModel.ocrValue
        )
    }

    fun mapFromEntityList(entities: List<LprCacheEntity>): List<Lpr> {
        return entities.map { mapFromEntity(it) }
    }

}