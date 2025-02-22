package net.hirukarogue.alchemistsdream.items;

import net.hirukarogue.alchemistsdream.AlchemistsDreamMod;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ProcessorBlocks {
    public static final DeferredRegister<Item> BLOCKS =
            DeferredRegister.create(ForgeRegistries.ITEMS, AlchemistsDreamMod.MOD_ID);

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
