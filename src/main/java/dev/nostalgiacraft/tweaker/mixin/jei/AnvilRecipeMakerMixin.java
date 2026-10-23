package dev.nostalgiacraft.tweaker.mixin.jei;

import java.util.stream.Stream;
import mezz.jei.api.recipe.vanilla.IJeiAnvilRecipe;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(targets = "mezz.jei.library.plugins.vanilla.anvil.AnvilRecipeMaker")
abstract class AnvilRecipeMakerMixin {
    @Inject(method = "getBookEnchantmentRecipes()Ljava/util/stream/Stream;", at = @At("HEAD"), cancellable = true)
    private void tweaker$skipBookEnchantmentRecipes(CallbackInfoReturnable<Stream<IJeiAnvilRecipe>> cir) {
        cir.setReturnValue(Stream.empty());
    }
}
