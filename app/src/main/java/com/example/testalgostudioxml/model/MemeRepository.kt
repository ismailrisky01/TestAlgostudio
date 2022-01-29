package com.example.testalgostudioxml.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.testalgostudioxml.retrofit.NetworkConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MemeRepository : InterfaceMeme {
    override fun getAll(): LiveData<MutableList<ModelMim>> {
        val arrayList = MutableLiveData<MutableList<ModelMim>>()
        NetworkConfig().getService()
            .getAllMeme()
            .enqueue(object : Callback<ModelResponse> {
                val data = mutableListOf<ModelMim>()
                override fun onFailure(call: Call<ModelResponse>, t: Throwable) {
                    Log.d("IsmailJob", "${t.localizedMessage}")

                }

                override fun onResponse(
                    call: Call<ModelResponse>,
                    response: Response<ModelResponse>
                ) {
                    Log.d("IsmailJobRespon", "${response.body()?.data?.memes!!.size}")

                    response.body()?.data?.memes!!.forEach {
                        data.add(it)
                    }
                    arrayList.value = data
                }


            })
        return arrayList
    }

    override fun getMemeById(id: Int): ModelMim {
        TODO("Not yet implemented")
    }
}