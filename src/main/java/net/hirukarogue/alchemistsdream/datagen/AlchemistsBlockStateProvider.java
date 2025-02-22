package net.hirukarogue.tutorialmod.datagen;

import net.hirukarogue.alchemistsdream.AlchemistsDreamMod;
import net.hirukarogue.alchemistsdream.block.AlchemistsDreamBlocks;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.*;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

public class AlchemistsBlockStateProvider extends BlockStateProvider {
    public AlchemistsBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, AlchemistsDreamMod.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        simpleBlock(AlchemistsDreamBlocks.ALCHEMISTS_CAULDRON.get(), new ModelFile.UncheckedModelFile("alchemistsdream:block/alchemists_cauldron"));

    }

    private void blockWithItem(RegistryObject<Block> blockRegistryObject) {
        simpleBlockWithItem(blockRegistryObject.get(), cubeAll(blockRegistryObject.get()));
    }
}
