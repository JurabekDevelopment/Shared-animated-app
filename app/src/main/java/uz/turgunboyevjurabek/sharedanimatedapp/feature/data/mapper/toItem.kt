package uz.turgunboyevjurabek.sharedanimatedapp.feature.data.mapper

import uz.turgunboyevjurabek.sharedanimatedapp.feature.data.data_source.local.ItemEntity
import uz.turgunboyevjurabek.sharedanimatedapp.feature.domein.madels.Item

fun ItemEntity.toItem():Item{
    return Item(
        id = this.id,
        title = this.title,
        description = this.description,
        imageUrl = this.imageUrl
    )
}