package ca.dal.teacherly.utils

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import ca.dal.teacherly.MainActivity
import ca.dal.teacherly.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.login.*

class LoginManager : AppCompatActivity() {
    private lateinit var auth:FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)
        auth = FirebaseAuth.getInstance()

        // When user clicks on register
        RegisterButton.setOnClickListener(){
            nav()
        }

        // When user clicks on forgot password
        forgotPassword.setOnClickListener{
            var intent = Intent(this, ResetPassword::class.java)
            startActivity(intent)
        }

        // When user clicks on login
        LoginButton.setOnClickListener(){
            // Validating inputs
            if(checkForInput()){
                val email = LoginEmail.text.toString()
                val password = LoginPassword.text.toString()
                //Sign in is done with the given email and password
                auth.signInWithEmailAndPassword(email,password)
                    .addOnCompleteListener(this){
                        task->
                        if(task.isSuccessful){
                            // Check if the user has verified his/her email
                            val verified = auth.currentUser?.isEmailVerified
                            if(verified == true){
                                var intent = Intent(applicationContext, MainActivity::class.java)
                                intent.putExtra("Email", email)
                                startActivity(intent)
                                finish()
                            }else{
                                Toast.makeText(this, "Please verify your email!", Toast.LENGTH_LONG).show()
                            }
                        }else{
                            Toast.makeText(this, "Wrong Details", Toast.LENGTH_LONG).show()
                        }
                    }
            }else{
                Toast.makeText(this, "Enter the details", Toast.LENGTH_LONG).show()
            }
        }
    }

    // Navigate to Register Page
    private fun nav(){
        val intent = Intent(this, RegisterManager::class.java)
        startActivity(intent)
    }

    // Check if the input fields are empty or not
    private fun checkForInput(): Boolean {
        if(LoginEmail.text.toString().trim().isNotEmpty() && LoginPassword.text.toString().trim().isNotEmpty())
            return true
        return false
    }

    // Restrict from going back
    override fun onBackPressed() {
        Toast.makeText(this, "Log In into Teacherly", Toast.LENGTH_LONG).show()
    }
}