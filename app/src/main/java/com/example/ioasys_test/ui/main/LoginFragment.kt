package com.example.ioasys_test.ui.main

import android.accounts.AccountManager
import android.accounts.AccountManagerCallback
import android.accounts.AccountManagerFuture
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat.startActivityForResult
import com.example.ioasys_test.*
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.HttpURLConnection
import java.net.URL

class LoginFragment : Fragment() {

    companion object {
        fun newInstance() = LoginFragment()
    }

    private lateinit var sessionManager: SessionManager
    private lateinit var apiClient: ApiClient


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        apiClient = ApiClient()
        sessionManager = SessionManager(requireContext())
        doRequestAuthentication()
        fetchPosts()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    @DelicateCoroutinesApi
    fun doRequestAuthentication(){
        GlobalScope.async {
            apiClient.getApiService(requireContext()).login(LoginRequest(email = "testeapple@ioasys.com.br", password = "12341234"))
                .enqueue(object : Callback<LoginResponse> {
                    override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                        // Error logging in
                    }

                    override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                        val loginResponse = response.body()
                    }
                })
        }
    }

    /**
     * Function to fetch posts
     */
    private fun fetchPosts() {
        CoroutineScope(IO).launch {
            apiClient.getApiService(requireContext()).fetchPosts()
                .enqueue(object : Callback<PostsResponse> {
                    override fun onFailure(call: Call<PostsResponse>, t: Throwable) {
                        // Error fetching posts
                    }

                    override fun onResponse(call: Call<PostsResponse>, response: Response<PostsResponse>) {
                        // Handle function to display posts
                    }
                })
        }
    }

}