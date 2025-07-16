package planmate.dependencyInjection

import org.koin.core.qualifier.named
import org.koin.dsl.module
import planmate.data.csvHandler.CsvFileHandler
import planmate.data.datasource.TasksDataSourceImp
import planmate.data.repository.TasksRepositoryImp
import planmate.data.repository.datasource.TasksDataSource
import planmate.domain.repository.TasksRepository
import planmate.domain.usecase.tasksUseCases.CreateTaskUseCase
import planmate.domain.usecase.tasksUseCases.DeleteTaskUseCase
import planmate.domain.usecase.tasksUseCases.GetAllTasksUseCase
import planmate.domain.usecase.tasksUseCases.UpdateTaskUseCase
import planmate.presentation.tasksCli.CreateTaskCli
import planmate.presentation.tasksCli.DeleteTaskCli
import planmate.presentation.tasksCli.MainTasksCli
import planmate.presentation.tasksCli.ShowTasksCli
import planmate.presentation.tasksCli.UpdateTaskCli

private const val TASKS_CSV_FILE = "tasks.csv"
private const val TASK_FILE_NAME = "tasks file"

val tasksModule = module {
    single(named(TASK_FILE_NAME)) { CsvFileHandler(TASKS_CSV_FILE) }

    single<TasksDataSource> { TasksDataSourceImp(get(named(TASK_FILE_NAME))) }
    single<TasksRepository> { TasksRepositoryImp(get()) }

    // Use Cases
    single { GetAllTasksUseCase(get()) }
    single { CreateTaskUseCase(get()) }
    single { UpdateTaskUseCase(get()) }
    single { DeleteTaskUseCase(get()) }

    // CLI
    single { ShowTasksCli(get()) }
    single { CreateTaskCli(get(), get()) }
    single { UpdateTaskCli(get(), get(), get()) }
    single { DeleteTaskCli(get(), get(), get()) }
    single { MainTasksCli(get(), get(), get(), get()) }
}
