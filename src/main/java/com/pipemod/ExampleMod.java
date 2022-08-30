package com.pipemod;

import com.pipemod.block.PipeBlock;
import com.pipemod.entity.TravelingItemEntity;
import com.pipemod.item.PipeItem;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.SpriteRenderer;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.stream.Collectors;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("pipemod")
public class ExampleMod {

  // Directly reference a log4j logger.
  private static final Logger LOGGER = LogManager.getLogger();
  private static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(
      ForgeRegistries.ENTITIES, "pipemod");

  public static final RegistryObject<EntityType<TravelingItemEntity>> TRAVELING_ITEM_ENTITY_TYPE = ENTITIES.register(
      "traveling_item_entity",
      () -> EntityType.Builder.<TravelingItemEntity>of(
              TravelingItemEntity::new, EntityClassification.MISC)
          .sized(0.25F, 0.25F).clientTrackingRange(4).updateInterval(4)
          .build(new ResourceLocation("pipemod", "traveling_pipe_item").toString()));


  public ExampleMod() {
    // Register the setup method for modloading
    FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
    // Register the enqueueIMC method for modloading
    FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);
    // Register the processIMC method for modloading
    FMLJavaModLoadingContext.get().getModEventBus().addListener(this::processIMC);
    // Register the doClientStuff method for modloading
    FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);

    // Register ourselves for server and other game events we are interested in
    MinecraftForge.EVENT_BUS.register(this);
    ENTITIES.register(FMLJavaModLoadingContext.get().getModEventBus());
  }

  private void setup(final FMLCommonSetupEvent event) {
    // some preinit code
    LOGGER.info("HELLO FROM PREINIT");
    LOGGER.info("DIRT BLOCK >> {}", Blocks.DIRT.getRegistryName());
  }

  private void doClientStuff(final FMLClientSetupEvent event) {
    // do something that can only be done on the client
    RenderingRegistry.registerEntityRenderingHandler(TRAVELING_ITEM_ENTITY_TYPE.get(), m -> new TravelingItemRenderer<>(
        m, Minecraft.getInstance().getItemRenderer()));
    LOGGER.info("Got game settings {}", event.getMinecraftSupplier().get().options);
  }

  private void enqueueIMC(final InterModEnqueueEvent event) {
    // some example code to dispatch IMC to another mod
    InterModComms.sendTo("examplemod", "helloworld", () -> {
      LOGGER.info("Hello world from the MDK");
      return "Hello world";
    });
  }

  private void processIMC(final InterModProcessEvent event) {
    // some example code to receive and process InterModComms from other mods
    LOGGER.info("Got IMC {}", event.getIMCStream().
        map(m -> m.getMessageSupplier().get()).
        collect(Collectors.toList()));
  }

  // You can use SubscribeEvent and let the Event Bus discover methods to call
  @SubscribeEvent
  public void onServerStarting(FMLServerStartingEvent event) {
    // do something when the server starts
    LOGGER.info("HELLO from server starting");
  }


  // You can use EventBusSubscriber to automatically subscribe events on the contained class (this is subscribing to the MOD
  // Event bus for receiving Registry Events)
  @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
  public static class RegistryEvents {

    static PipeBlock pipe_block = new PipeBlock();


    @SubscribeEvent
    public static void onBlockRegister(final RegistryEvent.Register<Block> event) {
      event.getRegistry().register(pipe_block.setRegistryName("pipe"));
      LOGGER.info("registered pipe.json");
    }

    @SubscribeEvent
    public static void onItemRegister(final RegistryEvent.Register<Item> event) {
      event.getRegistry().register(new PipeItem(pipe_block).setRegistryName("pipe"));
      LOGGER.info("registered pipe.json item");
    }
  }
}
