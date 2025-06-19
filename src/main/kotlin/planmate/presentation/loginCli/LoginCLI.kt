package planmate.presentation.loginCli

import planmate.domain.models.User
import planmate.domain.usecase.usersUseCases.FindUserUseCase
import planmate.presentation.console.ConsoleIO

class LoginCLI (private val findUserUseCase: FindUserUseCase){
    private lateinit var userName: String

    fun login(){
        try {
            enterUserName()
            findUser()
        }
        catch(e: Exception){
            ConsoleIO.writeError("User not Valid! ${e.message}")
            login()
        }
    }
    private fun enterUserName(){
        ConsoleIO.write("Enter user name: ")
        userName= ConsoleIO.read()
    }
    private fun findUser(){
        USER=findUserUseCase.findUser(userName)
    }

    companion object{
        lateinit var USER:User
    }
}