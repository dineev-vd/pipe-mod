package com.pipemod.block;

import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.StateContainer;
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

    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> p_206840_1_) {
        p_206840_1_.add(NORTH, EAST, SOUTH, WEST, UP, DOWN);
    }

    public BlockState getStateForPlacement(BlockItemUseContext p_196258_1_) {
        return this.getStateForPlacement(p_196258_1_.getLevel(), p_196258_1_.getClickedPos());
    }

    public BlockState getStateForPlacement(IBlockReader p_196497_1_, BlockPos p_196497_2_) {
        Block block = p_196497_1_.getBlockState(p_196497_2_.below()).getBlock();
        Block block1 = p_196497_1_.getBlockState(p_196497_2_.above()).getBlock();
        Block block2 = p_196497_1_.getBlockState(p_196497_2_.north()).getBlock();
        Block block3 = p_196497_1_.getBlockState(p_196497_2_.east()).getBlock();
        Block block4 = p_196497_1_.getBlockState(p_196497_2_.south()).getBlock();
        Block block5 = p_196497_1_.getBlockState(p_196497_2_.west()).getBlock();
        return this.defaultBlockState().setValue(DOWN, Boolean.valueOf(block == this)).setValue(UP, Boolean.valueOf(block1 == this)).setValue(NORTH, Boolean.valueOf(block2 == this)).setValue(EAST, Boolean.valueOf(block3 == this)).setValue(SOUTH, Boolean.valueOf(block4 == this)).setValue(WEST, Boolean.valueOf(block5 == this));
    }

    public BlockState updateShape(BlockState p_196271_1_, Direction p_196271_2_, BlockState p_196271_3_, IWorld p_196271_4_, BlockPos p_196271_5_, BlockPos p_196271_6_) {
        boolean flag = p_196271_3_.getBlock() == this;
        return p_196271_1_.setValue(PROPERTY_BY_DIRECTION.get(p_196271_2_), Boolean.valueOf(flag));

    }
}
