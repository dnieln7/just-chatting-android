package xyz.dnieln7.domain.fake

fun buildException(message: String? = "There was an error :("): Exception {
    return Exception(message)
}
