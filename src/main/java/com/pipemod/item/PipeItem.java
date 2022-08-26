package com.pipemod.item;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemGroup;

public class PipeItem extends BlockItem {
    public static final Properties props = new Properties().tab(ItemGroup.TAB_DECORATIONS).stacksTo(1);

    public PipeItem(Block block) {
        this(block, props);
    }

    public PipeItem(Block block, Properties props) {
        super(block, props);
    }
}
