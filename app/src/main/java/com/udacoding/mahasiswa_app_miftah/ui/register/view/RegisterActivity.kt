package com.udacoding.mahasiswa_app_miftah.ui.register.view

import RegisterPresenter
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.udacoding.mahasiswa_app_miftah.R
import com.udacoding.mahasiswa_app_miftah.model.getRegister.ResponseRegister
import com.udacoding.mahasiswa_app_miftah.ui.login.view.LoginActivity
import com.udacoding.mahasiswa_app_miftah.ui.register.presenter.RegisterView
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity(), RegisterView {

    private var presenter: RegisterPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        presenter = RegisterPresenter(this)

        initData()
    }

    private fun initData() {
        btn_daftar.setOnClickListener {
            //ambil data inputan
            val username = edt_username.text.toString()
            val email = edt_email.text.toString()
            val password = edt_password.text.toString()
            val passwordConfirm = edt_password_confirm.text.toString()

            if (username.isNotEmpty() != email.isNotEmpty() != password.isNotEmpty() != passwordConfirm.isNotEmpty()) {
                if (username.isEmpty()) {
                    edt_username.error = "Lengkapi Username Anda"
                }

                if (email.isEmpty()) {
                    edt_email.error = "Lengkapi Email Anda"
                }

                if (password.isEmpty()) {
                    edt_password.error = "Lengkapi Password Anda"
                }

                if (passwordConfirm.isEmpty()) {
                    edt_password_confirm.error = "Lengkapi Password Anda"
                }
                Toast.makeText(
                    applicationContext,
                    "Registrasi Gagal, Lengkapi Isian",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                presenter?.getRegistrasi(username, email, password, passwordConfirm)
            }
        }

        tv_login.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }

    override fun successRegister(responseRegister: ResponseRegister) {
        startActivity(Intent(this, LoginActivity::class.java))
    }

    override fun errorRegister(msg: String) {
        showToast(msg)
    }

    override fun empty() {
    }

    override fun passwordConfirm() {
        showToast("password tidak cocok")
    }

    override fun progresVisible() {
        pb_main.visibility = View.VISIBLE
    }

    override fun progresGone() {
        pb_main.visibility = View.GONE
    }

    fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}