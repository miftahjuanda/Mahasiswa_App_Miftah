package com.udacoding.mahasiswa_app_miftah.view

import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.udacoding.mahasiswa_app_miftah.R
import com.udacoding.mahasiswa_app_miftah.model.action.ResponseAction
import com.udacoding.mahasiswa_app_miftah.model.getData.DataItem
import com.udacoding.mahasiswa_app_miftah.network.NetworkModule
import kotlinx.android.synthetic.main.activity_add.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class addActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val getData = intent.getParcelableExtra<DataItem>("data")

        if (getData != null) {
            edt_nama.setText(getData.mahasiswaNama)
            edt_hp.setText(getData.mahasiswaNohp)
            edt_alamat.setText(getData.mahasiswaAlamat)

            btn_save.text = "Update"
        }

        when (btn_save.text) {
            "Update" -> {
                btn_save.setOnClickListener {
                    updateData(
                        getData?.idMahasiswa,
                        edt_nama.text.toString(),
                        edt_hp.text.toString(),
                        edt_alamat.text.toString()
                    )
                }
            }
            else -> {
                btn_save.setOnClickListener {
                    inputData(
                        edt_nama.text.toString(),
                        edt_hp.text.toString(),
                        edt_alamat.text.toString()
                    )
                }
            }
        }

        btn_cancel.setOnClickListener {
            finish()
        }
    }

    private fun inputData(nama: String?, nohp: String?, alamat: String?) {
        val input = NetworkModule.service().insertData(
            nama ?: "", nohp ?: "", alamat ?: ""
        )
        input.enqueue(object : Callback<ResponseAction> {
            override fun onResponse(
                call: Call<ResponseAction>,
                response: Response<ResponseAction>
            ) {
                Toast.makeText(applicationContext, "Data Disimpan", Toast.LENGTH_SHORT).show()
                finish()
            }

            override fun onFailure(call: Call<ResponseAction>, t: Throwable) {
                Toast.makeText(applicationContext, t.message, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun updateData(id: String?, nama: String?, nohp: String?, alamat: String?) {
        val input = NetworkModule.service().updateData(
            id ?: "", nama ?: "", nohp ?: "", alamat ?: ""
        )
        input.enqueue(object : Callback<ResponseAction> {
            override fun onResponse(
                call: Call<ResponseAction>,
                response: Response<ResponseAction>
            ) {
                Toast.makeText(applicationContext, "Data Terupdate", Toast.LENGTH_SHORT).show()
                finish()
            }

            override fun onFailure(call: Call<ResponseAction>, t: Throwable) {
                Toast.makeText(applicationContext, t.message, Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        } else {
            Toast.makeText(applicationContext, item.title, Toast.LENGTH_SHORT).show()
        }
        return super.onOptionsItemSelected(item)
    }
}