package com.unibo.mobile.uicompose.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unibo.mobile.domain.models.Ability
import com.unibo.mobile.domain.models.CharacterData
import com.unibo.mobile.domain.models.CharacterEnemy
import com.unibo.mobile.domain.models.CharacterPlayer
import com.unibo.mobile.domain.models.CombatSnapshot
import com.unibo.mobile.domain.models.CombatStatus
import com.unibo.mobile.domain.models.GamePhase
import com.unibo.mobile.domain.models.SaveGame
import com.unibo.mobile.domain.usecases.api.FetchAbilityByClassNameUseCase
import com.unibo.mobile.domain.usecases.api.FetchAbilityByEnemyUseCase
import com.unibo.mobile.domain.usecases.api.FetchAbilityByNameUseCase
import com.unibo.mobile.domain.usecases.api.FetchEnemyByChallengeRatingUseCase
import com.unibo.mobile.domain.usecases.gamedata.GetAllPlayerClassesUseCase
import com.unibo.mobile.domain.usecases.gamedata.SetupDungeonUseCase
import com.unibo.mobile.domain.usecases.gamelogic.ApplyPlayerAbilityCostUseCase
import com.unibo.mobile.domain.usecases.gamelogic.ApplyAbilityResultUseCase
import com.unibo.mobile.domain.usecases.gamelogic.CalculateAbilityResultUseCase
import com.unibo.mobile.domain.usecases.gamelogic.CheckCombatStatusUseCase
import com.unibo.mobile.domain.usecases.gamelogic.CheckpointUseCase
import com.unibo.mobile.domain.usecases.gamelogic.DecideEnemyAbilityUseCase
import com.unibo.mobile.domain.usecases.gamelogic.DetermineChallengeRatingUseCase
import com.unibo.mobile.domain.usecases.gamelogic.DetermineGamePhaseUseCase
import com.unibo.mobile.domain.usecases.gamelogic.TurnCheckUseCase
import com.unibo.mobile.domain.usecases.savegame.LoadSaveGameUseCase
import com.unibo.mobile.domain.usecases.savegame.SaveSaveGameUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class GameScreenViewModel(

    //private val getAllPlayerClassesUseCase: GetAllPlayerClassesUseCase,
    private val loadSaveGameUseCase: LoadSaveGameUseCase,
    private val saveSaveGameUseCase: SaveSaveGameUseCase,

    //private val fetchAbilityByClassNameUseCase: FetchAbilityByClassNameUseCase,
    //private val fetchAbilityByEnemyUseCase: FetchAbilityByEnemyUseCase,
    //private val fetchAbilityByNameUseCase: FetchAbilityByNameUseCase,
    private val fetchEnemyByChallengeRatingUseCase: FetchEnemyByChallengeRatingUseCase,

    private val applyAbilityResultUseCase: ApplyAbilityResultUseCase,
    private val applyPlayerAbilityCostUseCase: ApplyPlayerAbilityCostUseCase,
    private val calculateAbilityResultUseCase: CalculateAbilityResultUseCase,
    private val checkCombatStatusUseCase: CheckCombatStatusUseCase,
    //private val checkpointUseCase: CheckpointUseCase,
    private val decideEnemyAbilityUseCase: DecideEnemyAbilityUseCase,
    private val determineChallengeRatingUseCase: DetermineChallengeRatingUseCase,
    private val determineGamePhaseUseCase: DetermineGamePhaseUseCase,
    //private val dungeonUseCase: SetupDungeonUseCase,
    //private val turnCheckUseCase: TurnCheckUseCase

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



    private val _abilitySelectedChannel = Channel<Ability>(Channel.RENDEZVOUS)

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

    fun onAbilitySelected(ability: Ability) {
        println("DEBUG: onAbilitySelected called: ${ability.name}")
        viewModelScope.launch {
            println("DEBUG: Sending to channel: ${ability.name}")
            _abilitySelectedChannel.send(ability)
        }
    }

    fun resetNavigateToEndScreen() {
        _navigateToEndScreen.value = null
    }

    // --- Orchestrator Private Functions

    private suspend fun gameLoop() {
        println("DEBUG: gameLoop started, phase: ${_gamePhase.value}")
        while (true) {
            when (_gamePhase.value) {
                GamePhase.DUNGEON_WON -> {
                    endGame(true)
                    return
                }

                GamePhase.DUNGEON_LOST -> {
                    endGame(false)
                    return
                }

                else -> {
                    val gamePhase = determineGamePhaseUseCase.invoke(
                        _dungeonIndex.value,
                        _dungeonLength.value
                    )
                    println("DEBUG: Determined phase: $gamePhase")
                    when (gamePhase) {
                        GamePhase.COMBAT -> {
                            println("DEBUG: Starting combatLoop")
                            combatLoop()
                            println("DEBUG: combatLoop finished")
                        }

                        GamePhase.CHECKPOINT -> checkpointLoop()
                        else -> throw IllegalStateException("Error: Unexpected game phase: $gamePhase")
                    }
                }
            }
        }
    }

    private suspend fun combatLoop() {
        // --- INIT
        if (_combatSnapshot.value == null) {
            initCombat()
        }

        // --- LOOP
        while (true) {
            val combatSnapshot = _combatSnapshot.value
                ?: throw IllegalStateException("Error: combatSnapshot not initialized")

            when (combatSnapshot.combatStatus) {
                CombatStatus.PLAYER_TURN -> {
                    _lockUi.value = false
                    println("DEBUG: Waiting for ability...")
                    val playerAbility = _abilitySelectedChannel.receive()
                    _lockUi.value = true
                    println("DEBUG: Received ability: ${playerAbility.name}")
                    executeTurn(combatSnapshot, playerAbility, isPlayerTurn = true)
                }

                CombatStatus.ENEMY_TURN -> {
                    _lockUi.value = true
                    println("DEBUG: Enemy turn started")
                    val enemyAbility = decideEnemyAbilityUseCase.invoke(
                        enemy = combatSnapshot.enemy
                    )
                    println("DEBUG: Enemy ability: ${enemyAbility.name}")
                    executeTurn(combatSnapshot, enemyAbility, isPlayerTurn = false)
                }

                CombatStatus.VICTORY -> {
                    _dungeonIndex.value++
                    _combatSnapshot.value = null
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

    private fun checkpointLoop() {
        //checkpointLogic TODO
    }

    private fun endGame(isWon: Boolean) {
        _gamePhase.value = if (isWon) GamePhase.DUNGEON_WON else GamePhase.DUNGEON_LOST
        _combatSnapshot.value = null
        _navigateToEndScreen.value = isWon
        if (isWon) {
            val updatedSaveGame = _saveGame.value.copy(
                winCounter = _saveGame.value.winCounter + 1
            )
            viewModelScope.launch {
                saveSaveGameUseCase.invoke(updatedSaveGame)
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
        _dungeonLength.value = loadedSaveSession.dungeon.dungeonLength
        _characterPlayer.value = loadedSaveSession.characterPlayer
        _isLoading.value = false
    }

    private suspend fun initCombat() {
        _lockUi.value = true
        _isLoading.value = true
        val enemyChallengeRating = determineChallengeRatingUseCase.invoke(
            dungeonIndex = _dungeonIndex.value
        )
        println("DEBUG: ChallengeRating = $enemyChallengeRating")
        val enemy: CharacterEnemy =
            fetchEnemyByChallengeRatingUseCase.invoke(enemyChallengeRating)
        println("DEBUG: Enemy = $enemy")
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
            val target = if (isPlayerTurn) combatSnapshot.enemy else combatSnapshot.player

            println("DEBUG: Before calculateAbilityResultUseCase")
            val abilityResult = calculateAbilityResultUseCase.invoke(target, ability)
            println("DEBUG: After calculateAbilityResultUseCase: $abilityResult")

            println("DEBUG: Before applyAbilityResultUseCase")
            val updatedTargetData = applyAbilityResultUseCase.invoke(target, abilityResult)
            println("DEBUG: After applyAbilityResultUseCase")

            val updatedSnapshot = if (isPlayerTurn) {
                println("DEBUG: Before applyPlayerAbilityCostUseCase")
                val updatedPlayer = applyPlayerAbilityCostUseCase.invoke(
                    combatSnapshot.player,
                    abilityResult.ability
                )
                println("DEBUG: After applyPlayerAbilityCostUseCase")
                combatSnapshot.copy(
                    player = updatedPlayer,
                    enemy = combatSnapshot.enemy.copy(characterData = updatedTargetData)
                )
            } else {
                combatSnapshot.copy(
                    player = combatSnapshot.player.copy(characterData = updatedTargetData),
                    enemy = combatSnapshot.enemy
                )
            }

            println("DEBUG: Before checkCombatStatusUseCase")
            val newCombatStatus = checkCombatStatusUseCase.invoke(updatedSnapshot)
            println("DEBUG: New combat status: $newCombatStatus")

            _combatSnapshot.value = updatedSnapshot.copy(combatStatus = newCombatStatus)
            _characterPlayer.value = updatedSnapshot.player
            println("DEBUG: Snapshot updated")
        } catch (e: Exception) {
            println("DEBUG: EXCEPTION in executeTurn: ${e.message}")
            e.printStackTrace()
        }
    }

}
