package net.karnx.icenfirefactionsweapons.datagen;

import net.karnx.icenfirefactionsweapons.IceNFireFactionsWeapons;
import net.karnx.icenfirefactionsweapons.block.ModBlocks;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, IceNFireFactionsWeapons.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        columnBlock(ModBlocks.ANCIENTITE_ORE, modLoc("block/ancientite_block_side"), modLoc("block/ancientite_block_top"));
        columnBlock(ModBlocks.ANCIENTITE_BLOCK, modLoc("block/ancientite_ore_side"), modLoc("block/ancientite_ore_top"));
        columnBlock(ModBlocks.ANCIENT_VAULT, modLoc("block/ancient_vault_side"), modLoc("block/ancient_vault_top"));
    }

    private void blockWithItem(RegistryObject<Block> blockRegistryObject) {
        simpleBlockWithItem(blockRegistryObject.get(), cubeAll(blockRegistryObject.get()));
    }
    private void columnBlock(RegistryObject<Block> block, ResourceLocation side, ResourceLocation end) {
        simpleBlockWithItem(block.get(), models().cubeColumn(
                block.getId().getPath(), side, end));
    }
}
