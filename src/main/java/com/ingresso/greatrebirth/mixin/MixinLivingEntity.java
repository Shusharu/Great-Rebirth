package com.ingresso.greatrebirth.mixin;

import com.ingresso.greatrebirth.common.logic.MixinLogic;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(LivingEntity.class)
public class MixinLivingEntity {
    @ModifyArg(
            method = "checkTotemDeathProtection",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/LivingEntity;addEffect(Lnet/minecraft/world/effect/MobEffectInstance;)Z",
                    ordinal = 1
            )
    )
    private MobEffectInstance totemEffectAbsorption(MobEffectInstance pEffectInstance) {
        return MixinLogic.INSTANCE.totemEffectsLogic(
                (LivingEntity) (Object) this,
                pEffectInstance.getEffect(),
                pEffectInstance.getDuration(),
                pEffectInstance.getAmplifier()
        );
    }
}
