package com.example.testalgostudioxml

import com.example.testalgostudioxml.model.InterfaceMeme
import com.example.testalgostudioxml.model.ModelMim


class ExampleUnitTest : InterfaceMeme {
    private val maps = mutableMapOf<Int, ModelMim>()
    override fun getAll() = maps.values.toList()

    override fun getMemeById(id: Int): ModelMim? {
        return maps[id]
    }

}