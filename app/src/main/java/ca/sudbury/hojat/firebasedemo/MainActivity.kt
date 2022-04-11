package ca.sudbury.hojat.firebasedemo

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import ca.sudbury.hojat.firebasedemo.databinding.ActivityMainBinding
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"
    private lateinit var binding: ActivityMainBinding

    // The keys we'll be using to save our values to the Firestore database.
    private val KEY_TITLE = "title"
    private val KEY_DESCRIPTION = "description"

    // A reference to Firestore database
    val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.buttonSave.setOnClickListener {
            val title = binding.editTextTitle.text.toString()
            val description = binding.editTextDescription.text.toString()

            // we need to firstly put our data into key-value pairs
            // and then upload them into Firestore
            val note = HashMap<String, Any>().apply {
                this.put(KEY_TITLE, title)
                this.put(KEY_DESCRIPTION, description)
            }
            // Now we create a "Notebook" collection, the document
            // "My First Note" inside that collection and will add
            // our key-value pairs to that document.
            db.collection("Notebook").document("My First Note").set(note)
                .addOnSuccessListener {
                    // the call was successful
                    Toast.makeText(this, "Note saved successfully!", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {
                    // we've got an error
                    Toast.makeText(
                        this,
                        "Error occurred in database connection!",
                        Toast.LENGTH_SHORT
                    ).show()
                    Log.e(TAG, "Firestore Error : ${it.message}")
                }

        }
    }
}