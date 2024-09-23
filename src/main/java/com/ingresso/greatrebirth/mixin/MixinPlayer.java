package com.ingresso.greatrebirth.mixin;

import com.ingresso.greatrebirth.common.logic.MixinLogic;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(Player.class)
public class MixinPlayer {
    @ModifyArg(
            method = "attack",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/Entity;hurt(Lnet/minecraft/world/damagesource/DamageSource;F)Z"
            ),
            index = 1
    )
    private float hurtGeneral(float pAmount) {
        return MixinLogic.INSTANCE.hurtLogic((Player) (Object) this, pAmount);
    }

    @ModifyArg(
            method = "attack",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/LivingEntity;hurt(Lnet/minecraft/world/damagesource/DamageSource;F)Z"
            ),
            index = 1
    )
    private float hurtNear(float pAmount) {
        return MixinLogic.INSTANCE.hurtLogic((Player) (Object) this, pAmount);
    }
}
