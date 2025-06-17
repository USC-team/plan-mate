package planmate.presentation.usersCli

import planmate.domain.usecase.usersUseCases.GetAllUsersUseCase
import planmate.presentation.console.ConsoleIO
import kotlin.uuid.ExperimentalUuidApi

class ShowUsersCli(private val getAllUsersUseCase: GetAllUsersUseCase) {
    @OptIn(ExperimentalUuidApi::class)
    fun showUsers(){
        try {
            getAllUsersUseCase.getAllUsers().forEach { ConsoleIO.writeSuccess("${it.id}\t ${it.name}\t ${it.role}\t") }
        }
        catch (e: Exception){
            ConsoleIO.writeError("No users to show\n ${e.message}")
        }
    }
}