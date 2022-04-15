package fr.adriendebreuillydev.plantscollection.fragments

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import androidx.fragment.app.Fragment
import fr.adriendebreuillydev.plantscollection.MainActivity
import fr.adriendebreuillydev.plantscollection.PlantModel
import fr.adriendebreuillydev.plantscollection.PlantRepository
import fr.adriendebreuillydev.plantscollection.PlantRepository.Singleton.downloadUri
import fr.adriendebreuillydev.plantscollection.R
import java.util.*

class AddPlantFragment(

    private val context: MainActivity

) : Fragment() {

    private var file:Uri? = null
    private var uploadedImage: ImageView? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater?.inflate(R.layout.fragment_add_plant, container, false)

        // recuperer uploadedImage pour lui associer son composant
        uploadedImage = view.findViewById(R.id.preview_image)

        // recuperer le bouton pour charger l'image
        val pickupImageButton = view.findViewById<Button>(R.id.upload_button)
        //lorsqu'on clique dessus ça ouvre les image du telephone
        pickupImageButton.setOnClickListener {pickupImage()}

        // recuperer le bouton confirme
        val confirmeButton = view.findViewById<Button>(R.id.confirm_button)
        confirmeButton.setOnClickListener {sendForm(view)}

        return view
    }

    private fun sendForm(view: View) {
        val repo = PlantRepository()
        repo.uploadImage(file!!) {
            val plantName = view.findViewById<EditText>(R.id.name_input).text.toString()
            val plantDescription = view.findViewById<EditText>(R.id.description_input).text.toString()
            val grow = view.findViewById<Spinner>(R.id.grow_spinner).selectedItem.toString()
            val water = view.findViewById<Spinner>(R.id.water_spinner).selectedItem.toString()
            val downloadImageUrl = downloadUri

            //creer un nouvel objet PlantModel
            val plant = PlantModel(
                UUID.randomUUID().toString(),
                plantName,
                plantDescription,
                downloadImageUrl.toString(),
                grow,
                water
            )
            //envoyer en bdd
            repo.inserPlant(plant)
        }
    }

    private fun pickupImage() {
        val intent = Intent()
        intent.type = "image/"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 50)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 50 && resultCode == Activity.RESULT_OK){
            // verfier ci données sont nulles
            if(data == null || data.data == null) return
            //recuperer l'image
            file = data.data
            //mettre à jour l'aperçu de l'image
            uploadedImage?.setImageURI(file)
        }
    }

}