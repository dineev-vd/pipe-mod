package com.pipemod.entity;

import com.pipemod.ExampleMod;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.IRendersAsItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.play.server.SSpawnObjectPacket;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.registries.ForgeRegistries;


@OnlyIn(
    value = Dist.CLIENT,
    _interface = IRendersAsItem.class
)
public class TravelingItemEntity extends Entity implements IRendersAsItem {


    public TravelingItemEntity(EntityType<? extends TravelingItemEntity> p_i48580_1_, World p_i48580_2_) {
        super(p_i48580_1_, p_i48580_2_);
    }

    public TravelingItemEntity(World p_i48580_2_, BlockPos blockPos) {
        this(ExampleMod.TRAVELING_ITEM_ENTITY_TYPE.get(), p_i48580_2_);
        this.setPos(blockPos.getX(), blockPos.getY(), blockPos.getZ());
    }

    @Override
    protected void defineSynchedData() {
        this.getEntityData();
    }

    @Override
    protected void readAdditionalSaveData(CompoundNBT p_70037_1_) {
    }

    @Override
    protected void addAdditionalSaveData(CompoundNBT p_213281_1_) {
    }

    @Override
    public IPacket<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    public ItemStack getItem() {
        return new ItemStack(Items.STONE);
    }

    public float getBrightness() {
        return 1.0F;
    }

    public boolean isAttackable() {
        return false;
    }
}
