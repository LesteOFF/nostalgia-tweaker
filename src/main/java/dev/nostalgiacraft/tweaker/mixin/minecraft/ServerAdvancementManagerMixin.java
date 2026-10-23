package dev.nostalgiacraft.tweaker.mixin.minecraft;

import com.google.gson.JsonElement;
import java.util.Map;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.ServerAdvancementManager;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerAdvancementManager.class)
abstract class ServerAdvancementManagerMixin {
    @Unique
    private static final String TWEAKER$RECIPE_UNLOCK_FOLDER = "recipes/";

    @Inject(method = "apply(Ljava/util/Map;Lnet/minecraft/server/packs/resources/ResourceManager;Lnet/minecraft/util/profiling/ProfilerFiller;)V", at = @At("HEAD"))
    private void tweaker$skipRecipeUnlocks(Map<ResourceLocation, JsonElement> advancements, ResourceManager resourceManager, ProfilerFiller profiler, CallbackInfo ci) {
        advancements.keySet().removeIf(id -> id.getPath().startsWith(TWEAKER$RECIPE_UNLOCK_FOLDER));
    }
}
