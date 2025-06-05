package planmate.domain.usecase

import planmate.domain.models.State
import planmate.domain.repository.StatesRepository

class CreateStateUseCase(private val repo: StatesRepository) {
    operator fun invoke(state: State) = repo.createState(state)
}