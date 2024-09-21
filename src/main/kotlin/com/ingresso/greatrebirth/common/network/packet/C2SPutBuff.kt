package com.ingresso.greatrebirth.common.network.packet

import com.ingresso.greatrebirth.common.capability.BuffsProvider
import net.minecraft.network.FriendlyByteBuf
import net.minecraftforge.network.NetworkEvent
import java.util.function.Supplier

class C2SPutBuff(private val buff: String) {
    companion object {
        fun encode(msg: C2SPutBuff, buf: FriendlyByteBuf) {
            buf.writeUtf(msg.buff)
        }

        fun decode(buf: FriendlyByteBuf): C2SPutBuff {
            return C2SPutBuff(buf.readUtf())
        }

        fun handle(msg: C2SPutBuff, ctx: Supplier<NetworkEvent.Context>) {
            ctx.get().enqueueWork {
                val player = ctx.get().sender
                player?.getCapability(BuffsProvider.BUFF_CAP)?.ifPresent {
                    if (it.isPositiveBuffs) {
                        it.actualBuffs.add(msg.buff)
                    }
                }
            }
            ctx.get().packetHandled = true
        }
    }
}