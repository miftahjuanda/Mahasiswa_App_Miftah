package com.udacoding.mahasiswa_app_miftah.view

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.udacoding.mahasiswa_app_miftah.R
import com.udacoding.mahasiswa_app_miftah.adapter.AdapterMahasiswa
import com.udacoding.mahasiswa_app_miftah.model.action.ResponseAction
import com.udacoding.mahasiswa_app_miftah.model.getData.DataItem
import com.udacoding.mahasiswa_app_miftah.model.getData.ResponseGetData
import com.udacoding.mahasiswa_app_miftah.network.NetworkModule
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.delete.*
import kotlinx.android.synthetic.main.no_internet.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)

        fab.setOnClickListener {
            val intent = Intent(this, addActivity::class.java)
            startActivity(intent)
        }

        showData()
    }

    private fun showData() {
        if (isConnect()) {
            val listMahasiswa = NetworkModule.service().getData()
            listMahasiswa.enqueue(object : Callback<ResponseGetData> {
                override fun onResponse(
                    call: Call<ResponseGetData>,
                    response: Response<ResponseGetData>
                ) {
                    if (response.isSuccessful) {
                        val item = response.body()?.data

                        val adapter = AdapterMahasiswa(item, object : AdapterMahasiswa.ItemClick {
                            override fun onClick(item: DataItem?) {
                                pb_main.visibility = View.GONE

                                val intent = Intent(applicationContext, addActivity::class.java)
                                intent.putExtra("data", item)
                                startActivity(intent)

                            }

                            override fun delete(item: DataItem?) {
                                showDeleteDialog(item)
                            }
                        })
                        rv_list.adapter = adapter
                    }
                }

                override fun onFailure(call: Call<ResponseGetData>, t: Throwable) {
                    Log.d("error server", t.message)
                    pb_main.visibility = View.GONE
                }
            })
        } else {
            pb_main.visibility = View.GONE
            showCustomDialog()
        }
    }

    private fun deleteData(id: String?) {
        val delete = NetworkModule.service().deleteData(id ?: "")
        delete.enqueue(object : Callback<ResponseAction> {
            override fun onResponse(
                call: Call<ResponseAction>,
                response: Response<ResponseAction>
            ) {
                Toast.makeText(applicationContext, "Data Dihapus", Toast.LENGTH_SHORT).show()
            }

            override fun onFailure(call: Call<ResponseAction>, t: Throwable) {
                Toast.makeText(applicationContext, t.message, Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun isConnect(): Boolean {
        val connect: ConnectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        return connect.activeNetworkInfo != null && connect.activeNetworkInfo.isConnected
    }

    private fun showDeleteDialog(item: DataItem?) {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.delete)
        dialog.setCancelable(true)

        dialog.btn_hapus.setOnClickListener {
            deleteData(item?.idMahasiswa)
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

    override fun onResume() {
        super.onResume()
        showData()
    }

    override fun onBackPressed() {
        finishAffinity()
        finish()
    }
}