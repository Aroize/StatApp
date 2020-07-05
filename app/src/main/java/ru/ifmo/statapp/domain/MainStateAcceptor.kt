package ru.ifmo.statapp.domain

interface MainStateAcceptor {
    fun acceptState(state: MainState)
}