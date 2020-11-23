package com.example.crud

class data_mahasiswa {
    var NIM : String? = null
    var Nama : String? = null
    var Jurusan : String? = null
    var key :String? = null
    constructor()
    constructor(NIM:String?, Nama:String?, Jurusan:String?){
        this.NIM = NIM
        this.Nama = Nama
        this.Jurusan = Jurusan
    }
}