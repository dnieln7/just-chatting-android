package xyz.dnieln7.domain.provider

interface ResourceProvider {
    fun getString(id: Int): String
    fun getString(id: Int, vararg args: Any): String
}