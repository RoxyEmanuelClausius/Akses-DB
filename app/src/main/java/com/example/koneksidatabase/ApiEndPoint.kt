package com.example.koneksidatabase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
class ApiEndPoint {
    companion object{
        private val SERVER = "http://192.168.0.107/universitaskotlin/"
        val CREATE =SERVER+"create_fakuktas.php"
        val READ =  SERVER+"read_fakuktas.php"
        val UPDATE = SERVER+"update_fakultas.php"
        val DETETE = SERVER+"delete_fakultas.php"
    }
}