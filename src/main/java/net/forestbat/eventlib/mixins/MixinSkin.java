package net.forestbat.eventlib.mixins;

import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import net.forestbat.eventlib.callbacks.SkinLoadCallback;
import net.minecraft.client.texture.PlayerSkinProvider;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerSkinProvider.class)
public class MixinSkin {
    @Inject(method = "loadSkin(Lcom/mojang/authlib/minecraft/MinecraftProfileTexture;" +
            "Lcom/mojang/authlib/minecraft/MinecraftProfileTexture$Type;Lnet/minecraft/client/texture/PlayerSkinProvider" +
            "$SkinTextureAvailableCallback;)Lnet/minecraft/util/Identifier;",at = @At("HEAD"),cancellable = true)
    public void beforeLoadSkin(MinecraftProfileTexture minecraftProfileTexture, MinecraftProfileTexture.Type minecraftProfileTextureType,
                               PlayerSkinProvider.SkinTextureAvailableCallback skinTextureAvailableCallback,
                               CallbackInfoReturnable<Identifier> cir){
        //todo
        if(SkinLoadCallback.SKIN_LOAD_CALLBACK_EVENT.invoker().accept(null,null,null)== ActionResult.FAIL)
            cir.cancel();
    }
}
