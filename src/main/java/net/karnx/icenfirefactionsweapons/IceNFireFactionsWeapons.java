package net.karnx.icenfirefactionsweapons;

import com.mojang.logging.LogUtils;
import net.karnx.icenfirefactionsweapons.block.ModBlocks;
import net.karnx.icenfirefactionsweapons.item.ModItems;
import net.karnx.icenfirefactionsweapons.item.factitems.AncientStaff;
import net.karnx.icenfirefactionsweapons.item.factitems.AncientStaffHandler;
import net.karnx.icenfirefactionsweapons.item.factitems.AnnihilatorRayHandler;
import net.karnx.icenfirefactionsweapons.util.AncientBossHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(IceNFireFactionsWeapons.MODID)
public class IceNFireFactionsWeapons
{

    public static final String MODID = "icenfirefactionsweapons";

    private static final Logger LOGGER = LogUtils.getLogger();


    public IceNFireFactionsWeapons(FMLJavaModLoadingContext context)
    {
        IEventBus modEventBus = context.getModEventBus();
        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);
        modEventBus.addListener(this::commonSetup);
        MinecraftForge.EVENT_BUS.register(new AncientBossHandler());
        MinecraftForge.EVENT_BUS.register(new AnnihilatorRayHandler());
        MinecraftForge.EVENT_BUS.register(new AncientStaffHandler());
        MinecraftForge.EVENT_BUS.register(this);

        modEventBus.addListener(this::addCreative);

    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
        LOGGER.info("HELLO FROM COMMON SETUP");

    }

    private void addCreative(BuildCreativeModeTabContentsEvent event)
    {
        if(event.getTabKey() == CreativeModeTabs.INGREDIENTS) {
            event.accept(ModItems.ANCIENTITE);
            event.accept(ModItems.ANCIENT_FANG);
            event.accept(ModItems.ANCIENT_BONE);
            event.accept(ModItems.ANCIENT_EYE);
            event.accept(ModItems.ANCIENT_SCALE);
            event.accept(ModItems.ANCIENT_HORN);


        } else if(event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS) {
            event.accept(ModBlocks.ANCIENTITE_BLOCK);
            event.accept(ModBlocks.ANCIENT_VAULT);

        } if(event.getTabKey() == CreativeModeTabs.COMBAT) {
            event.accept(ModItems.ANCIENTITE_BOOTS);
            event.accept(ModItems.ANCIENTITE_CHESTPLATE);
            event.accept(ModItems.ANCIENTITE_LEGGINGS);
            event.accept(ModItems.ANCIENTITE_HELMET);

            event.accept(ModItems.ANCIENTITE_SLAYER_BOOTS);
            event.accept(ModItems.ANCIENTITE_SLAYER_CHESTPLATE);
            event.accept(ModItems.ANCIENTITE_SLAYER_LEGGINGS);
            event.accept(ModItems.ANCIENTITE_SLAYER_HELMET);

            event.accept(ModItems.ANCIENTITE_TAMER_BOOTS);
            event.accept(ModItems.ANCIENTITE_TAMER_CHESTPLATE);
            event.accept(ModItems.ANCIENTITE_TAMER_LEGGINGS);
            event.accept(ModItems.ANCIENTITE_TAMER_HELMET);

            event.accept(ModItems.ANCIENTITE_SANCTUM_BOOTS);
            event.accept(ModItems.ANCIENTITE_SANCTUM_CHESTPLATE);
            event.accept(ModItems.ANCIENTITE_SANCTUM_LEGGINGS);
            event.accept(ModItems.ANCIENTITE_SANCTUM_HELMET);

            event.accept(ModItems.ANCIENTITE_ACCORD_BOOTS);
            event.accept(ModItems.ANCIENTITE_ACCORD_CHESTPLATE);
            event.accept(ModItems.ANCIENTITE_ACCORD_LEGGINGS);
            event.accept(ModItems.ANCIENTITE_ACCORD_HELMET);


            event.accept(ModItems.DRAGONSLAYER_BLADE);
            event.accept(ModItems.COMMANDER_STAFF);
            event.accept(ModItems.VAULT_KEY);
            event.accept(ModItems.ANNIHILATOR_RAY);
            event.accept(ModItems.ANCIENT_STAFF);


        }else if(event.getTabKey() == CreativeModeTabs.NATURAL_BLOCKS) {
            event.accept(ModBlocks.ANCIENTITE_ORE);

    }}

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {

        LOGGER.info("HELLO from server starting");
    }

    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {

            LOGGER.info("HELLO FROM CLIENT SETUP");
            LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
        }
    }
}
