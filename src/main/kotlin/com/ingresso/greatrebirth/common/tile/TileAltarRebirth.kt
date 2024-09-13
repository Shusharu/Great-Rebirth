package com.ingresso.greatrebirth.common.tile

import com.ingresso.greatrebirth.Main
import net.minecraft.core.BlockPos
import net.minecraft.world.level.block.state.BlockState
import net.minecraftforge.fluids.FluidType
import wayoftime.bloodmagic.altar.IBloodAltar
import wayoftime.bloodmagic.common.tile.TileInventory

class TileAltarRebirth(
    pos: BlockPos,
    state: BlockState
) : TileInventory(Main.TILE_ALTAR_REBIRTH.get(), 1, "rebirthaltar", pos, state), IBloodAltar {
    override fun getCapacity(): Int = FluidType.BUCKET_VOLUME * 100

    override fun getCurrentBlood(): Int {
        TODO("Not yet implemented")
    }

    override fun getTier(): Int = 1

    override fun getProgress(): Int = 0

    override fun getSacrificeMultiplier(): Float = 0f

    override fun getSelfSacrificeMultiplier(): Float = 0f

    override fun getOrbMultiplier(): Float = 0f

    override fun getDislocationMultiplier(): Float = 0f

    override fun getConsumptionMultiplier(): Float = 0f

    override fun getConsumptionRate(): Float = 0f

    override fun getChargingRate(): Int = 0

    override fun getChargingFrequency(): Int = 0

    override fun getTotalCharge(): Int = 0

    override fun getLiquidRequired(): Int = 0

    override fun getBufferCapacity(): Int = 0

    override fun sacrificialDaggerCall(amount: Int, isSacrifice: Boolean) {

    }

    override fun startCycle() {

    }

    override fun checkTier() {

    }

    override fun isActive(): Boolean = false

    override fun setActive() {}

    override fun fillMainTank(amount: Int): Int {
        TODO("Not yet implemented")
    }

    override fun requestPauseAfterCrafting(cooldown: Int) {

    }

}