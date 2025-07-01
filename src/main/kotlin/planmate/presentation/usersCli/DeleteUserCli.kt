package planmate.presentation.usersCli

import planmate.domain.models.User
import planmate.domain.usecase.usersUseCases.DeleteUserUseCase
import planmate.domain.usecase.usersUseCases.GetAllUsersUseCase
import planmate.presentation.console.ConsoleIO
import kotlin.uuid.ExperimentalUuidApi

class DeleteUserCli(private val deleteUserUseCase: DeleteUserUseCase,
                    private val getAllUsersUseCase: GetAllUsersUseCase) {
    @OptIn(ExperimentalUuidApi::class)
    fun deleteUser(){
        try {
            val user= findUser()

            deleteUserUseCase.deleteUser(user.id)
            ConsoleIO.writeSuccess("deleted successfully!")
        }
        catch (e: Exception){
            ConsoleIO.writeError("User is not valid!\n${e.message}")
        }
    }

    private fun findUser():User{
        val userName = enterUserName()
        return getAllUsersUseCase.getAllUsers().first { it.name == userName }
    }

    private fun enterUserName(): String{
        ConsoleIO.write("Enter User name:")
        return ConsoleIO.read()
    }
}