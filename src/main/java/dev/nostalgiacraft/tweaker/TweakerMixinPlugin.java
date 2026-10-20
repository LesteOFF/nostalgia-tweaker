package dev.nostalgiacraft.tweaker;

import com.bawnorton.mixinsquared.canceller.MixinCancellerRegistrar;
import java.util.List;
import java.util.Set;
import net.neoforged.fml.loading.LoadingModList;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

public final class TweakerMixinPlugin implements IMixinConfigPlugin {
    private static final String MIXIN_PACKAGE = "dev.nostalgiacraft.tweaker.mixin.";
    private static final Set<String> CANCELLED_MIXINS = Set.of(
            "net.trique.mythicupgrades.mixin.MixinTheEndBiomeSource");

    @Override
    public void onLoad(String mixinPackage) {
        MixinCancellerRegistrar.register((targets, mixin) -> CANCELLED_MIXINS.contains(mixin));
    }

    @Override
    public String getRefMapperConfig() {
        return null;
    }

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        return LoadingModList.get().getModFileById(targetModId(mixinClassName)) != null;
    }

    private static String targetModId(String mixinClassName) {
        return mixinClassName.substring(MIXIN_PACKAGE.length()).split("\\.")[0];
    }

    @Override
    public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {
    }

    @Override
    public List<String> getMixins() {
        return null;
    }

    @Override
    public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {
    }

    @Override
    public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {
    }
}
