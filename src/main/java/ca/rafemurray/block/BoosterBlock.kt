package ca.rafemurray.block

import com.mojang.serialization.MapCodec
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.util.RandomSource
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.InsideBlockEffectApplier
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.context.BlockPlaceContext
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.Level
import net.minecraft.world.level.LevelReader
import net.minecraft.world.level.ScheduledTickAccess
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.PipeBlock
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.StateDefinition
import net.minecraft.world.level.block.state.properties.BlockStateProperties
import net.minecraft.world.level.block.state.properties.EnumProperty
import net.minecraft.world.level.block.state.properties.Property
import net.minecraft.world.phys.shapes.CollisionContext
import net.minecraft.world.phys.shapes.Shapes
import net.minecraft.world.phys.shapes.VoxelShape

//TODO: Add textures for "connected" boosters
class BoosterBlock(properties: Properties) : PipeBlock(16.0f, properties) {
    init {
        registerDefaultState(
            stateDefinition.any()
                .setValue(NORTH, false)
                .setValue(SOUTH, false)
                .setValue(EAST, false)
                .setValue(WEST, false)
                .setValue(UP, false)
                .setValue(DOWN, false)
                .setValue(FACING, Direction.NORTH)
        )
    }

    override fun entityInside(
        state: BlockState, level: Level, pos: BlockPos,
        entity: Entity, effectApplier: InsideBlockEffectApplier,
        bl: Boolean
    ) {
        if (entity is Player) {
            this.boost(state, entity)
        }
    }

    private fun boost(state: BlockState, entity: Entity) {
        val velocity = entity.deltaMovement
        if (!state.`is`(ModBlocks.BOOSTER)) return
        val facing = state.getValue(FACING)

        var x = velocity.x
        var y = velocity.y
        var z = velocity.z
        when (facing) {
            Direction.NORTH -> z -= 0.5
            Direction.SOUTH -> z += 0.5
            Direction.EAST -> x += 0.5
            Direction.WEST -> x -= 0.5
            Direction.UP -> y += 0.25
            Direction.DOWN -> y -= 0.25
            null -> {}
        }
        entity.setDeltaMovement(x, y, z)
    }

    override fun getStateForPlacement(context: BlockPlaceContext): BlockState {
        return getStateWithConnections(
            context.level, context.clickedPos, context.clickedFace,
            this.defaultBlockState()
        )
    }

    override fun updateShape(
        blockState: BlockState,
        levelReader: LevelReader,
        scheduledTickAccess: ScheduledTickAccess,
        blockPos: BlockPos,
        direction: Direction,
        blockPos2: BlockPos,
        other: BlockState,
        randomSource: RandomSource
    ): BlockState {
        val canConnect = other.`is`(this) && canConnect(other, blockState.getValue(FACING))
        return blockState.setValue(PROPERTY_BY_DIRECTION[direction] as Property<Boolean>, canConnect)
    }

    override fun createBlockStateDefinition(builder: StateDefinition.Builder<Block, BlockState>) {
        builder.add(FACING, NORTH, EAST, SOUTH, WEST, UP, DOWN)
    }

    override fun codec(): MapCodec<BoosterBlock> {
        return CODEC
    }

    override fun getVisualShape(
        blockState: BlockState,
        blockGetter: BlockGetter,
        blockPos: BlockPos,
        collisionContext: CollisionContext
    ): VoxelShape {
        return Shapes.empty()
    }

    override fun getShadeBrightness(blockState: BlockState, blockGetter: BlockGetter, blockPos: BlockPos): Float {
        return 1.0f
    }

    override fun propagatesSkylightDown(blockState: BlockState): Boolean {
        return true
    }

    companion object {
        val CODEC: MapCodec<BoosterBlock> = simpleCodec { properties: Properties ->
            BoosterBlock(
                properties
            )
        }
        val FACING: EnumProperty<Direction> = BlockStateProperties.FACING

        private fun canConnect(neighbour: BlockState, facing: Direction): Boolean {
            if (neighbour.block !is BoosterBlock) {
                return false
            }
            if (neighbour.getValue(FACING) != facing) {
                return false
            }
            return true
        }

        fun getStateWithConnections(
            blockGetter: BlockGetter,
            blockPos: BlockPos,
            direction: Direction,
            blockState: BlockState
        ): BlockState {
            val below = blockGetter.getBlockState(blockPos.below())
            val above = blockGetter.getBlockState(blockPos.above())
            val north = blockGetter.getBlockState(blockPos.north())
            val south = blockGetter.getBlockState(blockPos.south())
            val east = blockGetter.getBlockState(blockPos.east())
            val west = blockGetter.getBlockState(blockPos.west())
            return blockState
                .setValue(DOWN, canConnect(below, direction))
                .setValue(UP, canConnect(above, direction))
                .setValue(NORTH, canConnect(north, direction))
                .setValue(SOUTH, canConnect(south, direction))
                .setValue(EAST, canConnect(east, direction))
                .setValue(WEST, canConnect(west, direction))
                .setValue(FACING, direction)
        }
    }
}
