package com.udacoding.mahasiswa_app_miftah.network

import com.udacoding.mahasiswa_app_miftah.model.action.ResponseAction
import com.udacoding.mahasiswa_app_miftah.model.getData.ResponseGetData
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    //getData
    @GET("getData.php")
    fun getData(): Call<ResponseGetData>

    //getDataById
    @GET("getData.php")
    fun getDataById(@Query("id_mahasiswa") id_mahasiswa: String): Call<ResponseGetData>

    //insert
    @FormUrlEncoded
    @POST("insert.php")
    fun insertData(@Field("mahasiswa_nama") mahasiswa_nama : String,
                    @Field("mahasiswa_nohp") mahasiswa_nohp : String,
                    @Field("mahasiswa_alamat") mahasiswa_alamat : String ) : Call<ResponseAction>

    //update
    @FormUrlEncoded
    @POST("update.php")
    fun updateData(@Field("id_mahasiswa") id_mahasiswa : String,
                   @Field("mahasiswa_nama") mahasiswa_nama : String,
                   @Field("mahasiswa_nohp") mahasiswa_nohp : String,
                   @Field("mahasiswa_alamat") mahasiswa_alamat : String ) : Call<ResponseAction>

    //delete
    @FormUrlEncoded
    @POST("delete.php")
    fun deleteData(@Field("id_mahasiswa") id_mahasiswa : String) : Call<ResponseAction>
}