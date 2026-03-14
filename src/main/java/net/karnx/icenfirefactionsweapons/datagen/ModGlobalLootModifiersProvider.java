package net.karnx.icenfirefactionsweapons.datagen;

import net.karnx.icenfirefactionsweapons.IceNFireFactionsWeapons;
import net.karnx.icenfirefactionsweapons.item.ModItems;
import net.karnx.icenfirefactionsweapons.loot.AddItemModifiers;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.data.GlobalLootModifierProvider;
import net.minecraftforge.common.loot.LootTableIdCondition;

public class ModGlobalLootModifiersProvider extends GlobalLootModifierProvider {
    public ModGlobalLootModifiersProvider(PackOutput output) {
        super(output, IceNFireFactionsWeapons.MODID);
    }

    @Override
    protected void start() {
        add("eyes_from_ender", new AddItemModifiers(new LootItemCondition[]{
                new LootTableIdCondition.Builder(new ResourceLocation("entities/ender_dragon")).build()
        }, ModItems.ANCIENT_EYE.get(), 2
        ));
        add("horns_from_ender", new AddItemModifiers(new LootItemCondition[]{
                new LootTableIdCondition.Builder(new ResourceLocation("entities/ender_dragon")).build()
        }, ModItems.ANCIENT_HORN.get(), 2
        ));
        add("scales_from_ender", new AddItemModifiers(new LootItemCondition[]{
                new LootTableIdCondition.Builder(new ResourceLocation("entities/ender_dragon")).build()
        }, ModItems.ANCIENT_SCALE.get(), 10
        ));
        add("fang_from_ender", new AddItemModifiers(new LootItemCondition[]{
                new LootTableIdCondition.Builder(new ResourceLocation("entities/ender_dragon")).build()
        }, ModItems.ANCIENT_FANG.get(), 5
        ));
        add("bones_from_ender", new AddItemModifiers(new LootItemCondition[]{
                new LootTableIdCondition.Builder(new ResourceLocation("entities/ender_dragon")).build()
        }, ModItems.ANCIENT_BONE.get(), 10
        ));

    }
}
