package net.hirukarogue.alchemistsdream.items.custom;

import net.minecraft.world.item.BucketItem;
import net.minecraft.world.level.material.Fluid;

import java.util.function.Supplier;

public class PotionBucketItem extends BucketItem {
    private static int tintColor;

    public PotionBucketItem(int color, Supplier<? extends Fluid> supplier, Properties builder) {
        super(supplier, builder);
        tintColor = color;
    }

    public int getTintColor() {
        return tintColor;
    }
}
