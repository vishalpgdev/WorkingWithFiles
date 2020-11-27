package com.dream.vishaltraining.workingwithfiles.externalstoragefile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.widget.EditText
import android.widget.Toast
import com.dream.vishaltraining.workingwithfiles.R
import kotlinx.android.synthetic.main.activity_eternal_storage_file.*
import java.io.*

class EternalStorageFileActivity : AppCompatActivity() {
    private var fileName : EditText? =null
    private var fileData : EditText? =null
    private val filePath = "MyStorage"
    internal var myExternalFile:File?= null
    //for checking the external storage is set to the read only.
    private val isExternalStorageReadOnly:Boolean
    get() {
        val extStorage = Environment.getExternalStorageState()
        return Environment.MEDIA_MOUNTED_READ_ONLY == extStorage

    }
    private val isExternalStorageAvailable : Boolean
    get() {
        val extStorage = Environment.getExternalStorageState()
        return Environment.MEDIA_MOUNTED == extStorage
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_eternal_storage_file)
        //init resources.
        init()
    }
    private fun init(){
        fileName = findViewById(R.id.edtFileName)
        fileData = findViewById(R.id.edtFileData)
        //add listener to the controls.
        addListener()
    }
    private fun addListener(){
        //code to save file to the external storage.
        btnSave.setOnClickListener {
            myExternalFile = File(getExternalFilesDir(filePath),fileName?.text.toString())
            try {
                val  fos = FileOutputStream(myExternalFile)
                fos.write(fileData?.text.toString().toByteArray())
                fos.close()
            }catch (e:IOException){
                e.printStackTrace()
            }
            Toast.makeText(applicationContext, "Data Saved SuccessFully", Toast.LENGTH_SHORT).show()
            fileName?.text?.clear()
            fileData?.text?.clear()
        }
        //code for view file from the external storage.
        btnView.setOnClickListener {
            if (fileName?.text.isNullOrEmpty())
            {
                Toast.makeText(applicationContext, "file name can not empty", Toast.LENGTH_SHORT).show()
                btnView.isEnabled = false
            }
            else
            {
                btnView.isEnabled = true
                myExternalFile = File(getExternalFilesDir(filePath),fileName?.text.toString())

                val filename = fileName?.text.toString()

                //     myExternalFile = File(getExternalFilesDir(filename),filename)

                if (filename.trim()!="")
                {
                    val fileINS = FileInputStream(myExternalFile)
                    val inSR = InputStreamReader(fileINS)
                    val bufferedReader = BufferedReader(inSR)
                    val stringBuilder = StringBuilder()
                    var text:String?= null
                    while ({text =bufferedReader.readLine();text}()!=null){
                        stringBuilder.append(text)
                    }
                    fileINS.close()

                    //Displaying data on EditText.
                    Toast.makeText(applicationContext, stringBuilder.toString(), Toast.LENGTH_SHORT).show()

                }
            }

        }
    if (!isExternalStorageAvailable || isExternalStorageReadOnly){
        btnSave.isEnabled = false
    }
    }


}