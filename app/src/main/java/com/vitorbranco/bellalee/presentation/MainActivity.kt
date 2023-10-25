package com.vitorbranco.bellalee.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import com.vitorbranco.bellalee.R
import com.vitorbranco.bellalee.data.local.Product
import com.vitorbranco.bellalee.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val adapter = ProductAdapter()
    private val viewModel by lazy {
        ProductViewModel.create()
    }

    private val productsList = mutableListOf<Product>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val rvProducts = binding.recyclerViewProducts
        rvProducts.adapter = adapter
        val progressBar = binding.progressBar

        viewModel.loadingLiveData.observe(this) { isLoading ->
            if (isLoading) {
                progressBar.visibility = View.VISIBLE
            } else {
                progressBar.visibility = View.GONE
            }
        }

        viewModel.productLiveData.observe(this) { productListDto ->
            productsList.clear()
            productsList.addAll(productListDto.map { productDto ->
                Product(
                    brand = productDto.brand,
                    name = productDto.name,
                    price = productDto.price,
                    apiFeaturedImage = productDto.apiFeaturedImage
                )
            })
            adapter.submitList(productsList)
        }

        val searchView = binding.searchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) {
                    val filteredList = productsList.filter { product ->
                        product.brand?.contains(newText, ignoreCase = true) ?: false ||
                                product.name?.contains(newText, ignoreCase = true) ?: false ||
                                product.price?.contains(newText, ignoreCase = true) ?: false
                    }
                    adapter.submitList(filteredList)
                }
                return true
            }
        })
    }

    fun showFilterMenu(view: View) {
        val popup = PopupMenu(this, view)
        val inflater = popup.menuInflater
        inflater.inflate(R.menu.filter_menu, popup.menu)

        popup.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.menu_option_maybelline -> {
                    val filteredList = productsList.filter { product ->
                        product.brand?.contains("Maybelline", true) ?: false
                    }
                    adapter.submitList(filteredList)
                    Toast.makeText(this, "Exibindo resultados para Maybelline", Toast.LENGTH_SHORT).show()
                    true
                }

                R.id.menu_option_nyx -> {
                    val filteredList = productsList.filter { product ->
                        product.brand?.contains("Nyx", true) ?: false
                    }
                    adapter.submitList(filteredList)
                    Toast.makeText(this, "Exibindo resultados para Nyx", Toast.LENGTH_SHORT).show()
                    true
                }

                R.id.menu_option_colourpop -> {
                    val filteredList = productsList.filter { product ->
                        product.brand?.contains("Colourpop", true) ?: false
                    }
                    adapter.submitList(filteredList)
                    Toast.makeText(this, "Exibindo resultados para Colourpop", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.menu_option_clear_filters -> {
                    adapter.submitList(productsList)
                    Toast.makeText(this, "Filtros resetados", Toast.LENGTH_SHORT).show()
                    true
                }

                else -> false
            }
        }
        popup.show()
    }
}