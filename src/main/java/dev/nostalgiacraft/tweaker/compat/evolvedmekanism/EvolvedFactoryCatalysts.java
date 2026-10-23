package dev.nostalgiacraft.tweaker.compat.evolvedmekanism;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import mekanism.client.recipe_viewer.jei.MekanismJEI;
import mekanism.client.recipe_viewer.type.IRecipeViewerRecipeType;
import mekanism.client.recipe_viewer.type.RecipeViewerRecipeType;
import mekanism.common.block.attribute.Attribute;
import mekanism.common.block.attribute.AttributeFactoryType;
import mekanism.common.content.blocktype.FactoryType;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.RecipeTypes;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;

@JeiPlugin
public final class EvolvedFactoryCatalysts implements IModPlugin {
    private static final String EVOLVED_MEKANISM = "evolvedmekanism";
    private static final ResourceLocation UID = ResourceLocation.fromNamespaceAndPath("nostalgia_tweaker", "evolved_factory_catalysts");

    @Override
    public ResourceLocation getPluginUid() {
        return UID;
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        Map<FactoryType, List<RecipeType<?>>> categoriesByFactory = categoriesByFactory();
        BuiltInRegistries.BLOCK.entrySet().stream()
                .filter(entry -> EVOLVED_MEKANISM.equals(entry.getKey().location().getNamespace()))
                .forEach(entry -> registerFactory(registration, categoriesByFactory, entry.getValue()));
    }

    private static void registerFactory(IRecipeCatalystRegistration registration, Map<FactoryType, List<RecipeType<?>>> categoriesByFactory, Block block) {
        AttributeFactoryType factory = Attribute.get(block, AttributeFactoryType.class);
        if (factory == null) {
            return;
        }
        List<RecipeType<?>> categories = categoriesByFactory.get(factory.getFactoryType());
        if (categories != null) {
            registration.addRecipeCatalyst(block, categories.toArray(RecipeType[]::new));
        }
    }

    private static Map<FactoryType, List<RecipeType<?>>> categoriesByFactory() {
        Map<FactoryType, List<RecipeType<?>>> categories = new EnumMap<>(FactoryType.class);
        categories.put(FactoryType.SMELTING, List.of(mekanism(RecipeViewerRecipeType.SMELTING), RecipeTypes.SMELTING));
        categories.put(FactoryType.ENRICHING, List.of(mekanism(RecipeViewerRecipeType.ENRICHING)));
        categories.put(FactoryType.CRUSHING, List.of(mekanism(RecipeViewerRecipeType.CRUSHING)));
        categories.put(FactoryType.COMPRESSING, List.of(mekanism(RecipeViewerRecipeType.COMPRESSING)));
        categories.put(FactoryType.COMBINING, List.of(mekanism(RecipeViewerRecipeType.COMBINING)));
        categories.put(FactoryType.PURIFYING, List.of(mekanism(RecipeViewerRecipeType.PURIFYING)));
        categories.put(FactoryType.INJECTING, List.of(mekanism(RecipeViewerRecipeType.INJECTING)));
        categories.put(FactoryType.INFUSING, List.of(mekanism(RecipeViewerRecipeType.METALLURGIC_INFUSING)));
        categories.put(FactoryType.SAWING, List.of(mekanism(RecipeViewerRecipeType.SAWING)));
        return categories;
    }

    private static RecipeType<?> mekanism(IRecipeViewerRecipeType<?> type) {
        return MekanismJEI.genericRecipeType(type);
    }
}
