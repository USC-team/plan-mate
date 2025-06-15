package planmate.dependencyInjection


import org.koin.dsl.module
import planmate.data.csvHandler.CsvFileHandler
import planmate.data.datasource.UserDataSourceImp
import planmate.data.repository.UsersRepositoryImp
import planmate.data.repository.datasource.UsersDataSource
import planmate.domain.repository.UsersRepository
import planmate.domain.usecase.usersUseCases.FindUserUseCase
import planmate.presentation.loginCli.LoginCLI

object ModuleVar{
    lateinit var fileName: String
}

val appModule = module {
    single { CsvFileHandler(ModuleVar.fileName) }
    single<UsersDataSource> { UserDataSourceImp(get()) }
    single<UsersRepository> { UsersRepositoryImp(get()) }
    single { FindUserUseCase(get()) }
    single { LoginCLI(get()) }
}
/*private const val USERS_FILE = "src/main/resources/users.csv"
private const val PROJECTS_FILE = "src/main/resources/projects.csv"

val csvDataSourceModule = module {
    single(named("user file")) { File(USERS_FILE) }
    single(named("project file")) { File(PROJECTS_FILE) }

    single(named("user handler")) { CsvHandler(get(named("user file"))) }
    single(named("project handler")) { CsvHandler(get(named("project file"))) }

    singleOf(::UserCsvParser)
    singleOf(::ProjectCsvParser)

    single { UserDataSource(get(named("user handler")), get()) }
    single { ProjectDataSource(get(named("project handler")), get()) }
}*/