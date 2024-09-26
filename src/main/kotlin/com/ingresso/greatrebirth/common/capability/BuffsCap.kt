package com.ingresso.greatrebirth.common.capability

import net.minecraft.nbt.CompoundTag
import net.minecraftforge.common.capabilities.AutoRegisterCapability

@AutoRegisterCapability
class BuffsCap {
    var isPositiveBuffs: Boolean = true
    val actualBuffs: MutableList<String> = mutableListOf()

    fun saveData(): CompoundTag {
        val generalTag = CompoundTag()
        generalTag.putBoolean("isPositiveBuffs", isPositiveBuffs)
        val listTag = CompoundTag()
        actualBuffs.forEach() {
            listTag.putString(it, "")
        }
        generalTag.put("actualBuffs", listTag)
        return generalTag
    }

    fun loadData(tag: CompoundTag) {
        isPositiveBuffs = tag.getBoolean("isPositiveBuffs")
        val listTag = tag.getCompound("actualBuffs")
        listTag.allKeys.forEach {
            if (it.contains("translation")) {
                val sliced = it.replace("translation{key='", "").replace("', args=[]}", "")
                actualBuffs.add(sliced)
            } else {
                actualBuffs.add(it)
            }
        }
    }

    fun copyFrom(oldCap: BuffsCap) {
        isPositiveBuffs = oldCap.isPositiveBuffs
        actualBuffs.addAll(oldCap.actualBuffs)
    }
}