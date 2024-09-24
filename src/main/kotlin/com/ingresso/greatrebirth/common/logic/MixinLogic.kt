package com.ingresso.greatrebirth.common.logic

import com.ingresso.greatrebirth.common.capability.BuffsProvider
import net.minecraft.world.effect.MobEffect
import net.minecraft.world.effect.MobEffectInstance
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.npc.Villager
import net.minecraft.world.entity.player.Player
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable

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

    fun canWearingGold(target: Entity, cir: CallbackInfoReturnable<Boolean>) {
        if (target is Player) {
            lateinit var actualBuffs: List<String>
            target.getCapability(BuffsProvider.BUFF_CAP).ifPresent {
                actualBuffs = it.actualBuffs
            }
            if (actualBuffs.contains("buff.piglin.wearing")) {
                cir.returnValue = true
            }
        }
    }

    fun setOfferDiscount(villager: Villager, player: Player) {
        lateinit var actualBuffs: List<String>
        player.getCapability(BuffsProvider.BUFF_CAP).ifPresent {
            actualBuffs = it.actualBuffs
        }
        var costDiscount = 0
        if (actualBuffs.contains("buff.villager.discount")) {
            for (merchantOffer in villager.getOffers()) {
                costDiscount = merchantOffer.baseCostA.count / 2
                merchantOffer.addToSpecialPriceDiff(-costDiscount)
            }
        }
    }
}