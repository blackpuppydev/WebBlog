package com.blackpuppydev.webblog.listener

import com.blackpuppydev.webblog.api.Api
import com.blackpuppydev.webblog.api.ApiRepository

interface FragmentEvent {

    fun onResult(result:ArrayList<ApiRepository.Data>,landingPage:String)

}