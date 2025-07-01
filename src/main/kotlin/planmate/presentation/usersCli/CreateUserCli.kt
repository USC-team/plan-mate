package planmate.presentation.usersCli

import planmate.domain.models.User
import planmate.domain.usecase.usersUseCases.CreateUserUseCase
import planmate.presentation.console.ConsoleIO
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class CreateUserCli(private val createUserUseCase:CreateUserUseCase) {
    fun createUser() {
        try {
            val user= buildUser()
            createUserUseCase.createUser(user)
            ConsoleIO.writeSuccess("created successfully!")
        }
        catch (e: Exception){
            ConsoleIO.writeError("Couldn't create user!\n${e.message}")
        }
    }

    @OptIn(ExperimentalUuidApi::class)
    private fun buildUser() : User{
        val userName= enterUserName()
        val userRole= enterUserRole()
        return User(Uuid.random(), userName,userRole)
    }

    private fun enterUserName():String {
        ConsoleIO.write("Enter user name: ")
        return ConsoleIO.read()
    }

    private fun enterUserRole(): User.Role {
        ConsoleIO.write("Enter user role (admin, mate): ")
        val userRoleString=ConsoleIO.read()
        if(userRoleString.equals("admin", ignoreCase = true))
           return User.Role.ADMIN
        if(userRoleString.equals("mate", ignoreCase = true))
            return User.Role.ADMIN
        ConsoleIO.writeError("Wrong Entry")
        return enterUserRole()
    }
}