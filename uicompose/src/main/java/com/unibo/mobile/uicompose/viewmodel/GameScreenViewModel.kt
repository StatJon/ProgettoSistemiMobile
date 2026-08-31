package com.unibo.mobile.uicompose.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unibo.mobile.domain.models.Ability
import com.unibo.mobile.domain.models.AbilityHeal
import com.unibo.mobile.domain.models.CharacterData
import com.unibo.mobile.domain.models.CharacterEnemy
import com.unibo.mobile.domain.models.CharacterPlayer
import com.unibo.mobile.domain.models.CombatSnapshot
import com.unibo.mobile.domain.models.CombatStatus
import com.unibo.mobile.domain.models.GamePhase
import com.unibo.mobile.domain.models.SaveGame
import com.unibo.mobile.domain.models.SaveSession
import com.unibo.mobile.domain.usecases.api.FetchEnemyByChallengeRatingUseCase
import com.unibo.mobile.domain.usecases.gamedata.ValidateDungeonLengthUseCase
import com.unibo.mobile.domain.usecases.gamelogic.ApplyPlayerAbilityCostUseCase
import com.unibo.mobile.domain.usecases.gamelogic.ApplyAbilityResultUseCase
import com.unibo.mobile.domain.usecases.gamelogic.CalculateAbilityResultUseCase
import com.unibo.mobile.domain.usecases.gamelogic.CheckCombatStatusUseCase
import com.unibo.mobile.domain.usecases.gamelogic.CheckpointUseCase
import com.unibo.mobile.domain.usecases.gamelogic.DecideEnemyAbilityUseCase
import com.unibo.mobile.domain.usecases.gamelogic.DetermineChallengeRatingUseCase
import com.unibo.mobile.domain.usecases.gamelogic.DetermineGamePhaseUseCase
import com.unibo.mobile.domain.usecases.gamelogic.LevelUpUseCase
import com.unibo.mobile.domain.usecases.savegame.LoadSaveGameUseCase
import com.unibo.mobile.domain.usecases.savegame.SaveSaveGameUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlin.coroutines.cancellation.CancellationException

/**
 * ViewModel responsible for managing the game state and logic for the GameScreen screen.
 * Handles combat, checkpoint progression, game phase transitions, and persistence of game state.
 */
class GameScreenViewModel(
    private val loadSaveGameUseCase: LoadSaveGameUseCase,
    private val saveSaveGameUseCase: SaveSaveGameUseCase,
    private val fetchEnemyByChallengeRatingUseCase: FetchEnemyByChallengeRatingUseCase,
    private val applyAbilityResultUseCase: ApplyAbilityResultUseCase,
    private val applyPlayerAbilityCostUseCase: ApplyPlayerAbilityCostUseCase,
    private val calculateAbilityResultUseCase: CalculateAbilityResultUseCase,
    private val checkCombatStatusUseCase: CheckCombatStatusUseCase,
    private val decideEnemyAbilityUseCase: DecideEnemyAbilityUseCase,
    private val determineChallengeRatingUseCase: DetermineChallengeRatingUseCase,
    private val determineGamePhaseUseCase: DetermineGamePhaseUseCase,
    private val checkpointUseCase: CheckpointUseCase,
    private val levelUpUseCase: LevelUpUseCase,
    private val validateDungeonLengthUseCase: ValidateDungeonLengthUseCase,
) : ViewModel() {

    // --- StateFlow

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _lockUi = MutableStateFlow<Boolean>(false)
    val lockUi: StateFlow<Boolean> = _lockUi

    private val _saveGame = MutableStateFlow(SaveGame(0, null))
    val saveGame: StateFlow<SaveGame> = _saveGame

    private val _dungeonIndex = MutableStateFlow(0)
    val dungeonIndex: StateFlow<Int> = _dungeonIndex

    private val _dungeonLength = MutableStateFlow(0)
    val dungeonLength: StateFlow<Int> = _dungeonLength

    private val _characterPlayer = MutableStateFlow<CharacterPlayer?>(null)
    val characterPlayer: StateFlow<CharacterPlayer?> = _characterPlayer

    private val _combatSnapshot = MutableStateFlow<CombatSnapshot?>(null)
    val combatSnapshot: StateFlow<CombatSnapshot?> = _combatSnapshot

    private val _gamePhase = MutableStateFlow(GamePhase.COMBAT)
    val gamePhase: StateFlow<GamePhase> = _gamePhase

    private val _lastActionMessage = MutableStateFlow<String?>(null)
    val lastActionMessage: StateFlow<String?> = _lastActionMessage

    private val _navigateToEndScreen = MutableStateFlow<Boolean?>(null)
    val navigateToEndScreen: StateFlow<Boolean?> = _navigateToEndScreen

    /**
     * Channel called when the user selects an ability, used to inject the user's input inside the gameloop.
     */
    private val _abilitySelectedChannel = Channel<Ability>(Channel.RENDEZVOUS)

    /**
     * Resets the _abilitySelectedChannel Channel to avoid leaks and issues.
     */
    override fun onCleared() {
        _abilitySelectedChannel.close()
        super.onCleared()
    }

    // --- Init

    init {
        viewModelScope.launch {
            loadSaveAndConstructPlayer()
            gameLoop()
        }
    }

    // --- Public Functions

    /**
     * Called when the user selects an ability to use in combat.
     *
     * @param ability The selected ability to be used.
     */
    fun onAbilitySelected(ability: Ability) {
        println("DEBUG: onAbilitySelected called: ${ability.name}")
        viewModelScope.launch {
            println("DEBUG: Sending to channel: ${ability.name}")
            _abilitySelectedChannel.trySend(ability)
        }
    }

    /**
     * Resets the navigation flag to the end screen.
     */
    fun resetNavigateToEndScreen() {
        _navigateToEndScreen.value = null
    }

    // --- Orchestrator - GameLoop Private Functions

    /**
     * Main game loop that orchestrates game flow using a state machine based on [GamePhase].
     * Handles transitions between phases and delegates to combatLoop, checkpointLoop, or endGame.
     */
    private suspend fun gameLoop() {
        println("DEBUG: gameLoop started, phase: ${_gamePhase.value}")
        while (true) {
            try {
                when (_gamePhase.value) {

                    GamePhase.DUNGEON_LOST -> {
                        println("DEBUG: Entering GamePhase.DUNGEON_LOST")
                        endGame(false)
                        return
                    }

                    GamePhase.CHECKPOINT -> {
                        println("DEBUG: Entering CheckpointLoop")
                        checkpointLoop()
                        println("DEBUG: Exiting CheckpointLoop")
                    }

                    else -> {
                        val gamePhase = determineGamePhaseUseCase.invoke(
                            _dungeonIndex.value,
                            _dungeonLength.value
                        )
                        when (gamePhase) {
                            GamePhase.COMBAT -> {
                                println("DEBUG: Entering GamePhase.COMBAT")
                                combatLoop()
                                println("DEBUG: Exiting GamePhase.COMBAT")
                            }

                            GamePhase.DUNGEON_WON -> {
                                println("DEBUG: Entering GamePhase.DUNGEON_WON")
                                endGame(true)
                                return
                            }


                            else -> throw IllegalStateException("ERROR: Unexpected game phase: $gamePhase")
                        }
                    }
                }
            } catch (e: CancellationException) {
                throw e //Ignores CancellationExceptions which are expected behaviour when closing gameLoop and related viewModelScopes
            } catch (e: Exception) {
                println("ERROR: EXCEPTION in gameLoop (phase=${_gamePhase.value}): ${e.message}")
                e.printStackTrace()
                _isLoading.value = false
                _lockUi.value = false
                endGame(false)
                return
            }
        }
    }

    /**
     * Manages the combat phase using a state machine based on [CombatStatus].
     * Handles turn-based logic between player and enemy, and transitions to CHECKPOINT on victory
     * or DUNGEON_LOST on defeat.
     * Uses an INIT + LOOP pattern
     */
    private suspend fun combatLoop() {
        // --- INIT
        if (_combatSnapshot.value == null) {
            initCombat()
        }

        // --- LOOP
        while (true) {
            val combatSnapshot = _combatSnapshot.value
                ?: throw IllegalStateException("ERROR: combatSnapshot not initialized")

            when (combatSnapshot.combatStatus) {
                CombatStatus.PLAYER_TURN -> {
                    _lockUi.value = false
                    val playerAbility = _abilitySelectedChannel.receive()
                    _lockUi.value = true
                    executeTurn(combatSnapshot, playerAbility, isPlayerTurn = true)
                }

                CombatStatus.ENEMY_TURN -> {
                    _lockUi.value = true
                    val enemyAbility = decideEnemyAbilityUseCase.invoke(
                        enemy = combatSnapshot.enemy
                    )
                    executeTurn(combatSnapshot, enemyAbility, isPlayerTurn = false)
                }

                CombatStatus.VICTORY -> {
                    //_dungeonIndex.value++
                    _combatSnapshot.value = null
                    _gamePhase.value = GamePhase.CHECKPOINT
                    _lockUi.value = false
                    return
                }

                CombatStatus.DEFEAT -> {
                    _gamePhase.value = GamePhase.DUNGEON_LOST
                    _lockUi.value = false
                    return
                }
            }
        }
    }

    /**
     * Manages all operations between two combat loops.
     */
    private suspend fun checkpointLoop() {
        _lockUi.value = true
        _isLoading.value = true

        val updatedSaveSession = checkpointUseCase.invoke(_saveGame.value.saveSession)
        saveSaveSession(updatedSaveSession)
        updateViewModelState(updatedSaveSession)


        _gamePhase.value = GamePhase.COMBAT
        _isLoading.value = false
        _lockUi.value = false
    }

    /**
     * Updates the _saveGame with updatedSaveSession
     *
     * @param updatedSaveSession the SaveSession used to update the _saveGame
     */
    private suspend fun saveSaveSession(updatedSaveSession: SaveSession) {
        _saveGame.value = _saveGame.value.copy(saveSession = updatedSaveSession)
        saveSaveGameUseCase.invoke(saveGame = _saveGame.value)
        println("DEBUG: saved ${_saveGame.value}")
    }

    /**
     * Updates the ViewModel StateFlow with the saveSession
     *
     * @param saveSession the SaveSession used to update the StateFlow
     */
    private fun updateViewModelState(saveSession: SaveSession) {
        _dungeonIndex.value = saveSession.dungeon.dungeonIndex
        _dungeonLength.value = saveSession.dungeon.dungeonLength
        _characterPlayer.value = saveSession.characterPlayer
    }

    /**
     * Determines the win or loss conditions of the active session and applies appropriate actions.
     */
    private fun endGame(isWon: Boolean) {
        _gamePhase.value = if (isWon) GamePhase.DUNGEON_WON else GamePhase.DUNGEON_LOST
        _combatSnapshot.value = null
        _navigateToEndScreen.value = isWon
        if (isWon) {
            viewModelScope.launch {
                val currentSaveGame = _saveGame.value
                val updatedSaveGame = currentSaveGame.copy(
                    winCounter = currentSaveGame.winCounter + 1,
                    saveSession = null
                )
                saveSaveGameUseCase.invoke(updatedSaveGame)
                _saveGame.value = updatedSaveGame
            }
        } else {
            viewModelScope.launch {
                val updatedSaveGame = _saveGame.value.copy(saveSession = null)
                saveSaveGameUseCase.invoke(updatedSaveGame)
                _saveGame.value = updatedSaveGame
                _characterPlayer.value = null
            }
        }
    }

    // --- Logic Private Functions

    private suspend fun loadSaveAndConstructPlayer() {
        _isLoading.value = true
        val loadedSaveGame = loadSaveGameUseCase.invoke()
        val loadedSaveSession =
            loadedSaveGame.saveSession ?: throw IllegalStateException("Error: saveSession missing")
        _saveGame.value = loadedSaveGame
        _dungeonIndex.value = loadedSaveSession.dungeon.dungeonIndex
        _dungeonLength.value =
            validateDungeonLengthUseCase.invoke(loadedSaveSession.dungeon.dungeonLength)
        _characterPlayer.value = loadedSaveSession.characterPlayer
        _isLoading.value = false
    }

    private suspend fun initCombat() {
        _lockUi.value = true
        _isLoading.value = true
        val enemyChallengeRating = determineChallengeRatingUseCase.invoke(
            dungeonIndex = _dungeonIndex.value
        )
        val enemy: CharacterEnemy =
            fetchEnemyByChallengeRatingUseCase.invoke(enemyChallengeRating)
        _combatSnapshot.value = CombatSnapshot(
            player = _characterPlayer.value
                ?: throw IllegalStateException("Player not initialized"),
            enemy = enemy,
            combatStatus = CombatStatus.PLAYER_TURN,
        )
        _isLoading.value = false
        _lockUi.value = false
    }

    private fun executeTurn(
        combatSnapshot: CombatSnapshot,
        ability: Ability,
        isPlayerTurn: Boolean
    ) {
        try {
            val caster = if (isPlayerTurn) combatSnapshot.player else combatSnapshot.enemy
            val target = if (ability is AbilityHeal) caster
            else if (isPlayerTurn) combatSnapshot.enemy
            else combatSnapshot.player

            val abilityResult = calculateAbilityResultUseCase.invoke(target, ability)
            val updatedTargetData = applyAbilityResultUseCase.invoke(target, abilityResult)

            val updatedSnapshot = if (isPlayerTurn) {
                val updatedPlayer = applyPlayerAbilityCostUseCase.invoke(
                    combatSnapshot.player,
                    abilityResult.ability
                )
                if (target == combatSnapshot.player) {
                    combatSnapshot.copy(
                        player = updatedPlayer.copy(characterData = updatedTargetData),
                        enemy = combatSnapshot.enemy
                    )
                } else {
                    combatSnapshot.copy(
                        player = updatedPlayer,
                        enemy = combatSnapshot.enemy.copy(characterData = updatedTargetData)
                    )
                }
            } else {
                if (target == combatSnapshot.enemy) {
                    combatSnapshot.copy(
                        player = combatSnapshot.player,
                        enemy = combatSnapshot.enemy.copy(characterData = updatedTargetData)
                    )
                } else {
                    combatSnapshot.copy(
                        player = combatSnapshot.player.copy(characterData = updatedTargetData),
                        enemy = combatSnapshot.enemy
                    )
                }
            }
            val newCombatStatus = checkCombatStatusUseCase.invoke(updatedSnapshot)
            _combatSnapshot.value = updatedSnapshot.copy(combatStatus = newCombatStatus)
            _characterPlayer.value = updatedSnapshot.player
        } catch (e: Exception) {
            println("DEBUG: EXCEPTION in executeTurn: ${e.message}")
            e.printStackTrace()
        }
    }
}
