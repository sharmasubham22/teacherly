package ca.dal.teacherly.utils

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import ca.dal.teacherly.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.login.*

class LoginManager : AppCompatActivity() {
    private lateinit var auth:FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)
        auth = FirebaseAuth.getInstance()


        LoginButton.setOnClickListener(){
            if(checkForInput()){
                val email = LoginEmail.text.toString()
                val password = LoginPassword.text.toString()
                auth.signInWithEmailAndPassword(email,password)
                    .addOnCompleteListener(this){
                        task->
                        if(task.isSuccessful){
                            Toast.makeText(this, "Login Successfull", Toast.LENGTH_LONG).show()
                        }else{
                            Toast.makeText(this, "Wrong Details", Toast.LENGTH_LONG).show()
                        }
                    }
            }else{
                Toast.makeText(this, "Enter the details", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun checkForInput(): Boolean {
        if(LoginEmail.text.toString().trim().isNotEmpty() && LoginPassword.text.toString().trim().isNotEmpty())
            return true
        return false
    }
}