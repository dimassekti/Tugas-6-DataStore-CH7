package com.coufie.tugasestafet24mei

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.asLiveData
import androidx.navigation.Navigation
import com.coufie.tugasestafet24mei.model.GetUserItem
import com.coufie.tugasestafet24mei.network.ApiClient
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response

class LoginFragment : Fragment() {

    lateinit var email : String
    lateinit var password : String
    private var userManager : UserManager? = null
    private lateinit var userLogin : List<GetUserItem>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_login, container, false)

        // Inflate the layout for this fragment
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userManager = UserManager(requireActivity())

        userManager!!.userUsername.asLiveData().observe(viewLifecycleOwner,{
            if(it != ""){
                Navigation.findNavController(requireView()).navigate(R.id.action_loginFragment_to_homeFragment)
            }
        })


        btnLogin.setOnClickListener {
            if(loginEmail.text.isNotEmpty() && loginPassword.text.isNotEmpty()){
                email = loginEmail.text.toString()
                password = loginPassword.text.toString()

                login(email, password)
            }else{
                Toast.makeText(requireContext(), "Mohon isi form login", Toast.LENGTH_SHORT).show()

            }
        }

    }

    fun login(username : String, password : String){
        ApiClient.instance.getUser(username)
            .enqueue(object : retrofit2.Callback<List<GetUserItem>>{
                override fun onResponse(
                    call: Call<List<GetUserItem>>,
                    response: Response<List<GetUserItem>>
                ) {
                    if(response.isSuccessful){
                        if(response.body()?.isEmpty() == true){
                            Toast.makeText(requireContext(), "User tidak ditemukan", Toast.LENGTH_SHORT).show()
                        }else{
                            when{
                                response.body()?.size!! > 1 -> {
                                    Toast.makeText(requireContext(), "Mohon masukan data yang benar", Toast.LENGTH_SHORT).show()
                                }
                                username!=response.body()!![0].username -> {
                                    Toast.makeText(requireContext(), "Email tidak terdaftar", Toast.LENGTH_SHORT).show()
                                }
                                password!=response.body()!![0].password -> {
                                    Toast.makeText(requireContext(), "Password Salah", Toast.LENGTH_SHORT).show()
                                }
                                else -> {
                                    userLogin = response.body()!!
                                    detailUser(userLogin)

                                    view?.let {
                                        Navigation.findNavController(it).navigate(R.id.action_loginFragment_to_homeFragment)
                                    }
                                }
                            }
                        }
                    }else{
                        Toast.makeText(requireContext(), response.message(), Toast.LENGTH_SHORT).show()

                    }
                }

                override fun onFailure(call: Call<List<GetUserItem>>, t: Throwable) {
                    Toast.makeText(requireContext(), t.message, Toast.LENGTH_SHORT).show()
                }

            })
    }

    fun detailUser(listLogin : List<GetUserItem>){
        for(i in listLogin.indices){
            GlobalScope.launch {
                userManager!!.saveData(
                    listLogin[i].username,
                    listLogin[i].password,
                    listLogin[i].name,
                    listLogin[i].umur.toString(),
                    listLogin[i].image.toString(),
                    listLogin[i].address,
                    listLogin[i].id
                )
            }
        }
    }

}