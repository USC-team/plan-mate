package planmate.presentation.usersCli

import planmate.domain.models.Role
import planmate.domain.models.User
import planmate.domain.usecase.usersUseCases.CreateUserUseCase
import planmate.presentation.console.ConsoleIO
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class CreateUserCli(private val createUserUseCase:CreateUserUseCase, private val creator: User) {
    private lateinit var userName: String
    private lateinit var userRole: Role
    private lateinit var user: User

    fun createUser() {
        try {
            enterUserName()
            enterUserRole()
            buildUser()
            createUserUseCase.createUser(user, creator)
            ConsoleIO.writeSuccess("created successfully!")
        }
        catch (e: Exception){
            ConsoleIO.writeError("Couldn't create user!\n${e.message}")
        }
    }

    private fun enterUserName() {
        ConsoleIO.write("Enter user name: ")
        userName = ConsoleIO.read()
    }

    private fun enterUserRole() {
        ConsoleIO.write("Enter user role (admin, mate): ")
        if(ConsoleIO.read().equals("admin", ignoreCase = true))
            userRole = Role.ADMIN
        else if(ConsoleIO.read().equals("mate", ignoreCase = true))
            userRole = Role.MATE
        else {
            ConsoleIO.writeError("Wrong Entry")
            enterUserRole()
        }
    }

    @OptIn(ExperimentalUuidApi::class)
    private fun buildUser() {
        user = User(Uuid.random().toString(), userName,userRole)
    }
}