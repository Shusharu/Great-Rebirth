package com.ingresso.greatrebirth.common.tile

import com.ingresso.greatrebirth.Main
import com.ingresso.greatrebirth.common.container.ContainerAltarRebirth
import net.minecraft.core.BlockPos
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.chat.Component
import net.minecraft.world.MenuProvider
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.inventory.AbstractContainerMenu
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState
import net.minecraftforge.fluids.FluidType
import wayoftime.bloodmagic.altar.IBloodAltar
import wayoftime.bloodmagic.common.tile.TileInventory
import kotlin.math.min

class TileAltarRebirth(
    type: BlockEntityType<*>,
    pos: BlockPos,
    state: BlockState
) : TileInventory(type, 1, "rebirthaltar", pos, state),
    MenuProvider, IBloodAltar
{
    private var bloodAmount = 0

    constructor(pos: BlockPos, state: BlockState) : this(Main.TILE_ALTAR_REBIRTH.get(), pos, state)

    override fun serialize(tag: CompoundTag): CompoundTag {
        super.serialize(tag)
        tag.putInt("bloodAmount", bloodAmount)
        return tag
    }

    override fun deserialize(tag: CompoundTag) {
        super.deserialize(tag)
        bloodAmount = tag.getInt("bloodAmount")
    }

    override fun getCapacity(): Int = FluidType.BUCKET_VOLUME * 100

    override fun getCurrentBlood(): Int = bloodAmount

    override fun getTier(): Int = 5

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
        bloodAmount += min(capacity - bloodAmount, amount)
    }

    override fun startCycle() {

    }

    override fun checkTier() {

    }

    override fun isActive(): Boolean = false

    override fun setActive() {}

    override fun fillMainTank(amount: Int): Int {
        val filledAmount = min(capacity - bloodAmount, amount)
        bloodAmount += filledAmount

        return filledAmount
    }

    override fun requestPauseAfterCrafting(cooldown: Int) {

    }

    override fun createMenu(
        id: Int,
        inv: Inventory,
        player: Player
    ): AbstractContainerMenu = ContainerAltarRebirth(id, this, inv)

    override fun getDisplayName(): Component = Component.literal("Rebirth Altar")
}