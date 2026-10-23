package dev.nostalgiacraft.tweaker.mixin.hazennstuff;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.fml.loading.FMLEnvironment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(targets = {
        "net.hazen.hazennstuff.Item.Curios.Misc.PendantOfHarmonyCurio",
        "net.hazen.hazennstuff.Item.Weapons.Generic.Spectrum.SpectrumItem",
        "net.hazen.hazennstuff.Item.Weapons.Reference.Terraria.Meowmere.MeowmereItem"
})
abstract class RainbowItemNameMixin {
    @Inject(method = "getName", at = @At("HEAD"), cancellable = true)
    private void tweaker$plainNameOnDedicatedServer(ItemStack stack, CallbackInfoReturnable<Component> cir) {
        if (FMLEnvironment.dist.isDedicatedServer()) {
            cir.setReturnValue(Component.translatable(((Item) (Object) this).getDescriptionId(stack)));
        }
    }
}
