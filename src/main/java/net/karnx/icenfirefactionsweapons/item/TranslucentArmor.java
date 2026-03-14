package net.karnx.icenfirefactionsweapons.item;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;


import java.util.function.Consumer;

public class TranslucentArmor extends ArmorItem {
    public TranslucentArmor(ArmorMaterial p_40386_, Type p_266831_, Properties p_40388_) {
        super(p_40386_, p_266831_, p_40388_);
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            @Override
            public HumanoidModel<?> getHumanoidArmorModel(LivingEntity entity, ItemStack stack, EquipmentSlot slot, HumanoidModel<?> defaultModel) {
                ModelPart root = Minecraft.getInstance().getEntityModels().bakeLayer(
                        slot == EquipmentSlot.LEGS ? ModelLayers.PLAYER_INNER_ARMOR : ModelLayers.PLAYER_OUTER_ARMOR
                );
                return new HumanoidModel<>(root) {
                    @Override
                    public void renderToBuffer(PoseStack pose, VertexConsumer ignored, int light, int overlay, float r, float g, float b, float a) {
                        ResourceLocation texture = new ResourceLocation("icenfirefactionsweapons",
                                "textures/models/armor/ancientite_accord_layer_" +
                                        (slot == EquipmentSlot.LEGS ? "2" : "1") + ".png");
                        VertexConsumer translucent = Minecraft.getInstance().renderBuffers()
                                .bufferSource()
                                .getBuffer(RenderType.entityTranslucent(texture));
                        super.renderToBuffer(pose, translucent, light, overlay, r, g, b, 0.6f);
                    }
                };
            }
        });
    }
}
