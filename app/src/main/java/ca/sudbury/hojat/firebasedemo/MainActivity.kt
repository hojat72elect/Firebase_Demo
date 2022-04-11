package ca.sudbury.hojat.firebasedemo

import android.annotation.SuppressLint
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

    // A reference to the document that we're interacting with ().
    val noteRef = db.collection("Notebook").document("My First Note")

    @SuppressLint("SetTextI18n")
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
            noteRef.set(note)
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
        binding.buttonLoad.setOnClickListener {
            noteRef.get()
                .addOnSuccessListener {
                    if (it.exists()) {
                        // Get our needed data out of this DocumentSnapshot and load into TextView.
                        val note = it.getData()
                        binding.textViewData.text =
                            "Title : ${note?.get(KEY_TITLE)}\nDescription : ${
                                note?.get(
                                    KEY_DESCRIPTION
                                )
                            }"
                    } else {
                        Toast.makeText(this, "Document doesn't exist!", Toast.LENGTH_SHORT).show()
                    }
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Error retrieving data!", Toast.LENGTH_SHORT).show()
                    Log.e(TAG, it.message.toString())
                }
        }
    }
}