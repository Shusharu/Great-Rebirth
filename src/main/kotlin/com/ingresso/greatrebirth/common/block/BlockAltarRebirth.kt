package com.ingresso.greatrebirth.common.block

import com.ingresso.greatrebirth.common.tile.TileAltarRebirth
import net.minecraft.client.Minecraft
import net.minecraft.core.BlockPos
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResult
import net.minecraft.world.MenuProvider
import net.minecraft.world.entity.player.Player
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.EntityBlock
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.phys.BlockHitResult
import net.minecraft.world.phys.shapes.CollisionContext
import net.minecraft.world.phys.shapes.VoxelShape
import net.minecraftforge.network.NetworkHooks

class BlockAltarRebirth(prop: Properties) : Block(prop), EntityBlock {
    private val shape: VoxelShape = box(
        0.0,
        0.0,
        0.0,
        16.0,
        13.0,
        16.0
    )

    override fun getShape(
        state: BlockState,
        worldIn: BlockGetter,
        pos: BlockPos,
        context: CollisionContext
    ): VoxelShape {
        return shape
    }

    override fun use(
        state: BlockState,
        level: Level,
        pos: BlockPos,
        player: Player,
        hand: InteractionHand,
        blockHit: BlockHitResult
    ): InteractionResult {
        if (level.isClientSide) {
            return InteractionResult.SUCCESS
        }
        val tile = level.getBlockEntity(pos) as TileAltarRebirth
        NetworkHooks.openScreen(player as ServerPlayer, tile, pos)
        return InteractionResult.SUCCESS
    }

    override fun newBlockEntity(pos: BlockPos, state: BlockState): BlockEntity {
        return TileAltarRebirth(pos, state)
    }
}