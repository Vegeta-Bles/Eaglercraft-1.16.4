package net.minecraft.client.render.entity;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.BlockRenderManager;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModelManager;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.entity.decoration.ItemFrameEntity;
import net.minecraft.item.FilledMapItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.map.MapState;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;

public class ItemFrameEntityRenderer extends EntityRenderer<ItemFrameEntity> {
   private static final ModelIdentifier NORMAL_FRAME = new ModelIdentifier("item_frame", "map=false");
   private static final ModelIdentifier MAP_FRAME = new ModelIdentifier("item_frame", "map=true");
   private final MinecraftClient client = MinecraftClient.getInstance();
   private final ItemRenderer itemRenderer;

   public ItemFrameEntityRenderer(EntityRenderDispatcher dispatcher, ItemRenderer itemRenderer) {
      super(dispatcher);
      this.itemRenderer = itemRenderer;
   }

   public void render(ItemFrameEntity _snowman, float _snowman, float _snowman, MatrixStack _snowman, VertexConsumerProvider _snowman, int _snowman) {
      super.render(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
      _snowman.push();
      Direction _snowmanxxxxxx = _snowman.getHorizontalFacing();
      Vec3d _snowmanxxxxxxx = this.getPositionOffset(_snowman, _snowman);
      _snowman.translate(-_snowmanxxxxxxx.getX(), -_snowmanxxxxxxx.getY(), -_snowmanxxxxxxx.getZ());
      double _snowmanxxxxxxxx = 0.46875;
      _snowman.translate((double)_snowmanxxxxxx.getOffsetX() * 0.46875, (double)_snowmanxxxxxx.getOffsetY() * 0.46875, (double)_snowmanxxxxxx.getOffsetZ() * 0.46875);
      _snowman.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(_snowman.pitch));
      _snowman.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(180.0F - _snowman.yaw));
      boolean _snowmanxxxxxxxxx = _snowman.isInvisible();
      if (!_snowmanxxxxxxxxx) {
         BlockRenderManager _snowmanxxxxxxxxxx = this.client.getBlockRenderManager();
         BakedModelManager _snowmanxxxxxxxxxxx = _snowmanxxxxxxxxxx.getModels().getModelManager();
         ModelIdentifier _snowmanxxxxxxxxxxxx = _snowman.getHeldItemStack().getItem() == Items.FILLED_MAP ? MAP_FRAME : NORMAL_FRAME;
         _snowman.push();
         _snowman.translate(-0.5, -0.5, -0.5);
         _snowmanxxxxxxxxxx.getModelRenderer()
            .render(
               _snowman.peek(),
               _snowman.getBuffer(TexturedRenderLayers.getEntitySolid()),
               null,
               _snowmanxxxxxxxxxxx.getModel(_snowmanxxxxxxxxxxxx),
               1.0F,
               1.0F,
               1.0F,
               _snowman,
               OverlayTexture.DEFAULT_UV
            );
         _snowman.pop();
      }

      ItemStack _snowmanxxxxxxxxxx = _snowman.getHeldItemStack();
      if (!_snowmanxxxxxxxxxx.isEmpty()) {
         boolean _snowmanxxxxxxxxxxx = _snowmanxxxxxxxxxx.getItem() == Items.FILLED_MAP;
         if (_snowmanxxxxxxxxx) {
            _snowman.translate(0.0, 0.0, 0.5);
         } else {
            _snowman.translate(0.0, 0.0, 0.4375);
         }

         int _snowmanxxxxxxxxxxxx = _snowmanxxxxxxxxxxx ? _snowman.getRotation() % 4 * 2 : _snowman.getRotation();
         _snowman.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion((float)_snowmanxxxxxxxxxxxx * 360.0F / 8.0F));
         if (_snowmanxxxxxxxxxxx) {
            _snowman.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion(180.0F));
            float _snowmanxxxxxxxxxxxxx = 0.0078125F;
            _snowman.scale(0.0078125F, 0.0078125F, 0.0078125F);
            _snowman.translate(-64.0, -64.0, 0.0);
            MapState _snowmanxxxxxxxxxxxxxx = FilledMapItem.getOrCreateMapState(_snowmanxxxxxxxxxx, _snowman.world);
            _snowman.translate(0.0, 0.0, -1.0);
            if (_snowmanxxxxxxxxxxxxxx != null) {
               this.client.gameRenderer.getMapRenderer().draw(_snowman, _snowman, _snowmanxxxxxxxxxxxxxx, true, _snowman);
            }
         } else {
            _snowman.scale(0.5F, 0.5F, 0.5F);
            this.itemRenderer.renderItem(_snowmanxxxxxxxxxx, ModelTransformation.Mode.FIXED, _snowman, OverlayTexture.DEFAULT_UV, _snowman, _snowman);
         }
      }

      _snowman.pop();
   }

   public Vec3d getPositionOffset(ItemFrameEntity _snowman, float _snowman) {
      return new Vec3d((double)((float)_snowman.getHorizontalFacing().getOffsetX() * 0.3F), -0.25, (double)((float)_snowman.getHorizontalFacing().getOffsetZ() * 0.3F));
   }

   public Identifier getTexture(ItemFrameEntity _snowman) {
      return SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE;
   }

   protected boolean hasLabel(ItemFrameEntity _snowman) {
      if (MinecraftClient.isHudEnabled() && !_snowman.getHeldItemStack().isEmpty() && _snowman.getHeldItemStack().hasCustomName() && this.dispatcher.targetedEntity == _snowman) {
         double _snowmanx = this.dispatcher.getSquaredDistanceToCamera(_snowman);
         float _snowmanxx = _snowman.isSneaky() ? 32.0F : 64.0F;
         return _snowmanx < (double)(_snowmanxx * _snowmanxx);
      } else {
         return false;
      }
   }

   protected void renderLabelIfPresent(ItemFrameEntity _snowman, Text _snowman, MatrixStack _snowman, VertexConsumerProvider _snowman, int _snowman) {
      super.renderLabelIfPresent(_snowman, _snowman.getHeldItemStack().getName(), _snowman, _snowman, _snowman);
   }
}
