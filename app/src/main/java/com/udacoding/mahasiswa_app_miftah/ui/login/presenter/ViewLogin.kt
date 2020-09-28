package com.udacoding.mahasiswa_app_miftah.ui.login.presenter


import com.udacoding.mahasiswa_app_miftah.model.getLogin.DataUser

interface ViewLogin {

    fun loginSuccess(msg: String, user : List<DataUser>)
    fun errorLogin(msg: String)
}