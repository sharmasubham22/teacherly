package ca.dal.teacherly.utils

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import ca.dal.teacherly.MainActivity
import ca.dal.teacherly.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.edit_profile.*
import com.google.firebase.storage.FirebaseStorage
import java.util.*

class EditProfile: AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    private lateinit var phone : TextView
    private lateinit var name : TextView
    private lateinit var street : TextView
    private lateinit var city : TextView
    private lateinit var province : TextView
    private lateinit var postalCode : TextView
    private lateinit var fetchedImage : String


    private var imageuri: Uri? = null

    private var storageReference =  FirebaseStorage.getInstance().getReference("uploads")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_profile)

        // Get email from intent
        var email = intent.getStringExtra("Email").toString()

        // When the user clicks upload image
        uploadImage.setOnClickListener{
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(intent, 100)
        }

        name = findViewById<TextView>(R.id.editName)
        phone = findViewById<TextView>(R.id.editMobile)
        street = findViewById<TextView>(R.id.editStreetName)
        city = findViewById<TextView>(R.id.editCity)
        province = findViewById<TextView>(R.id.editProvince)
        postalCode = findViewById<TextView>(R.id.editPostalCode)

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        // Fetching the data of the logged in user
        val ref = db.collection("USERS").document(email)
        ref.get().addOnSuccessListener {
            if(it!=null){
                val fetchedName = it.data?.get("Name")?.toString()
                val fetchedPhoneNumber = it.data?.get("Mobile Number")?.toString()
                val fetchedStreetName = it.data?.get("Street Name")?.toString()
                val fetchedCity = it.data?.get("City")?.toString()
                val fetchedProvince = it.data?.get("Province")?.toString()
                val fetchedPostalCode = it.data?.get("Postal Code")?.toString()
                fetchedImage = it.data?.get("imageUrl")?.toString().toString()

                // Displaying the fetched data of user by default
                name.text = fetchedName
                phone.text = fetchedPhoneNumber
                street.text = fetchedStreetName
                city.text = fetchedCity
                province.text = fetchedProvince
                postalCode.text = fetchedPostalCode
            }
        }

        // When the user clicks edit button
        editProfileButton.setOnClickListener {
            val reference = storageReference.child(Date().time.toString())

            // If theres no profile photo of user in database the system prompts user to add the photo
            if(fetchedImage == "" && imageuri == null){
                Toast.makeText(this, "Please upload a profile photo.", Toast.LENGTH_LONG).show()
            }
            // If there is a photo of user in database
            else{
                if(imageuri == null){
                    uploadInformation(email)
                }
                // Data of uploaded file
                else{
                    imageuri?.let { it1 ->
                        reference.putFile(it1).addOnCompleteListener {
                            if (it.isSuccessful) {
                                reference.downloadUrl.addOnSuccessListener { tasks ->
                                    uploadInformation(tasks.toString(), email)
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    // Profile updating with both new photo and data
    private fun uploadInformation(imageUrl: String, email: String) {
        var name1 = name.text.toString()
        var mobileNumber1 = phone.text.toString()
        var streetName1 = street.text.toString()
        var city1 = city.text.toString()
        var province1 = province.text.toString()
        var postalCode1 = postalCode.text.toString()

        // Creating a hash map of updated user data
        val user = hashMapOf(
            "Name" to name1,
            "Mobile Number" to mobileNumber1,
            "Street Name" to streetName1,
            "City" to city1,
            "Province" to province1,
            "Postal Code" to postalCode1,
            "imageUrl" to imageUrl
        )

        // Updating profile of user
        val users = db.collection("USERS")
        val query = users.document(email).get()
            .addOnSuccessListener { tasks ->
                if (tasks.exists()) {
                    users.document(email).update(user as Map<String, String>)
                    Toast.makeText(this, "Profile update successful", Toast.LENGTH_LONG).show()
                    var intent = Intent(applicationContext, MainActivity::class.java)
                    intent.putExtra("Email", email)
                    startActivity(intent)
                    finish()
                }else{
                    Toast.makeText(this, "Email doesn't exist.", Toast.LENGTH_LONG).show()
                }
            }
    }

    // Profile updating with new data
    private fun uploadInformation(email: String) {
        var name1 = name.text.toString()
        var mobileNumber1 = phone.text.toString()
        var streetName1 = street.text.toString()
        var city1 = city.text.toString()
        var province1 = province.text.toString()
        var postalCode1 = postalCode.text.toString()

        // Creating a hash map of updated user data
        val user = hashMapOf(
            "Name" to name1,
            "Mobile Number" to mobileNumber1,
            "Street Name" to streetName1,
            "City" to city1,
            "Province" to province1,
            "Postal Code" to postalCode1
        )

        // Updating profile of user
        val users = db.collection("USERS")
        val query = users.document(email).get()
            .addOnSuccessListener { tasks ->
                if (tasks.exists()) {
                    users.document(email).update(user as Map<String, String>)
                    Toast.makeText(this, "Profile update successful", Toast.LENGTH_LONG).show()
                    var intent = Intent(applicationContext, MainActivity::class.java)
                    intent.putExtra("Email", email)
                    startActivity(intent)
                    finish()
                }else{
                    Toast.makeText(this, "Email doesn't exist.", Toast.LENGTH_LONG).show()
                }
            }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==100 && resultCode== RESULT_OK){
            imageuri = data?.data!!

            profileImage.setImageURI(imageuri)
        }

    }
}
