package planmate.data.datasource

import planmate.data.csvHandler.CsvFileHandler
import planmate.data.dto.UserDto
import planmate.data.repository.datasource.UsersDataSource
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class UserDataSourceImp (
    private val csvFileHandler: CsvFileHandler,
) : UsersDataSource {

    private val header = arrayOf("id", "name", "role")

    override fun getAllUsers(): List<UserDto> {
        return csvFileHandler.readAllLines()
            .map { line ->
                UserDto(
                    id = line[0],
                    name = line[1],
                    role = line[2]
                )
            }
    }

    override fun createUser(user: UserDto) {
        val userRow = arrayOf(user.id, user.name, user.role)

        csvFileHandler.appendLine(headerColumns = header, newRow = userRow)
    }

    override fun updateUser(user: UserDto) {
        csvFileHandler.updateLine(
            headerColumns = header,
            updatedRow = arrayOf(user.id, user.name, user.role)
        )
    }

    @OptIn(ExperimentalUuidApi::class)
    override fun deleteUser(userId: Uuid) {
        csvFileHandler.deleteLine(
            headerColumns = header,
            rowIdToDelete = userId.toString()
        )
    }
}