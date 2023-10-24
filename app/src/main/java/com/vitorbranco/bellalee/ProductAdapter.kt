package com.vitorbranco.bellalee

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.vitorbranco.bellalee.data.Product
import coil.load
import coil.transform.RoundedCornersTransformation

class ProductAdapter : ListAdapter<Product, ProductViewHolder>(ProductAdapter) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_product, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = getItem(position)
        holder.bind(product)
    }

    companion object : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.name == newItem.name &&
                    oldItem.imageLink == newItem.imageLink
        }
    }
}

class ProductViewHolder(
    private val view: View
) : RecyclerView.ViewHolder(view) {

    private val tvBrand = view.findViewById<TextView>(R.id.text_view_product_brand)
    private val tvName = view.findViewById<TextView>(R.id.text_view_product_name)
    private val tvPrice = view.findViewById<TextView>(R.id.text_view_product_price)
    private val ivImage = view.findViewById<ImageView>(R.id.image_view_product)

    fun bind(
        product: Product
    ) {
        tvBrand.text = capitalizeFirstLetter(product.brand)
        tvName.text = product.name
        tvPrice.text = product.price

        ivImage.load(product.imageLink) {
            transformations(RoundedCornersTransformation(16f))
        }
    }

    private fun capitalizeFirstLetter(input: String): String {
        if (input.isEmpty()) {
            return input
        }
        return input.substring(0, 1).uppercase() + input.substring(1).lowercase()
    }

}
