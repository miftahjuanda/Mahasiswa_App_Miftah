package com.udacoding.mahasiswa_app_miftah.ui.login.presenter

import com.udacoding.mahasiswa_app_miftah.network.NetworkModule
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class LoginPresenter(val viewLogin: ViewLogin) {

    fun getLogin(email: String, password: String) {

        if (email.isNotEmpty() && password.isNotEmpty()) {
            NetworkModule.service().getLogin(email, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->
                    val status = response.isSuccess
                    val message = response.message
                    if (status ?: true) {
                        response.data?.let { viewLogin.loginSuccess(message ?: "", it) }
                    } else {
                        viewLogin.errorLogin(message ?: "")
                    }
                },
                    { error ->
                        viewLogin.errorLogin(error.localizedMessage)
                    })
        }
        else {
            viewLogin.errorLogin("Isian Login Harus Di Isi")
        }
    }
}