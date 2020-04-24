package net.forestbat.eventlib.callbacks;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;

public interface EnchantmentCallback {
    Event<EnchantmentCallback> ENCHANTMENT_CALLBACK_EVENT = EventFactory.createArrayBacked(
            EnchantmentCallback.class,callbacks->((enchantment,itemStack,level) -> {
                for(EnchantmentCallback callback:callbacks)
                    if(callback.accept(enchantment,itemStack,level)!= ActionResult.FAIL)
                        return ActionResult.PASS;
                return ActionResult.PASS;
            })
    );
    ActionResult accept(PlayerEntity player,ItemStack stack, int level);
}
