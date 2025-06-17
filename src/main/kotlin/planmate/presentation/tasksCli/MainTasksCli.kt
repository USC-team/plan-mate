package planmate.presentation.tasksCli

import planmate.presentation.console.ConsoleIO

class MainTasksCli(
    private val showTasksCli: ShowTasksCli, private val createTasksCli: CreateTaskCli,
    private val updateTaskCli: UpdateTaskCli, private val deleteTaskCli: DeleteTaskCli
) {
    private lateinit var choice: String

    fun enterChoice() {
        choices()
        enterUserChoice()

        when (choice) {
            "0" -> return
            "1" -> showTasksCli.showTasks()
            "2" -> createTasksCli.createTask()
            "3" -> updateTaskCli.updateTask()
            "4" -> deleteTaskCli.deleteTask()

            else -> ConsoleIO.writeError("Invalid choice!\n Try again")
        }

        enterChoice()
    }

    private fun choices() {
        ConsoleIO.write(
            "Enter your choice:\n" +
                    "1) show all tasks\n" +
                    "2) create a new task\n" +
                    "3) update an existing task\n" +
                    "4) delete a task\n" +
                    "0) back to main menu\n"
        )
    }

    private fun enterUserChoice():String {
        return ConsoleIO.read()
    }
}