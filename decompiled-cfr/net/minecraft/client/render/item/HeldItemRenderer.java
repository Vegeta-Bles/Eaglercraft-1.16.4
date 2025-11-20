/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.MoreObjects
 */
package net.minecraft.client.render.item;

import com.google.common.base.MoreObjects;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.FilledMapItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.map.MapState;
import net.minecraft.util.Arm;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Matrix4f;

public class HeldItemRenderer {
    private static final RenderLayer MAP_BACKGROUND = RenderLayer.getText(new Identifier("textures/map/map_background.png"));
    private static final RenderLayer MAP_BACKGROUND_CHECKERBOARD = RenderLayer.getText(new Identifier("textures/map/map_background_checkerboard.png"));
    private final MinecraftClient client;
    private ItemStack mainHand = ItemStack.EMPTY;
    private ItemStack offHand = ItemStack.EMPTY;
    private float equipProgressMainHand;
    private float prevEquipProgressMainHand;
    private float equipProgressOffHand;
    private float prevEquipProgressOffHand;
    private final EntityRenderDispatcher renderManager;
    private final ItemRenderer itemRenderer;

    public HeldItemRenderer(MinecraftClient client) {
        this.client = client;
        this.renderManager = client.getEntityRenderDispatcher();
        this.itemRenderer = client.getItemRenderer();
    }

    public void renderItem(LivingEntity entity, ItemStack stack, ModelTransformation.Mode renderMode, boolean leftHanded, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        if (stack.isEmpty()) {
            return;
        }
        this.itemRenderer.renderItem(entity, stack, renderMode, leftHanded, matrices, vertexConsumers, entity.world, light, OverlayTexture.DEFAULT_UV);
    }

    private float getMapAngle(float tickDelta) {
        float f = 1.0f - tickDelta / 45.0f + 0.1f;
        f = MathHelper.clamp(f, 0.0f, 1.0f);
        f = -MathHelper.cos(f * (float)Math.PI) * 0.5f + 0.5f;
        return f;
    }

    private void renderArm(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, Arm arm) {
        this.client.getTextureManager().bindTexture(this.client.player.getSkinTexture());
        PlayerEntityRenderer playerEntityRenderer = (PlayerEntityRenderer)this.renderManager.getRenderer(this.client.player);
        matrices.push();
        float _snowman2 = arm == Arm.RIGHT ? 1.0f : -1.0f;
        matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(92.0f));
        matrices.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(45.0f));
        matrices.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion(_snowman2 * -41.0f));
        matrices.translate(_snowman2 * 0.3f, -1.1f, 0.45f);
        if (arm == Arm.RIGHT) {
            playerEntityRenderer.renderRightArm(matrices, vertexConsumers, light, this.client.player);
        } else {
            playerEntityRenderer.renderLeftArm(matrices, vertexConsumers, light, this.client.player);
        }
        matrices.pop();
    }

    private void renderMapInOneHand(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, float equipProgress, Arm arm, float swingProgress, ItemStack stack) {
        float f = arm == Arm.RIGHT ? 1.0f : -1.0f;
        matrices.translate(f * 0.125f, -0.125, 0.0);
        if (!this.client.player.isInvisible()) {
            matrices.push();
            matrices.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion(f * 10.0f));
            this.renderArmHoldingItem(matrices, vertexConsumers, light, equipProgress, swingProgress, arm);
            matrices.pop();
        }
        matrices.push();
        matrices.translate(f * 0.51f, -0.08f + equipProgress * -1.2f, -0.75);
        _snowman = MathHelper.sqrt(swingProgress);
        _snowman = MathHelper.sin(_snowman * (float)Math.PI);
        _snowman = -0.5f * _snowman;
        _snowman = 0.4f * MathHelper.sin(_snowman * ((float)Math.PI * 2));
        _snowman = -0.3f * MathHelper.sin(swingProgress * (float)Math.PI);
        matrices.translate(f * _snowman, _snowman - 0.3f * _snowman, _snowman);
        matrices.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(_snowman * -45.0f));
        matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(f * _snowman * -30.0f));
        this.renderFirstPersonMap(matrices, vertexConsumers, light, stack);
        matrices.pop();
    }

    private void renderMapInBothHands(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, float pitch, float equipProgress, float swingProgress) {
        float f = MathHelper.sqrt(swingProgress);
        _snowman = -0.2f * MathHelper.sin(swingProgress * (float)Math.PI);
        _snowman = -0.4f * MathHelper.sin(f * (float)Math.PI);
        matrices.translate(0.0, -_snowman / 2.0f, _snowman);
        _snowman = this.getMapAngle(pitch);
        matrices.translate(0.0, 0.04f + equipProgress * -1.2f + _snowman * -0.5f, -0.72f);
        matrices.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(_snowman * -85.0f));
        if (!this.client.player.isInvisible()) {
            matrices.push();
            matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(90.0f));
            this.renderArm(matrices, vertexConsumers, light, Arm.RIGHT);
            this.renderArm(matrices, vertexConsumers, light, Arm.LEFT);
            matrices.pop();
        }
        _snowman = MathHelper.sin(f * (float)Math.PI);
        matrices.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(_snowman * 20.0f));
        matrices.scale(2.0f, 2.0f, 2.0f);
        this.renderFirstPersonMap(matrices, vertexConsumers, light, this.mainHand);
    }

    private void renderFirstPersonMap(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int swingProgress, ItemStack stack) {
        matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(180.0f));
        matrices.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion(180.0f));
        matrices.scale(0.38f, 0.38f, 0.38f);
        matrices.translate(-0.5, -0.5, 0.0);
        matrices.scale(0.0078125f, 0.0078125f, 0.0078125f);
        MapState mapState = FilledMapItem.getOrCreateMapState(stack, this.client.world);
        VertexConsumer _snowman2 = vertexConsumers.getBuffer(mapState == null ? MAP_BACKGROUND : MAP_BACKGROUND_CHECKERBOARD);
        Matrix4f _snowman3 = matrices.peek().getModel();
        _snowman2.vertex(_snowman3, -7.0f, 135.0f, 0.0f).color(255, 255, 255, 255).texture(0.0f, 1.0f).light(swingProgress).next();
        _snowman2.vertex(_snowman3, 135.0f, 135.0f, 0.0f).color(255, 255, 255, 255).texture(1.0f, 1.0f).light(swingProgress).next();
        _snowman2.vertex(_snowman3, 135.0f, -7.0f, 0.0f).color(255, 255, 255, 255).texture(1.0f, 0.0f).light(swingProgress).next();
        _snowman2.vertex(_snowman3, -7.0f, -7.0f, 0.0f).color(255, 255, 255, 255).texture(0.0f, 0.0f).light(swingProgress).next();
        if (mapState != null) {
            this.client.gameRenderer.getMapRenderer().draw(matrices, vertexConsumers, mapState, false, swingProgress);
        }
    }

    private void renderArmHoldingItem(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, float equipProgress, float swingProgress, Arm arm) {
        boolean bl = arm != Arm.LEFT;
        float _snowman2 = bl ? 1.0f : -1.0f;
        float _snowman3 = MathHelper.sqrt(swingProgress);
        float _snowman4 = -0.3f * MathHelper.sin(_snowman3 * (float)Math.PI);
        float _snowman5 = 0.4f * MathHelper.sin(_snowman3 * ((float)Math.PI * 2));
        float _snowman6 = -0.4f * MathHelper.sin(swingProgress * (float)Math.PI);
        matrices.translate(_snowman2 * (_snowman4 + 0.64000005f), _snowman5 + -0.6f + equipProgress * -0.6f, _snowman6 + -0.71999997f);
        matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(_snowman2 * 45.0f));
        float _snowman7 = MathHelper.sin(swingProgress * swingProgress * (float)Math.PI);
        float _snowman8 = MathHelper.sin(_snowman3 * (float)Math.PI);
        matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(_snowman2 * _snowman8 * 70.0f));
        matrices.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion(_snowman2 * _snowman7 * -20.0f));
        ClientPlayerEntity _snowman9 = this.client.player;
        this.client.getTextureManager().bindTexture(_snowman9.getSkinTexture());
        matrices.translate(_snowman2 * -1.0f, 3.6f, 3.5);
        matrices.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion(_snowman2 * 120.0f));
        matrices.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(200.0f));
        matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(_snowman2 * -135.0f));
        matrices.translate(_snowman2 * 5.6f, 0.0, 0.0);
        PlayerEntityRenderer _snowman10 = (PlayerEntityRenderer)this.renderManager.getRenderer(_snowman9);
        if (bl) {
            _snowman10.renderRightArm(matrices, vertexConsumers, light, _snowman9);
        } else {
            _snowman10.renderLeftArm(matrices, vertexConsumers, light, _snowman9);
        }
    }

    private void applyEatOrDrinkTransformation(MatrixStack matrices, float tickDelta, Arm arm, ItemStack stack) {
        float f = (float)this.client.player.getItemUseTimeLeft() - tickDelta + 1.0f;
        _snowman = f / (float)stack.getMaxUseTime();
        if (_snowman < 0.8f) {
            _snowman = MathHelper.abs(MathHelper.cos(f / 4.0f * (float)Math.PI) * 0.1f);
            matrices.translate(0.0, _snowman, 0.0);
        }
        _snowman = 1.0f - (float)Math.pow(_snowman, 27.0);
        int _snowman2 = arm == Arm.RIGHT ? 1 : -1;
        matrices.translate(_snowman * 0.6f * (float)_snowman2, _snowman * -0.5f, _snowman * 0.0f);
        matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion((float)_snowman2 * _snowman * 90.0f));
        matrices.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(_snowman * 10.0f));
        matrices.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion((float)_snowman2 * _snowman * 30.0f));
    }

    private void applySwingOffset(MatrixStack matrices, Arm arm, float swingProgress) {
        int n = arm == Arm.RIGHT ? 1 : -1;
        float _snowman2 = MathHelper.sin(swingProgress * swingProgress * (float)Math.PI);
        matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion((float)n * (45.0f + _snowman2 * -20.0f)));
        float _snowman3 = MathHelper.sin(MathHelper.sqrt(swingProgress) * (float)Math.PI);
        matrices.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion((float)n * _snowman3 * -20.0f));
        matrices.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(_snowman3 * -80.0f));
        matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion((float)n * -45.0f));
    }

    private void applyEquipOffset(MatrixStack matrices, Arm arm, float equipProgress) {
        int n = arm == Arm.RIGHT ? 1 : -1;
        matrices.translate((float)n * 0.56f, -0.52f + equipProgress * -0.6f, -0.72f);
    }

    public void renderItem(float tickDelta, MatrixStack matrices, VertexConsumerProvider.Immediate vertexConsumers, ClientPlayerEntity player, int light) {
        ItemStack itemStack;
        float f = player.getHandSwingProgress(tickDelta);
        Hand _snowman2 = (Hand)((Object)MoreObjects.firstNonNull((Object)((Object)player.preferredHand), (Object)((Object)Hand.MAIN_HAND)));
        _snowman = MathHelper.lerp(tickDelta, player.prevPitch, player.pitch);
        boolean _snowman3 = true;
        boolean _snowman4 = true;
        if (player.isUsingItem()) {
            itemStack = player.getActiveItem();
            if (itemStack.getItem() == Items.BOW || itemStack.getItem() == Items.CROSSBOW) {
                _snowman3 = player.getActiveHand() == Hand.MAIN_HAND;
                boolean bl = _snowman4 = !_snowman3;
            }
            if ((_snowman5 = player.getActiveHand()) == Hand.MAIN_HAND && (_snowman = player.getOffHandStack()).getItem() == Items.CROSSBOW && CrossbowItem.isCharged(_snowman)) {
                _snowman4 = false;
            }
        } else {
            itemStack = player.getMainHandStack();
            Object _snowman5 = player.getOffHandStack();
            if (itemStack.getItem() == Items.CROSSBOW && CrossbowItem.isCharged(itemStack)) {
                boolean bl = _snowman4 = !_snowman3;
            }
            if (_snowman5.getItem() == Items.CROSSBOW && CrossbowItem.isCharged(_snowman5)) {
                _snowman3 = !itemStack.isEmpty();
                _snowman4 = !_snowman3;
            }
        }
        float f2 = MathHelper.lerp(tickDelta, player.lastRenderPitch, player.renderPitch);
        _snowman = MathHelper.lerp(tickDelta, player.lastRenderYaw, player.renderYaw);
        matrices.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion((player.getPitch(tickDelta) - f2) * 0.1f));
        matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion((player.getYaw(tickDelta) - _snowman) * 0.1f));
        if (_snowman3) {
            _snowman = _snowman2 == Hand.MAIN_HAND ? f : 0.0f;
            _snowman = 1.0f - MathHelper.lerp(tickDelta, this.prevEquipProgressMainHand, this.equipProgressMainHand);
            this.renderFirstPersonItem(player, tickDelta, _snowman, Hand.MAIN_HAND, _snowman, this.mainHand, _snowman, matrices, vertexConsumers, light);
        }
        if (_snowman4) {
            _snowman = _snowman2 == Hand.OFF_HAND ? f : 0.0f;
            _snowman = 1.0f - MathHelper.lerp(tickDelta, this.prevEquipProgressOffHand, this.equipProgressOffHand);
            this.renderFirstPersonItem(player, tickDelta, _snowman, Hand.OFF_HAND, _snowman, this.offHand, _snowman, matrices, vertexConsumers, light);
        }
        vertexConsumers.draw();
    }

    private void renderFirstPersonItem(AbstractClientPlayerEntity player, float tickDelta, float pitch, Hand hand, float swingProgress, ItemStack item, float equipProgress, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        boolean bl = hand == Hand.MAIN_HAND;
        Arm _snowman2 = bl ? player.getMainArm() : player.getMainArm().getOpposite();
        matrices.push();
        if (item.isEmpty()) {
            if (bl && !player.isInvisible()) {
                this.renderArmHoldingItem(matrices, vertexConsumers, light, equipProgress, swingProgress, _snowman2);
            }
        } else if (item.getItem() == Items.FILLED_MAP) {
            if (bl && this.offHand.isEmpty()) {
                this.renderMapInBothHands(matrices, vertexConsumers, light, pitch, equipProgress, swingProgress);
            } else {
                this.renderMapInOneHand(matrices, vertexConsumers, light, equipProgress, _snowman2, swingProgress, item);
            }
        } else if (item.getItem() == Items.CROSSBOW) {
            boolean bl2;
            _snowman = CrossbowItem.isCharged(item);
            bl2 = _snowman2 == Arm.RIGHT;
            int n = _snowman = bl2 ? 1 : -1;
            if (player.isUsingItem() && player.getItemUseTimeLeft() > 0 && player.getActiveHand() == hand) {
                this.applyEquipOffset(matrices, _snowman2, equipProgress);
                matrices.translate((float)_snowman * -0.4785682f, -0.094387f, 0.05731530860066414);
                matrices.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(-11.935f));
                matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion((float)_snowman * 65.3f));
                matrices.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion((float)_snowman * -9.785f));
                float f = (float)item.getMaxUseTime() - ((float)this.client.player.getItemUseTimeLeft() - tickDelta + 1.0f);
                _snowman = f / (float)CrossbowItem.getPullTime(item);
                if (_snowman > 1.0f) {
                    _snowman = 1.0f;
                }
                if (_snowman > 0.1f) {
                    _snowman = MathHelper.sin((f - 0.1f) * 1.3f);
                    _snowman = _snowman - 0.1f;
                    _snowman = _snowman * _snowman;
                    matrices.translate(_snowman * 0.0f, _snowman * 0.004f, _snowman * 0.0f);
                }
                matrices.translate(_snowman * 0.0f, _snowman * 0.0f, _snowman * 0.04f);
                matrices.scale(1.0f, 1.0f, 1.0f + _snowman * 0.2f);
                matrices.multiply(Vector3f.NEGATIVE_Y.getDegreesQuaternion((float)_snowman * 45.0f));
            } else {
                float f = -0.4f * MathHelper.sin(MathHelper.sqrt(swingProgress) * (float)Math.PI);
                _snowman = 0.2f * MathHelper.sin(MathHelper.sqrt(swingProgress) * ((float)Math.PI * 2));
                _snowman = -0.2f * MathHelper.sin(swingProgress * (float)Math.PI);
                matrices.translate((float)_snowman * f, _snowman, _snowman);
                this.applyEquipOffset(matrices, _snowman2, equipProgress);
                this.applySwingOffset(matrices, _snowman2, swingProgress);
                if (_snowman && swingProgress < 0.001f) {
                    matrices.translate((float)_snowman * -0.641864f, 0.0, 0.0);
                    matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion((float)_snowman * 10.0f));
                }
            }
            this.renderItem(player, item, bl2 ? ModelTransformation.Mode.FIRST_PERSON_RIGHT_HAND : ModelTransformation.Mode.FIRST_PERSON_LEFT_HAND, !bl2, matrices, vertexConsumers, light);
        } else {
            boolean bl3;
            boolean bl4 = bl3 = _snowman2 == Arm.RIGHT;
            if (player.isUsingItem() && player.getItemUseTimeLeft() > 0 && player.getActiveHand() == hand) {
                int n = bl3 ? 1 : -1;
                switch (item.getUseAction()) {
                    case NONE: {
                        this.applyEquipOffset(matrices, _snowman2, equipProgress);
                        break;
                    }
                    case EAT: 
                    case DRINK: {
                        this.applyEatOrDrinkTransformation(matrices, tickDelta, _snowman2, item);
                        this.applyEquipOffset(matrices, _snowman2, equipProgress);
                        break;
                    }
                    case BLOCK: {
                        this.applyEquipOffset(matrices, _snowman2, equipProgress);
                        break;
                    }
                    case BOW: {
                        this.applyEquipOffset(matrices, _snowman2, equipProgress);
                        matrices.translate((float)n * -0.2785682f, 0.18344387412071228, 0.15731531381607056);
                        matrices.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(-13.935f));
                        matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion((float)n * 35.3f));
                        matrices.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion((float)n * -9.785f));
                        float f = (float)item.getMaxUseTime() - ((float)this.client.player.getItemUseTimeLeft() - tickDelta + 1.0f);
                        _snowman = f / 20.0f;
                        _snowman = (_snowman * _snowman + _snowman * 2.0f) / 3.0f;
                        if (_snowman > 1.0f) {
                            _snowman = 1.0f;
                        }
                        if (_snowman > 0.1f) {
                            _snowman = MathHelper.sin((f - 0.1f) * 1.3f);
                            _snowman = _snowman - 0.1f;
                            _snowman = _snowman * _snowman;
                            matrices.translate(_snowman * 0.0f, _snowman * 0.004f, _snowman * 0.0f);
                        }
                        matrices.translate(_snowman * 0.0f, _snowman * 0.0f, _snowman * 0.04f);
                        matrices.scale(1.0f, 1.0f, 1.0f + _snowman * 0.2f);
                        matrices.multiply(Vector3f.NEGATIVE_Y.getDegreesQuaternion((float)n * 45.0f));
                        break;
                    }
                    case SPEAR: {
                        this.applyEquipOffset(matrices, _snowman2, equipProgress);
                        matrices.translate((float)n * -0.5f, 0.7f, 0.1f);
                        matrices.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(-55.0f));
                        matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion((float)n * 35.3f));
                        matrices.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion((float)n * -9.785f));
                        float _snowman3 = (float)item.getMaxUseTime() - ((float)this.client.player.getItemUseTimeLeft() - tickDelta + 1.0f);
                        float _snowman4 = _snowman3 / 10.0f;
                        if (_snowman4 > 1.0f) {
                            _snowman4 = 1.0f;
                        }
                        if (_snowman4 > 0.1f) {
                            float f = MathHelper.sin((_snowman3 - 0.1f) * 1.3f);
                            _snowman = _snowman4 - 0.1f;
                            _snowman = f * _snowman;
                            matrices.translate(_snowman * 0.0f, _snowman * 0.004f, _snowman * 0.0f);
                        }
                        matrices.translate(0.0, 0.0, _snowman4 * 0.2f);
                        matrices.scale(1.0f, 1.0f, 1.0f + _snowman4 * 0.2f);
                        matrices.multiply(Vector3f.NEGATIVE_Y.getDegreesQuaternion((float)n * 45.0f));
                        break;
                    }
                }
            } else if (player.isUsingRiptide()) {
                this.applyEquipOffset(matrices, _snowman2, equipProgress);
                int _snowman5 = bl3 ? 1 : -1;
                matrices.translate((float)_snowman5 * -0.4f, 0.8f, 0.3f);
                matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion((float)_snowman5 * 65.0f));
                matrices.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion((float)_snowman5 * -85.0f));
            } else {
                float _snowman6 = -0.4f * MathHelper.sin(MathHelper.sqrt(swingProgress) * (float)Math.PI);
                float _snowman7 = 0.2f * MathHelper.sin(MathHelper.sqrt(swingProgress) * ((float)Math.PI * 2));
                float _snowman8 = -0.2f * MathHelper.sin(swingProgress * (float)Math.PI);
                int _snowman9 = bl3 ? 1 : -1;
                matrices.translate((float)_snowman9 * _snowman6, _snowman7, _snowman8);
                this.applyEquipOffset(matrices, _snowman2, equipProgress);
                this.applySwingOffset(matrices, _snowman2, swingProgress);
            }
            this.renderItem(player, item, bl3 ? ModelTransformation.Mode.FIRST_PERSON_RIGHT_HAND : ModelTransformation.Mode.FIRST_PERSON_LEFT_HAND, !bl3, matrices, vertexConsumers, light);
        }
        matrices.pop();
    }

    public void updateHeldItems() {
        this.prevEquipProgressMainHand = this.equipProgressMainHand;
        this.prevEquipProgressOffHand = this.equipProgressOffHand;
        ClientPlayerEntity clientPlayerEntity = this.client.player;
        ItemStack _snowman2 = clientPlayerEntity.getMainHandStack();
        ItemStack _snowman3 = clientPlayerEntity.getOffHandStack();
        if (ItemStack.areEqual(this.mainHand, _snowman2)) {
            this.mainHand = _snowman2;
        }
        if (ItemStack.areEqual(this.offHand, _snowman3)) {
            this.offHand = _snowman3;
        }
        if (clientPlayerEntity.isRiding()) {
            this.equipProgressMainHand = MathHelper.clamp(this.equipProgressMainHand - 0.4f, 0.0f, 1.0f);
            this.equipProgressOffHand = MathHelper.clamp(this.equipProgressOffHand - 0.4f, 0.0f, 1.0f);
        } else {
            float f = clientPlayerEntity.getAttackCooldownProgress(1.0f);
            this.equipProgressMainHand += MathHelper.clamp((this.mainHand == _snowman2 ? f * f * f : 0.0f) - this.equipProgressMainHand, -0.4f, 0.4f);
            this.equipProgressOffHand += MathHelper.clamp((float)(this.offHand == _snowman3 ? 1 : 0) - this.equipProgressOffHand, -0.4f, 0.4f);
        }
        if (this.equipProgressMainHand < 0.1f) {
            this.mainHand = _snowman2;
        }
        if (this.equipProgressOffHand < 0.1f) {
            this.offHand = _snowman3;
        }
    }

    public void resetEquipProgress(Hand hand) {
        if (hand == Hand.MAIN_HAND) {
            this.equipProgressMainHand = 0.0f;
        } else {
            this.equipProgressOffHand = 0.0f;
        }
    }
}

