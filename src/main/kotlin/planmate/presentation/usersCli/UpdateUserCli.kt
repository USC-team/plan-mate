package planmate.presentation.usersCli

import kotlinx.coroutines.runBlocking
import planmate.domain.models.User
import planmate.domain.usecase.usersUseCases.GetAllUsersUseCase
import planmate.domain.usecase.usersUseCases.UpdateUserUseCase
import planmate.presentation.console.ConsoleIO
import kotlin.uuid.ExperimentalUuidApi

class UpdateUserCli(private val updateUserUseCase: UpdateUserUseCase,
                    private val getAllUsersUseCase: GetAllUsersUseCase) {
    fun updateUser(){
        runBlocking {
            try {
                val user= findUser()
                val newUser= buildUpdatedUser(user)

                updateUserUseCase.updateUser(newUser)

                ConsoleIO.writeSuccess("updated successfully!")
            }
            catch (e: Exception){
                ConsoleIO.writeError("User is not valid!\n${e.message}")
            }
        }
    }

    private suspend fun findUser(): User{
        val userName= enterUserName()
       return getAllUsersUseCase.getAllUsers().first { it.name == userName }
    }

    private fun enterUserName(): String{
        ConsoleIO.write("Enter User name:")
       return ConsoleIO.read()
    }

    @OptIn(ExperimentalUuidApi::class)
    private fun buildUpdatedUser(user : User): User{
        val newUserName= enterNewUserName()
        val newUserRole= enterNewUserRole()
        return User (user.id, newUserName, newUserRole)
    }

    private fun enterNewUserName():String{
        ConsoleIO.write("Enter new User name:")
       return ConsoleIO.read()
    }

    private fun enterNewUserRole(): User.Role {
        ConsoleIO.write("Enter user role (admin, mate): ")
        val userRole= ConsoleIO.read()
        if(userRole.equals("admin", ignoreCase = true))
           return User.Role.ADMIN
        if(userRole.equals("mate", ignoreCase = true))
            return User.Role.MATE
        ConsoleIO.writeError("Wrong Entry")
        return enterNewUserRole()
    }
}