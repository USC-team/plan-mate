package planmate.domain.usecase.exceptions

class InvalidRoleException: Exception("The user is not an Admin")
class NameCantBeNullException :Exception("Name can't be null")
class UserNotFoundException: Exception("User is not found!")
class NoUsersFoundException: Exception("No Users are found!")
class NoProjectFoundException: Exception("No Projects are found!")
class NoStatesFoundException: Exception("No States are found!")
class NoTasksFoundException: Exception("No tASKs are found!")