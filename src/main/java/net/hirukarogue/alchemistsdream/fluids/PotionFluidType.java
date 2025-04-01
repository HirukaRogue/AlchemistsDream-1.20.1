package net.hirukarogue.alchemistsdream.fluids;

import net.hirukarogue.alchemistsdream.AlchemistsDreamMod;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraftforge.common.SoundAction;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryObject;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;

public class PotionFluidType {
    public static final ResourceLocation WATER_STILL_RL = new ResourceLocation("block/water_still");
    public static final ResourceLocation WATER_FLOW_RL = new ResourceLocation("block/water_flow");
    public static final ResourceLocation WATER_OVERLAY_RL = new ResourceLocation("block/water_overlay");

    public static final DeferredRegister<FluidType> FLUID_TYPES = DeferredRegister.create(ForgeRegistries.Keys.FLUID_TYPES, AlchemistsDreamMod.MOD_ID);

    public static final List<RegistryObject<FluidType>> POTION_FLUID_TYPE = new ArrayList<>();

    static {
        String type_name = "potion_fluid_type";
        register("potion_fluid_type");
        IForgeRegistry<MobEffect> effects = ForgeRegistries.MOB_EFFECTS;
        for (MobEffect effect : effects) {
            register(effect.getDescriptionId() + "_" + type_name);
        }
    }

    private static void register(String name) {
        String base_name = name.replace("type", "base");
        POTION_FLUID_TYPE.add(FLUID_TYPES.register(name, () -> new BasePotionFluidType.Builder(base_name).build()));
    }

    public static void register(IEventBus eventBus) {
        FLUID_TYPES.register(eventBus);
    }
}
