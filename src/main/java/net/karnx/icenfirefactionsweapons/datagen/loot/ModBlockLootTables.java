package net.karnx.icenfirefactionsweapons.datagen.loot;

import net.karnx.icenfirefactionsweapons.block.ModBlocks;
import net.karnx.icenfirefactionsweapons.item.ModItems;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.registries.RegistryObject;

import java.util.Set;

public class ModBlockLootTables extends BlockLootSubProvider {
    public ModBlockLootTables() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    public LootTable.Builder createcustomoredrops(Block p_251306_, Item item, float b1, float b2) {
        return createSilkTouchDispatchTable(p_251306_,
                this.applyExplosionDecay(p_251306_,
                        LootItem.lootTableItem(item)
                                .apply(SetItemCountFunction
                                        .setCount(UniformGenerator
                                                .between(b1, b2)))));
    }

    @Override
    protected void generate() {

        this.add(ModBlocks.ANCIENTITE_ORE.get(), block -> createcustomoredrops(ModBlocks.ANCIENTITE_ORE.get(), ModItems.ANCIENTITE.get(), 1.0f, 2.0f));
        this.add(ModBlocks.ANCIENTITE_BLOCK.get(), block -> createcustomoredrops(ModBlocks.ANCIENTITE_ORE.get(), ModItems.ANCIENTITE.get(), 1.0f, 10.0f));
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ModBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }
}
