package com.ingresso.greatrebirth.client.screens

import com.ingresso.greatrebirth.Main
import com.ingresso.greatrebirth.client.ability.BuffsList
import com.ingresso.greatrebirth.common.container.ContainerAltarRebirth
import com.ingresso.greatrebirth.common.network.NetworkHandler
import com.ingresso.greatrebirth.common.network.packet.C2SPutBuff
import com.ingresso.greatrebirth.common.tile.TileAltarRebirth
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.gui.components.Button
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen
import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.entity.player.Inventory

class ScreenAltarRebirth(container: ContainerAltarRebirth, player: Inventory, title: Component) :
    AbstractContainerScreen<ContainerAltarRebirth>(container, player, title) {

    private val background = ResourceLocation(Main.MODID, "textures/gui/rebirthaltar.png")
    private val tile: TileAltarRebirth
    private var isButtonDown = false
    private var tripleSet:
            Triple<Pair<Int, Component>, Pair<Int, Component>, Pair<Int, Component>>? = null

    init {
        imageWidth = 176
        imageHeight = 205
        inventoryLabelY = 108
        tile = container.tile
    }

    override fun render(guiGraphics: GuiGraphics, mouseX: Int, mouseY: Int, partialTick: Float) {
        super.render(guiGraphics, mouseX, mouseY, partialTick)
        clearWidgets()
        val rebirthButton = Button.builder(Component.translatable("button.rebirth")) {
            if (tile.currentBlood >= 30000) {
                tripleSet = BuffsList.createChoose()
                if (tripleSet != null) {
                    isButtonDown = true
                }
            }
        }
            .pos(leftPos + 86, topPos + 91)
            .size(80, 20)
            .build()
        addRenderableWidget(rebirthButton)

        if (isButtonDown) {
            addChooseButtons { num, text ->
                isButtonDown = false
                Minecraft.getInstance().setScreen(null)
                BuffsList.clearNumbers()
                BuffsList.remainingAbilities.removeAt(num)
                NetworkHandler.INSTANCE.sendToServer(C2SPutBuff(text))
            }
        }
    }

    override fun renderBg(
        pGuiGraphics: GuiGraphics,
        pPartialTick: Float,
        pMouseX: Int,
        pMouseY: Int
    ) {
        super.renderBackground(pGuiGraphics)
        pGuiGraphics.blit(background, leftPos, topPos, 0, 0, imageWidth, imageHeight)
        val l = bloodProgress(90)
        pGuiGraphics.blit(background, leftPos + 14, topPos + 16 + 90 - l, imageWidth, 90 - l, 18, l)

        pGuiGraphics.blit(background, leftPos + 99, topPos + 10, 0, imageHeight, 71, 20)
        pGuiGraphics.blit(background, leftPos + 99, topPos + 33, 0, imageHeight, 71, 20)
        pGuiGraphics.blit(background, leftPos + 99, topPos + 56, 0, imageHeight, 71, 20)
    }

    private fun addChooseButtons(onPress: (Int, String) -> Unit) {
        val firstChoose = Button.builder(tripleSet!!.first.second) {
            onPress.invoke(tripleSet!!.first.first, tripleSet!!.first.second.toString())
        }
            .pos(leftPos + 99, topPos + 10)
            .size(71, 20)
            .build()
        val secondChoose = Button.builder(tripleSet!!.second.second) {
            onPress.invoke(tripleSet!!.second.first, tripleSet!!.second.second.toString())
        }
            .pos(leftPos + 99, topPos + 33)
            .size(71, 20)
            .build()
        val thirdChoose = Button.builder(tripleSet!!.third.second) {
            onPress.invoke(tripleSet!!.third.first, tripleSet!!.third.second.toString())
        }
            .pos(leftPos + 99, topPos + 56)
            .size(71, 20)
            .build()

        addRenderableWidget(firstChoose)
        addRenderableWidget(secondChoose)
        addRenderableWidget(thirdChoose)
    }

    private fun bloodProgress(scale: Int): Int {
        val currentBlood = tile.currentBlood
        val factor = currentBlood / tile.capacity.toDouble()
        return (factor * scale).toInt()
    }
}