package net.forestbat.eventlib;

import net.fabricmc.api.ModInitializer;
import net.forestbat.eventlib.callbacks.*;
import net.minecraft.block.entity.FurnaceBlockEntity;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.*;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.entity.mob.EndermanEntity;
import net.minecraft.entity.mob.WitherSkeletonEntity;
import net.minecraft.entity.passive.PigEntity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.LiteralText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.*;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.explosion.Explosion;
import net.minecraft.world.loot.context.LootContextTypes;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EventLib implements ModInitializer {
	public static Logger LOGGER= LogManager.getLogger();
	@Override
	public void onInitialize() {
		LOGGER.info("EventLib is loaded!");
		/*ServerTickCallback.EVENT.register(minecraftServer -> {
			for(World world:minecraftServer.getWorlds())
				if(world.isRaining())
					minecraftServer.sendMessage(new LiteralText("xia yu le"));
		});*/
		//todo proved
		ClientStartCallback.EVENT.register(client -> LOGGER.warn("Client Started!"));
		//todo proved
		ClientStopCallback.EVENT.register(client -> LOGGER.warn("Client Stopped!"));
		PlayerSleepCallback.PLAYER_SLEEP_CALLBACK_EVENT.register((player,bedPos,time)->{
			if(player.inventory.isInvEmpty()) {
				Entity entity=new SheepEntity(EntityType.SHEEP,player.world);
				entity.setPosition(player.getBlockPos().getX(),player.getBlockPos().getY()+1,player.getBlockPos().getZ());
				player.world.spawnEntity(entity);
			}
			return ActionResult.PASS;
		});
		//todo proved
		EnchantmentCallback.ENCHANTMENT_CALLBACK_EVENT.register(((playerEntity,itemStack,level) -> {
			if(itemStack.getTranslationKey().contains("gold"))
				itemStack.setDamage(itemStack.getMaxDamage()/2);
			return ActionResult.PASS;
		}));
		//todo proved
		PlayerLevelupCallback.PLAYER_LEVELUP_CALLBACK_EVENT.register((player,level)->{
			if(player.experienceLevel>30)
				player.addPotionEffect(new StatusEffectInstance(StatusEffects.BLINDNESS,30,1));
			return ActionResult.PASS;
		});
		//todo proved
		/*CommandStartCallback.COMMAND_START_CALLBACK_EVENT.register((source,command)->{
			if(source.getEntityOrThrow()!=null) {
				Entity entity=source.getEntity();
				if (command.contains("give") && entity instanceof PlayerEntity)
					entity.kill();
				return ActionResult.PASS;
			}
			return ActionResult.PASS;
		});*/
		//todo proved
		BlockEntityConstructCallback.BLOCK_ENTITY_CONSTRUCT_CALLBACK_EVENT.register((blockEntity,pos,world)->{
			if(blockEntity instanceof FurnaceBlockEntity && world!=null) {
				world.getLevelProperties().setClearWeatherTime(0);
				world.getLevelProperties().setRaining(true);
			}
			return ActionResult.PASS;
		});
		//todo proved
		FishingCallback.FISHING_CALLBACK_EVENT.register(((world, player) -> {
			if(world.getLevelProperties().isRaining())
				player.dropItem(new ItemStack(Items.DIAMOND),false);
			return new TypedActionResult<>(ActionResult.PASS, LootContextTypes.CHEST);
		}));
		//todo proved
		AdvancementCallback.ADVANCEMENT_CALLBACK_EVENT.register(((playerEntity, world, advancementPacket) -> {
			advancementPacket.getAdvancementsToProgress().forEach(((identifier, advancementProgress) -> {
				if(identifier.toString().equals("minecraft:story/mine_diamond"))
					playerEntity.dropItem(Items.EMERALD_BLOCK);
			}));
			return ActionResult.PASS;
		}));
		//todo proved
		ChunkLoadCallback.CHUNK_LOAD_CALLBACK_EVENT.register((chunk -> {
			if(chunk!=null && chunk.getBiome(new BlockPos(200,80,200))== Biomes.SWAMP) {
				Entity entity=new EnderDragonEntity(EntityType.ENDER_DRAGON,chunk.getWorld());
				entity.setPosition(200,80,200);
				chunk.addEntity(entity);
			}
			return ActionResult.PASS;
		}));
		//todo proved
		DayNightCallback.DAY_NIGHT_CALLBACK_EVENT.register(((world, player) -> {
			if(world.isThundering())
				player.addPotionEffect(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE));
		}));
		//todo proved
		EnterDimensionCallback.ENTER_DIMENSION_CALLBACK_EVENT.register(((dimension,entity, world, dimensionType) -> {
			if(!world.isClient && dimensionType.equals(DimensionType.THE_END) && entity instanceof LivingEntity)
				entity.dropItem(Items.DIAMOND);
			return ActionResult.PASS;
		}));
		//todo proved
		EntityDeathCallback.ENTITY_DEATH_CALLBACK_EVENT.register(((world, entity, playerEntity, pos) -> {
			if(entity instanceof WitherSkeletonEntity)
				entity.dropItem(Items.ACACIA_BOAT);
			return ActionResult.PASS;
		}));
		//todo proved
		EntityHealCallback.ENTITY_HEAL_CALLBACK_EVENT.register(((entity, world, health) -> {
			if(entity instanceof CreeperEntity)
				entity.setHealth(entity.getMaxBreath());
			return ActionResult.PASS;
		}));
		//todo proved
		/*EntityMoveCallback.ENTITY_MOVE_CALLBACK_EVENT.register(((world, self, direction) -> {
			if(self instanceof LivingEntity && direction.x>0 && direction.z>0 && direction.x<0.5 && direction.z<0.5)
				self.dropItem(Items.ANVIL);
			return ActionResult.PASS;
		}));*/
		//todo proved
		EntitySpawnCallback.EVENT_SPAWN_CALLBACK.register(((world, player, self, pos) -> {
			if(self instanceof PigEntity)
				self.dropItem(Items.BONE_MEAL);
			return ActionResult.PASS;
		}));
		//todo proved
		ExplosionCallback.EXPLOSION_CALLBACK_EVENT.register(((world, pos) -> {
			if(pos.getY()>64)
				world.getPlayers().forEach(player->{
					if(!player.world.isClient)
					player.sendMessage(new LiteralText("wo cao ni ma!"));
				});
			return ActionResult.PASS;
		}));
		//todo proved
		EntityTeleportCallback.ENTITY_TELEPORT_CALLBACK_EVENT.register(((entity, startPos, destPos) -> {
			if(entity instanceof EndermanEntity && startPos.getY()>40) {
				Entity tnt=new TntEntity(entity.world,startPos.getX(),startPos.getY(),startPos.getZ(),null);
				entity.world.createExplosion(tnt,startPos.getX(),startPos.getY(),startPos.getZ(),7, Explosion.DestructionType.DESTROY);
			}
			return ActionResult.PASS;
		}));
		//todo proved
		FireworkCallback.FIREWORK_CALLBACK_EVENT.register((world,player,firework)->{
			if(world.getBiome(player.getBlockPos())==Biomes.MOUNTAINS && !world.isClient)
				player.sendMessage(new LiteralText("ni ma zha le"));
			return ActionResult.PASS;
		});
		//todo proved
		GuiScreenCallback.GUI_SCREEN_CALLBACK_EVENT.register(screen -> {
			if(screen.isPauseScreen()) {
				LOGGER.warn("PAUSED!");
			}
			return ActionResult.PASS;
		});
		//todo proved
		MobSpawnerCallback.MOB_SPAWNER_CALLBACK_EVENT.register((world, playerEntity, mobEntity) -> {
			if(world.isClient && mobEntity!=null&&mobEntity.getType()==EntityType.PIG)
				playerEntity.sendMessage(new LiteralText("这不清真"));
			return ActionResult.PASS;
		});
		//todo proved
		OpenContainerCallback.OPEN_CONTAINER_CALLBACK_EVENT.register(playerEntity -> {
			if(!playerEntity.inventory.isInvEmpty())
				playerEntity.getArmorItems().forEach(itemStack ->
					itemStack.setDamage(itemStack.getMaxDamage()/2));
			return ActionResult.PASS;
		});
		//todo proved
		PlayerChatCallback.PLAYER_CHAT_CALLBACK_EVENT.register((player, text) -> {
			if(text.asString().contains("nmsl"))
				player.kill();
			return new TypedActionResult<>(ActionResult.PASS,text);
		});
		//todo proved
		PlaySoundCallback.PLAY_SOUND_CALLBACK_EVENT.register((world, player, pos, event, category) -> {
			if(category.equals(SoundCategory.WEATHER)){
				Entity entity=new LightningEntity(world,player.getBlockPos().getX()+0.5,player.getBlockPos().getY(),
						player.getBlockPos().getZ()+0.5,false);
				world.spawnEntity(entity);
			}
			return ActionResult.PASS;
		});
		//todo proved
		PortalSpawnCallback.PORTAL_SPAWN_CALLBACK_EVENT.register((playerEntity, world) -> {
				if(playerEntity.getMainHandStack().getItem()==Items.FLINT_AND_STEEL)
					world.createExplosion(new TntEntity(EntityType.TNT,world),playerEntity.getBlockPos().getX(),
							playerEntity.getBlockPos().getY(),
							playerEntity.getBlockPos().getZ(),7,true, Explosion.DestructionType.DESTROY);
				return ActionResult.PASS;
			});
		//todo proved
		PreLoginCallback.PRE_LOGIN_CALLBACK_EVENT.register(((server, key, entity) -> {
			LOGGER.warn(server.getMaxPlayerCount());
			return ActionResult.PASS;
		}));
		//todo proved
		RaidCallback.RAID_CALLBACK_EVENT.register((raid, player) -> {
			if(raid.hasStarted())
				player.inventory.armor.forEach(itemStack -> {
					itemStack.addEnchantment(Enchantments.PROTECTION,4);
					itemStack.addEnchantment(Enchantments.UNBREAKING,3);
				});
			return ActionResult.PASS;
		});
		//todo proved
		RecipeCallback.RECIPE_CALLBACK_EVENT.register((world, player,itemStack) -> {
			if(!world.isClient && itemStack.getItem()==Items.CRAFTING_TABLE)
				return ActionResult.FAIL;
			return ActionResult.PASS;
		});
		//todo proved
		RidingCallback.RIDING_CALLBACK_EVENT.register((player, horse) -> {
			if(horse.getDisplayName().toString().contains("nmsl"))
				horse.kill();
			return ActionResult.PASS;
		});
		//todo proved
		ShootingCallback.SHOOTING_CALLBACK_EVENT.register((player,world,projectileEntity) -> {
			if(player instanceof PlayerEntity && world.isThundering()) {
				projectileEntity.remove();
				return ActionResult.FAIL;
			}
			return ActionResult.PASS;
		});
		//todo proved
		TamedEntityCallback.TAMED_ENTITY_CALLBACK_EVENT.register((world, playerEntity, entity) -> {
			if(!entity.isFireImmune()) {
				entity.setOnFireFor(8);
				entity.addPotionEffect(new StatusEffectInstance(StatusEffects.HEALTH_BOOST));
			}
			return ActionResult.PASS;
		});
	}
}
