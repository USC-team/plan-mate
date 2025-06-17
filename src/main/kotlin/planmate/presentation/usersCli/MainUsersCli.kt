package planmate.presentation.usersCli

import planmate.presentation.console.ConsoleIO

class MainUsersCli(
    private val showUsersCli: ShowUsersCli, private val createUsersCli: CreateUserCli,
    private val updateUserCli: UpdateUserCli, private val deleteUserCli: DeleteUserCli
) {
    private lateinit var choice: String

    fun enterChoice() {
        choices()
        enterUserChoice()

        when (choice) {
            "0" -> return
            "1" -> showUsersCli.showUsers()
            "2" -> createUsersCli.createUser()
            "3" -> updateUserCli.updateUser()
            "4" -> deleteUserCli.deleteUser()

            else -> ConsoleIO.writeError("Invalid choice!\n Try again")
        }

        enterChoice()
    }

    private fun choices() {
        ConsoleIO.write(
            "Enter your choice:\n" +
                    "1) show all users\n" +
                    "2) create a new user\n" +
                    "3) update an existing user\n" +
                    "4) delete a user\n" +
                    "0) back to main menu\n"
        )
    }

    private fun enterUserChoice():String {
        return ConsoleIO.read()
    }
}