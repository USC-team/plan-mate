package planmate.dependencyInjection


import org.koin.core.qualifier.named
import org.koin.dsl.module
import planmate.data.csvHandler.CsvFileHandler
import planmate.data.datasource.ProjectsDataSourceImp
import planmate.data.datasource.StatesDataSourceImp
import planmate.data.datasource.TasksDataSourceImp
import planmate.data.datasource.UserDataSourceImp
import planmate.data.repository.ProjectsRepositoryImp
import planmate.data.repository.StatesRepositoryImp
import planmate.data.repository.TasksRepositoryImp
import planmate.data.repository.UsersRepositoryImp
import planmate.data.repository.datasource.ProjectsDataSource
import planmate.data.repository.datasource.StatesDataSource
import planmate.data.repository.datasource.TasksDataSource
import planmate.data.repository.datasource.UsersDataSource
import planmate.domain.repository.ProjectRepository
import planmate.domain.repository.StatesRepository
import planmate.domain.repository.TasksRepository
import planmate.domain.repository.UsersRepository
import planmate.domain.usecase.projectsUseCases.CreateProjectUseCase
import planmate.domain.usecase.projectsUseCases.DeleteProjectUseCase
import planmate.domain.usecase.projectsUseCases.GetAllProjectsUseCase
import planmate.domain.usecase.projectsUseCases.UpdateProjectUseCase
import planmate.domain.usecase.statesUseCases.CreateStateUseCase
import planmate.domain.usecase.statesUseCases.DeleteStateUseCase
import planmate.domain.usecase.statesUseCases.GetAllStatesUseCase
import planmate.domain.usecase.statesUseCases.UpdateStateUseCase
import planmate.domain.usecase.tasksUseCases.CreateTaskUseCase
import planmate.domain.usecase.tasksUseCases.DeleteTaskUseCase
import planmate.domain.usecase.tasksUseCases.GetAllTasksUseCase
import planmate.domain.usecase.tasksUseCases.UpdateTaskUseCase
import planmate.domain.usecase.usersUseCases.CreateUserUseCase
import planmate.domain.usecase.usersUseCases.DeleteUserUseCase
import planmate.domain.usecase.usersUseCases.FindUserUseCase
import planmate.domain.usecase.usersUseCases.GetAllUsersUseCase
import planmate.domain.usecase.usersUseCases.UpdateUserUseCase
import planmate.presentation.loginCli.LoginCLI
import planmate.presentation.projectsCli.CreateProjectsCli
import planmate.presentation.projectsCli.DeleteProjectCli
import planmate.presentation.projectsCli.MainProjectsCli
import planmate.presentation.projectsCli.ShowProjectsCli
import planmate.presentation.projectsCli.UpdateProjectCli
import planmate.presentation.statesCli.CreateStateCli
import planmate.presentation.statesCli.DeleteStateCli
import planmate.presentation.statesCli.MainStatesCli
import planmate.presentation.statesCli.ShowStatesCli
import planmate.presentation.statesCli.UpdateStateCli
import planmate.presentation.tasksCli.CreateTaskCli
import planmate.presentation.tasksCli.DeleteTaskCli
import planmate.presentation.tasksCli.MainTasksCli
import planmate.presentation.tasksCli.ShowTasksCli
import planmate.presentation.tasksCli.UpdateTaskCli
import planmate.presentation.usersCli.CreateUserCli
import planmate.presentation.usersCli.DeleteUserCli
import planmate.presentation.usersCli.MainUsersCli
import planmate.presentation.usersCli.ShowUsersCli
import planmate.presentation.usersCli.UpdateUserCli

private const val BASE_BATH= "src/main/resources/"
private const val USERS_FILE : String= "users.csv"
private const val PROJECTS_FILE : String= "projects.csv"
private const val STATES_FILE : String= "states.csv"
private const val TASKS_FILE : String= "tasks.csv"

val appModule = module {
    single(named("users file")) { CsvFileHandler(USERS_FILE) }
    single(named("projects file")) { CsvFileHandler(PROJECTS_FILE) }
    single(named("states file")) { CsvFileHandler(STATES_FILE) }
    single(named("tasks file")) { CsvFileHandler(TASKS_FILE) }

    // Users
    single<UsersDataSource> { UserDataSourceImp(get(named("users file"))) }
    single<UsersRepository> { UsersRepositoryImp(get()) }

    single { GetAllUsersUseCase(get()) }
    single { CreateUserUseCase(get()) }
    single { UpdateUserUseCase(get()) }
    single { DeleteUserUseCase(get()) }
    single { FindUserUseCase(get()) }

    single { LoginCLI(get()) }
    single { ShowUsersCli(get()) }
    single { CreateUserCli(get()) }
    single { UpdateUserCli(get(), get()) }
    single { DeleteUserCli(get(), get()) }
    single { MainUsersCli(get(), get(), get(), get()) }

    // Projects
    single<ProjectsDataSource> { ProjectsDataSourceImp(get(named("projects file"))) }
    single<ProjectRepository> { ProjectsRepositoryImp(get()) }

    single { GetAllProjectsUseCase(get()) }
    single { CreateProjectUseCase(get(), get()) }
    single { UpdateProjectUseCase(get()) }
    single { DeleteProjectUseCase(get()) }

    single { ShowProjectsCli(get()) }
    single { CreateProjectsCli(get()) }
    single { UpdateProjectCli(get(), get()) }
    single { DeleteProjectCli(get(), get()) }
    single { MainProjectsCli(get(), get(), get(), get()) }

    // States
    single<StatesDataSource> { StatesDataSourceImp(get(named("states file"))) }
    single<StatesRepository> { StatesRepositoryImp(get()) }

    single { GetAllStatesUseCase(get()) }
    single { CreateStateUseCase(get()) }
    single { UpdateStateUseCase(get()) }
    single { DeleteStateUseCase(get()) }

    single { ShowStatesCli(get()) }
    single { CreateStateCli(get(), get()) }
    single { UpdateStateCli(get(), get(), get()) }
    single { DeleteStateCli(get(), get(), get()) }
    single { MainStatesCli(get(), get(), get(), get()) }

    // Tasks
    single<TasksDataSource> { TasksDataSourceImp(get(named("tasks file"))) }
    single<TasksRepository> { TasksRepositoryImp(get()) }

    single { GetAllTasksUseCase(get()) }
    single { CreateTaskUseCase(get()) }
    single { UpdateTaskUseCase(get()) }
    single { DeleteTaskUseCase(get()) }

    single { ShowTasksCli(get()) }
    single { CreateTaskCli(get(), get(), get()) }
    single { UpdateTaskCli(get(), get(), get()) }
    single { DeleteTaskCli(get(), get(), get()) }
    single { MainTasksCli(get(), get(), get(), get()) }
}