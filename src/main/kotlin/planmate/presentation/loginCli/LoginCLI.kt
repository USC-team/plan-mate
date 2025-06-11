package planmate.presentation.loginCli

import planmate.domain.models.User
import planmate.domain.usecase.FindUserUseCase

class LoginCLI (private val findUserUseCase: FindUserUseCase){
    private var userName: String=""
    private lateinit var user:User

    fun login(){
        try {
            enterUserName()
            findUser()
        }
        catch(e: Exception){
            println("User is not valid!")
        }
    }
    fun enterUserName(){
        println("Enter user name: ")
        userName=readln().trim()
    }
    fun findUser(){
        user=findUserUseCase.finUser(userName)
    }
}