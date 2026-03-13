package net.karnx.icenfirefactionsweapons.item;

import net.karnx.icenfirefactionsweapons.IceNFireFactionsWeapons;

import net.karnx.icenfirefactionsweapons.item.factitems.AnnihilatorRay;
import net.karnx.icenfirefactionsweapons.item.factitems.CommanderStaff;
import net.karnx.icenfirefactionsweapons.item.factitems.DragonslayerSword;
import net.karnx.icenfirefactionsweapons.item.factitems.SanctumKey;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tiers;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;


public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, IceNFireFactionsWeapons.MODID);

    public static final RegistryObject<Item> ANCIENTITE = ITEMS.register("ancientite", () -> new Item(new Item.Properties().stacksTo(9)));
    public static final RegistryObject<Item> ANCIENT_SCALE = ITEMS.register("ancient_scale", () -> new Item(new Item.Properties().stacksTo(2)));
    public static final RegistryObject<Item> ANCIENT_FANG = ITEMS.register("ancient_fang", () -> new Item(new Item.Properties().stacksTo(2)));
    public static final RegistryObject<Item> ANCIENT_BONE = ITEMS.register("ancient_bone", () -> new Item(new Item.Properties().stacksTo(2)));
    public static final RegistryObject<Item> ANCIENT_EYE = ITEMS.register("ancient_eye", () -> new Item(new Item.Properties().stacksTo(2)));

    public static final RegistryObject<Item> ANCIENT_HORN = ITEMS.register("ancient_horn", () -> new Item(new Item.Properties().stacksTo(2)));

    public static final RegistryObject<Item> ANNIHILATOR_RAY = ITEMS.register("annihilator_ray", AnnihilatorRay::new);

    public static final RegistryObject<Item> VAULT_KEY = ITEMS.register("ancient_key", () -> new SanctumKey(new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> DRAGONSLAYER_BLADE = ITEMS.register("dragonslayer_blade", () -> new DragonslayerSword(Tiers.NETHERITE, 10, -2.4f, new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> COMMANDER_STAFF = ITEMS.register("commander_staff", () -> new CommanderStaff(new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> ANCIENTITE_HELMET =ITEMS.register("ancientite_helmet",
            () -> new ModArmorItem(ModArmorMaterials.ANCIENTITE, ModArmorItem.Type.HELMET, new Item.Properties()));
    public static final RegistryObject<Item> ANCIENTITE_CHESTPLATE =ITEMS.register("ancientite_chestplate",
            () -> new ModArmorItem(ModArmorMaterials.ANCIENTITE, ModArmorItem.Type.CHESTPLATE, new Item.Properties()));
    public static final RegistryObject<Item> ANCIENTITE_LEGGINGS =ITEMS.register("ancientite_leggings",
            () -> new ModArmorItem(ModArmorMaterials.ANCIENTITE, ModArmorItem.Type.LEGGINGS, new Item.Properties()));
    public static final RegistryObject<Item> ANCIENTITE_BOOTS =ITEMS.register("ancientite_boots",
            () -> new ModArmorItem(ModArmorMaterials.ANCIENTITE, ModArmorItem.Type.BOOTS, new Item.Properties()));

    public static final RegistryObject<Item> ANCIENTITE_SLAYER_HELMET =ITEMS.register("ancientite_slayer_helmet",
            () -> new ModArmorItem(ModArmorMaterials.ANCIENTITE_SLAYER, ModArmorItem.Type.HELMET, new Item.Properties()));
    public static final RegistryObject<Item> ANCIENTITE_SLAYER_CHESTPLATE =ITEMS.register("ancientite_slayer_chestplate",
            () -> new ModArmorItem(ModArmorMaterials.ANCIENTITE_SLAYER, ModArmorItem.Type.CHESTPLATE, new Item.Properties()));
    public static final RegistryObject<Item> ANCIENTITE_SLAYER_LEGGINGS =ITEMS.register("ancientite_slayer_leggings",
            () -> new ModArmorItem(ModArmorMaterials.ANCIENTITE_SLAYER, ModArmorItem.Type.LEGGINGS, new Item.Properties()));
    public static final RegistryObject<Item> ANCIENTITE_SLAYER_BOOTS =ITEMS.register("ancientite_slayer_boots",
            () -> new ModArmorItem(ModArmorMaterials.ANCIENTITE_SLAYER, ModArmorItem.Type.BOOTS, new Item.Properties()));

    public static final RegistryObject<Item> ANCIENTITE_TAMER_HELMET =ITEMS.register("ancientite_tamer_helmet",
            () -> new ModArmorItem(ModArmorMaterials.ANCIENTITE_TAMER, ModArmorItem.Type.HELMET, new Item.Properties()));
    public static final RegistryObject<Item> ANCIENTITE_TAMER_CHESTPLATE =ITEMS.register("ancientite_tamer_chestplate",
            () -> new ModArmorItem(ModArmorMaterials.ANCIENTITE_TAMER, ModArmorItem.Type.CHESTPLATE, new Item.Properties()));
    public static final RegistryObject<Item> ANCIENTITE_TAMER_LEGGINGS =ITEMS.register("ancientite_tamer_leggings",
            () -> new ModArmorItem(ModArmorMaterials.ANCIENTITE_TAMER, ModArmorItem.Type.LEGGINGS, new Item.Properties()));
    public static final RegistryObject<Item> ANCIENTITE_TAMER_BOOTS =ITEMS.register("ancientite_tamer_boots",
            () -> new ModArmorItem(ModArmorMaterials.ANCIENTITE_TAMER, ModArmorItem.Type.BOOTS, new Item.Properties()));

    public static final RegistryObject<Item> ANCIENTITE_SANCTUM_HELMET =ITEMS.register("ancientite_sanctum_helmet",
            () -> new ModArmorItem(ModArmorMaterials.ANCIENTITE_SANCTUM, ModArmorItem.Type.HELMET, new Item.Properties()));
    public static final RegistryObject<Item> ANCIENTITE_SANCTUM_CHESTPLATE =ITEMS.register("ancientite_sanctum_chestplate",
            () -> new ModArmorItem(ModArmorMaterials.ANCIENTITE_SANCTUM, ModArmorItem.Type.CHESTPLATE, new Item.Properties()));
    public static final RegistryObject<Item> ANCIENTITE_SANCTUM_LEGGINGS =ITEMS.register("ancientite_sanctum_leggings",
            () -> new ModArmorItem(ModArmorMaterials.ANCIENTITE_SANCTUM, ModArmorItem.Type.LEGGINGS, new Item.Properties()));
    public static final RegistryObject<Item> ANCIENTITE_SANCTUM_BOOTS =ITEMS.register("ancientite_sanctum_boots",
            () -> new ModArmorItem(ModArmorMaterials.ANCIENTITE_SANCTUM, ModArmorItem.Type.BOOTS, new Item.Properties()));

    public static final RegistryObject<Item> ANCIENTITE_ACCORD_HELMET =ITEMS.register("ancientite_accord_helmet",
            () -> new ModArmorItem(ModArmorMaterials.ANCIENTITE_ACCORD, ModArmorItem.Type.HELMET, new Item.Properties()));
    public static final RegistryObject<Item> ANCIENTITE_ACCORD_CHESTPLATE =ITEMS.register("ancientite_accord_chestplate",
            () -> new TranslucentArmor(ModArmorMaterials.ANCIENTITE_ACCORD, ModArmorItem.Type.CHESTPLATE, new Item.Properties()));
    public static final RegistryObject<Item> ANCIENTITE_ACCORD_LEGGINGS =ITEMS.register("ancientite_accord_leggings",
            () -> new TranslucentArmor(ModArmorMaterials.ANCIENTITE_ACCORD, ModArmorItem.Type.LEGGINGS, new Item.Properties()));
    public static final RegistryObject<Item> ANCIENTITE_ACCORD_BOOTS =ITEMS.register("ancientite_accord_boots",
            () -> new TranslucentArmor(ModArmorMaterials.ANCIENTITE_ACCORD, ModArmorItem.Type.BOOTS, new Item.Properties()));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }


}
