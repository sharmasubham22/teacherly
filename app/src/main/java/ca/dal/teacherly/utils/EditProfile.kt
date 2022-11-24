package ca.dal.teacherly.utils

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import ca.dal.teacherly.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.edit_profile.*
import kotlinx.android.synthetic.main.registration.*

class EditProfile: AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

//    private lateinit var image : ImageView
//    private lateinit var selectImage: Button
//    private lateinit var save : Button


    private lateinit var phone : TextView
    private lateinit var name : TextView
    private lateinit var street : TextView
    private lateinit var city : TextView
    private lateinit var province : TextView
    private lateinit var postalCode : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_profile)

//        image = findViewById(R.id.profileImage)
//        selectImage = findViewById(R.id.uploadImage)
//        save = findViewById(R.id.editProfileButton)




        var email = intent.getStringExtra("Email").toString()
        println("Edit Profile Email: $email")

        name = findViewById<TextView>(R.id.editName)
        phone = findViewById<TextView>(R.id.editMobile)
        street = findViewById<TextView>(R.id.editStreetName)
        city = findViewById<TextView>(R.id.editCity)
        province = findViewById<TextView>(R.id.editProvince)
        postalCode = findViewById<TextView>(R.id.editPostalCode)

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        val ref = db.collection("USERS").document(email)
        ref.get().addOnSuccessListener {
            if(it!=null){
                val fetchedName = it.data?.get("Name")?.toString()
                val fetchedPhoneNumber = it.data?.get("Mobile Number")?.toString()
                val fetchedStreetName = it.data?.get("Street Name")?.toString()
                val fetchedCity = it.data?.get("City")?.toString()
                val fetchedProvince = it.data?.get("Province")?.toString()
                val fetchedPostalCode = it.data?.get("Postal Code")?.toString()

                name.text = fetchedName
                phone.text = fetchedPhoneNumber
                street.text = fetchedStreetName
                city.text = fetchedCity
                province.text = fetchedProvince
                postalCode.text = fetchedPostalCode
            }
        }

    }
    private fun checkForInput(): Boolean {
        if(editName.text.toString().trim().isNotEmpty() && editMobile.text.toString().trim().isNotEmpty()
            && editStreetName.text.toString().trim().isNotEmpty()
            && editCity.text.toString().trim().isNotEmpty() && editProvince.text.toString().trim().isNotEmpty()
            && editPostalCode.text.toString().trim().isNotEmpty()){
            return true
        }
        return false
    }
}