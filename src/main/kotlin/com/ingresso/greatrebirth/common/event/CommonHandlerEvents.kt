package com.ingresso.greatrebirth.common.event

import com.ingresso.greatrebirth.client.ability.BuffsList
import com.ingresso.greatrebirth.common.capability.BuffsProvider
import com.ingresso.greatrebirth.common.network.NetworkHandler
import com.ingresso.greatrebirth.common.network.packet.S2CSyncCapability
import net.minecraft.nbt.CompoundTag
import net.minecraft.server.level.ServerPlayer
import net.minecraftforge.event.entity.player.PlayerEvent
import net.minecraftforge.eventbus.api.SubscribeEvent

class CommonHandlerEvents {
    @SubscribeEvent
    fun onPlayerClone(event: PlayerEvent.Clone) {
        if (!event.isWasDeath) return
        lateinit var nbt: CompoundTag
        val oldPlayer = event.original
        val newPlayer = event.entity

        oldPlayer.reviveCaps()
        oldPlayer.getCapability(BuffsProvider.BUFF_CAP).ifPresent {
            nbt = it.saveData()
        }
        newPlayer.getCapability(BuffsProvider.BUFF_CAP).ifPresent {
            it.loadData(nbt)
            if (it.actualBuffs.isNotEmpty()) {
                if (it.isPositiveBuffs) {
                    it.actualBuffs.removeLast()
                } else if (!it.isPositiveBuffs && it.actualBuffs.size < 15) {
                    // TODO: add new de-buff
                }
            } else {
                // TODO: add first de-buff
                it.isPositiveBuffs = false
            }
        }
        newPlayer.invalidateCaps()
    }

    @SubscribeEvent
    fun onPlayerJoin(event: PlayerEvent.PlayerLoggedInEvent) {
        val player = event.entity as ServerPlayer
        player.getCapability(BuffsProvider.BUFF_CAP).ifPresent {
            NetworkHandler.sendToPlayer({ player }, S2CSyncCapability(it.saveData()))
        }
    }
}