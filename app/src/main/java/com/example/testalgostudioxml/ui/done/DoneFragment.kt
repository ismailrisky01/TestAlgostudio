package com.example.testalgostudioxml.ui.done

import android.content.ActivityNotFoundException
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.testalgostudioxml.databinding.FragmentDashboardBinding
import com.example.testalgostudioxml.databinding.FragmentDoneBinding
import com.example.testalgostudioxml.model.MemeViewModel
import com.example.testalgostudioxml.util.Util
import android.content.Intent
import android.widget.Toast
import android.graphics.BitmapFactory
import androidx.navigation.fragment.findNavController
import java.io.File


class DoneFragment : Fragment() {
    private var _binding: FragmentDoneBinding? = null
    private val binding get() = _binding!!
    val mViewModel by viewModels<MemeViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDoneBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val uriString = arguments?.getString("uriString")
        val uri = Uri.parse(uriString)
        binding.IDDoneBtnBack.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.IDDoneImageView.setImageURI(uri)
        binding.IDDoneBtnFB.setOnClickListener {
            fn_share(uri)
        }
        binding.IDDoneBtnTwitter.setOnClickListener {
            fn_share(uri)
        }
    }
    //For Whatsapp
    fun wa_share(uri: Uri) {
        val shareIntent = Intent()
        shareIntent.action = Intent.ACTION_SEND
        //Target whatsapp:
        //Target whatsapp:
        shareIntent.setPackage("com.whatsapp")
        //Add text and then Image URI
        //Add text and then Image URI
        shareIntent.putExtra(Intent.EXTRA_TEXT, "AlgoStudio")
        shareIntent.putExtra(Intent.EXTRA_STREAM, uri)
        shareIntent.type = "image/jpeg"
        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)

        try {
            startActivity(shareIntent)
        } catch (ex: ActivityNotFoundException) {
            Toast.makeText(requireContext(), "${ex.localizedMessage}", Toast.LENGTH_SHORT).show()
        }
    }

    fun fn_share(uri: Uri) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "image/*"
        intent.putExtra(Intent.EXTRA_STREAM, uri)
        startActivity(Intent.createChooser(intent, "Share Image"))
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}