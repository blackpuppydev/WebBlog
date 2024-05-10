package com.blackpuppydev.webblog.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.blackpuppydev.webblog.R
import com.blackpuppydev.webblog.adapter.BlogAdapter
import com.blackpuppydev.webblog.api.ApiRepository
import com.blackpuppydev.webblog.constance.LandingPage
import com.blackpuppydev.webblog.listener.FragmentEvent
import kotlinx.android.synthetic.main.fragment_bookmark.*
import java.lang.ClassCastException


private const val ARG_UNREAD = "unread"


class UnreadFragment : Fragment() {

    private var blog: ArrayList<ApiRepository.Data>? = null
    val bookmarkUnread = arrayListOf<ApiRepository.Data>()

    private lateinit var listener: FragmentEvent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            blog = it.getSerializable(ARG_UNREAD) as ArrayList<ApiRepository.Data>
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try{
            listener = context as FragmentEvent
        }catch (e: ClassCastException){ }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_unread, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        list_blog.layoutManager = LinearLayoutManager(context)


        for (itemBlog in blog!!){
            if (!itemBlog.read){
                bookmarkUnread.add(itemBlog)
            }
        }

        list_blog.adapter = object : BlogAdapter(bookmarkUnread){
            override fun onItemClick(currentBlog: ArrayList<ApiRepository.Data>) {

                for (baseBlog in blog!!){
                    for (current in currentBlog){
                        if (baseBlog.title == current.title){
                            baseBlog.read = current.read
                        }
                    }
                }

                listener.onResult(blog!!, LandingPage.PAGE_UNREAD)
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

        for (item in bookmarkUnread){
            if (text != null) {
                if (item.title.lowercase().contains(text.lowercase())){
                    filterList.add(item)
                }
            }
        }

        list_blog.adapter = object : BlogAdapter(filterList){
            override fun onItemClick(currentBlog: ArrayList<ApiRepository.Data>) {
                for (baseBlog in blog!!){
                    for (current in currentBlog){
                        if (baseBlog.title == current.title){
                            baseBlog.read = current.read
                        }
                    }
                }
                listener.onResult(blog!!, LandingPage.PAGE_UNREAD)
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(blog: ArrayList<ApiRepository.Data>) =
            UnreadFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_UNREAD, blog)
                }
            }
    }

}