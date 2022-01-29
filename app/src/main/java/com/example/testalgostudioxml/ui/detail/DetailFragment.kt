package com.example.testalgostudioxml.ui.detail

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.graphics.*
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.testalgostudioxml.databinding.FragmentDetailBinding
import com.example.testalgostudioxml.model.MemeViewModel
import com.example.testalgostudioxml.model.ModelMim
import com.example.testalgostudioxml.util.Util
import com.squareup.picasso.Picasso
import kotlinx.coroutines.launch
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import com.example.testalgostudioxml.R
import retrofit2.http.Url
import java.io.*
import java.util.*

class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    val mViewModel by viewModels<MemeViewModel>()
    val REQUEST_CODE = 100
    var bitmapString: String = ""
    var imageBackgroundUrl = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.IDDetailBtnSelesai.visibility = View.GONE
        binding.IDDetailBtnSimpan.visibility = View.GONE
        val id = arguments?.getInt("id")!!
        //Show Detail
        mViewModel.getModelMim.observe(viewLifecycleOwner, {
            val list = it.filter { it.id == id }.first()

            if (list.url.isNotEmpty()) {
                imageBackgroundUrl = list.url
                Picasso.get().load(list.url).into(binding.IDDetailImageView)
                lifecycleScope.launch {
                    val bitmapBackground = mViewModel.getImage(
                        requireContext(), list.url
                    )
                    bitmapString = Util().BitMapToString(bitmapBackground)!!
                }
            } else {
                Toast.makeText(requireContext(), "Hallo", Toast.LENGTH_SHORT).show()
            }
        })
        //Fungsi Button Kembali
        binding.IDDetailBtnBack.setOnClickListener {
            findNavController().navigateUp()
        }
        //Fungsi Buttton Ambil gambar dari galley
        binding.IDDetailBtnUploadImage.setOnClickListener {
            uploadImage()
        }
        binding.IDDetailUploadText.setOnClickListener {
            val bitmap = Util().StringToBitMap(bitmapString)?.let { it1 ->
                Util().drawTextToBitmap(
                    requireContext(),
                    it1, 15, "Risky"
                )
            }
            bitmapString = Util().BitMapToString(bitmap!!)!!
            binding.IDDetailImageView.setImageBitmap(bitmap)
            binding.IDDetailBtnSelesai.visibility = View.VISIBLE
            binding.IDDetailBtnSimpan.visibility = View.VISIBLE
            binding.IDDetailUploadText.visibility = View.GONE
            binding.IDDetailBtnUploadImage.visibility = View.GONE
            binding.textView3.visibility = View.GONE
            binding.textView4.visibility = View.GONE

        }
        binding.IDDetailBtnSelesai.setOnClickListener {
            if (bitmapString != "") {
                val Uri =
                    getImageUriFromBitmap(requireContext(), Util().StringToBitMap(bitmapString)!!)
                Log.d("IsmailJobBitmap", Uri.toString())
                val bundle = Bundle()
                bundle.putString("uriString", Uri.toString())
                findNavController().navigate(R.id.action_detailFragment_to_doneFragment, bundle)
            }

        }

    }

    private fun uploadImage() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, REQUEST_CODE)
    }


    private fun getImageUriFromBitmap(context: Context, bitmap: Bitmap): Uri {
        val bytes = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path =
            MediaStore.Images.Media.insertImage(context.contentResolver, bitmap, "Title", null)
        return Uri.parse(path.toString())
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE) {
            Log.d("IsmailJobUri", data?.data.toString())
            lifecycleScope.launch {
                var bitmap = mViewModel.getImage(
                    requireContext(),
                    data?.data.toString()
                )// handle chosen image

                bitmap = Util().getResizedBitmap(bitmap, 60, 60)!!
                val bitmapBackground = mViewModel.getImage(
                    requireContext(), imageBackgroundUrl
                )
                bitmapString = Util().BitMapToString(bitmapBackground)!!

                val result = Bitmap.createBitmap(
                    bitmapBackground.width,
                    bitmapBackground.height,
                    bitmapBackground.config
                )
                val canvas = Canvas(result)
                canvas.drawBitmap(bitmapBackground, Matrix(), null)

                val imagePosX = (bitmapBackground.width / 3) / 2f
                val imagePosY = ((bitmapBackground.height / 2) / 2f) - 120

                canvas.drawBitmap(bitmap, imagePosX, imagePosY, null)

                binding.IDDetailImageView.setImageBitmap(
                    result
                )
                bitmapString = Util().BitMapToString(result)!!
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}