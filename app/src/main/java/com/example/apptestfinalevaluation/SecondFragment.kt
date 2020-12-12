package com.example.apptestfinalevaluation

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.apptestfinalevaluation.viewModel.ProductViewModel
import kotlinx.android.synthetic.main.fragment_second.*

class SecondFragment : Fragment() {

    var id: String = ""
    lateinit var mViewModel: ProductViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel = ViewModelProvider(this).get(ProductViewModel::class.java)

        arguments.let {
            id = arguments?.getString("id") ?: ""
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_second, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d("VIEW", id)
        id?.let {
            mViewModel.productById(it).observe(viewLifecycleOwner, Observer {
                Log.i("Product", it.toString())
                textview_second.text = it.name
            }
            )

        }
        view.findViewById<Button>(R.id.button_second).setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }
    }
}