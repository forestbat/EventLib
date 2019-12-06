package net.forestbat.eventlib;

import net.fabricmc.api.ModInitializer;
import net.forestbat.eventlib.callbacks.*;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResult;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.loot.context.LootContext;
import net.minecraft.world.loot.context.LootContextTypes;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EventLib implements ModInitializer {
	public static Logger LOGGER= LogManager.getLogger();
	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
		//ClientTickCallback.EVENT.register((client)->{client.keyboard.setClipboard(null);});
		LOGGER.info("EventLib is loaded!");
		PlayerSleepCallback.PLAYER_SLEEP_CALLBACK_EVENT.register((player,bedPos,time)->{
			if(player.inventory.isInvEmpty()) {
				Entity entity=new SheepEntity(EntityType.SHEEP,player.world);
				entity.setPosition(player.getBlockPos().getX(),player.getBlockPos().getY()+1,player.getBlockPos().getZ());
				player.world.spawnEntity(entity);
			}
			return ActionResult.PASS;
		});
		EnchantmentCallback.ENCHANTMENT_CALLBACK_EVENT.register(((enchantment, itemStack) -> {
			if(enchantment.getMaximumLevel()==3)
				itemStack.setDamage(itemStack.getMaxDamage()/2);
			return ActionResult.PASS;
		}));
		ModLoadCallback.MOD_LOAD_CALLBACK_EVENT.register((modid)->{
			if(modid.contains("fabric"))
				LOGGER.warn("Fabric mod loaded!");
			return ActionResult.PASS;
		});
		PlayerLevelupCallback.PLAYER_LEVELUP_CALLBACK_EVENT.register((player,level)->{
			if(level>30)
				player.addPotionEffect(new StatusEffectInstance(StatusEffects.BLINDNESS,30,1));
			return ActionResult.PASS;
		});
		CommandStartCallback.COMMAND_START_CALLBACK_EVENT.register((source,command)->{
			if(source.getEntityOrThrow()!=null) {
				Entity entity=source.getEntity();
				if (command.contains("give") && entity instanceof PlayerEntity)
					entity.kill();
				return ActionResult.PASS;
			}
			return ActionResult.PASS;
		});
		/*BlockEntityConstructCallback.BLOCK_ENTITY_CONSTRUCT_CALLBACK_EVENT.register((type,pos,world)->{
			if(type.equals(BlockEntityType.FURNACE) && world!=null)
				world.getLevelProperties().setRaining(true);
			return ActionResult.PASS;
		});*/
		FishingCallback.FISHING_CALLBACK_EVENT.register(((world, player) -> {
			if(world.getLevelProperties().isRaining())
				player.dropItem(new ItemStack(Items.DIAMOND),false);
			return new TypedActionResult<>(ActionResult.PASS, LootContextTypes.CHEST);
		}));
	}
}
