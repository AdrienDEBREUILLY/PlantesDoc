package fr.adriendebreuillydev.plantscollection.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import fr.adriendebreuillydev.plantscollection.MainActivity
import fr.adriendebreuillydev.plantscollection.PlantRepository.Singleton.plantList
import fr.adriendebreuillydev.plantscollection.R
import fr.adriendebreuillydev.plantscollection.adapter.PlantAdapter
import fr.adriendebreuillydev.plantscollection.adapter.PlantItemDecoration

class HomeFragment(
    private val context: MainActivity,
) : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_home, container, false)

        //recuperer le recyclerview
        val horizontalRecyclerView = view.findViewById<RecyclerView>(R.id.horizontal_recycler_view)
        horizontalRecyclerView.adapter = PlantAdapter(context, plantList.filter {!it.liked}, R.layout.item_horizontal_plant)

        //recuperer le second recyclerview
        val verticalRecyclerView = view.findViewById<RecyclerView>(R.id.vertical_recycler_view)
        verticalRecyclerView.adapter = PlantAdapter(context, plantList, R.layout.item_vertical_plant)
        verticalRecyclerView.addItemDecoration(PlantItemDecoration())

        return view
    }
}