package com.pipemod.block;

import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;

public class Pipe extends FenceBlock {
    private static final AbstractBlock.Properties props = AbstractBlock.Properties.of(Material.DIRT, MaterialColor.DIRT).strength(0.5F).sound(SoundType.GRAVEL);

    public Pipe() {
        super(props);
    }
}
