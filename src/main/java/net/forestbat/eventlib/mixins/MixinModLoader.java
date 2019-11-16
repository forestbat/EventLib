package net.forestbat.eventlib.mixins;

import net.forestbat.eventlib.callbacks.ModLoadCallback;
import net.fabricmc.loader.FabricLoader;
import net.fabricmc.loader.ModContainer;
import net.minecraft.util.ActionResult;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(FabricLoader.class)
public class MixinModLoader {
    @Shadow @Final
    protected Map<String,ModContainer> modMap;
    @Inject(method = "load",at=@At("HEAD"))
    public void beforeLoad(CallbackInfo callbackInfo){
        for(String modid:modMap.keySet()) {
            if (ModLoadCallback.MOD_LOAD_CALLBACK_EVENT.invoker().accept(modid) == ActionResult.FAIL)
                callbackInfo.cancel();
        }
    }
}
