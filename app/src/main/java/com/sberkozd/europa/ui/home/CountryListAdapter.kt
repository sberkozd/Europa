package com.sberkozd.europa.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sberkozd.europa.data.models.Country
import com.sberkozd.europa.databinding.ItemCountriesBinding
import com.sberkozd.europa.loadSvg

class CountryListAdapter(val clickListener: (country: Country) -> Unit
) : ListAdapter<Country, CountryListAdapter.CountryViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        val binding = ItemCountriesBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return CountryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        holder.binding.apply {
            tvCountryAndCityName.text = "${getItem(position).name?.common} / ${getItem(position).capital?.firstOrNull().toString()}"
            tvCountrySubRegion.text = getItem(position).subregion
            ivCountryFlag.loadSvg(getItem(position).flags?.svg)
            root.setOnClickListener {
                clickListener(getItem(holder.adapterPosition))
            }
        }
    }

    override fun submitList(list: MutableList<Country>?) {
        super.submitList(list?.map { it.copy() }?.toMutableList())
    }

    class CountryViewHolder(val binding: ItemCountriesBinding) :
        RecyclerView.ViewHolder(binding.root)

    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<Country> =
            object : DiffUtil.ItemCallback<Country>() {
                override fun areItemsTheSame(oldItem: Country, newItem: Country) =
                    oldItem.cca2 == newItem.cca2

                override fun areContentsTheSame(
                    oldItem: Country,
                    newItem: Country,
                ) = oldItem == newItem
            }
    }
}