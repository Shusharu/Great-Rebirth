package com.ingresso.greatrebirth.common.block

import com.ingresso.greatrebirth.common.tile.TileAltarRebirth
import net.minecraft.core.BlockPos
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.EntityBlock
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.phys.shapes.CollisionContext
import net.minecraft.world.phys.shapes.VoxelShape

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

    override fun newBlockEntity(pos: BlockPos, state: BlockState): BlockEntity {
        return TileAltarRebirth(null, pos, state)
    }
}