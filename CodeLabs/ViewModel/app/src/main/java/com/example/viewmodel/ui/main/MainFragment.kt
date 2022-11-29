package com.example.viewmodel.ui.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.viewmodel.R
import com.example.viewmodel.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!



    companion object {
        fun newInstance() = MainFragment()
    }




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        binding.btn.setOnClickListener {
            if (binding.et.text.isNotEmpty()){
                viewModel.Cal(binding.et.text.toString().toInt())
                binding.message.text = viewModel.getResult().toString()
            }
        }

    }

}