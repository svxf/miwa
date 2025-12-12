package gg.cat.miwa.mixin;

import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.SimpleOption;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(GameOptions.class)
public interface OptionsMixin {
    @Accessor("sneakToggled")
    SimpleOption<Boolean> getSneakToggled();
}