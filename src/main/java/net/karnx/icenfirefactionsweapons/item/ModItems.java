package net.karnx.icenfirefactionsweapons.item;

import net.karnx.icenfirefactionsweapons.IceNFireFactionsWeapons;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, IceNFireFactionsWeapons.MODID);

    public static final RegistryObject<Item> ANCIENTINE = ITEMS.register("ancientine", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> ANCIENT_SCALE = ITEMS.register("ancient_scale", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> ANCIENT_FANG = ITEMS.register("ancient_fang", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> ANCIENT_BONE = ITEMS.register("ancient_bone", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> ANCIENT_EYE = ITEMS.register("ancient_eye", () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> ANCIENT_HORN = ITEMS.register("ancient_horn", () -> new Item(new Item.Properties()));
    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }


}
