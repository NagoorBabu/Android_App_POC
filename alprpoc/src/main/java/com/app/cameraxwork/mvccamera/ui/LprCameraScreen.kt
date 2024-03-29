package com.app.cameraxwork.mvccamera.ui

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.cameraxwork.R
import com.app.cameraxwork.databinding.ActivityLprCameraScreenBinding
import com.app.cameraxwork.mvccamera.model.Lpr
import com.app.cameraxwork.mvccamera.util.DataState
import com.app.cameraxwork.mvccamera.util.ProgressHUD
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.nio.ByteBuffer
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

typealias LumaListener = (luma: Double) -> Unit

@AndroidEntryPoint
class LPRCameraScreen : AppCompatActivity(), LprRecyclerAdapter.Interaction,
    LprItemUpdateDialog.DialogInteraction {

    override fun onItemSelected(position: Int, item: Lpr) {
        println("DEBUG: CLICKED ${position}")
        println("DEBUG: CLICKED ${item}")
        LprItemUpdateDialog.newInstance(position, item)
            .show(supportFragmentManager, LprItemUpdateDialog.TAG)

    }

    override fun onApply(position: Int, item: Lpr) {
        println("DEBUG: CLICKED ${position}")
        println("DEBUG: CLICKED ${item}")
    }

    override fun onCancel(position: Int, item: Lpr) {
        println("DEBUG: CLICKED ${position}")
        println("DEBUG: CLICKED ${item}")
    }

    lateinit var lprRecyclerAdapter: LprRecyclerAdapter

    private lateinit var viewBinding: ActivityLprCameraScreenBinding

    private var imageCapture: ImageCapture? = null

    private lateinit var cameraExecutor: ExecutorService

    private val viewModel: LprViewModel by viewModels()

    private lateinit var outputDirectory: File

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityLprCameraScreenBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        // Request camera permissions
        if (allPermissionsGranted()) {
            startCamera()
        } else {
            ActivityCompat.requestPermissions(
                this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS
            )
        }

        // Set up the listeners for take photo and video capture buttons
        viewBinding.ImageTakePicture.setOnClickListener { takePhoto() }

        cameraExecutor = Executors.newSingleThreadExecutor()

        initRecyclerView()
        subscribeObservers()
        outputDirectory = getOutputDirectory()

    }

    private fun initRecyclerView() {
        viewBinding.actOnBoardCameraRvScanPreview.apply {
            layoutManager =
                LinearLayoutManager(this@LPRCameraScreen, LinearLayoutManager.HORIZONTAL, false)
            addItemDecoration(
                DividerItemDecoration(
                    this@LPRCameraScreen,
                    LinearLayoutManager.HORIZONTAL
                )
            )
            lprRecyclerAdapter = LprRecyclerAdapter(this@LPRCameraScreen)
            adapter = lprRecyclerAdapter
        }
    }

    private fun getOutputDirectory(): File {
        val mediaDir = externalMediaDirs.firstOrNull()?.let {
            File(it, resources.getString(R.string.app_name)).apply { mkdirs() }
        }
        return if (mediaDir != null && mediaDir.exists())
            mediaDir else filesDir
    }

    private fun subscribeObservers() {
        viewModel.dataState.observe(this, Observer { dataState ->
            when (dataState) {
                is DataState.Success<List<Lpr>> -> {
                    displayProgressBar(false)
                    appendBlogTitles(dataState.data)
                }
                is DataState.Error -> {
                    displayProgressBar(false)
                    displayError(dataState.exception.message)
                }
                is DataState.Loading -> {
                    displayProgressBar(true)
                }
            }
        })
    }

    private fun displayError(message: String?) {
        /* if (message != null) {
             text.text = message
         } else {
             text.text = "UnKnown error"
         }*/
    }

    private lateinit var progressHUD: ProgressHUD

    private fun displayProgressBar(isDisplayed: Boolean) {
        if (isDisplayed)
            progressHUD = ProgressHUD.show(this@LPRCameraScreen, "Loading...", true, false)
        else
            if (progressHUD != null && progressHUD.isShowing()) progressHUD.dismiss()
    }

    private fun appendBlogTitles(lprList: List<Lpr>) {
        val msg = "Photo capture succeeded: ${lprList.toString()}"

        runOnUiThread {
            lprRecyclerAdapter.submitList(lprList)
            viewBinding.actOnBoardCameraRvScanPreview.scrollToPosition(lprRecyclerAdapter.itemCount - 1)
        }
    }

    private fun takePhoto() {
        // Get a stable reference of the modifiable image capture use case
        val imageCapture = imageCapture ?: return

        // Create time stamped name and MediaStore entry.
        val name = SimpleDateFormat(FILENAME_FORMAT, Locale.US)
            .format(System.currentTimeMillis())
        /*val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, name)
            put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {
                put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/CameraX-Image")
            }
        }*/

        // Create output options object which contains file + metadata
        /* val outputOptions = ImageCapture.OutputFileOptions
             .Builder(
                 contentResolver,
                 MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                 contentValues
             )
             .build()*/
        val photoFile = File(
            outputDirectory,
            SimpleDateFormat(
                FILENAME_FORMAT, Locale.US
            ).format(System.currentTimeMillis()) + ".jpg"
        )

        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()
        // Set up image capture listener, which is triggered after photo has
        // been taken
        imageCapture.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(this),
            object : ImageCapture.OnImageSavedCallback {
                override fun onError(exc: ImageCaptureException) {
                    Log.e(TAG, "Photo capture failed: ${exc.message}", exc)
                }

                override fun
                        onImageSaved(output: ImageCapture.OutputFileResults) {
                    val msg = "Photo capture succeeded: ${output.savedUri}"
                    Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
                    Log.d(TAG, msg)
                    viewModel.setStateEvent(
                        LprStateEvent.GetOcrValueEvent(
                            File(
                                output.savedUri!!.path
                            )
                        )
                    )
                }
            }
        )
    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)

        cameraProviderFuture.addListener({
            // Used to bind the lifecycle of cameras to the lifecycle owner
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

            // Preview
            val preview = Preview.Builder()
                .build()
                .also {
                    it.setSurfaceProvider(viewBinding.viewFinder.surfaceProvider)
                }

            imageCapture = ImageCapture.Builder().build()

            /*val imageAnalyzer = ImageAnalysis.Builder()
                .build()
                .also {
                    it.setAnalyzer(cameraExecutor, LuminosityAnalyzer { luma ->
                        Log.d(TAG, "Average luminosity: $luma")
                    })
                }*/
            // Select back camera as a default
            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            try {
                // Unbind use cases before rebinding
                cameraProvider.unbindAll()

                // Bind use cases to camera
                cameraProvider.bindToLifecycle(
                    this, cameraSelector, preview, imageCapture
                )
                /* cameraProvider.bindToLifecycle(
                     this, cameraSelector, preview, imageCapture, imageAnalyzer
                 )*/

            } catch (exc: Exception) {
                Log.e(TAG, "Use case binding failed", exc)
            }

        }, ContextCompat.getMainExecutor(this))

    }


    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(
            baseContext, it
        ) == PackageManager.PERMISSION_GRANTED
    }

    override fun onDestroy() {
        super.onDestroy()
        cameraExecutor.shutdown()
    }

    companion object {
        private const val TAG = "CameraXApp"
        private const val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"
        private const val REQUEST_CODE_PERMISSIONS = 10
        private val REQUIRED_PERMISSIONS =
            mutableListOf(
                Manifest.permission.CAMERA,
                Manifest.permission.RECORD_AUDIO
            ).apply {
                if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
                    add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                }
            }.toTypedArray()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>, grantResults:
        IntArray
    ) {
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (allPermissionsGranted()) {
                startCamera()
            } else {
                Toast.makeText(
                    this,
                    "Permissions not granted by the user.",
                    Toast.LENGTH_SHORT
                ).show()
                finish()
            }
        }
    }

    private class LuminosityAnalyzer(private val listener: LumaListener) : ImageAnalysis.Analyzer {

        private fun ByteBuffer.toByteArray(): ByteArray {
            rewind()    // Rewind the buffer to zero
            val data = ByteArray(remaining())
            get(data)   // Copy the buffer into a byte array
            return data // Return the byte array
        }

        override fun analyze(image: ImageProxy) {

            val buffer = image.planes[0].buffer
            val data = buffer.toByteArray()
            val pixels = data.map { it.toInt() and 0xFF }
            val luma = pixels.average()

            listener(luma)

            image.close()
        }
    }


}