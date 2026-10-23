package dev.nostalgiacraft.tweaker.mixin.mythicupgrades;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.trique.mythicupgrades.effect.JadeAuraEffect;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(JadeAuraEffect.class)
abstract class JadeAuraEffectMixin {
    @Shadow
    @Final
    private static ResourceLocation SPEED_ID;

    @Inject(method = "addAttributeModifiers", at = @At("HEAD"))
    private void tweaker$dropStaleSpeedModifier(AttributeMap attributes, int amplifier, CallbackInfo ci) {
        AttributeInstance movementSpeed = attributes.getInstance(Attributes.MOVEMENT_SPEED);
        if (movementSpeed != null) {
            movementSpeed.removeModifier(SPEED_ID);
        }
    }
}
