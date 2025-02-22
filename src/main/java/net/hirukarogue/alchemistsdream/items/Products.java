package net.hirukarogue.alchemistsdream.items;

import net.hirukarogue.alchemistsdream.AlchemistsDreamMod;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class Products {
    public static final DeferredRegister<Item> PRODUCTS =
            DeferredRegister.create(ForgeRegistries.ITEMS, AlchemistsDreamMod.MOD_ID);

    public static final RegistryObject<Item> GLUE =
            PRODUCTS.register("glue", () -> new Item(new Item.Properties()));

    public static void register(IEventBus eventBus) {
        PRODUCTS.register(eventBus);
    }
}
