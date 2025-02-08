package gg.cat.miwa.mixin;

import gg.cat.miwa.EventHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


import net.minecraft.client.MinecraftClient;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixins {

    @Inject(method = "handleInputEvents()V", at = @At("HEAD"))
    private void handleInputEvents(CallbackInfo ci) {
        EventHandler.onHandleInputEvents();
    }
}