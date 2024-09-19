package com.ingresso.greatrebirth.client.ability

import net.minecraft.network.chat.Component

data class Buff(val text: String) {
    fun getComponent(): Component = Component.translatable(text)
}
