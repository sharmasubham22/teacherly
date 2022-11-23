package ca.dal.teacherly.utils

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ca.dal.teacherly.R

class EditProfile: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_profile)

        var email = intent.getStringExtra("Email")
        println("Edit Profile Email $email")


    }
}