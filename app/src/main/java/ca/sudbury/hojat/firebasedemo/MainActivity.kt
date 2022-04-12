package ca.sudbury.hojat.firebasedemo

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import ca.sudbury.hojat.firebasedemo.databinding.ActivityMainBinding
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions


class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"
    private lateinit var binding: ActivityMainBinding

    // The keys we'll be using to save our values to the Firestore database.
    private val KEY_TITLE = "title"
    private val KEY_DESCRIPTION = "description"

    // A reference to Firestore database
    val db = FirebaseFirestore.getInstance()

    // A reference to the document that we're interacting with ().
    private val noteRef = db.collection("Notebook").document("My First Note")

    @SuppressLint("SetTextI18n")
    override fun onStart() {
        super.onStart()
        noteRef.addSnapshotListener(this) { value, error ->
            if (error != null) {
                Toast.makeText(this, "Error while loading!", Toast.LENGTH_SHORT).show()
                Log.e(TAG, error.message.toString())
                return@addSnapshotListener
            }
            if (value?.exists() == true) {
                val note = value.toObject(Note::class.java)
                binding.textViewData.text = "Title : ${note?.title}\nDescription : ${
                    note?.description
                }"
            } else {
                // The document doesn't exists (most likely user has deleted it)
                binding.textViewData.text = ""
            }
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.buttonSave.setOnClickListener {
            val title = binding.editTextTitle.text.toString()
            val description = binding.editTextDescription.text.toString()
            val note = Note(title, description)

            // Now we create a "Notebook" collection, the document
            // "My First Note" inside that collection and will add
            // our kotlin data class to that document.
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
                        // recreate our Note object from Document Snapshot:
                        val note = it.toObject(Note::class.java)
                        binding.textViewData.text =
                            "Title : ${note?.title}\nDescription : ${
                                note?.description
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
        binding.buttonUpdateDescription.setOnClickListener {
            // we get the input text of description EditText and
            // only update the field of "description" in the
            // corresponding document in Firestore (without
            // overriding any other data).
            val description = binding.editTextDescription.text.toString()
            val note = HashMap<String, Any>().also {
                it[KEY_DESCRIPTION] = description
            }

            // This code line only merges this data with
            // the document in the corresponding DocumentReference.
            noteRef.set(note, SetOptions.merge())

        }
        binding.buttonDeleteDescription.setOnClickListener {
            // Only deletes the description field from document
            noteRef.update(KEY_DESCRIPTION, FieldValue.delete())
        }
        binding.buttonDeleteNote.setOnClickListener {
            // Deletes the document in this DocumentReference
            noteRef.delete()
        }
    }


}