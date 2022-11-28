package ca.dal.teacherly.utils

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import ca.dal.teacherly.R
import com.google.firebase.auth.FirebaseAuth

class ResetPassword :AppCompatActivity() {

    private lateinit var editPassword : TextView
    private lateinit var resetPasswordButton : Button

    private lateinit var auth : FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.reset_password)

        editPassword = findViewById(R.id.registeredEmail)
        resetPasswordButton = findViewById(R.id.resetPasswordButton)

        auth = FirebaseAuth.getInstance()

        // When reset password is clicked a link to reset the password is sent to the user on his/her designated email.
        resetPasswordButton.setOnClickListener{
            val newPassword = editPassword.text.toString()
            auth.sendPasswordResetEmail(newPassword)
                .addOnSuccessListener {
                    Toast.makeText(this, "Please check your email", Toast.LENGTH_SHORT).show()
                    var intent = Intent(this, LoginManager::class.java)
                    startActivity(intent)
                    finish()
                }
                .addOnFailureListener{
                    Toast.makeText(this, it.toString(), Toast.LENGTH_SHORT).show()
                }
        }
    }
}