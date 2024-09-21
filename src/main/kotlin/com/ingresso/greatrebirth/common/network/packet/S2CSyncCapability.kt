package com.ingresso.greatrebirth.common.network.packet

import com.ingresso.greatrebirth.client.ability.BuffsList
import com.ingresso.greatrebirth.common.capability.BuffsProvider
import net.minecraft.client.Minecraft
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.FriendlyByteBuf
import net.minecraftforge.network.NetworkEvent
import java.util.function.Supplier

class S2CSyncCapability(private val nbt: CompoundTag) {
    companion object {
        fun encode(msg: S2CSyncCapability, buf: FriendlyByteBuf) {
            buf.writeNbt(msg.nbt)
        }

        fun decode(buf: FriendlyByteBuf): S2CSyncCapability {
            return S2CSyncCapability(buf.readNbt()!!)
        }

        fun handle(msg: S2CSyncCapability, ctx: Supplier<NetworkEvent.Context>) {
            ctx.get().enqueueWork {
                Minecraft.getInstance().player!!.getCapability(BuffsProvider.BUFF_CAP).ifPresent {
                    it.loadData(msg.nbt)
                    BuffsList.remainingAbilities.clear()

                }
            }
            ctx.get().packetHandled = true
        }
    }
}