package net.hirukarogue.alchemistsdream.block;

import net.hirukarogue.alchemistsdream.AlchemistsDreamMod;
import net.hirukarogue.alchemistsdream.block.entity.AlchemistsCauldron.AlchemistsCauldronBlockEntity;
import net.hirukarogue.alchemistsdream.block.processing.AlchemistsCauldron;
import net.hirukarogue.alchemistsdream.fluids.PotionFluids;
import net.hirukarogue.alchemistsdream.items.ProcessorBlocks;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class AlchemistsDreamBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, AlchemistsDreamMod.MOD_ID);

    //Processor Blocks
    public static final RegistryObject<Block> ALCHEMISTS_CAULDRON =
            registerBlock("alchemists_cauldron", () -> new AlchemistsCauldron(BlockBehaviour.Properties
                    .copy(Blocks.IRON_BLOCK).noOcclusion()));

    public static final List<RegistryObject<LiquidBlock>> POTION_FLUIDS = new ArrayList<>();

    static {
        for (RegistryObject<FlowingFluid> fluid : PotionFluids.SOURCE_POTION_FLUIDS) {
            registerPotionFluid(fluid);
        }
    }

    private static void registerPotionFluid(RegistryObject<FlowingFluid> fluid) {
        String name = String.valueOf(fluid.getKey());
        POTION_FLUIDS.add(registerBlock(name,
                () -> new LiquidBlock(fluid, BlockBehaviour.Properties.copy(Blocks.WATER))));
    }

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
