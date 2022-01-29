package com.example.testalgostudioxml.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.testalgostudioxml.R
import com.example.testalgostudioxml.databinding.ItemDashboardBinding
import com.example.testalgostudioxml.model.ModelMim
import com.squareup.picasso.Picasso

class AdapterDashboard() : RecyclerView.Adapter<AdapterDashboard.ViewHolder>() {
    private var dataAdapter = mutableListOf<ModelMim>()
    private var unit: String? = null
    fun setData(data: MutableList<ModelMim>) {
        dataAdapter = data
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemDashboardBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.initData(dataAdapter[position])
    }

    override fun getItemCount(): Int {
        return dataAdapter.size
    }

    class ViewHolder(val binding: ItemDashboardBinding) : RecyclerView.ViewHolder(binding.root) {
        fun initData(modelMim: ModelMim) {
            Picasso.get().load(modelMim.url).into(binding.IDDashboardImageView)
            binding.IDDashboardImageView.setOnClickListener {
                val bundle = Bundle()
                bundle.putInt("id",modelMim.id)
                it.findNavController().navigate(R.id.action_dashboardFragment_to_detailFragment,bundle)
            }
        }
    }

}