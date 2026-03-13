package net.karnx.icenfirefactionsweapons.datagen;

import net.karnx.icenfirefactionsweapons.IceNFireFactionsWeapons;
import net.karnx.icenfirefactionsweapons.item.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.armortrim.TrimMaterial;
import net.minecraft.world.item.armortrim.TrimMaterials;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

import java.util.LinkedHashMap;

public class ModItemModelProvider extends ItemModelProvider {
    private static LinkedHashMap<ResourceKey<TrimMaterial>, Float> trimMaterials = new LinkedHashMap<>();
    static {
        trimMaterials.put(TrimMaterials.QUARTZ, 0.1F);
        trimMaterials.put(TrimMaterials.IRON, 0.2F);
        trimMaterials.put(TrimMaterials.NETHERITE, 0.3F);
        trimMaterials.put(TrimMaterials.REDSTONE, 0.4F);
        trimMaterials.put(TrimMaterials.COPPER, 0.5F);
        trimMaterials.put(TrimMaterials.GOLD, 0.6F);
        trimMaterials.put(TrimMaterials.EMERALD, 0.7F);
        trimMaterials.put(TrimMaterials.DIAMOND, 0.8F);
        trimMaterials.put(TrimMaterials.LAPIS, 0.9F);
        trimMaterials.put(TrimMaterials.AMETHYST, 1.0F);
    }


    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, IceNFireFactionsWeapons.MODID, existingFileHelper);
    }



    @Override
    protected void registerModels() {
        simpleItem(ModItems.ANCIENT_BONE);
        simpleItem(ModItems.ANCIENT_EYE);
        simpleItem(ModItems.ANCIENT_FANG);
        simpleItem(ModItems.ANCIENT_HORN);
        simpleItem(ModItems.ANCIENT_SCALE);

        simpleItem(ModItems.ANCIENTITE);


        trimmedArmorItem(ModItems.ANCIENTITE_HELMET);
        trimmedArmorItem(ModItems.ANCIENTITE_CHESTPLATE);
        trimmedArmorItem(ModItems.ANCIENTITE_LEGGINGS);
        trimmedArmorItem(ModItems.ANCIENTITE_BOOTS);

        trimmedArmorItem(ModItems.ANCIENTITE_SLAYER_HELMET);
        trimmedArmorItem(ModItems.ANCIENTITE_SLAYER_CHESTPLATE);
        trimmedArmorItem(ModItems.ANCIENTITE_SLAYER_LEGGINGS);
        trimmedArmorItem(ModItems.ANCIENTITE_SLAYER_BOOTS);

        trimmedArmorItem(ModItems.ANCIENTITE_TAMER_HELMET);
        trimmedArmorItem(ModItems.ANCIENTITE_TAMER_CHESTPLATE);
        trimmedArmorItem(ModItems.ANCIENTITE_TAMER_LEGGINGS);
        trimmedArmorItem(ModItems.ANCIENTITE_TAMER_BOOTS);

        trimmedArmorItem(ModItems.ANCIENTITE_SANCTUM_HELMET);
        trimmedArmorItem(ModItems.ANCIENTITE_SANCTUM_CHESTPLATE);
        trimmedArmorItem(ModItems.ANCIENTITE_SANCTUM_LEGGINGS);
        trimmedArmorItem(ModItems.ANCIENTITE_SANCTUM_BOOTS);

        trimmedArmorItem(ModItems.ANCIENTITE_ACCORD_HELMET);
        trimmedArmorItem(ModItems.ANCIENTITE_ACCORD_CHESTPLATE);
        trimmedArmorItem(ModItems.ANCIENTITE_ACCORD_LEGGINGS);
        trimmedArmorItem(ModItems.ANCIENTITE_ACCORD_BOOTS);


    }

    private void trimmedArmorItem(RegistryObject<Item> itemRegistryObject) {
        final String MOD_ID = IceNFireFactionsWeapons.MODID;

        if(itemRegistryObject.get() instanceof ArmorItem armorItem) {
            trimMaterials.entrySet().forEach(entry -> {

                ResourceKey<TrimMaterial> trimMaterial = entry.getKey();
                float trimValue = entry.getValue();

                String armorType = switch (armorItem.getEquipmentSlot()) {
                    case HEAD -> "helmet";
                    case CHEST -> "chestplate";
                    case LEGS -> "leggings";
                    case FEET -> "boots";
                    default -> "";
                };

                String armorItemPath = "item/" + armorItem;
                String trimPath = "trims/items/" + armorType + "_trim_" + trimMaterial.location().getPath();
                String currentTrimName = armorItemPath + "_" + trimMaterial.location().getPath() + "_trim";
                ResourceLocation armorItemResLoc = new ResourceLocation(MOD_ID, armorItemPath);
                ResourceLocation trimResLoc = new ResourceLocation(trimPath);
                ResourceLocation trimNameResLoc = new ResourceLocation(MOD_ID, currentTrimName);

                existingFileHelper.trackGenerated(trimResLoc, PackType.CLIENT_RESOURCES, ".png", "textures");

                // Trimmed armorItem files
                getBuilder(currentTrimName)
                        .parent(new ModelFile.UncheckedModelFile("item/generated"))
                        .texture("layer0", armorItemResLoc)
                        .texture("layer1", trimResLoc);

                // Non-trimmed armorItem file (normal variant)
                this.withExistingParent(itemRegistryObject.getId().getPath(),
                                mcLoc("item/generated"))
                        .override()
                        .model(new ModelFile.UncheckedModelFile(trimNameResLoc))
                        .predicate(mcLoc("trim_type"), trimValue).end()
                        .texture("layer0",
                                new ResourceLocation(MOD_ID,
                                        "item/" + itemRegistryObject.getId().getPath()));
            });
        }
    }


    private ItemModelBuilder handheldModelBuilder(RegistryObject<Item> item) {
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/handheld")).texture("layer0",
                new ResourceLocation(IceNFireFactionsWeapons.MODID, "item/" + item.getId().getPath()));
    }

    private ItemModelBuilder simpleItem(RegistryObject<Item> item) {
        assert item.getId() != null;
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/generated")).texture( "layer0", new ResourceLocation(IceNFireFactionsWeapons.MODID, "item/" + item.getId().getPath()));

    }
}
