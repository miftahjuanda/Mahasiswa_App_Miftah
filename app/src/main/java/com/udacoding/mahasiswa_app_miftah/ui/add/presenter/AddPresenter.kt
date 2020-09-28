package com.udacoding.mahasiswa_app_miftah.ui.add.presenter

import com.udacoding.mahasiswa_app_miftah.network.NetworkModule
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class AddPresenter(val view: AddView) {

    fun getInput(nama: String, nohp: String, alamat: String) {

        NetworkModule.service().insertData(nama, nohp, alamat)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ response ->
                val message = response.message
                if (response.isSuccess ?: true) {
                    view.onSucces(response)
                } else {
                    view.onError(message ?: "")
                }
            }, {
                view.onError(it.localizedMessage)
            })
    }

    fun getUpdate(id: String, nama: String, nohp: String, alamat: String) {

        NetworkModule.service().updateData(id ?: "", nama ?: "", nohp ?: "", alamat ?: "")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ response ->
                val message = response.message
                if (response.isSuccess ?: true) {
                    view.onSucces(response)
                } else {
                    view.onError(message ?: "")
                }
            }, {
                view.onError(it.localizedMessage)
            })
    }
}