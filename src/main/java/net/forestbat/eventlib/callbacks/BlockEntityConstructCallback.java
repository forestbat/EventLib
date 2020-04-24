package net.forestbat.eventlib.callbacks;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface BlockEntityConstructCallback {
    ActionResult accept(BlockEntity blockEntity, BlockPos blockPos, World world);
    Event<BlockEntityConstructCallback> BLOCK_ENTITY_CONSTRUCT_CALLBACK_EVENT= EventFactory.createArrayBacked(BlockEntityConstructCallback.class,
            listeners->(blockEntity,blockPos,world)->{
                for(BlockEntityConstructCallback callback:listeners){
                    if(callback.accept(blockEntity,blockPos,world)!=ActionResult.FAIL)
                        return ActionResult.PASS;
                }
                return ActionResult.PASS;
            });
}
