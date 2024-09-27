package com.ingresso.greatrebirth.common.event

import com.ingresso.greatrebirth.client.ability.BuffsList
import com.ingresso.greatrebirth.common.capability.BuffsProvider
import com.ingresso.greatrebirth.common.network.NetworkHandler
import com.ingresso.greatrebirth.common.network.packet.S2CSyncCapability
import net.minecraft.nbt.CompoundTag
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.effect.MobEffectInstance
import net.minecraftforge.event.entity.living.EnderManAngerEvent
import net.minecraftforge.event.entity.player.PlayerEvent
import net.minecraftforge.eventbus.api.SubscribeEvent
import wayoftime.bloodmagic.event.SacrificeKnifeUsedEvent

class CommonHandlerEvents {
    @SubscribeEvent
    fun onPlayerClone(event: PlayerEvent.Clone) {
        event.original.reviveCaps()
        event.original.getCapability(BuffsProvider.BUFF_CAP).ifPresent { oldCap ->
            event.entity.getCapability(BuffsProvider.BUFF_CAP).ifPresent { newCap ->
                newCap.copyFrom(oldCap)
                if (newCap.actualBuffs.isNotEmpty()) {
                    if (newCap.isPositiveBuffs) {
                        newCap.actualBuffs.removeLast()
                    } else if (!newCap.isPositiveBuffs && newCap.actualBuffs.size < 15) {
                        // TODO: add new de-buff
                    }
                } else {
                    // TODO: add first de-buff
                    newCap.isPositiveBuffs = false
                }
            }
        }
        event.original.invalidateCaps()
    }

    @SubscribeEvent
    fun onPlayerJoin(event: PlayerEvent.PlayerLoggedInEvent) {
        val player = event.entity as ServerPlayer
        player.getCapability(BuffsProvider.BUFF_CAP).ifPresent {
            NetworkHandler.sendToPlayer({ player }, S2CSyncCapability(it.saveData()))
        }
    }

    @SubscribeEvent
    fun onEndermanAnger(event: EnderManAngerEvent) {
        event.player.getCapability(BuffsProvider.BUFF_CAP).ifPresent {
            if (it.actualBuffs.contains("buff.enderman.anger")) {
                event.isCanceled = true
            }
        }
    }

    @SubscribeEvent
    fun onSacrificePlayer(event: SacrificeKnifeUsedEvent) {
        event.player.getCapability(BuffsProvider.BUFF_CAP).ifPresent {
            if (it.actualBuffs.contains("buff.sacrifice.lp")) {
                event.lpAdded += 200
            }
        }
    }
}