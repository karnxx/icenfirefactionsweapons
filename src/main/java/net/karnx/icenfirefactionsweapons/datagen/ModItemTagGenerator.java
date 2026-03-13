package net.karnx.icenfirefactionsweapons.datagen;

import net.karnx.icenfirefactionsweapons.IceNFireFactionsWeapons;
import net.karnx.icenfirefactionsweapons.item.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModItemTagGenerator extends ItemTagsProvider {
    public ModItemTagGenerator(PackOutput p_275343_, CompletableFuture<HolderLookup.Provider> p_275729_, CompletableFuture<TagLookup<Block>> p_275322_, @Nullable ExistingFileHelper existingFileHelper) {
        super(p_275343_, p_275729_, p_275322_, IceNFireFactionsWeapons.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider p_256380_) {
        assert ModItems.ANCIENTITE_ACCORD_HELMET.getKey() != null;
        assert ModItems.ANCIENTITE_ACCORD_CHESTPLATE.getKey() != null;
        assert ModItems.ANCIENTITE_ACCORD_LEGGINGS.getKey() != null;
        assert ModItems.ANCIENTITE_ACCORD_BOOTS.getKey() != null;
        assert ModItems.ANCIENTITE_SANCTUM_HELMET.getKey() != null;
        assert ModItems.ANCIENTITE_SANCTUM_CHESTPLATE.getKey() != null;
        assert ModItems.ANCIENTITE_SANCTUM_LEGGINGS.getKey() != null;
        assert ModItems.ANCIENTITE_SANCTUM_BOOTS.getKey() != null;
        assert ModItems.ANCIENTITE_TAMER_HELMET.getKey() != null;
        assert ModItems.ANCIENTITE_TAMER_CHESTPLATE.getKey() != null;
        assert ModItems.ANCIENTITE_TAMER_BOOTS.getKey() != null;
        assert ModItems.ANCIENTITE_TAMER_LEGGINGS.getKey() != null;
        assert ModItems.ANCIENTITE_SLAYER_HELMET.getKey() != null;
        assert ModItems.ANCIENTITE_SLAYER_CHESTPLATE.getKey() != null;
        assert ModItems.ANCIENTITE_SLAYER_LEGGINGS.getKey() != null;
        assert ModItems.ANCIENTITE_SLAYER_BOOTS.getKey() != null;
        assert ModItems.ANCIENTITE_HELMET.getKey() != null;
        assert ModItems.ANCIENTITE_CHESTPLATE.getKey() != null;
        assert ModItems.ANCIENTITE_LEGGINGS.getKey() != null;
        assert ModItems.ANCIENTITE_BOOTS.getKey() != null;
        this.tag(ItemTags.TRIMMABLE_ARMOR)
                .add(ModItems.ANCIENTITE_BOOTS.getKey())
                .add(ModItems.ANCIENTITE_LEGGINGS.getKey())
                .add(ModItems.ANCIENTITE_CHESTPLATE.getKey())
                .add(ModItems.ANCIENTITE_HELMET.getKey())

                .add(ModItems.ANCIENTITE_SLAYER_BOOTS.getKey())
                .add(ModItems.ANCIENTITE_SLAYER_LEGGINGS.getKey())
                .add(ModItems.ANCIENTITE_SLAYER_CHESTPLATE.getKey())
                .add(ModItems.ANCIENTITE_SLAYER_HELMET.getKey())

                .add(ModItems.ANCIENTITE_TAMER_BOOTS.getKey())
                .add(ModItems.ANCIENTITE_TAMER_LEGGINGS.getKey())
                .add(ModItems.ANCIENTITE_TAMER_CHESTPLATE.getKey())
                .add(ModItems.ANCIENTITE_TAMER_HELMET.getKey())

                .add(ModItems.ANCIENTITE_SANCTUM_BOOTS.getKey())
                .add(ModItems.ANCIENTITE_SANCTUM_LEGGINGS.getKey())
                .add(ModItems.ANCIENTITE_SANCTUM_CHESTPLATE.getKey())
                .add(ModItems.ANCIENTITE_SANCTUM_HELMET.getKey())

                .add(ModItems.ANCIENTITE_ACCORD_BOOTS.getKey())
                .add(ModItems.ANCIENTITE_ACCORD_LEGGINGS.getKey())
                .add(ModItems.ANCIENTITE_ACCORD_CHESTPLATE.getKey())
                .add(ModItems.ANCIENTITE_ACCORD_HELMET.getKey())

        ;

    }
}
