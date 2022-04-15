package fr.adriendebreuillydev.plantscollection

class PlantModel(
    val id: String = "plant0",
    val name: String = "Bonsaï",
    val description: String = "petite description",
    val imageUrl: String = "https://graven.yt/plente.jpg",
    val grow: String = "Faible",
    val water: String = "Moyenne",
    var liked: Boolean = false
)
