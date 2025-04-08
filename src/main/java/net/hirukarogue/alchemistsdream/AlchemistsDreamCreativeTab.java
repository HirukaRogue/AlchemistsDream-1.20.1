package net.hirukarogue.alchemistsdream;

import net.hirukarogue.alchemistsdream.block.AlchemistsDreamBlocks;
import net.hirukarogue.alchemistsdream.items.Ingredients;
import net.hirukarogue.alchemistsdream.items.PotionBucket;
import net.hirukarogue.alchemistsdream.items.Products;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class AlchemistsDreamCreativeTab {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TAB =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, AlchemistsDreamMod.MOD_ID);

    public static final RegistryObject<CreativeModeTab> ALCHEMISTS_DREAM_TAB =
            CREATIVE_MODE_TAB.register("alchemists_dream_tab", () -> CreativeModeTab.builder()
                    .icon(() -> new ItemStack(Ingredients.WARPED_NETHER_WART.get()))
                    .title(Component.translatable("creativetab.alchemistis_dream_tab"))
                    .displayItems((pParameters, pOutput) -> {
                        //ingredients
                        pOutput.accept(Ingredients.SUN_FLOWER_SEEDS.get());
                        pOutput.accept(Ingredients.WARPED_NETHER_WART.get());

                        //products
                        pOutput.accept(Products.GLUE.get());

                        //custom blocks
                        pOutput.accept(AlchemistsDreamBlocks.ALCHEMISTS_CAULDRON.get());

                        for (RegistryObject<Item> bucket : PotionBucket.POTION_BUCKETS) {
                            pOutput.accept(bucket.get());
                        }
                    }).build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TAB.register(eventBus);
    }
}
