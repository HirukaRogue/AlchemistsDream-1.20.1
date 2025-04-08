package net.hirukarogue.alchemistsdream.items.custom;

import net.hirukarogue.alchemistsdream.fluids.BasePotionFluidType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluid;

import java.util.function.Supplier;

public class PotionBucketItem extends BucketItem {
    private static int tintColor;

    {
        setTintColor();
    }

    public PotionBucketItem(Supplier<? extends Fluid> supplier, Properties builder) {
        super(supplier, builder);
    }

    private void setTintColor() {
        BasePotionFluidType fluid = (BasePotionFluidType) this.getFluid().getFluidType();
        tintColor = fluid.getTintColor();
    }

    public int getTintColor(ItemStack var1, int var2) {
        return tintColor;
    }
}
