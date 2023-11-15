package xyz.dnieln7.testing.fake

fun buildException(message: String? = "There was an error :("): Exception {
    return Exception(message)
}
