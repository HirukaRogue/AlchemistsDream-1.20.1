package net.hirukarogue.alchemistsdream;

import com.mojang.logging.LogUtils;
import net.hirukarogue.alchemistsdream.block.AlchemistsDreamBlocks;
import net.hirukarogue.alchemistsdream.block.entity.AlchemistisDreamBlockEntities;
import net.hirukarogue.alchemistsdream.fluids.PotionFluidType;
import net.hirukarogue.alchemistsdream.fluids.PotionFluids;
import net.hirukarogue.alchemistsdream.items.Ingredients;
import net.hirukarogue.alchemistsdream.items.PotionBucket;
import net.hirukarogue.alchemistsdream.items.ProcessorBlocks;
import net.hirukarogue.alchemistsdream.items.Products;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(AlchemistsDreamMod.MOD_ID)
public class AlchemistsDreamMod
{
    // Define mod id in a common place for everything to reference
    public static final String MOD_ID = "alchemistsdream";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();
    public AlchemistsDreamMod()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        //Creative mode tab
        AlchemistsDreamCreativeTab.register(modEventBus);

        //Items Registry
        Ingredients.register(modEventBus);
        Products.register(modEventBus);
        PotionBucket.register(modEventBus);

        //Block Items Registry
        ProcessorBlocks.register(modEventBus);

        //Block Registry
        AlchemistsDreamBlocks.register(modEventBus);
        AlchemistisDreamBlockEntities.register(modEventBus);

        //Fluid Registry
        PotionFluidType.register(modEventBus);
        PotionFluids.register(modEventBus);

        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);

        // Register the item to a creative tab
        modEventBus.addListener(this::addCreative);

        // Register our mod's ForgeConfigSpec so that Forge can create and load the config file for us
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {

    }

    // Add the example block item to the building blocks tab
    private void addCreative(BuildCreativeModeTabContentsEvent event)
    {

    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {

    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {

        }
    }
}
