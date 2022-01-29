package com.example.testalgostudioxml.model

data class Response(
	val data: Data? = null,
	val success: Boolean? = null
)

data class MemesItem(
	val name: String? = null,
	val width: Int? = null,
	val id: String? = null,
	val url: String? = null,
	val height: Int? = null,
	val boxCount: Int? = null
)

data class Data(
	val memes: List<MemesItem?>? = null
)

