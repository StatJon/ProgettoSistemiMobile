package com.unibo.mobile.uicompose.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unibo.mobile.domain.models.Ability
import com.unibo.mobile.domain.models.ChallengeRating
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
import com.unibo.mobile.domain.usecases.gamelogic.CombatLossUseCase
import com.unibo.mobile.domain.usecases.gamelogic.CombatWinUseCase
import com.unibo.mobile.domain.usecases.gamelogic.DecideEnemyAbilityUseCase
import com.unibo.mobile.domain.usecases.gamelogic.DetermineChallengeRatingUseCase
import com.unibo.mobile.domain.usecases.gamelogic.DetermineGamePhaseUseCase
import com.unibo.mobile.domain.usecases.gamelogic.TurnCheckUseCase
import com.unibo.mobile.domain.usecases.savegame.LoadSaveGameUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class GameScreenViewModel(

    private val getAllPlayerClassesUseCase: GetAllPlayerClassesUseCase,
    private val loadSaveGameUseCase: LoadSaveGameUseCase,

    private val fetchAbilityByClassNameUseCase: FetchAbilityByClassNameUseCase,
    private val fetchAbilityByEnemyUseCase: FetchAbilityByEnemyUseCase,
    private val fetchAbilityByNameUseCase: FetchAbilityByNameUseCase,
    private val fetchEnemyByChallengeRatingUseCase: FetchEnemyByChallengeRatingUseCase,

    private val applyAbilityResultUseCase: ApplyAbilityResultUseCase,
    private val applyPlayerAbilityCostUseCase: ApplyPlayerAbilityCostUseCase,
    private val calculateAbilityResultUseCase: CalculateAbilityResultUseCase,
    private val checkCombatStatusUseCase: CheckCombatStatusUseCase,
    private val checkpointUseCase: CheckpointUseCase,
    private val combatLossUseCase: CombatLossUseCase,
    private val combatWinUseCase: CombatWinUseCase,
    private val decideEnemyAbilityUseCase: DecideEnemyAbilityUseCase,
    private val determineChallengeRatingUseCase: DetermineChallengeRatingUseCase,
    private val determineGamePhaseUseCase: DetermineGamePhaseUseCase,
    private val dungeonUseCase: SetupDungeonUseCase,
    private val turnCheckUseCase: TurnCheckUseCase

) : ViewModel() {

    // --- StateFlow

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

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
        viewModelScope.launch {
            _abilitySelectedChannel.send(ability)
        }
    }

    // --- Orchestrator Private Functions

    private suspend fun gameLoop() {
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
                when (gamePhase) {
                    GamePhase.COMBAT -> combatLoop()
                    GamePhase.CHECKPOINT -> checkpointLoop()
                    else -> throw IllegalStateException("Error: Unexpected game phase: $gamePhase")
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
        val combatSnapshot = _combatSnapshot.value
            ?: throw IllegalStateException("Error: combatSnapshot not initialized")

        when (combatSnapshot.combatStatus) {
            CombatStatus.PLAYER_TURN -> {
                val playerAbility = _abilitySelectedChannel.receive()

                val abilityResult = calculateAbilityResultUseCase.invoke(
                    target = combatSnapshot.enemy,
                    ability = playerAbility
                )

                val updatedCharacterDataEnemy = applyAbilityResultUseCase.invoke(
                    target = combatSnapshot.enemy,
                    abilityResult = abilityResult
                )

                val updatedCharacterDataPlayer = applyPlayerAbilityCostUseCase.invoke(
                    player = combatSnapshot.player,
                    ability = abilityResult.ability
                )

                val updatedSnapshot = combatSnapshot.copy(
                    player = combatSnapshot.player.copy(
                        currentManaPoints = updatedCharacterDataPlayer.currentManaPoints,
                        characterData = updatedCharacterDataPlayer.characterData
                    ),
                    enemy = combatSnapshot.enemy.copy(characterData = updatedCharacterDataEnemy)
                )

                val newCombatStatus = checkCombatStatusUseCase.invoke(updatedSnapshot)

                _combatSnapshot.value = updatedSnapshot.copy(
                    combatStatus = newCombatStatus
                )

            }

            CombatStatus.ENEMY_TURN -> {
                val enemyAbility = decideEnemyAbilityUseCase.invoke(
                    enemy = combatSnapshot.enemy
                )
                val abilityResult = calculateAbilityResultUseCase.invoke(
                    target = combatSnapshot.player,
                    ability = enemyAbility
                )
                val updatedCharacterDataPlayer = applyAbilityResultUseCase.invoke(
                    target = combatSnapshot.player,
                    abilityResult = abilityResult
                )

                val updatedSnapshot = combatSnapshot.copy(
                    player = combatSnapshot.player.copy(characterData = updatedCharacterDataPlayer)
                )

                val newCombatStatus = checkCombatStatusUseCase.invoke(updatedSnapshot)

                _combatSnapshot.value = updatedSnapshot.copy(
                    combatStatus = newCombatStatus
                )
            }

            CombatStatus.VICTORY -> {
                _dungeonIndex.value++
                _combatSnapshot.value = null
                return
            }

            CombatStatus.DEFEAT -> {
                _gamePhase.value = GamePhase.DUNGEON_LOST
                return
            }
        }
    }

    private fun checkpointLoop() {
        //checkpointLogic TODO
    }

    private fun endGame(isWon: Boolean) {
        _gamePhase.value = if (isWon) GamePhase.DUNGEON_WON else GamePhase.DUNGEON_LOST
        _combatSnapshot.value = null
        //TODO add onNavigation
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
        _characterPlayer.value = loadedSaveSession.playerCharacter
        _isLoading.value = false
    }

    private suspend fun initCombat() {
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
    }
}
