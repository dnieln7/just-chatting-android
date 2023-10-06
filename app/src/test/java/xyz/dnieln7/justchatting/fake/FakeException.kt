package xyz.dnieln7.justchatting.fake

fun buildException(message: String? = "There was an error :("): Exception {
    return Exception(message)
}
