package net.hirukarogue.alchemistsdream.event;

import net.hirukarogue.alchemistsdream.AlchemistsDreamMod;
import net.hirukarogue.alchemistsdream.items.PotionBucket;
import net.hirukarogue.alchemistsdream.items.custom.PotionBucketItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid = AlchemistsDreamMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class Colorer {
    @SubscribeEvent
    public static void registerBucketColors(RegisterColorHandlersEvent.Item colorEvent) {
        for (RegistryObject<Item> bucket : PotionBucket.POTION_BUCKETS) {
            PotionBucketItem potion_bucket = (PotionBucketItem) bucket.get();
            colorEvent.register(potion_bucket::getTintColor, bucket.get());
        }
    }
}
