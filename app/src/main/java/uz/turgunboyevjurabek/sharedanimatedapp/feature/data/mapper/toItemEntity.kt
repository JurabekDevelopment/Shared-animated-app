package uz.turgunboyevjurabek.sharedanimatedapp.feature.data.mapper

import uz.turgunboyevjurabek.sharedanimatedapp.feature.data.data_source.local.ItemEntity
import uz.turgunboyevjurabek.sharedanimatedapp.feature.domein.madels.Item

fun Item.toItemEntity(): ItemEntity {
    return ItemEntity(
        id = this.id,
        title = this.title,
        description = this.description,
        imageUrl = this.imageUrl,
        timestamp=this.timestamp
    )

}