package com.pipemod.block;

import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.entity.Entity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;

public class Pipe extends SixWayBlock {
    private static final AbstractBlock.Properties props = AbstractBlock.Properties.of(Material.DIRT, MaterialColor.DIRT).strength(0.5F).sound(SoundType.GRAVEL);

    public Pipe() {
        super(0.25F, props);
        this.registerDefaultState(this.stateDefinition.any().setValue(NORTH, Boolean.valueOf(false)).setValue(EAST, Boolean.valueOf(false)).setValue(SOUTH, Boolean.valueOf(false)).setValue(WEST, Boolean.valueOf(false)).setValue(UP, Boolean.valueOf(false)).setValue(DOWN, Boolean.valueOf(false)));
    }

    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> stateBuilder) {
        stateBuilder.add(NORTH, EAST, SOUTH, WEST, UP, DOWN);
    }

    public BlockState getStateForPlacement(BlockItemUseContext itemUseContext) {
        return this.getStateForPlacement(itemUseContext.getLevel(), itemUseContext.getClickedPos());
    }

    public BlockState getStateForPlacement(IBlockReader blockReader, BlockPos blockPos) {
        BlockState blockState = this.defaultBlockState();

        for (Direction direction : UPDATE_SHAPE_ORDER) {
                Block block = blockReader.getBlockState(blockPos.relative(direction)).getBlock();
                TileEntity entity = blockReader.getBlockEntity(blockPos.relative(direction));
                blockState = blockState.setValue(PROPERTY_BY_DIRECTION.get(direction), block == this || (entity instanceof IInventory));
        }

        return blockState;
    }

    @Override
    @SuppressWarnings("deprecation")
    public BlockState updateShape(BlockState thisBlockState, Direction direction, BlockState otherBlockState, IWorld world, BlockPos thisBlockPos, BlockPos otherBlockPos) {
        Block otherBlock = otherBlockState.getBlock();
        TileEntity otherBlockEntity = world.getBlockEntity(otherBlockPos);
        boolean flag = otherBlock == this || (otherBlockEntity instanceof IInventory);
        return thisBlockState.setValue(PROPERTY_BY_DIRECTION.get(direction), Boolean.valueOf(flag));
    }
}
