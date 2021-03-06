package com.dream.vishaltraining.workingwithfiles.internlstoragefile

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import com.dream.vishaltraining.workingwithfiles.R
import kotlinx.android.synthetic.main.activity_internal_file.*
import java.io.*
import java.lang.NumberFormatException
import java.lang.StringBuilder

class InternalFileActivity : AppCompatActivity() {
    private var fileName : EditText? = null
    private var fileData : EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_internal_file)
        //init resources.
        init()
    }
    private fun init(){
        fileName = findViewById(R.id.edtFileName)
        fileData =findViewById(R.id.edtFileData)
        //setting click event.
        addListener()
    }

    private fun addListener(){
        //code for save file to the internal file storage.
        btnSave.setOnClickListener {
        val file: String = fileName?.text.toString()
        val data : String = fileData?.text.toString()
            val fos : FileOutputStream
            try {
                fos = openFileOutput(file,Context.MODE_PRIVATE)
                fos.write(data.toByteArray())
            }catch (e:FileNotFoundException)
            {
                e.printStackTrace()
            }catch (e:NumberFormatException){
                e.printStackTrace()
            }catch (e:IOException){
                e.printStackTrace()
            }
            Toast.makeText(applicationContext, "Data Saved Successfully.", Toast.LENGTH_SHORT).show()
            fileName?.text?.clear()
            fileData?.text?.clear()

        }
        //code for view the file data fro the internal.
        btnView.setOnClickListener {
        val filename = fileName?.text.toString()
            if (filename.trim()!=""){
                var fin : FileInputStream? =null
                fin = openFileInput(filename)
                var inSR:InputStreamReader = InputStreamReader(fin)
                val bufferedReader : BufferedReader = BufferedReader(inSR)
                val stringBuilder = StringBuilder()
                var text:String? = null
                while ({ text = bufferedReader.readLine();text}()!= null){

                    stringBuilder.append(text)
                }
                //displaying data on editText.
                fileData?.setText(stringBuilder.toString()).toString()
            }
            else{
                Toast.makeText(applicationContext, "file name can not be empty.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}