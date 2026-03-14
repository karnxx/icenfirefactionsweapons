package net.karnx.icenfirefactionsweapons.datagen;

import net.karnx.icenfirefactionsweapons.IceNFireFactionsWeapons;
import net.karnx.icenfirefactionsweapons.block.ModBlocks;
import net.karnx.icenfirefactionsweapons.util.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagGenerator extends BlockTagsProvider {
    public ModBlockTagGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, IceNFireFactionsWeapons.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider p_256380_) {
        this.tag(Tags.Blocks.ORES).add(ModBlocks.ANCIENTITE_ORE.get());
        this.tag(ModTags.Blocks.NEEDS_DRAGONSTEEL_TOOL).add(ModBlocks.ANCIENTITE_ORE.get()).add(ModBlocks.ANCIENTITE_BLOCK.get());

    }
}
