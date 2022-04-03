package com.example.reto1aplication

import android.Manifest
import android.app.Activity.RESULT_CANCELED
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.core.content.FileProvider
import com.example.reto1aplication.databinding.FragmentNewPostBinding
import java.io.File
import java.text.SimpleDateFormat
import java.util.*


class NewPostFragment(private val userLogged:String?): Fragment() {

    private var _binding: FragmentNewPostBinding?=null
    private val binding get() = _binding!!
    private var permissionAccepted = false
    //STATE
    private var id:String= ""
    private var file:File?=null
    //Listerner
    var listener: OnNewPostListerner? = null
    
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNewPostBinding.inflate(inflater,container,false)
        var view = binding.root
        val newHomeFragment = NewHomeFragment.newInstance()
        binding.btnNewPost.setOnClickListener{

            listener?.let{
                //Aqui va la clase

                val title = binding.textTitulo.text.toString()
                val author = this.userLogged.toString()
                val city = binding.spinnerCities.selectedItem.toString()
                val date = getCurrentDateTime().toString("yyyy/MM/dd HH:mm:ss")
                val description = binding.textDescription.text.toString()
                if(title.isEmpty() or author.isEmpty() or city.isEmpty() or date.isEmpty() or description.isEmpty()){
                    Toast.makeText(activity,"Datos incompletos",Toast.LENGTH_LONG).show()
                }else{
                    binding.textTitulo.text.clear()
                    binding.textDescription.text.clear()

                    it.onNewPost(id,title,author,city,date,description,file)
                    Toast.makeText(activity,"Guardado",Toast.LENGTH_LONG).show()

                    val transaction = parentFragmentManager.beginTransaction()
                    transaction.replace(R.id.fragmentContainer,newHomeFragment)
                    transaction.commit()
                }
            }
        }
        val cameraLauncher = registerForActivityResult(StartActivityForResult(),:: onCameraResult)
        val galleryLauncher = registerForActivityResult(StartActivityForResult(),:: onGalleryResult)

        binding.btnCamera.setOnClickListener{
            requestPermissions(arrayOf(
                Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ),1)

            if(permissionAccepted){
                val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                id = UUID.randomUUID().toString()
                file = File("${context?.getExternalFilesDir(null)}/photo_post_${id}.png")
                val uri = FileProvider.getUriForFile(requireContext(),context?.packageName!!,file!!)
                intent.putExtra(MediaStore.EXTRA_OUTPUT,uri)

                Log.e(">>>",file?.path.toString())

                cameraLauncher.launch(intent)
            }
        }

        binding.btnGalery.setOnClickListener{
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"
            galleryLauncher.launch(intent)
        }
        return view
    }

    fun onCameraResult(result: ActivityResult){
//        val bitMap = result.data?.extras?.get("data") as Bitmap
//        binding.imageView2.setImageBitmap(bitMap)
        if(result.resultCode == RESULT_OK){
            val bitmap = BitmapFactory.decodeFile(file?.path)
            val thumbnail = Bitmap.createScaledBitmap(bitmap, bitmap.width/4,bitmap.height/4,true)
            binding.imageNewPost.setImageBitmap(thumbnail)
        }else if(result.resultCode == RESULT_CANCELED){
            file = null
        }
    }

    fun onGalleryResult(result: ActivityResult){
        if(result.resultCode == RESULT_OK){
           val uriImage = result.data?.data
            uriImage?.let {
                binding.imageNewPost.setImageURI(uriImage)
            }
        }else if(result.resultCode == RESULT_CANCELED){
            file = null
        }
    }
    fun Date.toString(format: String, locale: Locale = Locale.getDefault()): String {
        val formatter = SimpleDateFormat(format, locale)
        return formatter.format(this)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode==1){
            var allGrand = true
            for(result in grantResults){
                if(result == PackageManager.PERMISSION_DENIED){
                    allGrand = false
                    break
                }
            }

            permissionAccepted = allGrand
        }
    }
    fun getCurrentDateTime(): Date {
        return Calendar.getInstance().time
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    interface  OnNewPostListerner{
        fun onNewPost(
            id:String,
            title:String,
            autor:String,
            city:String,
            date:String,
            description:String,
            image: File?
        )
    }

    companion object {
        @JvmStatic
        fun newInstance(user:String?) = NewPostFragment(user)

    }
}