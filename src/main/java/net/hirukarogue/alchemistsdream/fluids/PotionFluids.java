package net.hirukarogue.alchemistsdream.fluids;

import net.hirukarogue.alchemistsdream.AlchemistsDreamMod;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.List;

public class PotionFluids {
    public static final DeferredRegister<Fluid> FLUIDS =
            DeferredRegister.create(ForgeRegistries.FLUIDS, AlchemistsDreamMod.MOD_ID);

    public static final List<RegistryObject<FlowingFluid>> SOURCE_POTION_FLUIDS = new ArrayList<>();
    public static final List<RegistryObject<FlowingFluid>> FLOWING_POTION_FLUIDS = new ArrayList<>();
    public static final List<ForgeFlowingFluid.Properties> POTION_FLUID_PROPERTIES = new ArrayList<>();

    static {
        for (RegistryObject<FluidType> fluidType : PotionFluidType.POTION_FLUID_TYPE) {
            registerPotionFluid(fluidType);
        }
    }

    private static void registerPotionFluid(RegistryObject<FluidType> fluidType) {
        String fluidName = String.valueOf(fluidType.getKey());
        fluidName = fluidName.replace("_type", "");

        RegistryObject<FlowingFluid> source = FLUIDS.register(fluidName,
                () -> new ForgeFlowingFluid.Source(POTION_FLUID_PROPERTIES.get(SOURCE_POTION_FLUIDS.size())));
        RegistryObject<FlowingFluid> flowing = FLUIDS.register("flowing_" + fluidName,
                () -> new ForgeFlowingFluid.Flowing(POTION_FLUID_PROPERTIES.get(FLOWING_POTION_FLUIDS.size())));

        SOURCE_POTION_FLUIDS.add(source);
        FLOWING_POTION_FLUIDS.add(flowing);

        POTION_FLUID_PROPERTIES.add(new ForgeFlowingFluid.Properties(
                fluidType, source, flowing)
                .slopeFindDistance(2).levelDecreasePerBlock(2));
    }

    public static void register(IEventBus eventBus) {
        FLUIDS.register(eventBus);
    }
}