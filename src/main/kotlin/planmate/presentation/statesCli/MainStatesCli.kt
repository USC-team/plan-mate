package planmate.presentation.statesCli

import planmate.presentation.console.ConsoleIO

class MainStatesCli(
    private val showStatesCli: ShowStatesCli, private val createStatesCli: CreateStateCli,
    private val updateStateCli: UpdateStateCli, private val deleteStateCli: DeleteStateCli
) {
    private var choice: String=""

    fun enterChoice() {
        choices()
        enterUserChoice()

        when (choice) {
            "0" -> return
            "1" -> showStatesCli.showStates()
            "2" -> createStatesCli.createState()
            "3" -> updateStateCli.updateState()
            "4" -> deleteStateCli.deleteState()

            else -> ConsoleIO.writeError("Invalid choice!\n Try again")
        }

        enterChoice()
    }

    private fun choices() {
        ConsoleIO.write(
            "Enter your choice:\n" +
                    "1) show all states\n" +
                    "2) create a new state\n" +
                    "3) update an existing state\n" +
                    "4) delete a state\n" +
                    "0) back to main menu\n"
        )
    }

    private fun enterUserChoice() {
        choice=  ConsoleIO.read()
    }
}