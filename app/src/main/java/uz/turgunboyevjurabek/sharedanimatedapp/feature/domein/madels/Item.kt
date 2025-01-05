package uz.turgunboyevjurabek.sharedanimatedapp.feature.domein.madels

data class Item(
    val id:Int=0,
    val title:String,
    val description:String,
    val imageUrl: String,
    val timestamp: Long = System.currentTimeMillis()
)
