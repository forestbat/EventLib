package net.fabricmc.eventlib.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import static net.fabricmc.eventlib.EventLib.LOGGER;

@Mixin(PreparedStatement.class)
public class MixinPreparedStatementCallback {
    @Inject(method = "executeQuery",at=@At("RETURN"))
    public void afterExecuteQuery(CallbackInfoReturnable<ResultSet> returnable){
        if(!returnable.isCancelled())
        LOGGER.debug("A query has been excuted");
    }
}
