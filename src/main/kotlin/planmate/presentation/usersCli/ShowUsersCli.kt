package planmate.presentation.usersCli

import kotlinx.coroutines.runBlocking
import planmate.domain.usecase.exceptions.NoUsersFoundException
import planmate.domain.usecase.usersUseCases.GetAllUsersUseCase
import planmate.presentation.console.ConsoleIO
import kotlin.uuid.ExperimentalUuidApi

class ShowUsersCli(private val getAllUsersUseCase: GetAllUsersUseCase) {
    @OptIn(ExperimentalUuidApi::class)
    fun showUsers(){
        runBlocking {
            runCatching {
                getAllUsersUseCase.getAllUsers().takeIf { it.isNotEmpty() }
                    ?: throw NoUsersFoundException()
            }.onSuccess { users ->
                users.forEach { user ->
                    ConsoleIO.writeSuccess("${user.id}\t${user.name}\t${user.role}\t")
                }
            }.onFailure { e ->
                ConsoleIO.writeError("No users to show\n${e.message}")
            }
        }
    }
}