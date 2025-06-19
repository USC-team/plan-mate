package planmate.presentation.usersCli

import planmate.domain.models.Role
import planmate.domain.models.User
import planmate.domain.usecase.usersUseCases.GetAllUsersUseCase
import planmate.domain.usecase.usersUseCases.UpdateUserUseCase
import planmate.presentation.console.ConsoleIO
import kotlin.uuid.ExperimentalUuidApi

class UpdateUserCli(private val updateUserUseCase: UpdateUserUseCase,
                    private val getAllUsersUseCase: GetAllUsersUseCase) {
    private lateinit var user: User
    private lateinit var newUser: User
    private var newUserName: String=""
    private var userName: String=""
    private var newUserRole: Role= Role.MATE

    fun updateUser(){
        try {
            findUser()
            buildUpdatedUser()

            updateUserUseCase.updateUser(newUser)

            ConsoleIO.writeSuccess("updated successfully!")
        }
        catch (e: Exception){
            ConsoleIO.writeError("User is not valid!\n${e.message}")
        }
    }

    private fun findUser(){
        enterUserName()
        user = getAllUsersUseCase.getAllUsers().first { it.name == userName }
    }

    private fun enterUserName(){
        ConsoleIO.write("Enter User name:")
        userName= ConsoleIO.read()
    }

    @OptIn(ExperimentalUuidApi::class)
    private fun buildUpdatedUser(){
        enterNewUserName()
        enterNewUserRole()
        newUser= User (user.id, newUserName, newUserRole)
    }

    private fun enterNewUserName(){
        ConsoleIO.write("Enter new User name:")
        newUserName = ConsoleIO.read()
    }

    private fun enterNewUserRole() {
        ConsoleIO.write("Enter user role (admin, mate): ")
        val userRole= ConsoleIO.read()
        if(userRole.equals("admin", ignoreCase = true))
            newUserRole = Role.ADMIN
        else if(userRole.equals("mate", ignoreCase = true))
            newUserRole = Role.MATE
        else {
            ConsoleIO.writeError("Wrong Entry")
            enterNewUserRole()
        }
    }
}