package com.udacoding.mahasiswa_app_miftah.ui.add.view

import android.os.Bundle
import android.view.MenuItem
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.udacoding.mahasiswa_app_miftah.R
import com.udacoding.mahasiswa_app_miftah.model.action.ResponseAction
import com.udacoding.mahasiswa_app_miftah.model.getData.DataItem
import com.udacoding.mahasiswa_app_miftah.ui.add.presenter.AddPresenter
import com.udacoding.mahasiswa_app_miftah.ui.add.presenter.AddView
import kotlinx.android.synthetic.main.activity_add.*

class AddActivity : AppCompatActivity(), AddView {

    private var addPresenter: AddPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        addPresenter = AddPresenter(this)

        initData()
    }

    private fun initData() {
        val getData = intent.getParcelableExtra<DataItem>("data")

        if (getData != null) {
            edt_nama.setText(getData.mahasiswaNama)
            edt_hp.setText(getData.mahasiswaNohp)
            edt_alamat.setText(getData.mahasiswaAlamat)

            btn_save.text = getString(R.string.update)
        }

        when (btn_save.text) {
            "Update" -> {
                btn_save.setOnClickListener {
                    val nama = edt_nama.text.toString()
                    val hp = edt_hp.text.toString()
                    val alamat = edt_alamat.text.toString()

                    getData?.idMahasiswa?.let { it1 ->
                        addPresenter?.getUpdate(it1, nama, hp, alamat)
                    }
                    finish()
                }
            }
            else -> {
                btn_save.setOnClickListener {
                    val nama = edt_nama.text.toString()
                    val hp = edt_hp.text.toString()
                    val alamat = edt_alamat.text.toString()

                    if (nama.isEmpty() != hp.isEmpty() != alamat.isEmpty()) {

                        if (nama.isEmpty()) {
                            edt_nama.error = "Lengkapi Nama"
                        }

                        if (hp.isEmpty()) {
                            edt_hp.error = "Lengkapi Nama"
                        }

                        if (alamat.isEmpty()) {
                            edt_alamat.error = "Lengkapi Nama"
                        }

                    } else {
                        addPresenter?.getInput(nama, hp, alamat)
                    }
                }
            }
        }

        btn_cancel.setOnClickListener {
            finish()
        }
    }

    override fun onSucces(responseAdd: ResponseAction) {
        Toast.makeText(applicationContext, "Berhasil di Tambahkan", Toast.LENGTH_SHORT).show()
        finish()
    }

    override fun onError(msg: String) {
        Toast.makeText(applicationContext, msg, Toast.LENGTH_SHORT).show()
    }

    override fun addSuccess(msg: String) {
    }

    override fun addEmpty() {
        Toast.makeText(applicationContext, "Isian Harus di Isi", Toast.LENGTH_SHORT).show()
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