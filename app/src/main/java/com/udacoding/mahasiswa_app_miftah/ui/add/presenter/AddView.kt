package com.udacoding.mahasiswa_app_miftah.ui.add.presenter

import com.udacoding.mahasiswa_app_miftah.model.action.ResponseAction

interface AddView {

    fun onSucces(responseAdd : ResponseAction)
    fun onError(msg: String)
    fun addSuccess(msg: String)
    fun addEmpty()

}