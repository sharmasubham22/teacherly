package ca.dal.teacherly.utils

import android.content.Intent
import android.os.Bundle
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isEmpty
import androidx.core.view.isNotEmpty
import ca.dal.teacherly.MainActivity
import ca.dal.teacherly.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.login.*
import kotlinx.android.synthetic.main.registration.*

class RegisterManager: AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.registration)

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        val rdGroup = findViewById<RadioGroup>(R.id.radioGroup)

        Registration.setOnClickListener(){
            val selectedBtn:Int = rdGroup!!.checkedRadioButtonId
            val btn = findViewById<RadioButton>(selectedBtn)

            if(rdGroup.checkedRadioButtonId != -1){
                if(checkForInput()){

                    var type = btn.text.toString()
                    var name = RegisterName.text.toString()
                    val email = RegisterEmail.text.toString()
                    val password = RegisterPassword.text.toString()
                    var confirmPassword = RegisterConfirmPassword.text.toString()
                    var mobileNumber = RegisterMobile.text.toString()
                    var streetName = RegisterStreetName.text.toString()
                    var city = RegisterCity.text.toString()
                    var province = RegisterProvince.text.toString()
                    var postalCode = RegisterPostalCode.text.toString()

                    if(password == confirmPassword){

                        val user = hashMapOf(
                            "Type" to type,
                            "Name" to name,
                            "Email" to email,
                            "Mobile Number" to mobileNumber,
                            "Street Name" to streetName,
                            "City" to city,
                            "Province" to province,
                            "Postal Code" to postalCode
                        )

                        val users = db.collection("USERS")
                        val query = users.whereEqualTo("Email", email).get()
                            .addOnSuccessListener {
                                tasks->
                                if(tasks.isEmpty){
                                    auth.createUserWithEmailAndPassword(email, password)
                                        .addOnCompleteListener(this){
                                            task->
                                            if(task.isSuccessful){
                                                users.document(email).set(user)
                                                Toast.makeText(this, "Registration Successful for " + type, Toast.LENGTH_LONG).show()
                                                var intent = Intent(this, LoginManager::class.java)
                                                startActivity(intent)
                                                finish()
                                            }else{
                                                Toast.makeText(this, "Authentication Failed", Toast.LENGTH_LONG).show()
                                            }
                                        }

                                }else{
                                    Toast.makeText(this, "User already exists", Toast.LENGTH_LONG).show()
                                    var intent = Intent(this, LoginManager::class.java)
                                    startActivity(intent)
                                }
                            }
//                        auth.createUserWithEmailAndPassword(email,password)
//                            .addOnCompleteListener(this){
//                                    task->
//                                if(task.isSuccessful){
////                                    var intent = Intent(this, LoginManager::class.java)
////                                    startActivity(intent)
//                                Toast.makeText(this, "Registration Successful for " + btn.text.toString(), Toast.LENGTH_LONG).show()
//                                }else{
//                                    Toast.makeText(this, "Wrong Details", Toast.LENGTH_LONG).show()
//                                }
//                            }
                    }else{
                        Toast.makeText(this, "Password doesn't match", Toast.LENGTH_LONG).show()
                    }
                }else{
                    Toast.makeText(this, "Enter the details", Toast.LENGTH_LONG).show()
                }
            }else{
                Toast.makeText(this, "Select Registration Type", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun checkForInput(): Boolean {
        if(RegisterName.text.toString().trim().isNotEmpty() && RegisterEmail.text.toString().trim().isNotEmpty()
            && RegisterPassword.text.toString().trim().isNotEmpty() && RegisterConfirmPassword.text.toString().trim().isNotEmpty()
            && RegisterMobile.text.toString().trim().isNotEmpty() && RegisterStreetName.text.toString().trim().isNotEmpty()
            && RegisterCity.text.toString().trim().isNotEmpty() && RegisterProvince.text.toString().trim().isNotEmpty()
            && RegisterPostalCode.text.toString().trim().isNotEmpty()){
            return true
        }
        return false
    }
}