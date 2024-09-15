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
import wayoftime.bloodmagic.common.item.routing.IRoutingFilterProvider

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
        addSlot(Slot(tile, 0, 80, 51))

        for (i in 0..2) {
            for (j in 0..8) {
                addSlot(Slot(player, j + i * 9 + 9, 8 + j * 18, 123 + i * 18))
            }
        }
        for (i in 0..8) {
            addSlot(Slot(player, i, 8 + i * 18, 181))
        }
    }

    override fun quickMoveStack(pPlayer: Player, pIndex: Int): ItemStack {
        var itemstack = ItemStack.EMPTY
        val slot = slots[pIndex]
        if (slot != null && slot.hasItem()) {
            val itemstack1 = slot.item
            itemstack = itemstack1.copy()
            if (pIndex == 0) {
                if (!moveItemStackTo(itemstack1, 1, 37, false)) {
                    return ItemStack.EMPTY
                }
            } else if (pIndex > 0) {
                if (!moveItemStackTo(itemstack1, 0, 1, false)) {
                    return ItemStack.EMPTY
                }
            }

            if (itemstack1.isEmpty) {
                slot.set(ItemStack.EMPTY)
            } else {
                slot.setChanged()
            }

            if (itemstack1.count == itemstack.count) {
                return ItemStack.EMPTY
            }

            slot.onTake(pPlayer, itemstack1)
        }
        return itemstack
    }

    override fun stillValid(player: Player): Boolean = tile.stillValid(player)
}