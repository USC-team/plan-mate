package planmate.presentation.loginCli

import planmate.domain.models.User
import planmate.domain.usecase.usersUseCases.FindUserUseCase
import planmate.presentation.console.ConsoleIO

class LoginCLI (private val findUserUseCase: FindUserUseCase){
    fun login(){
        try {
            val userName= enterUserName()
            findUser(userName)
        }
        catch(e: Exception){
            ConsoleIO.writeError("User not Valid! ${e.message}")
            login()
        }
    }
    private fun enterUserName():String{
        ConsoleIO.write("Enter user name: ")
        return ConsoleIO.read()
    }
    private fun findUser(userName: String){
        USER=findUserUseCase.findUser(userName)
    }

    companion object{
        lateinit var USER:User
    }
}