package com.ingresso.greatrebirth.client.ability

import net.minecraft.network.chat.Component
import kotlin.random.Random

object BuffsList {
    val positiveAbilities: List<Buff> = listOf(
        Buff("buff.damage"), Buff("buff.totem"), Buff("buff.piglin.wearing"),
        Buff("buff.enderman.anger"), Buff("buff.villager.discount"), Buff("buff.sacrifice.lp"),
        Buff("buff.xp"), Buff("7"), Buff("8"),
        Buff("9"), Buff("10"), Buff("11"),
        Buff("12"), Buff("13"), Buff("14"),
    )
    var remainingAbilities: MutableList<Buff> = mutableListOf()
    private val numbers = mutableListOf<Int>()

    fun createChoose(): Triple<Pair<Int, Component>, Pair<Int, Component>, Pair<Int, Component>>? {
        if (!generateNumbers()) {
            return null
        }
        return Triple(
            generateAbility(numbers[0]),
            generateAbility(numbers[1]),
            generateAbility(numbers[2])
        )
    }

    fun clearNumbers() = numbers.clear()

    private fun generateAbility(index: Int): Pair<Int, Component> {
        val component = remainingAbilities[index].getComponent()
        return Pair(index, component)
    }

    private fun generateNumbers(): Boolean {
        if (remainingAbilities.isEmpty()) {
            return false
        }
        val random = Random.Default
        var num: Int
        while (numbers.size < 3) {
            num = random.nextInt(0, remainingAbilities.size)
            if (remainingAbilities.size >= 3) {
                if (!numbers.contains(num)) {
                    numbers.add(num)
                }
            } else {
                numbers.add(num)
            }
        }
        return true
    }
}