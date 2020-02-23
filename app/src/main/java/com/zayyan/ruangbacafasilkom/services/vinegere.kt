package com.zayyan.ruangbacafasilkom.services


import java.util.ArrayList

object vinegere {
    private var result_encode_vinegere: String? = null
    private var result_decode_vinegere: String? = null
    fun vinegere(plaintext: String?, chipertext: String?): String? {
        if (plaintext != null) {
            result_encode_vinegere = encode_vinegere(plaintext)
            return result_encode_vinegere
        } else if (chipertext != null) {
            result_decode_vinegere = decode_vinegere(chipertext)
            return result_decode_vinegere
        } else {
            return "null"
        }
    }

    //
    //    public static void main(String[] args)
    //    {
    //        String result = encode_vinegere("huifhsayfgbuahfgbuvsgbufgu6547v34b763v 873y :()");
    //        System.out.println(result);
    //        String result1 = decode_vinegere("yOisntaAfxvunnggduMMgoAggw6-/7I95b96+P ,$4y#:^|");
    //        System.out.println(result1);
    //    }
    private fun acii93(): List<String> {

        val ACCI93 = ArrayList<String>()
        ACCI93.add("a") //0
        ACCI93.add("b") //1
        ACCI93.add("c") //2
        ACCI93.add("d") //3
        ACCI93.add("e") //4
        ACCI93.add("f") //5
        ACCI93.add("g") //6
        ACCI93.add("h") //7
        ACCI93.add("i") //8
        ACCI93.add("j") //9
        ACCI93.add("k") //10
        ACCI93.add("l") //11
        ACCI93.add("m") //12
        ACCI93.add("n") //13
        ACCI93.add("o") //14
        ACCI93.add("p") //15
        ACCI93.add("q") //16
        ACCI93.add("r") //17
        ACCI93.add("s") //18
        ACCI93.add("t") //19
        ACCI93.add("u") //20
        ACCI93.add("v") //21
        ACCI93.add("w") //22
        ACCI93.add("x") //23
        ACCI93.add("y") //24
        ACCI93.add("z") //25
        ACCI93.add("A") //26
        ACCI93.add("B") //27
        ACCI93.add("C") //28
        ACCI93.add("D") //29
        ACCI93.add("E") //30
        ACCI93.add("F") //31
        ACCI93.add("G") //32
        ACCI93.add("H") //33
        ACCI93.add("I") //34
        ACCI93.add("J") //35
        ACCI93.add("K") //36
        ACCI93.add("L") //37
        ACCI93.add("M") //38
        ACCI93.add("N") //39
        ACCI93.add("O") //40
        ACCI93.add("P") //41
        ACCI93.add("Q") //42
        ACCI93.add("R") //43
        ACCI93.add("S") //44
        ACCI93.add("T") //45
        ACCI93.add("U") //46
        ACCI93.add("V") //47
        ACCI93.add("W") //48
        ACCI93.add("X") //49
        ACCI93.add("Y") //50
        ACCI93.add("Z") //51
        ACCI93.add("0") //52
        ACCI93.add("1") //53
        ACCI93.add("2") //54
        ACCI93.add("3") //55
        ACCI93.add("4") //56
        ACCI93.add("5") //57
        ACCI93.add("6") //58
        ACCI93.add("7") //59
        ACCI93.add("8") //60
        ACCI93.add("9") //61
        ACCI93.add(" ") //62
        ACCI93.add("!") //63
        ACCI93.add("#") //64
        ACCI93.add("$") //65
        ACCI93.add("%") //66
        ACCI93.add("&") //67
        ACCI93.add("'") //68
        ACCI93.add("(") //69
        ACCI93.add(")") //70
        ACCI93.add("*") //71
        ACCI93.add("+") //72
        ACCI93.add(",") //73
        ACCI93.add("-") //74
        ACCI93.add(".") //75
        ACCI93.add("/") //76
        ACCI93.add(":") //77
        ACCI93.add(";") //78
        ACCI93.add("<") //79
        ACCI93.add("=") //80
        ACCI93.add(">") //81
        ACCI93.add("?") //82
        ACCI93.add("@") //83
        ACCI93.add("[") //84
        ACCI93.add("]") //85
        ACCI93.add("^") //86
        ACCI93.add("_") //87
        ACCI93.add("`") //88
        ACCI93.add("{") //89
        ACCI93.add("|") //90
        ACCI93.add("}") //91
        ACCI93.add("~") //92
        return ACCI93
    }

    private fun nilai_karakter(karakter: String): Int {
        val nilai_acii: Int
        nilai_acii = acii93().indexOf(karakter)
        return nilai_acii
    }

    private fun karakter(nilaikode: Int): String {
        val getkarakter: String
        getkarakter = acii93()[nilaikode]
        return getkarakter
    }

    private fun setchar(getchar: String): List<Array<String>> {
        val length = getchar.length
        val getlength = Integer.toString(length)
        var putchar = ""
        var a = 0
        var nilaichar = 0
        var setnilaichar = ""
        val datatext = ArrayList<Array<String>>()
        datatext.add(arrayOf("length", getlength))
        for (i in 0 until length) {
            a = i + 1
            putchar = getchar.substring(i, a)
            nilaichar = nilai_karakter(putchar)
            setnilaichar = Integer.toString(nilaichar)
            datatext.add(arrayOf(putchar, setnilaichar))
        }
        return datatext
    }

    private fun encode_vinegere(getplaintext: String): String {
        val kode = "ruangbaca"
        val p_getlength: String
        val k_getlength: String
        var plaintext: List<Array<String>> = ArrayList()
        var kodetext: List<Array<String>> = ArrayList()
        plaintext = setchar(getplaintext)
        kodetext = setchar(kode)
        p_getlength = plaintext[0][1]
        k_getlength = kodetext[0][1]
        val p_length = Integer.parseInt(p_getlength)
        val k_length = Integer.parseInt(k_getlength)
        var cek = 0
        val p = 0
        var p_pair = p_length
        var plus_kode = ""
        val new_kode = StringBuilder()

        while (cek == 0) {
            if (p_length < k_length) {
                plus_kode = kode.substring(0, p_length)
                new_kode.append(plus_kode)
                cek = 1
            } else {
                p_pair = p_pair - k_length
                if (p_pair < k_length) {
                    new_kode.append(kode)
                    plus_kode = kode.substring(0, p_pair)
                    new_kode.append(plus_kode)
                    cek = 1
                } else if (p_pair == 0) {
                    new_kode.append(kode)
                    cek = 1
                } else if (p_pair > 0) {
                    new_kode.append(kode)
                }
            }
        }
        val sb_newkode = new_kode.toString()

        var newkodetext: List<Array<String>> = ArrayList()
        newkodetext = setchar(sb_newkode)
        val chipertext = StringBuilder()
        var list_a = 0
        var nilai_plaintext: Int
        var nilai_kode: Int
        var hasil: Int

        for (i in 0 until p_length) {
            list_a++
            val xnilai_plaintext = plaintext[list_a][1]
            val xnilai_kode = newkodetext[list_a][1]
            nilai_plaintext = Integer.parseInt(xnilai_plaintext)
            nilai_kode = Integer.parseInt(xnilai_kode)
            hasil = nilai_plaintext + nilai_kode
            if (hasil > 92) {
                hasil = hasil - 93
                chipertext.append(karakter(hasil))
            } else {
                chipertext.append(karakter(hasil))
            }
        }
        return chipertext.toString()
    }

    private fun decode_vinegere(getchipertext: String): String {
        val kode = "ruangbaca"
        val p_getlength: String
        val k_getlength: String
        var plaintext: List<Array<String>> = ArrayList()
        var kodetext: List<Array<String>> = ArrayList()
        plaintext = setchar(getchipertext)
        kodetext = setchar(kode)
        p_getlength = plaintext[0][1]
        k_getlength = kodetext[0][1]
        val p_length = Integer.parseInt(p_getlength)
        val k_length = Integer.parseInt(k_getlength)
        var cek = 0
        val p = 0
        var p_pair = p_length
        var plus_kode = ""
        val new_kode = StringBuilder()

        while (cek == 0) {
            if (p_length < k_length) {
                plus_kode = kode.substring(0, p_length)
                new_kode.append(plus_kode)
                cek = 1
            } else {
                p_pair = p_pair - k_length
                if (p_pair < k_length) {
                    new_kode.append(kode)
                    plus_kode = kode.substring(0, p_pair)
                    new_kode.append(plus_kode)
                    cek = 1
                } else if (p_pair == 0) {
                    new_kode.append(kode)
                    cek = 1
                } else if (p_pair > 0) {
                    new_kode.append(kode)
                }
            }
        }
        val sb_newkode = new_kode.toString()

        var newkodetext: List<Array<String>> = ArrayList()
        newkodetext = setchar(sb_newkode)
        val chipertext = StringBuilder()
        var list_a = 0
        var nilai_plaintext: Int
        var nilai_kode: Int
        var hasil: Int

        for (i in 0 until p_length) {
            list_a++
            val xnilai_plaintext = plaintext[list_a][1]
            val xnilai_kode = newkodetext[list_a][1]
            nilai_plaintext = Integer.parseInt(xnilai_plaintext)
            nilai_kode = Integer.parseInt(xnilai_kode)
            hasil = nilai_plaintext - nilai_kode
            if (hasil < 0) {
                hasil = hasil + 93
                chipertext.append(karakter(hasil))
            } else {
                chipertext.append(karakter(hasil))
            }
        }
        return chipertext.toString()
    }

}
