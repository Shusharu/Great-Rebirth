package com.ingresso.greatrebirth.mixin;

import com.ingresso.greatrebirth.common.logic.MixinLogic;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.piglin.PiglinAi;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PiglinAi.class)
public class MixinPiglinAi {
    @Inject(
            method = "isWearingGold",
            at = @At("HEAD"),
            cancellable = true
    )
    private static void canWearingGold(LivingEntity pLivingEntity, CallbackInfoReturnable<Boolean> cir) {
        MixinLogic.INSTANCE.canWearingGold(pLivingEntity, cir);
    }
}
