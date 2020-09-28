package com.udacoding.mahasiswa_app_miftah.ui.register.presenter

import com.udacoding.mahasiswa_app_miftah.model.getRegister.ResponseRegister

interface RegisterView {

    fun successRegister (responseRegister: ResponseRegister)
    fun errorRegister (msg : String)
    fun empty()
    fun passwordConfirm()
    fun progresVisible()
    fun progresGone()
}