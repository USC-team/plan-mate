package planmate.domain.usecase.statesUseCases

import planmate.domain.models.State
import planmate.domain.repository.StatesRepository

class UpdateStateUseCase(private val repo: StatesRepository) {
    fun updateState(state: State) = repo.updateState(state)
}