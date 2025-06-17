package planmate.presentation.usersCli

import planmate.domain.models.User
import planmate.domain.usecase.usersUseCases.DeleteUserUseCase
import planmate.domain.usecase.usersUseCases.GetAllUsersUseCase
import planmate.presentation.console.ConsoleIO
import kotlin.uuid.ExperimentalUuidApi

class DeleteUserCli(private val deleteUserUseCase: DeleteUserUseCase,
                    private val getAllUsersUseCase: GetAllUsersUseCase) {
    private lateinit var user: User
    private lateinit var userName: String

    @OptIn(ExperimentalUuidApi::class)
    fun deleteUser(){
        try {
            enterUserName()
            findUser()

            deleteUserUseCase.deleteUser(user.id)
            ConsoleIO.writeSuccess("deleted successfully!")
        }
        catch (e: Exception){
            ConsoleIO.writeError("User is not valid!\n${e.message}")
        }
    }

    private fun enterUserName(){
        userName= ConsoleIO.read()
    }

    private fun findUser(){
        user = getAllUsersUseCase.getAllUsers().first { it.name == userName }
    }
}