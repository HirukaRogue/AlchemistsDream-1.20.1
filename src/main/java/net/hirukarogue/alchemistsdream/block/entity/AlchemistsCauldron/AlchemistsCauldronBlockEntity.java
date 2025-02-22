package net.hirukarogue.alchemistsdream.block.entity.AlchemistsCauldron;

import net.hirukarogue.alchemistsdream.block.entity.AlchemistisDreamBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.Containers;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class AlchemistsCauldronBlockEntity extends BlockEntity {
    private final ItemStackHandler itemHandler = new ItemStackHandler(3){
        @Override
        protected void onContentsChanged(int slot) {
            super.onContentsChanged(slot);
            setChanged();
            if (!level.isClientSide()) {
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
            }
        }

        @Override
        public int getSlotLimit(int slot) {
            return 1;
        }
    };

    private LazyOptional<IItemHandler> lazyItemHander = LazyOptional.empty();
    private LazyOptional<IFluidHandler> lazyFluidHander = LazyOptional.empty();

    private final FluidTank WATER_AND_POTION_CONTAINER = new FluidTank(1000) {
        @Override
        protected void onContentsChanged() {
            super.onContentsChanged();
        }

        @Override
        public boolean isFluidValid(FluidStack stack) {
            return stack.getFluid() == Fluids.WATER;
        }
    };

    public void setFluid(FluidStack stack) {
        this.WATER_AND_POTION_CONTAINER.setFluid(stack);
    }

    public FluidStack getFluidStack() {
        return this.WATER_AND_POTION_CONTAINER.getFluid();
    }

    public AlchemistsCauldronBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(AlchemistisDreamBlockEntities.ALCHEMISTS_CAULDRON_BE.get(), pPos, pBlockState);
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap) {
        if (cap == ForgeCapabilities.ITEM_HANDLER) {
            return lazyItemHander.cast();
        }

        if (cap == ForgeCapabilities.FLUID_HANDLER) {
            return lazyFluidHander.cast();
        }

        return super.getCapability(cap);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHander = LazyOptional.of(() -> itemHandler);
        lazyFluidHander = LazyOptional.of(() -> WATER_AND_POTION_CONTAINER);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemHander.invalidate();
        lazyFluidHander.invalidate();
    }

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }

        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        pTag.put("inventory", itemHandler.serializeNBT());
        pTag = WATER_AND_POTION_CONTAINER.writeToNBT(pTag);

        super.saveAdditional(pTag);
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        itemHandler.deserializeNBT(pTag.getCompound("inventory"));
        WATER_AND_POTION_CONTAINER.readFromNBT(pTag);
    }

    public void tick(Level pLevel, BlockPos pPos, BlockState pState) {

    }

    public void mix() {
        if (hasRecipe()) {

        }
    }

    public void fill(int amout) {
        this.WATER_AND_POTION_CONTAINER.fill(new FluidStack(Fluids.WATER, amout), IFluidHandler.FluidAction.EXECUTE);
    }

    public void drain(int amout) {
        this.WATER_AND_POTION_CONTAINER.drain(amout, IFluidHandler.FluidAction.EXECUTE);
    }

    public void putItem(ItemStack item, Player pPlayer) {
        if (itemHandler.getStackInSlot(0).isEmpty()) {
            ItemStack addedItem = new ItemStack(item.getItem());
            addedItem.setCount(1);
            itemHandler.setStackInSlot(0, addedItem);
            item.setCount(item.getCount()-1);
            pPlayer.setItemSlot(EquipmentSlot.MAINHAND, item);
            return;
        }
        if (itemHandler.getStackInSlot(1).isEmpty()) {
            ItemStack addedItem = new ItemStack(item.getItem());
            addedItem.setCount(1);
            itemHandler.setStackInSlot(1, addedItem);
            item.setCount(item.getCount()-1);
            pPlayer.setItemSlot(EquipmentSlot.MAINHAND, item);
            return;
        }
        if (itemHandler.getStackInSlot(2).isEmpty()) {
            ItemStack addedItem = new ItemStack(item.getItem());
            addedItem.setCount(1);
            itemHandler.setStackInSlot(2, addedItem);
            item.setCount(item.getCount()-1);
            pPlayer.setItemSlot(EquipmentSlot.MAINHAND, item);
        }
    }

    public ItemStack retrieveItem() {
        if (!itemHandler.getStackInSlot(2).isEmpty()) {
            ItemStack stack = itemHandler.getStackInSlot(2);
            itemHandler.extractItem(2, 1, false);
            return stack;
        }
        if (!itemHandler.getStackInSlot(1).isEmpty()) {
            ItemStack stack = itemHandler.getStackInSlot(1);
            itemHandler.extractItem(1, 1, false);
            return stack;
        }
        if (!itemHandler.getStackInSlot(0).isEmpty()) {
            ItemStack stack = itemHandler.getStackInSlot(0);
            itemHandler.extractItem(0, 1, false);
            return stack;
        }

        return null;
    }

    public boolean containsLiquid() {
        return !this.WATER_AND_POTION_CONTAINER.isEmpty();
    }

    public boolean isFull() {
        return this.WATER_AND_POTION_CONTAINER.getFluidAmount() >= this.WATER_AND_POTION_CONTAINER.getCapacity();
    }

    private boolean hasRecipe() {
        return false;
    }

    private Optional<?> getCurrentRecipe() {
        return null;
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag() {
        return saveWithoutMetadata();
    }

}
