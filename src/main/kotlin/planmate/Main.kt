package planmate

import org.koin.core.context.startKoin
import planmate.dependencyInjection.ModuleVar
import planmate.dependencyInjection.appModule
import planmate.presentation.console.ConsoleIO

private lateinit var choice: String
private const val USERS_FILE="users.csv"
private const val PROJECTS_FILE="projects.csv"

fun main() {
    startKoin {
        modules(appModule)
    }
    enterChoice()
}

fun enterChoice(){
    choices()
    enterUserChoice()

    when (choice){
        "0"-> return
        "1"-> ModuleVar.fileName=USERS_FILE


        else -> ConsoleIO.writeError("Invalid choice!\n Try again")
    }

    enterChoice()
}

fun choices(){
    ConsoleIO.write("Enter your choice:\n" +
            "1) Users\n" +
            "2) Projects\n" +
            "3) States\n" +
            "4) Tasks\n" +
            "0) Exit\n")
}

private fun enterUserChoice(){
    choice= ConsoleIO.read()
}