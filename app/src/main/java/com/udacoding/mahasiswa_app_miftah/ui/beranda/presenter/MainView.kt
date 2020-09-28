package com.udacoding.mahasiswa_app_miftah.ui.beranda.presenter

import com.udacoding.mahasiswa_app_miftah.model.getData.DataItem


interface MainView {

    //todo 5
    fun onSucces(msg : String, response : List<DataItem>?)
    fun onInfo (msg: String)
    fun onError(msg: String)

}