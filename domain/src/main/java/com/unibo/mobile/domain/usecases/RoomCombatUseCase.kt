package com.unibo.mobile.domain.usecases

import com.unibo.mobile.domain.model.ability.Ability
import com.unibo.mobile.domain.model.combat.Action
import com.unibo.mobile.domain.model.combat.CombatState
import com.unibo.mobile.domain.model.combat.PostCombatReward
import com.unibo.mobile.domain.model.dungeon.RoomTypeCombat
import com.unibo.mobile.domain.model.entity.Ally
import com.unibo.mobile.domain.model.entity.CombatEntity
import com.unibo.mobile.domain.model.entity.Enemy
import com.unibo.mobile.domain.model.save.PlayerCharacter


interface RoomCombatUseCase {
    suspend fun startCombat(playerCharacter: PlayerCharacter, room: RoomTypeCombat): CombatState

    fun playerTurn(
        currentState: CombatState,
        ability: Ability,
        target: CombatEntity
    ): List<CombatState>

    fun initCombat(
        allies: List<Ally>,
        enemies: List<Enemy>
    ): CombatState

    fun resolveAttackPlayer(
        actor: CombatEntity,
        ability: Ability,
        target: CombatEntity,
        allEntities: List<CombatEntity>
    ): Action

    fun resolveAttackNpc(
        actor: CombatEntity,
        allEntities: List<CombatEntity>
    ): List<Action>

    fun executeAction(
        currentState: CombatState,
        actionToExecute: Action
    ): CombatState

    fun advanceTurn(currentState: CombatState): CombatState

    fun calculatePostCombatReward(enemyList: List<Enemy>): PostCombatReward

    fun updatePlayerCharacter(
        playerCharacter: PlayerCharacter,
        allies: List<Ally>,
        reward: PostCombatReward?
    ): PlayerCharacter

}
