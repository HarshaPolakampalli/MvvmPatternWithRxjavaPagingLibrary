package com.example.mvvm.data.repository

enum class Status{

    RUNNING,
    SUCCESS,
    ERROR,
    ENDOFLIST


}

class NetworkStatus(val status:Status,val msg:String){


    companion object{
        val Loaded= NetworkStatus(Status.SUCCESS,"Success")
        val Loading= NetworkStatus(Status.RUNNING,"Loading")
        val Error= NetworkStatus(Status.ERROR,"Error")
        val Endoflist= NetworkStatus(Status.ENDOFLIST,"End of List")
    }

}