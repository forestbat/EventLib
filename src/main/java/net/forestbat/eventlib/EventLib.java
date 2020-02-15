package net.forestbat.eventlib;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.network.ServerSidePacketRegistry;
import net.forestbat.eventlib.callbacks.*;
import net.forestbat.eventlib.packets.KeyBindingPacket;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.PistonBlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.TntEntity;
import net.minecraft.entity.boss.WitherEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.passive.PigEntity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.LiteralText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.*;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.explosion.Explosion;
import net.minecraft.world.loot.context.LootContextTypes;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Objects;

public class EventLib implements ModInitializer {
	public static Logger LOGGER= LogManager.getLogger();
	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
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
		BlockEntityConstructCallback.BLOCK_ENTITY_CONSTRUCT_CALLBACK_EVENT.register((type,pos,world)->{
			if(type.equals(BlockEntityType.FURNACE) && world!=null)
				world.getLevelProperties().setRaining(true);
			return ActionResult.PASS;
		});
		FishingCallback.FISHING_CALLBACK_EVENT.register(((world, player) -> {
			if(world.getLevelProperties().isRaining())
				player.dropItem(new ItemStack(Items.DIAMOND),false);
			return new TypedActionResult<>(ActionResult.PASS, LootContextTypes.CHEST);
		}));
		//fixme
		AdvancementCallback.ADVANCEMENT_CALLBACK_EVENT.register(((playerEntity, world, advancementPacket) -> {
			advancementPacket.getAdvancementsToProgress().forEach(((identifier, advancementProgress) -> {
				if(Objects.requireNonNull(advancementProgress.getCriterionProgress("diamond!")).isObtained())
					playerEntity.dropItem(new ItemStack(Items.EMERALD_BLOCK),false);
			}));
			return ActionResult.PASS;
		}));
		ChunkLoadCallback.CHUNK_LOAD_CALLBACK_EVENT.register((chunk -> {
			if(chunk.getBiome(new BlockPos(200,80,200))== Biomes.SNOWY_MOUNTAINS) {
				Entity entity=new SheepEntity(EntityType.SHEEP,chunk.getWorld());
				entity.setPosition(200,80,200);
				chunk.addEntity(entity);
			}
			return ActionResult.PASS;
		}));
		DayNightCallback.DAY_NIGHT_CALLBACK_EVENT.register(((world, player) -> {
			if(world.isThundering())
				player.addPotionEffect(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE));
		}));
		EnterDimensionCallback.ENTER_DIMENSION_CALLBACK_EVENT.register(((dimension, entity, world, dimensionType) -> {
			if(dimensionType.equals(DimensionType.THE_END))
				world.getEntities(EntityType.ENDER_DRAGON, Box.from(MutableIntBoundingBox.create(1000,0,1000,0,1000,0)),
						dragon->dragon.distanceTo(entity)<200).forEach(Entity::kill);
			return ActionResult.PASS;
		}));
		EntityDeathCallback.ENTITY_DEATH_CALLBACK_EVENT.register(((world, entity, playerEntity, pos) -> {
			if(entity instanceof WitherEntity)
				entity.dropItem(Items.ACACIA_BOAT);
			return ActionResult.PASS;
		}));
		EntityHealCallback.ENTITY_HEAL_CALLBACK_EVENT.register(((entity, world, health) -> {
			if(entity instanceof PlayerEntity)
				entity.setHealth(entity.getMaxBreath());
			return ActionResult.PASS;
		}));
		EntityMoveCallback.ENTITY_MOVE_CALLBACK_EVENT.register(((world, self, direction) -> {
			if(direction.x>0 && direction.z>0 && direction.x<0.5 && direction.z<0.5)
				self.dropItem(Items.ANVIL);
			return ActionResult.PASS;
		}));
		EntitySpawnCallback.EVENT_SPAWN_CALLBACK.register(((world, player, self, pos) -> {
			if(self instanceof PigEntity)
				self.dropItem(Items.BONE_MEAL);
			return ActionResult.PASS;
		}));
		ExplosionCallback.EXPLOSION_CALLBACK_EVENT.register(((world, pos) -> {
			if(pos.getY()>64)
				world.getPlayers().forEach(player->player.sendMessage(new LiteralText("wo cao ni ma!")));
			return ActionResult.PASS;
		}));
		EntityTeleportCallback.ENTITY_TELEPORT_CALLBACK_EVENT.register(((entity, startPos, destPos, startWorld, destWorld) -> {
			if(startWorld.getDimension().getType()==DimensionType.OVERWORLD && destWorld.getDimension().getType()==DimensionType.THE_END) {
				Entity tnt=new TntEntity(startWorld,startPos.getX(),startPos.getY(),startPos.getZ(),null);
				startWorld.createExplosion(tnt,startPos.getX(),startPos.getY(),startPos.getZ(),7, Explosion.DestructionType.DESTROY);
			}
			return ActionResult.PASS;
		}));
		FireworkCallback.FIREWORK_CALLBACK_EVENT.register((world,player,firework)->{
			if(world.getBiome(player.getBlockPos())==Biomes.BIRCH_FOREST)
				player.sendMessage(new LiteralText("ni ma zha le"));
			return ActionResult.PASS;
		});
		FishingCallback.FISHING_CALLBACK_EVENT.register((world,player)->{
			if(world.isRaining())
				player.sendMessage(new LiteralText("亲爱的，下雨天能钓上更多鱼是个谣言"));
			return new TypedActionResult<>(ActionResult.PASS,LootContextTypes.FISHING);
		});
		GuiScreenCallback.GUI_SCREEN_CALLBACK_EVENT.register(screen -> {
			if(screen.isPauseScreen())
				screen.resize(MinecraftClient.getInstance(),800,600);
			return ActionResult.PASS;
		});
		KeyInputCallback.KEY_INPUT_CALLBACK_EVENT.register((keyCode,player) -> {
			if(keyCode==57) {
				ServerSidePacketRegistry.INSTANCE.sendToPlayer(player, new KeyBindingPacket().setKeyCode(57));
				player.setStackInHand(Hand.MAIN_HAND,new ItemStack(Items.DIAMOND,64));
			}
			return new TypedActionResult<>(ActionResult.PASS,57);
		});
		MobSpawnerCallback.MOB_SPAWNER_CALLBACK_EVENT.register((world, playerEntity, mobEntity) -> {
			if(mobEntity.getType()==EntityType.PIG)
				playerEntity.sendMessage(new LiteralText("这不清真"));
			return ActionResult.PASS;
		});
		OpenContainerCallback.OPEN_CONTAINER_CALLBACK_EVENT.register(playerEntity -> {
			if(playerEntity.inventory.isInvEmpty())
				playerEntity.getArmorItems().forEach(itemStack -> {
					itemStack.setDamage(itemStack.getMaxDamage());
				});
			return ActionResult.PASS;
		});
		//todo
		PistonCallback.PISTON_CALLBACK_EVENT.register(piston -> {
			piston.getPushedBlock().activate(piston.getWorld(),null,null, new BlockHitResult(new Vec3d(1,1,1),
					Direction.NORTH,new BlockPos(0,0,0),false));
			return null;
		});
		PlayerChatCallback.PLAYER_CHAT_CALLBACK_EVENT.register((entity, world, text) -> {
			if(text.getString().contains("nmsl"))
				entity.kill();
			return new TypedActionResult<>(ActionResult.PASS,text);
		});
		PlaySoundCallback.PLAY_SOUND_CALLBACK_EVENT.register((world, player, pos, event, category) -> {
			if(category.equals(SoundCategory.WEATHER)){
				Entity entity=new LightningEntity(world,player.getBlockPos().getX(),player.getBlockPos().getY(),
						player.getBlockPos().getZ(),true);
				world.spawnEntity(entity);
			}
			return ActionResult.PASS;
		});
		PortalSpawnCallback.PORTAL_SPAWN_CALLBACK_EVENT.register((playerEntity, world) -> {
			playerEntity.playerContainer.getStacks().forEach(itemStack -> {
				if(itemStack.getItem()==Items.FLINT)
					world.createExplosion(new TntEntity(EntityType.TNT,world),playerEntity.getBlockPos().getX(),
							playerEntity.getBlockPos().getY(),
							playerEntity.getBlockPos().getZ(),7,true, Explosion.DestructionType.DESTROY);

			});
			return ActionResult.PASS;
		});
		PreLoginCallback.PRE_LOGIN_CALLBACK_EVENT.register(((server, key, entity) -> {
			if(server.getMaxPlayerCount()>1)
				server.save(true,true,true);
			return ActionResult.PASS;
		}));
		RaidCallback.RAID_CALLBACK_EVENT.register((raid, player) -> {
			if(raid.hasStarted())
				player.inventory.armor.forEach(itemStack -> {
					itemStack.addEnchantment(Enchantments.PROTECTION,4);
					itemStack.addEnchantment(Enchantments.UNBREAKING,3);
				});
			return ActionResult.PASS;
		});
		RecipeCallback.RECIPE_CALLBACK_EVENT.register((slot, world, player) -> {
			if(player.playerContainer.getCraftingSlotCount()==4)
				return ActionResult.FAIL;
			return ActionResult.PASS;
		});
		RidingCallback.RIDING_CALLBACK_EVENT.register((player, horse) -> {
			if(horse.getDisplayName().toString().contains("nmsl"))
				horse.kill();
			return ActionResult.PASS;
		});
		ShootingCallback.SHOOTING_CALLBACK_EVENT.register((player, entity, world) -> {
			if(player instanceof PlayerEntity && ((PlayerEntity)player).inventory.getMainHandStack().getDamage()<100 && world.isThundering())
				return ActionResult.FAIL;
			return ActionResult.PASS;
		});
		SkinLoadCallback.SKIN_LOAD_CALLBACK_EVENT.register((world, playerEntity, identifier) -> {
			if(playerEntity.getDisplayName().toString().contains("Player"))
				playerEntity.doesRenderOnFire();
			return ActionResult.PASS;
		});
		TamedEntityCallback.TAMED_ENTITY_CALLBACK_EVENT.register((world, playerEntity, entity) -> {
			if(!entity.isFireImmune()) {
				entity.setOnFireFor(8);
				entity.addPotionEffect(new StatusEffectInstance(StatusEffects.HEALTH_BOOST));
			}
			return ActionResult.PASS;
		});
		WeatherCallback.WEATHER_CALLBACK_EVENT.register((world, playerEntity, pos) -> {
			if(world.getSeaLevel()<24)
				world.setBlockState(playerEntity.getBlockPos().down(), Blocks.WATER.getDefaultState());
			return ActionResult.PASS;
		});
	}
}
