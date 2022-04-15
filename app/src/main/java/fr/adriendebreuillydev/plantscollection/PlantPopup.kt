package fr.adriendebreuillydev.plantscollection

import android.app.Dialog
import android.net.Uri
import android.os.Bundle
import android.view.Window
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import fr.adriendebreuillydev.plantscollection.adapter.PlantAdapter

class PlantPopup(
    private val adapter: PlantAdapter,
    private val currentPlant: PlantModel
    ) : Dialog(adapter.context) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.popup_plants_details)
        setupComponents()
        setupCloseButton()
        setupDeleteButton()
        setupStarButton()
    }

    private fun updateStar(button: ImageView) {
        if(currentPlant.liked) {
            button.setImageResource(R.drawable.ic_liked)
        }
        else {
            button.setImageResource(R.drawable.ic_unliked)
        }
    }

    private fun setupStarButton() {
        // recuperer
        val starButton = findViewById<ImageView>(R.id.star_button)

        updateStar(starButton)

        // interaction
        starButton.setOnClickListener {
            currentPlant.liked = !currentPlant.liked
            val repo = PlantRepository()
            repo.updatePlant(currentPlant)

            updateStar(starButton)
        }

    }

    private fun setupDeleteButton() {
        findViewById<ImageView>(R.id.delete_button).setOnClickListener {
            // suprimer la plante de la base de donnée
            val repo = PlantRepository()
            repo.deletePlant(currentPlant)
            dismiss()
        }
    }

    private fun setupCloseButton() {
        findViewById<ImageView>(R.id.close_button).setOnClickListener {
            // fermer la fenêtre popup
            dismiss()
        }
    }

    private fun setupComponents() {
        // actualiser l'image de la plante
        val plantImage = findViewById<ImageView>(R.id.image_item)
        Glide.with(adapter.context).load(Uri.parse(currentPlant.imageUrl)).into(plantImage)

        // actualiser le nom de la plante
        findViewById<TextView>(R.id.popup_plant_name).text = currentPlant.name

        // actualiser la description de la plante
        findViewById<TextView>(R.id.popup_plant_description_sub_title).text = currentPlant.description

        // actualiser la croissance de la plante
        findViewById<TextView>(R.id.popup_plant_grow_sub_title).text = currentPlant.grow

        // actualiser la consomation d'eau de la plante
        findViewById<TextView>(R.id.popup_plant_water_sub_title).text = currentPlant.water
    }

}