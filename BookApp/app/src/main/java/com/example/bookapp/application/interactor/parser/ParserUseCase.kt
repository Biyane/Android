package com.example.bookapp.application.interactor.parser

//class ParserUseCase : IParserUseCase {
//    override fun parse(jsonString: String): BookDTO {
//        val root = JSONObject(jsonString)
//        val volumeInfo = root.getJSONArray("items").getJSONObject(0)
//            .getJSONObject("volumeInfo")
//        return if (volumeInfo !is JSONException) {
//            val gSon = GsonBuilder().registerTypeAdapter(BookDTO::class.java, BookDTODeserializer()).create()
//            gSon.fromJson(volumeInfo.toString(), BookDTO::class.java)
//        }
//        else throw JSONException("")
//    }
//}