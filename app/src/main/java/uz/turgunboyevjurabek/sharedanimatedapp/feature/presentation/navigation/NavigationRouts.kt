package uz.turgunboyevjurabek.sharedanimatedapp.feature.presentation.navigation

import kotlinx.serialization.Serializable

@Serializable
data object MainRout

@Serializable
data class DetailRout(
    val id:Int?,
    val title:String?,
    val description:String?,
    val imageUrl:String?
)

@Serializable
data object AddItemRout
