package planmate.dependencyInjection

import org.koin.core.qualifier.named
import org.koin.dsl.module
import planmate.data.csvHandler.CsvFileHandler
import planmate.data.datasource.ProjectsDataSourceImp
import planmate.data.repository.ProjectsRepositoryImp
import planmate.data.repository.datasource.ProjectsDataSource
import planmate.domain.repository.ProjectRepository
import planmate.domain.usecase.projectsUseCases.CreateProjectUseCase
import planmate.domain.usecase.projectsUseCases.DeleteProjectUseCase
import planmate.domain.usecase.projectsUseCases.GetAllProjectsUseCase
import planmate.domain.usecase.projectsUseCases.UpdateProjectUseCase
import planmate.presentation.projectsCli.CreateProjectsCli
import planmate.presentation.projectsCli.DeleteProjectCli
import planmate.presentation.projectsCli.MainProjectsCli
import planmate.presentation.projectsCli.ShowProjectsCli
import planmate.presentation.projectsCli.ShowProjectsSwimlanes
import planmate.presentation.projectsCli.UpdateProjectCli

private const val PROJECTS_CSV_FILE = "projects.csv"
private const val PROJECT_FILE_NAME = "projects file"


val projectsModule = module {
    single(named(PROJECT_FILE_NAME)) { CsvFileHandler(PROJECTS_CSV_FILE) }

    single<ProjectsDataSource> { ProjectsDataSourceImp(get(named(PROJECT_FILE_NAME))) }
    single<ProjectRepository> { ProjectsRepositoryImp(get(), get()) }

    // Use Cases
    single { GetAllProjectsUseCase(get()) }
    single { CreateProjectUseCase(get()) }
    single { UpdateProjectUseCase(get()) }
    single { DeleteProjectUseCase(get()) }

    // CLI
    single { ShowProjectsCli(get()) }
    single { CreateProjectsCli(get()) }
    single { UpdateProjectCli(get(), get()) }
    single { DeleteProjectCli(get(), get()) }
    single { ShowProjectsSwimlanes(get(), get(), get()) }
    single { MainProjectsCli(get(), get(), get(), get(), get()) }
}
