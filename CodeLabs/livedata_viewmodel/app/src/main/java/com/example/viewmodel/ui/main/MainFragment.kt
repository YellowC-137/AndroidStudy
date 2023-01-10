package com.example.viewmodel.ui.main

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.SavedStateViewModelFactory
import androidx.savedstate.SavedStateRegistry
import androidx.savedstate.SavedStateRegistryOwner
import com.example.viewmodel.R
import com.example.viewmodel.databinding.FragmentMainBinding

class MainFragment : Fragment(){

    private lateinit var binding: FragmentMainBinding
    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory() // TODO: ViewModel Factory! SavedStateViewModelFactory
        ).get(MainViewModel::class.java)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)
        binding.lifecycleOwner = this

        binding.vm = viewModel
        binding.ui = this
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val observer = Observer<Float> { result ->
            binding.tv.text = result.toString()
        }
        viewModel.result().observe(viewLifecycleOwner, observer)

        binding.apply {
            btn.setOnClickListener {
                if (et.text.isNullOrEmpty()) {
                    tv.text = "null"
                } else {
                    viewModel.set(et.text.toString())
                    tv.text = viewModel.result().toString()
                }
            }

        }




    }

}