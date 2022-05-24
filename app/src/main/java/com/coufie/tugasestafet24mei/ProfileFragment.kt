package com.coufie.tugasestafet24mei

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.asLiveData
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_profile.*

class ProfileFragment : Fragment() {

    private lateinit var userManager: UserManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userManager = UserManager(requireActivity())

        userManager.userId.asLiveData().observe(viewLifecycleOwner,{
            tv_id.text = "ID : ${it.toString()}"
        })

        userManager.userUsername.asLiveData().observe(viewLifecycleOwner,{
            tv_username.text = "Username : ${it.toString()}"
        })

        userManager.userAddress.asLiveData().observe(viewLifecycleOwner,{
            tv_address.text = "Address : ${it.toString()}"
        })

        userManager.userNama.asLiveData().observe(viewLifecycleOwner,{
            tv_nama.text = "Nama : ${it.toString()}"
        })

        userManager.userUmur.asLiveData().observe(viewLifecycleOwner,{
            tv_umur.text = "Umur : ${it.toString()}"
        })

        userManager.userImage.asLiveData().observe(viewLifecycleOwner,{
            if(it!=""){
                Glide.with(requireActivity()).load(it).into(iv_profileimage)
            }
        })
    }

}