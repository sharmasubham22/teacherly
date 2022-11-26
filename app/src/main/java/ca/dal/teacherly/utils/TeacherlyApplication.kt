package ca.dal.teacherly.utils

import android.app.Application

class TeacherlyApplication : Application() {

    var isTutor = false

    override fun onCreate() {
        super.onCreate()
        this.isTutor = false
        instance = this
    }

    companion object {
        lateinit var instance: TeacherlyApplication
        private set
        var email = ""
    }

    fun setIsTutor(isTutor: Boolean){
        this.isTutor = isTutor
    }

    fun getIsTutor() : Boolean{
        return this.isTutor;
    }
}