package com.example.crud

import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.text.TextUtils.isEmpty
import android.view.View
import android.widget.Toast
import com.firebase.ui.auth.AuthUI
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_main.*
import org.w3c.dom.Text

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private var auth: FirebaseAuth? = null
    private val RC_SIGN_IN = 1
    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        logout.setOnClickListener(this)
        save.setOnClickListener(this)
        show_data.setOnClickListener(this)
        auth = FirebaseAuth.getInstance()
    }

    private fun isempty(s: String): Boolean {
        return TextUtils.isEmpty(s)
    }

    override fun onClick(v: View?) {
        when (v!!.getId()) {
            R.id.save -> {
                // Statement program untuk simpan data
                //Mendapatkan UserID dari pengguna yang Terautentikasi
                val getUserID = auth!!.currentUser!!.uid
                //Mendapatkan Instance dari Database
                val database = FirebaseDatabase.getInstance()
                //Menyimpan Data yang diinputkan User kedalam Variable
                val getNIM: String = NIM.getText().toString()
                val getNama: String = Nama.getText().toString()
                val getJurusan: String = Jurusan.getText().toString()
                // Mendapatkan Referensi dari Database
                val getReference: DatabaseReference
                getReference = database.reference
                // Mengecek apakah ada data yang kosong
                if (isEmpty(getNIM) || isEmpty(getNama) || isEmpty(getJurusan)) {  //Jika Ada, maka akan menampilkan pesan singkan seperti berikut  ini.
                    Toast.makeText(this@MainActivity, "Data tidak boleh ada yang  kosong", Toast.LENGTH_SHORT).show()
                } else {
                    /* Jika Tidak, maka data dapat diproses dan meyimpannya pada  Database Menyimpan data referensi pada Database berdasarkan User ID  dari masing-masing Akun
                    */

                    getReference.child("Admin").child(getUserID).child("Mahasiswa").push().setValue(data_mahasiswa(getNIM, getNama, getJurusan)).addOnCompleteListener(this) { //Peristiwa ini terjadi saat  user berhasil menyimpan datanya kedalam Database
                        NIM.setText("")
                        Nama.setText("")
                        Jurusan.setText("")
                        Toast.makeText(this@MainActivity, "Data Tersimpan", Toast.LENGTH_SHORT).show()
                    }
                }

            }
            R.id.logout -> {
                AuthUI.getInstance()
                        .signOut(this)
                        .addOnCompleteListener(this) {
                            if (it.isSuccessful) {
                                Toast.makeText(this@MainActivity, "Logout Berhasil", Toast.LENGTH_SHORT).show()
                                intent = Intent(applicationContext, LoginActivity::class.java)
                                startActivity(intent)
                                finish()
                            }
                        }
            }
            R.id.show_data -> {

            }
        }
    }
}