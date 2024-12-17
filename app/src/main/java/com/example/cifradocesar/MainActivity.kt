package com.example.cifradocesar

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.*


class MainActivity : AppCompatActivity() {
    lateinit var etPalabra: EditText
    lateinit var etDesplazamiento: EditText
    lateinit var tvPalabra: TextView
    lateinit var btnResultado: Button
    lateinit var rBCifrado: RadioButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        etPalabra = findViewById(R.id.inpPalabra)
        etDesplazamiento = findViewById(R.id.inpDesplazamiento)
        tvPalabra = findViewById(R.id.outPalabra)
        btnResultado = findViewById(R.id.btnResultado)
        rBCifrado = findViewById(R.id.rbCifrado)

        btnResultado.setOnClickListener {
            val palabra = etPalabra.text.toString()
            val desplazamiento = etDesplazamiento.text.toString().toIntOrNull()
            if (desplazamiento == null) {
                Toast.makeText(this, "Introduce un desplazamiento", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            } else if (palabra.isNullOrEmpty()) {
                Toast.makeText(this, "Introduce una palabra", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            if (rBCifrado.isChecked) {
                CoroutineScope(Dispatchers.Main).launch {
                    val texto = withContext(Dispatchers.Default) {
                        cifradoCesar(palabra, desplazamiento)
                    }
                    tvPalabra.text = texto
                }

            } else {
                val textoCifrado = tvPalabra.text
                if (textoCifrado == null) {
                    Toast.makeText(
                        this,
                        "Tienes que cifrar algo antes de usar el descifrador",
                        Toast.LENGTH_LONG
                    ).show()
                    return@setOnClickListener
                }
                CoroutineScope(Dispatchers.Main).launch {
                    val texto = withContext(Dispatchers.Default) {
                        descifradoCesar(textoCifrado.toString(), desplazamiento)
                    }
                    tvPalabra.text = texto
                }
            }


        }

    }

    private fun cifradoCesar(palabra: String, desplazamiento: Int): CharSequence {
        val result = StringBuilder()
        val charArray = palabra.toCharArray()
        for (char in charArray) {
            if (char.isWhitespace()) {
                result.append(" ")
            } else if (char.isLetter()) {
                val tempChar = char.code + desplazamiento
                result.append(tempChar.toChar())


            }


        }

        return result.toString()
    }

    private fun descifradoCesar(palabra: String, desplazamiento: Int): CharSequence {
        val result = StringBuilder()
        val charArray = palabra.toCharArray()
        for (char in charArray) {
            if (char.isWhitespace()) {
                result.append(" ")
            } else if (char.isLetter()) {
                val tempChar = char.code - desplazamiento
                result.append(tempChar.toChar())


            }


        }

        return result.toString()
    }

}