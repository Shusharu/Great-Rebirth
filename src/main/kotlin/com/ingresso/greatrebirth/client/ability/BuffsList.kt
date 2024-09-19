package com.ingresso.greatrebirth.client.ability

import net.minecraft.network.chat.Component
import kotlin.random.Random

object BuffsList {
    val positiveAbilities: MutableList<Buff> = mutableListOf(
        Buff("0"),
        Buff("1"),
        Buff("2"),
        Buff("3"),
        Buff("4"),
        Buff("5"),
        Buff("6"),
    )
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
        val component = positiveAbilities[index].getComponent()
        return Pair(index, component)
    }

    private fun generateNumbers(): Boolean {
        if (positiveAbilities.isEmpty()) {
            return false
        }
        val random = Random.Default
        var num: Int
        while (numbers.size < 3) {
            num = random.nextInt(0, positiveAbilities.size)
            if (positiveAbilities.size >= 3) {
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