package com.blackpuppydev.webblog.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.blackpuppydev.webblog.listener.FragmentEvent
import com.blackpuppydev.webblog.R
import com.blackpuppydev.webblog.adapter.BlogAdapter
import com.blackpuppydev.webblog.api.ApiRepository
import com.blackpuppydev.webblog.constance.LandingPage
import kotlinx.android.synthetic.main.fragment_home.*
import java.lang.ClassCastException
import android.content.Context as Context

private const val ARG_BLOG = "blog"

class HomeFragment : Fragment() {

    private var blog: ArrayList<ApiRepository.Data>? = null

    private lateinit var listener:FragmentEvent


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            blog = it.getSerializable(ARG_BLOG) as ArrayList<ApiRepository.Data>
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try{
            listener = context as FragmentEvent
        }catch (e: ClassCastException){ }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        list_blog.layoutManager = LinearLayoutManager(context)



        list_blog.adapter = object : BlogAdapter(blog,context){
            override fun onItemClick(currentBlog: ArrayList<ApiRepository.Data>) {
                listener.onResult(currentBlog,LandingPage.PAGE_HOME)
            }
        }

        search.clearFocus()
        search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(text: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(text: String?): Boolean {
                filterList(text)
                return false
            }

        })
    }

    fun filterList(text:String?){

        val filterList:ArrayList<ApiRepository.Data> = arrayListOf()

        for (item in blog!!){
            if (text != null) {
                if (item.title.lowercase().contains(text.lowercase())){
                    filterList.add(item)
                }
            }
        }

        list_blog.adapter = object : BlogAdapter(filterList,context){
            override fun onItemClick(currentBlog: ArrayList<ApiRepository.Data>) {
                listener.onResult(currentBlog,LandingPage.PAGE_HOME)
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(blog: ArrayList<ApiRepository.Data>) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_BLOG, blog)
                }
            }
    }

}