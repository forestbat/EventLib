package net.forestbat.eventlib.callbacks;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.world.World;

public interface ShootingCallback {
    ActionResult accept(LivingEntity player, ProjectileEntity entity, World world);
    Event<ShootingCallback> SHOOTING_CALLBACK_EVENT= EventFactory.createArrayBacked(ShootingCallback.class,
            listeners->(player,entity,world)->{
                for(ShootingCallback callback:listeners){
                    if(callback.accept(player,entity,world)!=ActionResult.FAIL)
                        return ActionResult.PASS;
                }
                return ActionResult.PASS;
            });
}
