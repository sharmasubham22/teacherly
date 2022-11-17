package ca.dal.teacherly.utils

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import ca.dal.teacherly.MainActivity
import ca.dal.teacherly.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.login.*
import kotlinx.android.synthetic.main.registration.*

class RegisterManager: AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.registration)
        auth = FirebaseAuth.getInstance()

        Registration.setOnClickListener(){
            if(checkForInput()){
                var name = RegisterPassword.text.toString()
                val email = RegisterEmail.text.toString()
                val password = RegisterPassword.text.toString()
                var confirmPassword = RegisterConfirmPassword.text.toString()
                var mobileNumber = RegisterMobile.text.toString()
                var streetName = RegisterStreetName.text.toString()
                var city = RegisterCity.text.toString()
                var province = RegisterProvince.text.toString()
                var postalCode = RegisterPostalCode.text.toString()

                if(password == confirmPassword){
                    auth.createUserWithEmailAndPassword(email,password)
                        .addOnCompleteListener(this){
                                task->
                            if(task.isSuccessful){
                            var intent = Intent(this, LoginManager::class.java)
                            startActivity(intent)
//                                Toast.makeText(this, "Registration Successful", Toast.LENGTH_LONG).show()
                            }else{
                                Toast.makeText(this, "Wrong Details", Toast.LENGTH_LONG).show()
                            }
                        }
                }else{
                    Toast.makeText(this, "Password doesn't match", Toast.LENGTH_LONG).show()
                }
            }else{
                Toast.makeText(this, "Enter the details", Toast.LENGTH_LONG).show()
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