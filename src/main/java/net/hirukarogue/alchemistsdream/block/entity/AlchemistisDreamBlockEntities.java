package net.hirukarogue.alchemistsdream.block.entity;

import net.hirukarogue.alchemistsdream.AlchemistsDreamMod;
import net.hirukarogue.alchemistsdream.block.AlchemistsDreamBlocks;
import net.hirukarogue.alchemistsdream.block.entity.AlchemistsCauldron.AlchemistsCauldronBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class AlchemistisDreamBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, AlchemistsDreamMod.MOD_ID);

    public static final RegistryObject<BlockEntityType<AlchemistsCauldronBlockEntity>> ALCHEMISTS_CAULDRON_BE =
            BLOCK_ENTITIES.register("alchemists_cauldron_be", () ->
                    BlockEntityType.Builder.of(AlchemistsCauldronBlockEntity::new,
                            AlchemistsDreamBlocks.ALCHEMISTS_CAULDRON.get()).build(null));

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}
