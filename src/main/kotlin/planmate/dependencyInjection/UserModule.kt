package planmate.dependencyInjection

import org.koin.core.qualifier.named
import org.koin.dsl.module
import planmate.data.csvHandler.CsvFileHandler
import planmate.data.datasource.UserDataSourceImp
import planmate.data.repository.UsersRepositoryImp
import planmate.data.repository.datasource.UsersDataSource
import planmate.domain.repository.UsersRepository
import planmate.domain.usecase.usersUseCases.CreateUserUseCase
import planmate.domain.usecase.usersUseCases.DeleteUserUseCase
import planmate.domain.usecase.usersUseCases.FindUserUseCase
import planmate.domain.usecase.usersUseCases.GetAllUsersUseCase
import planmate.domain.usecase.usersUseCases.UpdateUserUseCase
import planmate.presentation.loginCli.LoginCLI
import planmate.presentation.usersCli.CreateUserCli
import planmate.presentation.usersCli.DeleteUserCli
import planmate.presentation.usersCli.ShowUsersCli
import planmate.presentation.usersCli.UpdateUserCli
import planmate.presentation.usersCli.MainUsersCli


private const val USERS_CSV_FILE = "users.csv"
private const val USER_FILE_NAME = "users file"

val usersModule = module {
    single(named(USER_FILE_NAME)) { CsvFileHandler(USERS_CSV_FILE) }

    single<UsersDataSource> { UserDataSourceImp(get(named(USER_FILE_NAME))) }
    single<UsersRepository> { UsersRepositoryImp(get()) }

    // Use Cases
    single { GetAllUsersUseCase(get()) }
    single { CreateUserUseCase(get()) }
    single { UpdateUserUseCase(get()) }
    single { DeleteUserUseCase(get()) }
    single { FindUserUseCase(get()) }

    // CLI
    single { LoginCLI(get()) }
    single { ShowUsersCli(get()) }
    single { CreateUserCli(get()) }
    single { UpdateUserCli(get(), get()) }
    single { DeleteUserCli(get(), get()) }
    single { MainUsersCli(get(), get(), get(), get()) }
}
