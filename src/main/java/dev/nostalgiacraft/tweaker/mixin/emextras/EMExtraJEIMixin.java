package dev.nostalgiacraft.tweaker.mixin.emextras;

import fr.iglee42.evolvedmekanism.recipeviewers.EMRecipeViewersTypes;
import mekanism.client.recipe_viewer.type.RVRecipeTypeWrapper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(targets = "io.github.masyumero.emextras.client.recipe_viewer.jei.EMExtraJEI")
abstract class EMExtraJEIMixin {
    @Redirect(
            method = "registerRecipeCatalysts",
            at = @At(value = "FIELD", opcode = 178, target = "Lfr/iglee42/evolvedmekanism/jei/JEIRecipeTypes;ALLOYING:Lmekanism/client/recipe_viewer/type/RVRecipeTypeWrapper;"))
    private RVRecipeTypeWrapper<?, ?, ?> tweaker$movedAlloyingType() {
        return EMRecipeViewersTypes.ALLOYING;
    }
}
