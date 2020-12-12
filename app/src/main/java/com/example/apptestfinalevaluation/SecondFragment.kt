package com.example.apptestfinalevaluation

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.apptestfinalevaluation.viewModel.ProductViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.fragment_second.*

class SecondFragment : Fragment() {

    var id: String = ""
    var name: String = ""

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
        return inflater.inflate(R.layout.fragment_second, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d("VIEW", id)
        id?.let {
            mViewModel.productById(it).observe(viewLifecycleOwner, Observer {
                Log.i("Product", it.toString())
                context?.let { it1 -> Glide.with(it1).load(it.image).into(iv_product_2) }
                tv_name.text = it.name
                name = it.name
                tv_description.text = it.description
                tv_card.text = if (it.credit!!) "Acepta Crédito!!" else "Sólo Efectivo"
                tv_price.text =
                    "precio = $ " + it.price.toString() + "\nprecio actual = " + it.lastPrice.toString()

            })

        }
        view.findViewById<Button>(R.id.button_second).setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }
        view.findViewById<FloatingActionButton>(R.id.fab).setOnClickListener {
            val consulta = "Consulta ${name} id ${id}"
            val mensaje ="Hola\n" +
                    "Vi la propiedad ${name} de código ${id} y me gustaría que me\n" +
                    "contactaran a este correo o al siguiente número _________\n" +
                    "Quedo atento. "
            val destino ="info@novaera.cl"
            sendEmail(destino, consulta, mensaje)
        }
    }

    private fun sendEmail(recipient: String, subject: String, message: String) {
        val mIntent = Intent(Intent.ACTION_SEND)
        mIntent.type = "text/plain"
        mIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(recipient))
        mIntent.putExtra(Intent.EXTRA_SUBJECT, subject)
        mIntent.putExtra(Intent.EXTRA_TEXT, message)

        try {
            startActivity(Intent.createChooser(mIntent, "email...."))
        } catch (e: Exception) {
            Log.d("error", e.toString())
        }

    }
}