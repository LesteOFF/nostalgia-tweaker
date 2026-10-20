package dev.nostalgiacraft.tweaker.mixin.mythicupgrades;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import java.util.List;
import java.util.stream.Stream;
import net.minecraft.core.Holder;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.biome.TheEndBiomeSource;
import net.trique.mythicupgrades.MythicStats;
import net.trique.mythicupgrades.worldgen.MythicBiomeOverlay;
import net.trique.mythicupgrades.worldgen.MythicBiomes;
import net.trique.mythicupgrades.worldgen.MythicOverlayLookup;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = TheEndBiomeSource.class, priority = 500)
abstract class TheEndBiomeSourceMixin {
    @Unique
    private static final long TWEAKER$MAIN_ISLAND_QUARTS_SQUARED = 256L * 256L;

    @Unique
    private Holder<Biome> tweaker$mythicBarrens;

    @Unique
    private boolean tweaker$resolved;

    @ModifyReturnValue(method = "collectPossibleBiomes", at = @At("RETURN"))
    private Stream<Holder<Biome>> tweaker$addMythicBarrens(Stream<Holder<Biome>> original) {
        tweaker$resolved = true;
        List<Holder<Biome>> biomes = original.toList();
        if (biomes.isEmpty() || MythicOverlayLookup.isDatagen()) {
            return biomes.stream();
        }
        tweaker$mythicBarrens = MythicOverlayLookup.resolve(biomes.getFirst(), MythicBiomes.MYTHIC_BARRENS);
        if (tweaker$mythicBarrens == null) {
            return biomes.stream();
        }
        return Stream.concat(biomes.stream(), Stream.of(tweaker$mythicBarrens));
    }

    @Inject(method = "getNoiseBiome", at = @At("HEAD"), cancellable = true)
    private void tweaker$placeMythicBarrens(int x, int y, int z, Climate.Sampler sampler, CallbackInfoReturnable<Holder<Biome>> cir) {
        if (!tweaker$resolved) {
            ((BiomeSource) (Object) this).possibleBiomes();
            tweaker$resolved = true;
        }
        if (tweaker$mythicBarrens == null || (long) x * x + (long) z * z < TWEAKER$MAIN_ISLAND_QUARTS_SQUARED) {
            return;
        }
        int radius = MythicBiomeOverlay.blocksToQuarts(MythicStats.WORLDGEN_END_RADIUS);
        int grid = MythicBiomeOverlay.gridFor(radius, MythicStats.WORLDGEN_END_DENSITY);
        if (MythicBiomeOverlay.inPatch(x, z, 31, radius, grid)) {
            cir.setReturnValue(tweaker$mythicBarrens);
        }
    }
}
