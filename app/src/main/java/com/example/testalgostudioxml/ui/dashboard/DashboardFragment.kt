package com.example.testalgostudioxml.ui.dashboard

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.testalgostudioxml.databinding.FragmentDashboardBinding
import com.example.testalgostudioxml.model.MemeViewModel
import com.example.testalgostudioxml.model.ModelMim


class DashboardFragment : Fragment() {
    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!
    val mViewModel by viewModels<MemeViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mViewModel.getModelMim.observe(viewLifecycleOwner, Observer {
            val adapter = AdapterDashboard()
            binding.IDDashboardRecyclerview.layoutManager = GridLayoutManager(requireContext(), 3)
            binding.IDDashboardRecyclerview.adapter = adapter
            adapter.setData(it)
        })
        val array = ArrayList<ModelMim>()
        array.add(ModelMim(0, "asas", "asas", 121, 121, 12))
        array.add(ModelMim(0, "asas", "asas", 121, 121, 12))
        array.add(ModelMim(0, "asas", "asas", 121, 121, 12))
        array.add(ModelMim(0, "asas", "asas", 121, 121, 12))
        array.add(ModelMim(0, "asas", "asas", 121, 121, 12))
        array.add(ModelMim(0, "asas", "asas", 121, 121, 12))


    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}