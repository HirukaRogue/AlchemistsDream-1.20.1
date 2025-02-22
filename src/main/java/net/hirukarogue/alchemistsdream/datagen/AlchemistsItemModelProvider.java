package net.hirukarogue.alchemistsdream.datagen;

import net.hirukarogue.alchemistsdream.AlchemistsDreamMod;
import net.hirukarogue.alchemistsdream.block.AlchemistsDreamBlocks;
import net.hirukarogue.alchemistsdream.items.Ingredients;
import net.hirukarogue.alchemistsdream.items.Products;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class AlchemistsItemModelProvider extends ItemModelProvider {
    public AlchemistsItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, AlchemistsDreamMod.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        //ingredients
        simpleItem(Ingredients.SUN_FLOWER_SEEDS);
        simpleItem(Ingredients.WARPED_NETHER_WART);

        //products
        simpleItem(Products.GLUE);

        //blocks
        evenSimplerBlockItem(AlchemistsDreamBlocks.ALCHEMISTS_CAULDRON);
    }

    private ItemModelBuilder simpleItem(RegistryObject<Item> item) {
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/generated")).texture("layer0",
                new ResourceLocation(AlchemistsDreamMod.MOD_ID,"item/" + item.getId().getPath()));
    }

    public void evenSimplerBlockItem(RegistryObject<Block> block) {
        this.withExistingParent(AlchemistsDreamMod.MOD_ID + ":" + ForgeRegistries.BLOCKS.getKey(block.get()).getPath(),
                modLoc("block/" + ForgeRegistries.BLOCKS.getKey(block.get()).getPath()));
    }
}
