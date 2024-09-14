package com.ingresso.greatrebirth.common.container

import com.ingresso.greatrebirth.Main
import com.ingresso.greatrebirth.common.tile.TileAltarRebirth
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.world.Container
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.inventory.AbstractContainerMenu
import net.minecraft.world.inventory.Slot
import net.minecraft.world.item.ItemStack

class ContainerAltarRebirth(
    id: Int,
    tile: TileAltarRebirth,
    playerInventory: Inventory
) : AbstractContainerMenu(Main.CONTAINER_ALTAR_REBIRTH.get(), id) {
    val tile: TileAltarRebirth

    init {
        init(playerInventory, tile)
        this.tile = tile
    }

    constructor(
        windowId: Int,
        playerInventory: Inventory,
        extraData: FriendlyByteBuf
    ) : this(
        windowId,
        playerInventory.player.level().getBlockEntity(extraData.readBlockPos()) as TileAltarRebirth,
        playerInventory
    )

    private fun init(player: Container, tile: Container) {
        addSlot(Slot(tile, 0, 62, 15))

        for (i in 0..2) {
            for (j in 0..8) {
                addSlot(Slot(player, j + i * 9 + 9, 8 + j * 18, 123 + i * 18))
            }
        }
        for (i in 0..8) {
            addSlot(Slot(player, i, 8 + i * 18, 181))
        }
    }

    override fun quickMoveStack(p_38941_: Player, p_38942_: Int): ItemStack {
        TODO("Not yet implemented")
    }

    override fun stillValid(player: Player): Boolean = tile.stillValid(player)
}