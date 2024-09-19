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
    var buffsCap: BuffsCap? = null

    override fun <T : Any?> getCapability(cap: Capability<T>, side: Direction?): LazyOptional<T> {
        if (cap == BUFF_CAP) {
            return LazyOptional.of(::initBuffsCap).cast()
        }
        return LazyOptional.empty()
    }

    override fun serializeNBT(): CompoundTag {
        return buffsCap?.saveData() ?: CompoundTag()
    }

    override fun deserializeNBT(tag: CompoundTag) {
        buffsCap?.loadData(tag)
    }

    private fun initBuffsCap(): BuffsCap {
        if (buffsCap == null) {
            buffsCap = BuffsCap()
        }
        return buffsCap!!
    }

    companion object {
        val BUFF_CAP: Capability<BuffsCap> = CapabilityManager.get(object: CapabilityToken<BuffsCap>() {})
    }
}
