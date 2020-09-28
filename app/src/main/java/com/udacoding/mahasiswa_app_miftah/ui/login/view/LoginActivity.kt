package com.udacoding.mahasiswa_app_miftah.ui.login.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.udacoding.mahasiswa_app_miftah.R
import com.udacoding.mahasiswa_app_miftah.helper.SessionManager
import com.udacoding.mahasiswa_app_miftah.model.getLogin.DataUser
import com.udacoding.mahasiswa_app_miftah.ui.beranda.view.MainActivity
import com.udacoding.mahasiswa_app_miftah.ui.login.presenter.LoginPresenter
import com.udacoding.mahasiswa_app_miftah.ui.login.presenter.ViewLogin
import com.udacoding.mahasiswa_app_miftah.ui.register.view.RegisterActivity
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity(), ViewLogin {

    private var presenter : LoginPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        presenter = LoginPresenter(this)

        tv_registrasi.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        btn_login.setOnClickListener {
            val email = edt_email_login.text.toString()
            val password = edt_password_login.text.toString()

            presenter?.getLogin(email, password)
        }

    }

    override fun loginSuccess(msg: String, user: List<DataUser>) {
        val session = SessionManager(this)
        session.email = user.get(0).userEmail
        session.name = user.get(0).userNama
        session.login = true
        Log.d("sessionManager1", session.name.toString())
        Log.d("sessionManager2", session.email.toString())
        startActivity(Intent(this, MainActivity::class.java))
    }

    override fun errorLogin(msg: String) {
        Toast.makeText(applicationContext, msg, Toast.LENGTH_SHORT).show()
    }
}