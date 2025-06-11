package planmate.domain.usecase

import planmate.domain.models.State
import planmate.domain.repository.StatesRepository

class UpdateStateUseCase(private val repo: StatesRepository) {
    operator fun invoke(state: State) = repo.updateState(state)
}