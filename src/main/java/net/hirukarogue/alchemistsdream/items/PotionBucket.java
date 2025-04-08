package net.hirukarogue.alchemistsdream.items;

import net.hirukarogue.alchemistsdream.AlchemistsDreamMod;
import net.hirukarogue.alchemistsdream.fluids.BasePotionFluidType;
import net.hirukarogue.alchemistsdream.fluids.PotionFluids;
import net.hirukarogue.alchemistsdream.items.custom.PotionBucketItem;
import net.hirukarogue.alchemistsdream.miscellaneous.IDTrimmer;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
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

    static {
        for (RegistryObject<FlowingFluid> source : PotionFluids.SOURCE_POTION_FLUIDS) {
            registerBucket(source);
        }
    }

    private static void registerBucket(RegistryObject<FlowingFluid> fluid) {
        String name = IDTrimmer.trim(String.valueOf(fluid.getId())) + "_bucket";
        POTION_BUCKETS.add(BUCKETS.register(name, () -> new PotionBucketItem(fluid, new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1))));
    }

    public static void register(IEventBus eventBus) {
        BUCKETS.register(eventBus);
    }
}
