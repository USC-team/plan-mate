package planmate.presentation.projectsCli

import planmate.presentation.console.ConsoleIO

class MainProjectsCli(
    private val showProjectsCli: ShowProjectsCli, private val createProjectsCli: CreateProjectsCli,
    private val updateProjectCli: UpdateProjectCli, private val deleteProjectCli: DeleteProjectCli
) {
    private var choice: String=""

    fun enterChoice() {
        choices()
        enterUserChoice()

        when (choice) {
            "0" -> return
            "1" -> showProjectsCli.showProjects()
            "2" -> createProjectsCli.createProject()
            "3" -> updateProjectCli.updateProject()
            "4" -> deleteProjectCli.deleteProject()

            else -> ConsoleIO.writeError("Invalid choice!\n Try again")
        }

        enterChoice()
    }

    private fun choices() {
        ConsoleIO.write(
            "Enter your choice:\n" +
                    "1) show all projects\n" +
                    "2) create a new project\n" +
                    "3) update an existing project\n" +
                    "4) delete a project\n" +
                    "0) back to main menu\n"
        )
    }

    private fun enterUserChoice() {
        choice=  ConsoleIO.read()
    }
}