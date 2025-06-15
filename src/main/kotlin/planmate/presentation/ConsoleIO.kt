package planmate.presentation

import org.koin.core.logger.MESSAGE

class ConsoleIO {
    companion object {
        fun read(): String {
            return readln().trim()
        }

        fun write(message: String) {
            println(message)
        }

        fun writeSuccess(message: String) {
            println(ConsoleColors.GREEN_COLOR + message + ConsoleColors.RESET_COLOR)
        }

        fun writeError(message: String) {
            println(ConsoleColors.RED_COLOR + message + ConsoleColors.RESET_COLOR)
        }

        fun writeWelcome(message: String) {
            println(ConsoleColors.MAGENTA_COLOR + message + ConsoleColors.RESET_COLOR)
        }
    }
}