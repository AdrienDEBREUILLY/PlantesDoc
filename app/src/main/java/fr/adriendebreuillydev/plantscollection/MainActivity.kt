package fr.adriendebreuillydev.plantscollection

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import fr.adriendebreuillydev.plantscollection.fragments.AddPlantFragment
import fr.adriendebreuillydev.plantscollection.fragments.CollectionFragment
import fr.adriendebreuillydev.plantscollection.fragments.HomeFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadFragment(HomeFragment(this), R.string.home_page_title)

        // importer la bottomnavigationview
        val navigationView = findViewById<BottomNavigationView>(R.id.navigation_view)
        navigationView.setOnNavigationItemSelectedListener {
            when(it.itemId) {

                R.id.home_page -> {
                    loadFragment(HomeFragment(this), R.string.home_page_title)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.collection_page -> {
                    loadFragment(CollectionFragment(this), R.string.collection_page_title)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.add_plant_page -> {
                    loadFragment(AddPlantFragment(this), R.string.add_plant_page_title)
                    return@setOnNavigationItemSelectedListener true
                }
                else -> false
            }
        }
    }

    private fun loadFragment(fragment: Fragment, string: Int) {

        // charger notre plant repository
        val repo = PlantRepository()

        // actuamiser le titre de la page
        findViewById<TextView>(R.id.page_tile).text = resources.getString(string)

        // mettre à jour la liste de plante
        repo.updateData {
            // injecter le fragment dans notre boite (fragment_container)
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, fragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }
    }
}