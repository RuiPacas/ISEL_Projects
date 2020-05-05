package pt.isel.bgg.activities

import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.app.AlertDialog
import android.app.Dialog
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.graphics.Bitmap
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.widget.Toast
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.fragment.app.DialogFragment
import pt.isel.bgg.R
import java.io.File
import java.util.*


const val MY_PERMISSION_WRITE_EXTERNAL_STORAGE = 500
class CheckSaveImage(
    private val imageBitmap: Bitmap,
    private val onSaveImage : ()-> Unit,
    private val onSavingImageError : ()-> Unit
) : DialogFragment(){

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
           val builder = AlertDialog.Builder(it)
            builder.setMessage(getString(R.string.saveImageAlertDialog))
                .setPositiveButton(getString(R.string.saveImageAlertDialogButton)){ _, _ ->
                    if(checkForPermissions(WRITE_EXTERNAL_STORAGE))
                        storeImage(imageBitmap)
                    else {
                        requestPermissions(arrayOf(WRITE_EXTERNAL_STORAGE),
                            MY_PERMISSION_WRITE_EXTERNAL_STORAGE)
                    }
                }
                .setNegativeButton(getString(R.string.cancelAlertDialogButton)){ _, _ ->

                }
            builder.create()
        }?: throw IllegalStateException("Activity cannot be null")
    }

    private fun checkForPermissions(perm: String): Boolean = checkSelfPermission(context!!,perm) == PERMISSION_GRANTED

    private fun storeImage(imageBitmap: Bitmap) {
       MediaStore.Images.Media.insertImage(context!!.contentResolver, imageBitmap, UUID.randomUUID().toString(), "gameImage")
       onSaveImage()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when(requestCode){
            MY_PERMISSION_WRITE_EXTERNAL_STORAGE ->
                checkAndDo(grantResults, getString(R.string.noPermissionToSaveImage)){
                    if(checkForPermissions(WRITE_EXTERNAL_STORAGE))
                        storeImage(imageBitmap)
                }
        }
    }

    private fun checkAndDo(grantResults: IntArray, msg: String, action: () -> Unit) {
        if((grantResults.isNotEmpty() && grantResults[0] == PERMISSION_GRANTED)){
            action()
        }else{
            onSavingImageError()
        }
    }
}