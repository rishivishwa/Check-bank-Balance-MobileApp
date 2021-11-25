package com.checkbalance.calculator


    fun main(args : Array<String> )
    {
        var map = HashMap<String , String>()
//        var new = map.toInt()
        map.put("1","Ajay")
        map.put("2","vijay")
        map.put("3","332433244")

       println(map.get("2"))
        for (k in map.keys)
        {
            println(map.get(k))
        }

    }
