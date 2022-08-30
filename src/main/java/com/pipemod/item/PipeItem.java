package com.pipemod.item;

import com.pipemod.block.PipeBlock;
import com.pipemod.entity.TravelingItemEntity;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.projectile.EyeOfEnderEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.registries.ForgeRegistries;

public class PipeItem extends BlockItem {
    public static final Properties props = new Properties().tab(ItemGroup.TAB_DECORATIONS).stacksTo(1);
    public static final RegistryObject<Block> PIPE_BLOCK = RegistryObject.of(new ResourceLocation("pipemod:pipe"), ForgeRegistries.BLOCKS);


    public PipeItem(Block block) {
        this(block, props);
    }

    public PipeItem(Block block, Properties props) {
        super(block, props);
    }

    @Override
    public ActionResultType place(BlockItemUseContext itemUseContext) {
        ActionResultType result = super.place(itemUseContext);
        Entity e = new TravelingItemEntity(itemUseContext.getLevel(), itemUseContext.getClickedPos());
        if(itemUseContext.getLevel() instanceof ServerWorld) {
            itemUseContext.getLevel().addFreshEntity(e);
        }

        return result;
    }
}
