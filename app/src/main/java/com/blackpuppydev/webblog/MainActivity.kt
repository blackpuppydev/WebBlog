package com.blackpuppydev.webblog

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.blackpuppydev.webblog.api.Api
import com.blackpuppydev.webblog.api.ApiRepository
import com.blackpuppydev.webblog.constance.LandingPage
import com.blackpuppydev.webblog.fragment.BookmarkFragment
import com.blackpuppydev.webblog.fragment.HomeFragment
import com.blackpuppydev.webblog.fragment.UnreadFragment
import com.blackpuppydev.webblog.listener.FragmentEvent
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity() , FragmentEvent{


    var listAll:ArrayList<ApiRepository.Data>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()

        getBlogger()

        navbar.setOnItemSelectedListener { item ->

            when (item.itemId) {
                R.id.home -> replaceFragment(HomeFragment.newInstance(listAll!!))
                R.id.ur_article -> replaceFragment(UnreadFragment.newInstance(listAll!!))
                R.id.bookmark -> replaceFragment(BookmarkFragment.newInstance(listAll!!))
                else -> {
                    Log.e("Fragment page : ","no fragment")
                }
            }

            true
        }


    }

    private fun replaceFragment(fragment:Fragment,isAppInit:Boolean = false){

        val fragmentTransaction = supportFragmentManager.beginTransaction()

        if (isAppInit) fragmentTransaction.add(R.id.frameLayout,fragment)
        else fragmentTransaction.replace(R.id.frameLayout,fragment)

        fragmentTransaction.commit()
    }



    private fun getBlogger()  {
        Api.getRetrofit().create(ApiRepository::class.java).getBlog().enqueue(object :
            Callback<ArrayList<ApiRepository.Data>> {
            override fun onResponse(call: Call<ArrayList<ApiRepository.Data>>?, response: Response<ArrayList<ApiRepository.Data>>?) {
                if (response != null) {
                    listAll  = response.body()
                    replaceFragment(HomeFragment.newInstance(response.body()),true)
                }
            }

            override fun onFailure(call: Call<ArrayList<ApiRepository.Data>>?, t: Throwable?) {
                Log.e("onFailure : ","cannot get data from api -> " + t?.message.toString())
            }
        })
    }

    override fun onResult(result: ArrayList<ApiRepository.Data>, landingPage: String) {

        listAll = result

        when (landingPage) {
            LandingPage.PAGE_BOOKMARK -> replaceFragment(BookmarkFragment.newInstance(result))
            LandingPage.PAGE_UNREAD -> replaceFragment(UnreadFragment.newInstance(result))
        }
    }





}





