package tech.jayamakmur.jmdlibrary.lib

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import com.budiyev.android.codescanner.*
import kotlinx.android.synthetic.main.my_barcode_scanner.view.*
import tech.jayamakmur.jmdlibrary.R
import tech.jayamakmur.jmdlibrary.util.MyDialog

@SuppressLint("InflateParams")
class MyBarcodeScanner(private val context: Context) : MyDialog(context) {
    private lateinit var onCompleteListener: (String) -> Unit
    private var title = "Title Placeholder"
    private var desc = "Desc Placeholder"

    fun new() = MyBarcodeScanner(context)

    fun withUI(title: String, desc: String) {
        this.title = title
        this.desc = desc
    }

    fun start(onCompleteListener: (String) -> Unit) {
        this.onCompleteListener = onCompleteListener
        inflateView()
    }

    private fun inflateView() {
        val view = LayoutInflater.from(context).inflate(R.layout.my_barcode_scanner, null, false)
        iniUI(view)
        showDialog(view)
        startCamera(CodeScanner(context, view.ID_BarcodeScanner_Container))
    }

    private fun iniUI(view: View) {
        view.ID_BarcodeScanner_Title.text = title
        view.ID_BarcodeScanner_Desc.text = desc
        view.ID_BarcodeScanner_Cancel.setOnClickListener {
            codeScanner.stopPreview()
            dismissDialog()
        }
    }

    private lateinit var codeScanner: CodeScanner
    private fun startCamera(codeScanner: CodeScanner) {
        codeScanner.camera = CodeScanner.CAMERA_BACK
        codeScanner.formats = CodeScanner.ALL_FORMATS
        codeScanner.scanMode = ScanMode.SINGLE
        codeScanner.autoFocusMode = AutoFocusMode.CONTINUOUS
        codeScanner.isAutoFocusEnabled = true
        codeScanner.isFlashEnabled = false

        codeScanner.decodeCallback = DecodeCallback {
            codeScanner.stopPreview()
            dismissDialog()
            onCompleteListener.invoke(it.text)
        }

        codeScanner.errorCallback = ErrorCallback {
            Toast.makeText(
                context,
                "Camera initialization error: ${it.message}",
                Toast.LENGTH_LONG
            ).show()
        }

        codeScanner.startPreview()
    }

    private fun checkPermission() {
//        Dexter.withActivity(activity).withPermission(Manifest.permission.CAMERA)
//            .withListener(object : PermissionListener {
//                override fun onPermissionGranted(response: PermissionGrantedResponse) {
//                    inflateView()
//                }
//
//                override fun onPermissionDenied(response: PermissionDeniedResponse) {
//                    TODO("PERMISSION DENIED")
//                }
//
//                override fun onPermissionRationaleShouldBeShown(permission: PermissionRequest?, token: PermissionToken?) {
//                    TODO("NO RATIONAL")
//                }
//            }).check()
    }
}