package com.udacoding.mahasiswa_app_miftah.ui.beranda.view

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.udacoding.mahasiswa_app_miftah.R
import com.udacoding.mahasiswa_app_miftah.adapter.AdapterMahasiswa
import com.udacoding.mahasiswa_app_miftah.model.getData.DataItem
import com.udacoding.mahasiswa_app_miftah.model.getData.ResponseGetData
import com.udacoding.mahasiswa_app_miftah.ui.add.view.AddActivity
import com.udacoding.mahasiswa_app_miftah.ui.beranda.presenter.MainPresenter
import com.udacoding.mahasiswa_app_miftah.ui.beranda.presenter.MainView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.delete.*
import kotlinx.android.synthetic.main.no_internet.*
import retrofit2.Response

class MainActivity : AppCompatActivity(), MainView {

    //todo 7 deklarasi variable presenter
    private var presenter: MainPresenter? = null
    private var response: Response<ResponseGetData>? = null

    companion object {
        val id : String = "id"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        setSupportActionBar(toolbar)

        //todo 8 init presenter
        presenter = MainPresenter(this)
        presenter?.getData()

        fab.setOnClickListener {
            val intent = Intent(this, AddActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onSucces(msg: String, dataItem: List<DataItem>?) {

        val adapter = AdapterMahasiswa(dataItem, object : AdapterMahasiswa.ItemClick {
            override fun onClick(item: DataItem?) {
                pb_main.visibility = View.GONE

                val intent = Intent(applicationContext, AddActivity::class.java)
                intent.putExtra("data", item)
                startActivity(intent)
            }

            override fun delete(item: DataItem?) {
                showDeleteDialog(item)
            }
        })
        rv_list.adapter = adapter
    }

    override fun onInfo(msg: String) {
    }

    override fun onError(msg: String) {
    }

    private fun isConnect(): Boolean {
        val connect: ConnectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        return connect.activeNetworkInfo != null && connect.activeNetworkInfo.isConnected
    }

    private fun showDeleteDialog(item: DataItem?) {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.delete)

        dialog.btn_hapus.setOnClickListener {
            item?.idMahasiswa?.let { it1 -> presenter?.getDelete(it1) }
            presenter?.getData()
            dialog.dismiss()
        }

        dialog.btn_batal.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }

    private fun showCustomDialog() {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.no_internet)
        dialog.setCancelable(true)

        dialog.btn_close.setOnClickListener {
            finishAffinity()
            finish()
            dialog.dismiss()
        }
        dialog.show()
    }

    override fun onBackPressed() {
        finishAffinity()
        finish()
    }

    override fun onResume() {
        super.onResume()
        presenter?.getData()
    }
}