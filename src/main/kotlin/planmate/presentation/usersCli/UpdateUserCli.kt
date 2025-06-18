package planmate.presentation.usersCli

import planmate.domain.models.User
import planmate.domain.usecase.usersUseCases.GetAllUsersUseCase
import planmate.domain.usecase.usersUseCases.UpdateUserUseCase
import planmate.presentation.console.ConsoleIO

class UpdateUserCli(private val updateUserUseCase: UpdateUserUseCase,
                    private val getAllUsersUseCase: GetAllUsersUseCase) {
    private lateinit var user: User
    private lateinit var userName: String

    fun updateUser(){
        try {
            enterUserName()
            findUser()

            updateUserUseCase.updateUser(user)

            ConsoleIO.writeSuccess("updated successfully!")
        }
        catch (e: Exception){
            ConsoleIO.writeError("User is not valid!\n${e.message}")
        }
    }

    private fun enterUserName(){
        ConsoleIO.write("Enter User name:")
        userName= ConsoleIO.read()
    }

    private fun findUser(){
        user = getAllUsersUseCase.getAllUsers().first { it.name == userName }
    }
}