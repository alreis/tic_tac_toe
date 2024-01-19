package com.tictactoe.models

import io.ktor.server.websocket.*
import io.ktor.websocket.*
import kotlinx.coroutines.Dispatchers

class TicTacToeGame {
    private val state = MutableStateFlow(GameState())

    private val playerSockets = ConcurrentHashMap<Char, WebSocketSession>()

    private val gameScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    fun connectPlayer(session: WebSocketsSession): Char? {
        val isPlayerX = state.value.connectedPlayers.any { it == 'X'}
        val player = if(isPlayerX) 'O' else 'X'

        state.update {
            if(state.value.connectedPlayers.contains(player)) {
                return@update null
            }
            if(!playerSockets.containsKey(player)) {
                playerSockets[player] = session
            }

            it.copy(
                connectedPlayers = it.connectedPlayers + player
            )
        }
        return player
    }

    fun disconnectPlayer(player: Char) {
        playerSockets.remove(player)
        state.update {
            it.copy(
                connectedPlayers = it.connectedPlayers - player
            )
        }
    }

    fun broadcast(state: GameState)
}
