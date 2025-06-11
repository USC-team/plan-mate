package planmate.presentation.loginCli

import planmate.domain.models.User

class LoginCLI {
    private var userName: String=""
    private lateinit var user:User

    fun login(){

    }
    fun enterUserName(){
        println("Enter user name: ")
        userName=readln().trim()
    }
    fun findUser(){
        
    }
}