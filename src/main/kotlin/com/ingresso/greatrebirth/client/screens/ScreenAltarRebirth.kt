package com.ingresso.greatrebirth.client.screens

import com.ingresso.greatrebirth.common.container.ContainerAltarRebirth
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen
import net.minecraft.network.chat.Component
import net.minecraft.world.entity.player.Inventory

class ScreenAltarRebirth(container: ContainerAltarRebirth, player: Inventory, title: Component)
    : AbstractContainerScreen<ContainerAltarRebirth>(container, player, title) {

    override fun renderBg(gui: GuiGraphics, p_97788_: Float, p_97789_: Int, p_97790_: Int) {
        super.renderBackground(gui)
    }
}