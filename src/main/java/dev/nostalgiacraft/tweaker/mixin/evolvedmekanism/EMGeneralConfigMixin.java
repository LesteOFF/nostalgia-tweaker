package dev.nostalgiacraft.tweaker.mixin.evolvedmekanism;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.neoforged.neoforge.common.ModConfigSpec;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(targets = "fr.iglee42.evolvedmekanism.config.EMGeneralConfig")
abstract class EMGeneralConfigMixin {
    @WrapOperation(
            method = "<init>",
            at = @At(value = "INVOKE", target = "Lnet/neoforged/neoforge/common/ModConfigSpec$Builder;define(Ljava/lang/String;Ljava/lang/Object;)Lnet/neoforged/neoforge/common/ModConfigSpec$ConfigValue;"))
    private ModConfigSpec.ConfigValue<?> tweaker$defineLongAsRange(ModConfigSpec.Builder builder, String path, Object defaultValue, Operation<ModConfigSpec.ConfigValue<?>> original) {
        if (defaultValue instanceof Long longDefault) {
            return builder.defineInRange(path, longDefault.longValue(), Long.MIN_VALUE, Long.MAX_VALUE);
        }
        return original.call(builder, path, defaultValue);
    }
}
