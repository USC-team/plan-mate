package planmate.presentation.usersCli

import planmate.domain.usecase.usersUseCases.GetAllUsersUseCase
import planmate.presentation.console.ConsoleIO
import kotlin.uuid.ExperimentalUuidApi

class ShowUsersCli(private val getAllUsersUseCase: GetAllUsersUseCase) {
    @OptIn(ExperimentalUuidApi::class)
    fun showUsers(){
        runCatching {
            getAllUsersUseCase.getAllUsers().takeIf { it.isNotEmpty() }
                ?: throw Exception("")
        }.onSuccess { users ->
            users.forEach { user ->
                ConsoleIO.writeSuccess("${user.id}\t${user.name}\t${user.role}\t")
            }
        }.onFailure { e ->
            ConsoleIO.writeError("No users to show\n${e.message}")
        }
    }
}