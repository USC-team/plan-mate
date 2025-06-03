package planmate.domain.models

data class User(
    val id: String,
    val name: String,
    val role: Role
)

object DummyUsers {
    val users: List<User> = listOf(
        User("u1", "Ali", Role.ADMIN),
        User("u2", "Omar", Role.MATE),
        User("u3", "Alaa", Role.MATE)
    )
}