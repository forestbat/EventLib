package net.forestbat.eventlib.mixins;

import net.forestbat.eventlib.EventLib;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

@Mixin(PreparedStatement.class)
public class MixinPreparedStatement {
    @Inject(method = "executeQuery",at=@At("RETURN"))
    public void afterExecuteQuery(CallbackInfoReturnable<ResultSet> returnable){
        if(!returnable.isCancelled())
        EventLib.LOGGER.debug("A query has been excuted");
    }
}
