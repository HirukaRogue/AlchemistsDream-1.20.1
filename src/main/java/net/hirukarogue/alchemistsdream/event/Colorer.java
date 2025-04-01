package net.hirukarogue.alchemistsdream.event;

import net.hirukarogue.alchemistsdream.AlchemistsDreamMod;
import net.hirukarogue.alchemistsdream.items.custom.PotionBucketItem;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = AlchemistsDreamMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class Colorer {
    @SubscribeEvent
    public static void registerBucketColors(RegisterColorHandlersEvent.Block colorEvent) {
        colorEvent.register(PotionBucketItem::getTintColor, );
    }
}
