package com.coufie.tugasestafet24mei

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.asLiveData
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class HomeFragment : Fragment() {

    private var userManager : UserManager? = null
    private var username = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userManager = UserManager(requireActivity())

        userManager!!.userUsername.asLiveData().observe(viewLifecycleOwner, {
            username = it.toString()
            usernametampil.text = username
        })

        logouthome.setOnClickListener {
            GlobalScope.launch {
                userManager!!.clearData()
            }
            Navigation.findNavController(requireView()).navigate(R.id.action_homeFragment_to_loginFragment)

        }

        btnprofile.setOnClickListener {
            Navigation.findNavController(requireView()).navigate(R.id.action_homeFragment_to_profileFragment)

        }
    }
}