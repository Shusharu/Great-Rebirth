package com.ingresso.greatrebirth.mixin;

import com.ingresso.greatrebirth.common.logic.MixinLogic;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Villager.class)
public class MixinVillager {
    @Inject(method = "updateSpecialPrices", at = @At("TAIL"))
    private void setOfferDiscount(Player pPlayer, CallbackInfo ci) {
        MixinLogic.INSTANCE.setOfferDiscount((Villager) (Object) this, pPlayer);
    }
}
