package planmate

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.koin.core.context.startKoin
import org.koin.java.KoinJavaComponent.getKoin
import planmate.dependencyInjection.appModule
import planmate.presentation.console.ConsoleIO
import planmate.presentation.loginCli.LoginCLI
import planmate.presentation.projectsCli.MainProjectsCli
import planmate.presentation.statesCli.MainStatesCli
import planmate.presentation.tasksCli.MainTasksCli
import planmate.presentation.usersCli.MainUsersCli


fun main() {
    GlobalScope.launch {
        startKoin {
            modules(appModule)
        }
    }
    ConsoleIO.writeWelcome("WELCOME TO PLANMATE")
    val login: LoginCLI = getKoin().get()
    runBlocking { login.login() }
    enterChoice()
}

fun enterChoice() {
    choices()
    val choice = enterUserChoice()

    when (choice) {
        "0" -> return
        "1" -> {
            ConsoleIO.writeWelcome("USERS")
            val mainUsersCli: MainUsersCli = getKoin().get()
            mainUsersCli.enterChoice()
        }

        "2" -> {
            ConsoleIO.writeWelcome("PROJECTS")
            val mainProjectsCli: MainProjectsCli = getKoin().get()
            mainProjectsCli.enterChoice()
        }

        "3" -> {
            ConsoleIO.writeWelcome("STATES")
            val mainStatesCli: MainStatesCli = getKoin().get()
            mainStatesCli.enterChoice()
        }

        "4" -> {
            ConsoleIO.writeWelcome("TASKS")
            val mainTasksCli: MainTasksCli = getKoin().get()
            mainTasksCli.enterChoice()
        }

        else -> ConsoleIO.writeError("Invalid choice!\n Try again")
    }

    enterChoice()
}

fun choices() {
    ConsoleIO.write(
        "Enter your choice:\n" +
                "1) Users\n" +
                "2) Projects\n" +
                "3) States\n" +
                "4) Tasks\n" +
                "0) Exit\n"
    )
}

private fun enterUserChoice(): String {
    return ConsoleIO.read()
}