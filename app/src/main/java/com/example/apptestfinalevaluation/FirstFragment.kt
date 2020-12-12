package com.example.apptestfinalevaluation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.apptestfinalevaluation.model.local.ProductEntity
import com.example.apptestfinalevaluation.viewModel.ProductViewModel
import kotlinx.android.synthetic.main.fragment_first.*

class FirstFragment : Fragment(), ProductAdapter.CallbackInterface {

    lateinit var adapter: ProductAdapter
    lateinit var mViewModel: ProductViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel = ViewModelProvider(this).get(ProductViewModel::class.java)
        adapter = ProductAdapter(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = recyclerView
        recyclerView.layoutManager = GridLayoutManager(context, 2)
        recyclerView.adapter = adapter

        mViewModel.exposeLiveDataFromDatabase().observe(viewLifecycleOwner, Observer {
            Log.d("VIEW", it.toString())
            adapter.updateAdapter(it)
        })
    }

    override fun passTheData(product: ProductEntity) {
        val bundle = Bundle()
        bundle.putString("id", product.id)
        findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment, bundle)
    }
}