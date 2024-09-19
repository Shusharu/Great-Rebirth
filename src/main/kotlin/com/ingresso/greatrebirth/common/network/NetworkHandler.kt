package com.ingresso.greatrebirth.common.network

import com.ingresso.greatrebirth.Main
import com.ingresso.greatrebirth.common.network.packet.C2SPutBuff
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.level.ServerPlayer
import net.minecraftforge.network.NetworkDirection
import net.minecraftforge.network.NetworkRegistry
import net.minecraftforge.network.PacketDistributor

object NetworkHandler {
    const val PROTOCOL_VERSION = "1"
    val INSTANCE = NetworkRegistry.newSimpleChannel(
        ResourceLocation(Main.MODID, "packet"),
        ::PROTOCOL_VERSION,
        PROTOCOL_VERSION::equals,
        PROTOCOL_VERSION::equals
    )

    fun registerPacket() {
        INSTANCE.messageBuilder(C2SPutBuff::class.java, 1, NetworkDirection.PLAY_TO_SERVER)
            .encoder(C2SPutBuff::encode)
            .decoder(C2SPutBuff::decode)
            .consumerNetworkThread(C2SPutBuff::handle)
            .add()
    }

    fun <T> sendToPlayer(player: () -> ServerPlayer, message: T) {
        INSTANCE.send(PacketDistributor.PLAYER.with(player), message)
    }
}