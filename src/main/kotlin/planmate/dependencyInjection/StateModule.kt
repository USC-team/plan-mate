package planmate.dependencyInjection

import org.koin.core.qualifier.named
import org.koin.dsl.module
import planmate.data.csvHandler.CsvFileHandler
import planmate.data.datasource.StatesDataSourceImp
import planmate.data.repository.StatesRepositoryImp
import planmate.data.repository.datasource.StatesDataSource
import planmate.domain.repository.StatesRepository
import planmate.domain.usecase.statesUseCases.CreateStateUseCase
import planmate.domain.usecase.statesUseCases.DeleteStateUseCase
import planmate.domain.usecase.statesUseCases.GetAllStatesUseCase
import planmate.domain.usecase.statesUseCases.UpdateStateUseCase
import planmate.presentation.statesCli.CreateStateCli
import planmate.presentation.statesCli.DeleteStateCli
import planmate.presentation.statesCli.MainStatesCli
import planmate.presentation.statesCli.ShowStatesCli
import planmate.presentation.statesCli.UpdateStateCli


private const val STATES_CSV_FILE = "states.csv"
private const val STATE_FILE_NAME = "states file"


val statesModule = module {
    single(named(STATE_FILE_NAME)) { CsvFileHandler(STATES_CSV_FILE) }

    single<StatesDataSource> { StatesDataSourceImp(get(named(STATE_FILE_NAME))) }
    single<StatesRepository> { StatesRepositoryImp(get()) }

    // Use Cases
    single { GetAllStatesUseCase(get()) }
    single { CreateStateUseCase(get()) }
    single { UpdateStateUseCase(get()) }
    single { DeleteStateUseCase(get()) }

    // CLI
    single { ShowStatesCli(get()) }
    single { CreateStateCli(get(), get()) }
    single { UpdateStateCli(get(), get(), get()) }
    single { DeleteStateCli(get(), get(), get()) }
    single { MainStatesCli(get(), get(), get(), get()) }
}
