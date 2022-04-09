package com.example.reto1aplication

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.*
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import com.example.reto1aplication.databinding.FragmentNewProfileBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File
import java.lang.Integer.min
import java.lang.reflect.Type
import java.util.*


class NewProfileFragment (private val userLogged:User): Fragment() {
    private var _binding: FragmentNewProfileBinding?=null
    private val binding get() = _binding!!

    private var permissionAccepted = false
    private var file:File?=null

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNewProfileBinding.inflate(inflater,container,false)
        val view = binding.root

        //Setting
        Log.e("FOTO PROFILE",userLogged.photo)
        if(userLogged.photo!=""){
            val bitmap = MediaStore.Images.Media.getBitmap(context?.contentResolver , Uri.parse(userLogged.photo))
            val thumbnail = getCircularBitmap(bitmap)
            binding.imageProfile.setImageBitmap(thumbnail)
        }

        binding.testUserNameProfile.text = userLogged.user

        val cameraLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult(),:: onCameraResult)
        val galleryLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult(),:: onGalleryResult)

        binding.btnCamera.setOnClickListener{
            requestPermissions(arrayOf(
                Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ),1)

            if(permissionAccepted){
                val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)

                file = File("${context?.getExternalFilesDir(null)}/photoCOMPLETE_PROFILE_${id}.png")
                val uri = FileProvider.getUriForFile(requireContext(),context?.packageName!!,file!!)
                intent.putExtra(MediaStore.EXTRA_OUTPUT,uri)
                userLogged.photo = uri.toString()

                cameraLauncher.launch(intent)
            }
        }

        binding.btnGallery.setOnClickListener{
            requestPermissions(arrayOf(
                Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ),1)
            if(permissionAccepted) {
                val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
                intent.addCategory(Intent.CATEGORY_OPENABLE)
                intent.setType("image/*")
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
                galleryLauncher.launch(intent)
            }
        }
        binding.btnLogOut.setOnClickListener {
            val sharedPreferences = requireActivity().getSharedPreferences("MyPref",0)
            sharedPreferences.edit().remove("currentUser").apply()
            val i = Intent(requireContext(), MainActivity::class.java)
            startActivity(i)
        }
        return view
    }

        @RequiresApi(Build.VERSION_CODES.N)
        fun onCameraResult(result: ActivityResult){

        if(result.resultCode == Activity.RESULT_OK){
            val bitmap = BitmapFactory.decodeFile(file?.path)
            val thumbnail = getCircularBitmap(bitmap)

            binding.imageProfile.setImageBitmap(thumbnail)
        }else if(result.resultCode == Activity.RESULT_CANCELED){
            file = null
        }
    }

        @RequiresApi(Build.VERSION_CODES.N)
        fun onGalleryResult(result: ActivityResult){

        if(result.resultCode == Activity.RESULT_OK){
            val sourceTreeUri: Uri = result.data?.data!!
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                requireContext().contentResolver.takePersistableUriPermission(
                    sourceTreeUri,
                    Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION
                )
            }
            val uriImage = result.data?.data
            userLogged.photo = uriImage.toString()

            val bitmap = MediaStore.Images.Media.getBitmap(context?.contentResolver , uriImage)
            val thumbnail = getCircularBitmap(bitmap)
            binding.imageProfile.setImageBitmap(thumbnail)
        }else if(result.resultCode == Activity.RESULT_CANCELED){
            file = null
        }
    }
    @RequiresApi(Build.VERSION_CODES.N)
    private fun getCircularBitmap(srcBitmap: Bitmap?): Bitmap {

        // Select whichever of width or height is minimum
        val squareBitmapWidth = min(srcBitmap!!.width, srcBitmap.height)

        // Generate a bitmap with the above value as dimensions
        val dstBitmap = Bitmap.createBitmap(
            squareBitmapWidth,
            squareBitmapWidth,
            Bitmap.Config.ARGB_8888
        )

        // Initializing a Canvas with the above generated bitmap
        val canvas = Canvas(dstBitmap)

        // initializing Paint
        val paint = Paint()
        paint.isAntiAlias = true

        // Generate a square (rectangle with all sides same)
        val rect = Rect(0, 0, squareBitmapWidth, squareBitmapWidth)
        val rectF = RectF(rect)

        // Operations to draw a circle
        canvas.drawOval(rectF, paint)
        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
        val left = ((squareBitmapWidth - srcBitmap.width) / 2).toFloat()
        val top = ((squareBitmapWidth - srcBitmap.height) / 2).toFloat()
        canvas.drawBitmap(srcBitmap, left, top, paint)
        srcBitmap.recycle()

        // Return the bitmap
        return dstBitmap
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun saveUser(){
        val sharedPreferences = requireContext().getSharedPreferences("MyPref",0)
        var json = sharedPreferences.getString("allUsers","NO_DATA")

        if(json != "NO_DATA"){
            val type: Type = object : TypeToken<HashMap<String, User>>() {}.type
            val users =  Gson().fromJson<HashMap<String, User>>(json, type)

            val userName = userLogged.user
            users[userName] = userLogged

            val saveJson = Gson().toJson(users)
            sharedPreferences.edit().putString("allUsers",saveJson).apply()
        }
    }

    override fun onPause() {
        super.onPause()
        saveUser()
    }
    companion object {
        @JvmStatic
        fun newInstance(user:User) = NewProfileFragment(user)
    }
}