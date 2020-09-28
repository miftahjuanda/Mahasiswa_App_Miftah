package com.udacoding.mahasiswa_app_miftah.ui.beranda.presenter

import com.udacoding.mahasiswa_app_miftah.model.action.ResponseAction
import com.udacoding.mahasiswa_app_miftah.model.getData.ResponseGetData
import com.udacoding.mahasiswa_app_miftah.network.NetworkModule
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.adapter.rxjava3.Result

//todo 6 menambahkan parameter interface mainview
class MainPresenter(val view: MainView) {

    //todo 4
    fun getData() {
        NetworkModule.service().getData().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({it ->
                if (it.isSuccess == true){
                    view.onSucces(it.message ?: "", it.data)
                } else{
                    view.onError(it.message ?: "")
                }
            },
                {e ->
                    view.onError(e.localizedMessage)
            })

    }

    fun getDelete(id: String) {
        val delete = NetworkModule.service().deleteData(id ?: "")
        delete.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                view.onInfo("Data Berhasil di Hapus")
            },{
                view.onError(it.localizedMessage)
            })
    }
}