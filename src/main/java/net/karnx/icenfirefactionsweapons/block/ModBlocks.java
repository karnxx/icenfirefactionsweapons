package net.karnx.icenfirefactionsweapons.block;

import net.karnx.icenfirefactionsweapons.IceNFireFactionsWeapons;
import net.karnx.icenfirefactionsweapons.item.ModItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModBlocks {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, IceNFireFactionsWeapons.MODID);

    public static final RegistryObject<Block> ANCIENTITE_BLOCK = registerBlock("ancientite_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.OBSIDIAN).sound(SoundType.ANCIENT_DEBRIS)), 1);

    public static final RegistryObject<Block> ANCIENTITE_ORE = registerBlock("ancientite_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.OBSIDIAN).sound(SoundType.ANCIENT_DEBRIS)), 1);

    public static final RegistryObject<Block> ANCIENT_VAULT = registerBlock("ancient_vault", () -> new Block(BlockBehaviour.Properties.copy(Blocks.BEDROCK).sound(SoundType.BASALT)), 1);

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block, int stack) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn, stack);
        return toReturn;
    }

    private static <T extends  Block> void registerBlockItem(String name, RegistryObject<T> block, int stack) {
        ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static void register(IEventBus iEventBus) {
        BLOCKS.register(iEventBus);
    }
}
