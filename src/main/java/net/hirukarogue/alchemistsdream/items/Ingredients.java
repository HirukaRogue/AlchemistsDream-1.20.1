package net.hirukarogue.alchemistsdream.items;

import net.hirukarogue.alchemistsdream.AlchemistsDreamMod;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class Ingredients {
    public static final DeferredRegister<Item> INGREDIENTS =
            DeferredRegister.create(ForgeRegistries.ITEMS, AlchemistsDreamMod.MOD_ID);

    //new_ingredients
    public static final RegistryObject<Item> SUN_FLOWER_SEEDS =
            INGREDIENTS.register("sunflower_seeds", () -> new Item(new Item.Properties()));

    //processed fungies
    public static final RegistryObject<Item> WARPED_NETHER_WART =
            INGREDIENTS.register("warped_nether_wart", () -> new Item(new Item.Properties()));

    public static void register(IEventBus eventBus) {
        INGREDIENTS.register(eventBus);
    }
}
