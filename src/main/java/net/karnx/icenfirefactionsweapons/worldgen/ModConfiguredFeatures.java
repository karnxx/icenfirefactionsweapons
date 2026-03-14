package net.karnx.icenfirefactionsweapons.worldgen;

import net.karnx.icenfirefactionsweapons.IceNFireFactionsWeapons;
import net.karnx.icenfirefactionsweapons.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;

import java.util.List;

public class ModConfiguredFeatures {
    public static final ResourceKey<ConfiguredFeature<?,?>> END_ORE_DEPOSIT = registerkey("end_ore_dep");

    public static void boostrap(BootstapContext<ConfiguredFeature<?,?>> context) {
        RuleTest endReplacables = new BlockMatchTest(Blocks.END_STONE);

        register(context, END_ORE_DEPOSIT, Feature.ORE, new OreConfiguration(endReplacables,
                ModBlocks.ANCIENTITE_ORE.get().defaultBlockState(), 3));
    }



    public static ResourceKey<ConfiguredFeature<?, ? >> registerkey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, new ResourceLocation(IceNFireFactionsWeapons.MODID, name));
    }

    private static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(BootstapContext<ConfiguredFeature<?,?>> context,
                                                                                          ResourceKey<ConfiguredFeature<?,?>> key, F feature, FC config) {
        context.register(key, new ConfiguredFeature<>(feature, config));
    }


}
