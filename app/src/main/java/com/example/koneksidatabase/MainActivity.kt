package com.example.koneksidatabase

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.renderscript.RenderScript
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    var arrayList = ArrayList<Fakultas>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.title = "Data Fakultas"

        recycle_view.setHasFixedSize(true)
        recycle_view.layoutManager = LinearLayoutManager(this)
        FloatingActionButton.setOnClickListener{
            startActivity(Intent(this, ManageFakultasActivity :: class.java))
        }
        loadALLFakultas()
    }

    override fun onResume() {
        super.onResume()
        loadALLFakultas()
    }
    private fun loadALLFakultas(){
        val loading = ProgressDialog(this)
        loading.setMessage("Memuat data...")
        loading.show()

        AndroidNetworking.get(ApiEndPoint.READ)
            .setPriority(RenderScript.Priority.MEDIUM)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {
                override fun onResponse(response: JSONObject?) {
                    arrayList.clear()
                    val jsonArray =  response?.optJSONArray("result")
                    if(jsonArray?.length() == 0){
                        loading.dismiss()
                        Toast.makeText(applicationContext,"Fakultas ata is empty, add the data first"
                                Toast.LENGTH_SHORT).show()
                    }
                    for (i in 0 until jsonArray?.length()!!){
                        val jsonObject = jsonArray?.optJSONObject(i)
                        arrayList.add(Fakultas(jsonObject.getString("kode_fakultas")
                            ,jsonObject.getString("nama_fakultas")))
                        if(jsonArray?.length()-1 == i){
                            loading.dismiss()
                            val adapter = RVAAdapterFakultas(applicationContext,arrayList)
                            adapter.notifyDataSetChanged()
                            recycle_view.adapter = adapter
                        }

                    }
                }

                override fun onError(anError: ANError?) {
                    loading.dismiss()
                    Log.d("ONERROR",anError?.errorDetail?.toString())
                    Toast.makeText(applicationContext,"Connection failure", Toast.LENGTH_SHORT).show()
                }
            })
    }
}



