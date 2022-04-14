package ca.sudbury.hojat.firebasedemo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import ca.sudbury.hojat.firebasedemo.databinding.ActivityMainBinding
import com.google.firebase.firestore.FirebaseFirestore


class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"
    private lateinit var binding: ActivityMainBinding

    // A reference to Firestore database
    private val db = FirebaseFirestore.getInstance()

    // reference to the collection
    private val notebookRef = db.collection("Notebook")


    override fun onStart() {
        super.onStart()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.buttonAdd.setOnClickListener {
            notebookRef.add(
                Note(
                    binding.editTextTitle.text.toString(),
                    binding.editTextDescription.text.toString()
                )
            )
        }
        binding.buttonLoadAll.setOnClickListener {

            notebookRef.get().addOnSuccessListener { queryDocumentSnapshots ->
                var data = ""
                queryDocumentSnapshots.forEach {
                    val note = it.toObject(Note::class.java)
                    data += "Title: ${note.title}\nDescription: ${note.description}\n\n"
                }
                binding.textViewData.text = data
            }
        }

    }
}