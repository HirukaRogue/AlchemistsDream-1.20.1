package net.hirukarogue.alchemistsdream.block;

import net.hirukarogue.alchemistsdream.AlchemistsDreamMod;
import net.hirukarogue.alchemistsdream.block.entity.AlchemistsCauldron.AlchemistsCauldronBlockEntity;
import net.hirukarogue.alchemistsdream.block.processing.AlchemistsCauldron;
import net.hirukarogue.alchemistsdream.items.ProcessorBlocks;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class AlchemistsDreamBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, AlchemistsDreamMod.MOD_ID);

    //Processor Blocks
    public static final RegistryObject<Block> ALCHEMISTS_CAULDRON =
            registerBlock("alchemists_cauldron", () -> new AlchemistsCauldron(BlockBehaviour.Properties
                    .copy(Blocks.IRON_BLOCK).noOcclusion()));

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }
    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block) {
        return ProcessorBlocks.BLOCKS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }
    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
