package com.ingresso.greatrebirth.common.capability

import net.minecraft.core.Direction
import net.minecraft.nbt.CompoundTag
import net.minecraft.nbt.ListTag
import net.minecraftforge.common.capabilities.Capability
import net.minecraftforge.common.capabilities.CapabilityManager
import net.minecraftforge.common.capabilities.CapabilityToken
import net.minecraftforge.common.capabilities.ICapabilitySerializable
import net.minecraftforge.common.util.LazyOptional


class BuffsProvider: ICapabilitySerializable<CompoundTag> {
    private val buffsCap: BuffsCap = BuffsCap()

    override fun <T : Any?> getCapability(cap: Capability<T>, side: Direction?): LazyOptional<T> {
        return BUFF_CAP.orEmpty(cap, LazyOptional.of(::buffsCap))
    }

    override fun serializeNBT(): CompoundTag {
        return buffsCap.saveData()
    }

    override fun deserializeNBT(tag: CompoundTag) {
        buffsCap.loadData(tag)
    }

    companion object {
        val BUFF_CAP: Capability<BuffsCap> = CapabilityManager.get(object: CapabilityToken<BuffsCap>() {})
    }
}
