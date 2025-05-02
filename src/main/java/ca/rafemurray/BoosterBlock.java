package ca.rafemurray;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCollisionHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import net.minecraft.world.tick.ScheduledTickView;

//TODO: Add textures for "connected" boosters
public class BoosterBlock extends ConnectingBlock{
    public static final MapCodec<BoosterBlock> CODEC = createCodec(BoosterBlock::new);
    public static final EnumProperty<Direction> FACING = Properties.FACING;
    public static final BooleanProperty NORTH = ConnectingBlock.NORTH;
    public static final BooleanProperty EAST = ConnectingBlock.EAST;
    public static final BooleanProperty SOUTH = ConnectingBlock.SOUTH;
    public static final BooleanProperty WEST = ConnectingBlock.WEST;
    public static final BooleanProperty UP = ConnectingBlock.UP;
    public static final BooleanProperty DOWN = ConnectingBlock.DOWN;
    public BoosterBlock(AbstractBlock.Settings settings) {
        super(16.0f, settings);
        setDefaultState(getDefaultState()
                .with(NORTH, false)
                .with(SOUTH, false)
                .with(EAST, false)
                .with(WEST, false)
                .with(UP, false)
                .with(DOWN, false)
                .with(FACING, Direction.NORTH));
    }

    @Override
    protected boolean isTransparent(BlockState state) {
        return true;
    }

    protected VoxelShape getCameraCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return VoxelShapes.empty();
    }

    protected float getAmbientOcclusionLightLevel(BlockState state, BlockView world, BlockPos pos) {
        return 1.0F;
    }

    protected boolean isSideInvisible(BlockState state, BlockState stateFrom, Direction direction) {
        return stateFrom.isOf(this) || super.isSideInvisible(state, stateFrom, direction);
    }

    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockView world = ctx.getWorld();
        BlockPos pos = ctx.getBlockPos();
        BlockPos northPos = pos.north();
        BlockPos southPos = pos.south();
        BlockPos eastPos = pos.east();
        BlockPos westPos = pos.west();
        BlockPos upPos = pos.up();
        BlockPos downPos = pos.down();
        BlockState north = world.getBlockState(northPos);
        BlockState south = world.getBlockState(southPos);
        BlockState east = world.getBlockState(eastPos);
        BlockState west = world.getBlockState(westPos);
        BlockState up = world.getBlockState(upPos);
        BlockState down = world.getBlockState(downPos);
        Direction facing = ctx.getPlayerLookDirection();
        return (BlockState)this.getDefaultState()
                .with(FACING, facing)
                .with(NORTH, canConnect(north, facing))
                .with(SOUTH, canConnect(south, facing))
                .with(EAST, canConnect(east, facing))
                .with(WEST, canConnect(west, facing))
                .with(UP, canConnect(up, facing))
                .with(DOWN, canConnect(down, facing));
    }

    private boolean canConnect(BlockState neighbour, Direction facing) {
        if (!(neighbour.getBlock() instanceof BoosterBlock)) {
            return false;
        }
        if (neighbour.get(FACING) != facing) {
            return false;
        }
        return true;
    }

    protected void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity, EntityCollisionHandler handler) {
        if (entity instanceof PlayerEntity) {
            this.boost(state, entity);
        }
    }

    private void boost(BlockState state, Entity entity) {
        Vec3d velocity = entity.getVelocity();
        if (!state.contains(FACING)) return;
        Direction facing = state.get(FACING);

        double x = velocity.x;
        double y = velocity.y;
        double z = velocity.z;
        switch (facing) {
            case NORTH -> z -= 0.5;
            case SOUTH -> z += 0.5;
            case EAST -> x += 0.5;
            case WEST -> x -= 0.5;
            case UP -> y += 0.25;
            case DOWN -> y -= 0.25;
        }
        entity.setVelocity(x,y,z);
    }

    @Override
    protected BlockState getStateForNeighborUpdate(BlockState state, WorldView world, ScheduledTickView tickView, BlockPos pos, Direction direction, BlockPos neighborPos, BlockState neighborState, Random random) {
        return state.with(FACING_PROPERTIES.get(direction), canConnect(neighborState, state.get(FACING)));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING, NORTH, SOUTH, EAST, WEST, UP, DOWN);
    }

    @Override
    protected MapCodec<BoosterBlock> getCodec() {
        return CODEC;
    }
}
