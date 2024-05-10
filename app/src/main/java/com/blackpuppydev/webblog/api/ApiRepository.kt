package com.blackpuppydev.webblog.api

import com.google.gson.annotations.SerializedName
import org.parceler.Parcel
import retrofit2.Call
import retrofit2.http.GET

interface ApiRepository {

    @GET("/posts")
    fun getBlog(): Call<ArrayList<Data>>


    class Data {

        var userId = ""
        var id = ""
        var title = ""
        var body = ""

        var read = false
        var bookMark = false


//        {
//            "userId": 1,
//            "id": 1,
//            "title": "sunt aut facere repellat provident occaecati excepturi optio reprehenderit",
//            "body": "quia et suscipit\nsuscipit recusandae consequuntur expedita et cum\nreprehenderit molestiae ut ut quas totam\nnostrum rerum est autem sunt rem eveniet architecto"
//        }


    }


}