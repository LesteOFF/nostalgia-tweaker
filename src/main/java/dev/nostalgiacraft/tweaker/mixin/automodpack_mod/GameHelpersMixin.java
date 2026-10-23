package dev.nostalgiacraft.tweaker.mixin.automodpack_mod;

import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(targets = "pl.skidam.automodpack.modpack.GameHelpers")
abstract class GameHelpersMixin {
    @Redirect(
            method = {"getPlayerProfile", "isPlayerAuthorized"},
            at = @At(value = "INVOKE", target = "Lnet/minecraft/server/MinecraftServer;isSameThread()Z"),
            require = 2)
    private static boolean tweaker$readDirectlyFromNettyThread(MinecraftServer server) {
        return true;
    }
}
