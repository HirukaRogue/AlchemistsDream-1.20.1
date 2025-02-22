package net.hirukarogue.alchemistsdream.block.processing;

import net.hirukarogue.alchemistsdream.AlchemistsDreamMod;
import net.hirukarogue.alchemistsdream.block.entity.AlchemistisDreamBlockEntities;
import net.hirukarogue.alchemistsdream.block.entity.AlchemistsCauldron.AlchemistsCauldronBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.Nullable;

public class AlchemistsCauldron extends BaseEntityBlock {
    public static final VoxelShape SHAPE = Block.box(0,0,0,16,12,16);

    public AlchemistsCauldron(BlockBehaviour.Properties pProperties) {
        super(pProperties);
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pMovedByPiston) {
        if (pState.getBlock() != pNewState.getBlock()) {
            BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
            if (blockEntity instanceof AlchemistsCauldronBlockEntity) {
                ((AlchemistsCauldronBlockEntity) blockEntity).drops();
            }
        }

        super.onRemove(pState, pLevel, pPos, pNewState, pMovedByPiston);
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (!pLevel.isClientSide()) {
            BlockEntity entity = pLevel.getBlockEntity(pPos);
            if (entity instanceof AlchemistsCauldronBlockEntity) {
                AlchemistsCauldronBlockEntity cauldron = (AlchemistsCauldronBlockEntity) entity;
                ItemStack mainHandItem = pPlayer.getMainHandItem();
                ItemStack offHandItem = pPlayer.getOffhandItem();
                boolean interactionSucess = false;
                if (mainHandItem.isEmpty()) {
                    if (!pLevel.isClientSide()) {
                        ItemStack stack = cauldron.retrieveItem();
                        if (stack != null) {
                            pPlayer.getInventory().add(stack);
                            playSoundClientSide(pLevel, pPos, SoundEvents.ITEM_PICKUP, SoundSource.BLOCKS, 1f, 1f);
                        }
                    }
                } else if (mainHandItem.is(Items.WATER_BUCKET)) {
                    if (cauldron.getFluidStack().isEmpty()) {
                        cauldron.fill(1000);
                        pPlayer.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.BUCKET));
                        playSoundClientSide(pLevel, pPos, SoundEvents.BUCKET_EMPTY, SoundSource.BLOCKS, 1f, 1f);
                    }
                } else if (offHandItem.is(Items.WATER_BUCKET)) {
                    if (cauldron.getFluidStack().isEmpty()) {
                        cauldron.fill(1000);
                        pPlayer.setItemSlot(EquipmentSlot.OFFHAND, new ItemStack(Items.BUCKET));
                        playSoundClientSide(pLevel, pPos, SoundEvents.BUCKET_EMPTY, SoundSource.BLOCKS, 1f, 1f);
                    }
                } else if (mainHandItem.is(Items.BUCKET)) {
                    if (cauldron.getFluidStack().containsFluid(new FluidStack(Fluids.WATER, 1000))) {
                        cauldron.drain(1000);
                        pPlayer.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.WATER_BUCKET));
                        playSoundClientSide(pLevel, pPos, SoundEvents.BUCKET_FILL, SoundSource.BLOCKS, 1f, 1f);
                    }
                } else if (offHandItem.is(Items.BUCKET)) {
                    if (cauldron.getFluidStack().containsFluid(new FluidStack(Fluids.WATER, 1000))) {
                        cauldron.drain(1000);
                        pPlayer.setItemSlot(EquipmentSlot.OFFHAND, new ItemStack(Items.WATER_BUCKET));
                        playSoundClientSide(pLevel, pPos, SoundEvents.BUCKET_FILL, SoundSource.BLOCKS, 1f, 1f);
                    }
                } else if (isValidItem(pPlayer)) {
                    cauldron.putItem(mainHandItem, pPlayer);
                    playSoundClientSide(pLevel, pPos, SoundEvents.ITEM_PICKUP, SoundSource.BLOCKS, 1f, 1f);
                } else if (mainHandItem.is(Items.GLASS_BOTTLE)) {
                    if (cauldron.containsLiquid()) {
                        ItemStack bottles = mainHandItem;
                        bottles.setCount(bottles.getCount() - 1);
                        ItemStack water_bottle = new ItemStack(Items.POTION);
                        water_bottle.getOrCreateTag().putString("Potion", "minecraft:water");
                        pPlayer.getInventory().add(water_bottle);
                        pPlayer.setItemSlot(EquipmentSlot.MAINHAND, bottles);
                        cauldron.drain(250);
                        playSoundClientSide(pLevel, pPos, SoundEvents.ITEM_PICKUP, SoundSource.BLOCKS, 1f, 1f);
                    }
                } else if (mainHandItem.is(Items.POTION)) {
                    ItemStack waterBottle = mainHandItem;
                    if (waterBottle.getOrCreateTag().getString("Potion").contains("minecraft:water") && !cauldron.isFull()) {
                        waterBottle.setCount(waterBottle.getCount() - 1);
                        pPlayer.setItemSlot(EquipmentSlot.MAINHAND, waterBottle);
                        pPlayer.getInventory().add(new ItemStack(Items.GLASS_BOTTLE));
                        cauldron.fill(250);
                        playSoundClientSide(pLevel, pPos, SoundEvents.ITEM_PICKUP, SoundSource.BLOCKS, 1f, 1f);
                    }
                }
            } else {
                throw new IllegalStateException("Our Container provider is missing!");
            }
        }
        return InteractionResult.sidedSuccess(pLevel.isClientSide());
    }

    private void playSoundClientSide(Level level, BlockPos pos, SoundEvent sound, SoundSource source, float volume, float pitch) {
        if (level.isClientSide()) {
            level.playSound(null, pos, sound, source, volume, pitch);
        }
    }

    private boolean isValidItem(Player pPlayer) {
        return !(pPlayer.getMainHandItem().is(Items.BUCKET) || pPlayer.getMainHandItem().is(Items.LAVA_BUCKET) || pPlayer.getMainHandItem().is(Items.WATER_BUCKET) || pPlayer.getMainHandItem().is(Items.POTION) || pPlayer.getMainHandItem().is(Items.GLASS_BOTTLE));
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new AlchemistsCauldronBlockEntity(blockPos, blockState);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        if (pLevel.isClientSide()) {
            return null;
        }

        return createTickerHelper(pBlockEntityType, AlchemistisDreamBlockEntities.ALCHEMISTS_CAULDRON_BE.get(), (pLevel1, pPos, pState1, pBlockEntity) -> pBlockEntity.tick(pLevel1, pPos, pState1));
    }
}
