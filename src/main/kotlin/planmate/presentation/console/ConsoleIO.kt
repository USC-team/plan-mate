package planmate.presentation.console

class ConsoleIO {
    companion object {
        private const val  STARTS_NUMBER=60
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
            println(ConsoleColors.MAGENTA_COLOR)
            printStars()
            printBlanks()
            printMessage(message)
            printBlanks()
            printStars()
            print(ConsoleColors.RESET_COLOR)
        }

        private fun printStars(){
            (0..STARTS_NUMBER)
                .forEach {
                    print("*")
                }
            println()
        }

        private fun printBlanks(){
            print("*")
            (0..STARTS_NUMBER -2)
                .forEach {
                    print(" ")
                }
            print("*")
            println()
        }

        private fun printMessage(message: String){
            print("*")
            (0 .. ((STARTS_NUMBER - message.length -2) / 2)).forEach {
                print(" ")
            }
            print(message)
            (0 .. ((STARTS_NUMBER - message.length -2) / 2)).forEach {
                print(" ")
            }
            print("*")
            println()
        }
    }
}