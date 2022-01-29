package com.example.testalgostudioxml.model

import androidx.lifecycle.LiveData

interface InterfaceMeme {
fun getAll(): LiveData<MutableList<ModelMim>>
fun getMemeById(id:Int): ModelMim?
}