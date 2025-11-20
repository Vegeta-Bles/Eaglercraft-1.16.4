package net.minecraft.client.render.block.entity;

import com.google.common.collect.Maps;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import com.mojang.authlib.minecraft.MinecraftProfileTexture.Type;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.block.AbstractSkullBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.SkullBlock;
import net.minecraft.block.WallSkullBlock;
import net.minecraft.block.entity.SkullBlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.DragonHeadEntityModel;
import net.minecraft.client.render.entity.model.SkullEntityModel;
import net.minecraft.client.render.entity.model.SkullOverlayEntityModel;
import net.minecraft.client.util.DefaultSkinHelper;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.math.Direction;

public class SkullBlockEntityRenderer extends BlockEntityRenderer<SkullBlockEntity> {
   private static final Map<SkullBlock.SkullType, SkullEntityModel> MODELS = Util.make(Maps.newHashMap(), _snowman -> {
      SkullEntityModel _snowmanx = new SkullEntityModel(0, 0, 64, 32);
      SkullEntityModel _snowmanxx = new SkullOverlayEntityModel();
      DragonHeadEntityModel _snowmanxxx = new DragonHeadEntityModel(0.0F);
      _snowman.put(SkullBlock.Type.SKELETON, _snowmanx);
      _snowman.put(SkullBlock.Type.WITHER_SKELETON, _snowmanx);
      _snowman.put(SkullBlock.Type.PLAYER, _snowmanxx);
      _snowman.put(SkullBlock.Type.ZOMBIE, _snowmanxx);
      _snowman.put(SkullBlock.Type.CREEPER, _snowmanx);
      _snowman.put(SkullBlock.Type.DRAGON, _snowmanxxx);
   });
   private static final Map<SkullBlock.SkullType, Identifier> TEXTURES = Util.make(Maps.newHashMap(), _snowman -> {
      _snowman.put(SkullBlock.Type.SKELETON, new Identifier("textures/entity/skeleton/skeleton.png"));
      _snowman.put(SkullBlock.Type.WITHER_SKELETON, new Identifier("textures/entity/skeleton/wither_skeleton.png"));
      _snowman.put(SkullBlock.Type.ZOMBIE, new Identifier("textures/entity/zombie/zombie.png"));
      _snowman.put(SkullBlock.Type.CREEPER, new Identifier("textures/entity/creeper/creeper.png"));
      _snowman.put(SkullBlock.Type.DRAGON, new Identifier("textures/entity/enderdragon/dragon.png"));
      _snowman.put(SkullBlock.Type.PLAYER, DefaultSkinHelper.getTexture());
   });

   public SkullBlockEntityRenderer(BlockEntityRenderDispatcher _snowman) {
      super(_snowman);
   }

   public void render(SkullBlockEntity _snowman, float _snowman, MatrixStack _snowman, VertexConsumerProvider _snowman, int _snowman, int _snowman) {
      float _snowmanxxxxxx = _snowman.getTicksPowered(_snowman);
      BlockState _snowmanxxxxxxx = _snowman.getCachedState();
      boolean _snowmanxxxxxxxx = _snowmanxxxxxxx.getBlock() instanceof WallSkullBlock;
      Direction _snowmanxxxxxxxxx = _snowmanxxxxxxxx ? _snowmanxxxxxxx.get(WallSkullBlock.FACING) : null;
      float _snowmanxxxxxxxxxx = 22.5F * (float)(_snowmanxxxxxxxx ? (2 + _snowmanxxxxxxxxx.getHorizontal()) * 4 : _snowmanxxxxxxx.get(SkullBlock.ROTATION));
      render(_snowmanxxxxxxxxx, _snowmanxxxxxxxxxx, ((AbstractSkullBlock)_snowmanxxxxxxx.getBlock()).getSkullType(), _snowman.getOwner(), _snowmanxxxxxx, _snowman, _snowman, _snowman);
   }

   public static void render(
      @Nullable Direction _snowman, float _snowman, SkullBlock.SkullType _snowman, @Nullable GameProfile _snowman, float _snowman, MatrixStack _snowman, VertexConsumerProvider _snowman, int _snowman
   ) {
      SkullEntityModel _snowmanxxxxxxxx = MODELS.get(_snowman);
      _snowman.push();
      if (_snowman == null) {
         _snowman.translate(0.5, 0.0, 0.5);
      } else {
         float _snowmanxxxxxxxxx = 0.25F;
         _snowman.translate((double)(0.5F - (float)_snowman.getOffsetX() * 0.25F), 0.25, (double)(0.5F - (float)_snowman.getOffsetZ() * 0.25F));
      }

      _snowman.scale(-1.0F, -1.0F, 1.0F);
      VertexConsumer _snowmanxxxxxxxxx = _snowman.getBuffer(method_3578(_snowman, _snowman));
      _snowmanxxxxxxxx.method_2821(_snowman, _snowman, 0.0F);
      _snowmanxxxxxxxx.render(_snowman, _snowmanxxxxxxxxx, _snowman, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);
      _snowman.pop();
   }

   private static RenderLayer method_3578(SkullBlock.SkullType _snowman, @Nullable GameProfile _snowman) {
      Identifier _snowmanxx = TEXTURES.get(_snowman);
      if (_snowman == SkullBlock.Type.PLAYER && _snowman != null) {
         MinecraftClient _snowmanxxx = MinecraftClient.getInstance();
         Map<Type, MinecraftProfileTexture> _snowmanxxxx = _snowmanxxx.getSkinProvider().getTextures(_snowman);
         return _snowmanxxxx.containsKey(Type.SKIN)
            ? RenderLayer.getEntityTranslucent(_snowmanxxx.getSkinProvider().loadSkin(_snowmanxxxx.get(Type.SKIN), Type.SKIN))
            : RenderLayer.getEntityCutoutNoCull(DefaultSkinHelper.getTexture(PlayerEntity.getUuidFromProfile(_snowman)));
      } else {
         return RenderLayer.getEntityCutoutNoCullZOffset(_snowmanxx);
      }
   }
}
