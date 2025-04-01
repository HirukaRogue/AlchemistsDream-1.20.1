package net.hirukarogue.alchemistsdream.items;

import net.hirukarogue.alchemistsdream.AlchemistsDreamMod;
import net.hirukarogue.alchemistsdream.items.custom.PotionBucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.List;

public class PotionBucket {
    public static final DeferredRegister<Item> BUCKETS =
            DeferredRegister.create(ForgeRegistries.ITEMS, AlchemistsDreamMod.MOD_ID);

    public static final List<RegistryObject<Item>> POTION_BUCKETS = new ArrayList<>();


    private static void registerBucket(RegistryObject<FlowingFluid> fluid) {
        String name = String.valueOf(fluid.getKey());
        POTION_BUCKETS.add(BUCKETS.register(name, () -> new PotionBucketItem(fluid.get)));
    }

    public static void register(IEventBus eventBus) {
        BUCKETS.register(eventBus);
    }
}
