package com.ingresso.greatrebirth.common.logic

import com.ingresso.greatrebirth.common.capability.BuffsProvider
import net.minecraft.world.effect.MobEffect
import net.minecraft.world.effect.MobEffectInstance
import net.minecraft.world.effect.MobEffects
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.player.Player

object MixinLogic {
    fun hurtLogic(player: Player, damage: Float): Float {
        lateinit var actualBuffs: List<String>
        player.getCapability(BuffsProvider.BUFF_CAP).ifPresent {
            actualBuffs = it.actualBuffs
        }
        if (actualBuffs.contains("buff.damage")) {
            return damage * 1.3f
        }
        return damage
    }

    fun totemEffectsLogic(
        entity: LivingEntity,
        effect: MobEffect,
        duration: Int,
        amplifier: Int
    ): MobEffectInstance {
        if (entity is Player) {
            lateinit var actualBuffs: List<String>
            entity.getCapability(BuffsProvider.BUFF_CAP).ifPresent {
                actualBuffs = it.actualBuffs
            }
            if (actualBuffs.contains("buff.totem")) {
                return MobEffectInstance(effect, duration * 12, amplifier)
            }
        }
        return MobEffectInstance(effect, duration, amplifier)
    }
}