package planmate

import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.java.KoinJavaComponent.getKoin
import planmate.data.csvHandler.CsvFileHandler
import planmate.data.datasource.UserDataSourceImp
import planmate.data.repository.UsersRepositoryImp
import planmate.data.repository.datasource.UsersDataSource
import planmate.dependencyInjection.ModuleVar
import planmate.dependencyInjection.appModule
import planmate.domain.repository.UsersRepository
import planmate.domain.usecase.CreateProjectUseCase
import planmate.domain.usecase.DeleteProjectUseCase
import planmate.domain.usecase.FindUserUseCase
import planmate.domain.usecase.GetAllProjectsUseCase
import planmate.domain.usecase.UpdateProjectUseCase
import planmate.presentation.ConsoleIO
import planmate.presentation.projectsCli.CreateProjectsCli
import planmate.presentation.projectsCli.DeleteProjectCli
import planmate.presentation.projectsCli.MainProjectsCli
import planmate.presentation.projectsCli.UpdateProjectCli

private lateinit var choice: String
private const val USERS_FILE="users.csv"
private const val PROJECTS_FILE="projects.csv"

fun main() {
    startKoin {
        modules(appModule)
    }
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