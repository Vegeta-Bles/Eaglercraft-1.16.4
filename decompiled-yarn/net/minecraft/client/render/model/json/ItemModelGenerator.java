package net.minecraft.client.render.model.json;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mojang.datafixers.util.Either;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.util.math.Direction;

public class ItemModelGenerator {
   public static final List<String> LAYERS = Lists.newArrayList(new String[]{"layer0", "layer1", "layer2", "layer3", "layer4"});

   public ItemModelGenerator() {
   }

   public JsonUnbakedModel create(Function<SpriteIdentifier, Sprite> textureGetter, JsonUnbakedModel blockModel) {
      Map<String, Either<SpriteIdentifier, String>> _snowman = Maps.newHashMap();
      List<ModelElement> _snowmanx = Lists.newArrayList();

      for (int _snowmanxx = 0; _snowmanxx < LAYERS.size(); _snowmanxx++) {
         String _snowmanxxx = LAYERS.get(_snowmanxx);
         if (!blockModel.textureExists(_snowmanxxx)) {
            break;
         }

         SpriteIdentifier _snowmanxxxx = blockModel.resolveSprite(_snowmanxxx);
         _snowman.put(_snowmanxxx, Either.left(_snowmanxxxx));
         Sprite _snowmanxxxxx = textureGetter.apply(_snowmanxxxx);
         _snowmanx.addAll(this.addLayerElements(_snowmanxx, _snowmanxxx, _snowmanxxxxx));
      }

      _snowman.put("particle", blockModel.textureExists("particle") ? Either.left(blockModel.resolveSprite("particle")) : _snowman.get("layer0"));
      JsonUnbakedModel _snowmanxx = new JsonUnbakedModel(null, _snowmanx, _snowman, false, blockModel.getGuiLight(), blockModel.getTransformations(), blockModel.getOverrides());
      _snowmanxx.id = blockModel.id;
      return _snowmanxx;
   }

   private List<ModelElement> addLayerElements(int layer, String key, Sprite sprite) {
      Map<Direction, ModelElementFace> _snowman = Maps.newHashMap();
      _snowman.put(Direction.SOUTH, new ModelElementFace(null, layer, key, new ModelElementTexture(new float[]{0.0F, 0.0F, 16.0F, 16.0F}, 0)));
      _snowman.put(Direction.NORTH, new ModelElementFace(null, layer, key, new ModelElementTexture(new float[]{16.0F, 0.0F, 0.0F, 16.0F}, 0)));
      List<ModelElement> _snowmanx = Lists.newArrayList();
      _snowmanx.add(new ModelElement(new Vector3f(0.0F, 0.0F, 7.5F), new Vector3f(16.0F, 16.0F, 8.5F), _snowman, null, true));
      _snowmanx.addAll(this.addSubComponents(sprite, key, layer));
      return _snowmanx;
   }

   private List<ModelElement> addSubComponents(Sprite sprite, String key, int layer) {
      float _snowman = (float)sprite.getWidth();
      float _snowmanx = (float)sprite.getHeight();
      List<ModelElement> _snowmanxx = Lists.newArrayList();

      for (ItemModelGenerator.Frame _snowmanxxx : this.getFrames(sprite)) {
         float _snowmanxxxx = 0.0F;
         float _snowmanxxxxx = 0.0F;
         float _snowmanxxxxxx = 0.0F;
         float _snowmanxxxxxxx = 0.0F;
         float _snowmanxxxxxxxx = 0.0F;
         float _snowmanxxxxxxxxx = 0.0F;
         float _snowmanxxxxxxxxxx = 0.0F;
         float _snowmanxxxxxxxxxxx = 0.0F;
         float _snowmanxxxxxxxxxxxx = 16.0F / _snowman;
         float _snowmanxxxxxxxxxxxxx = 16.0F / _snowmanx;
         float _snowmanxxxxxxxxxxxxxx = (float)_snowmanxxx.getMin();
         float _snowmanxxxxxxxxxxxxxxx = (float)_snowmanxxx.getMax();
         float _snowmanxxxxxxxxxxxxxxxx = (float)_snowmanxxx.getLevel();
         ItemModelGenerator.Side _snowmanxxxxxxxxxxxxxxxxx = _snowmanxxx.getSide();
         switch (_snowmanxxxxxxxxxxxxxxxxx) {
            case UP:
               _snowmanxxxxxxxx = _snowmanxxxxxxxxxxxxxx;
               _snowmanxxxx = _snowmanxxxxxxxxxxxxxx;
               _snowmanxxxxxx = _snowmanxxxxxxxxx = _snowmanxxxxxxxxxxxxxxx + 1.0F;
               _snowmanxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxx;
               _snowmanxxxxx = _snowmanxxxxxxxxxxxxxxxx;
               _snowmanxxxxxxx = _snowmanxxxxxxxxxxxxxxxx;
               _snowmanxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxx + 1.0F;
               break;
            case DOWN:
               _snowmanxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxx;
               _snowmanxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxx + 1.0F;
               _snowmanxxxxxxxx = _snowmanxxxxxxxxxxxxxx;
               _snowmanxxxx = _snowmanxxxxxxxxxxxxxx;
               _snowmanxxxxxx = _snowmanxxxxxxxxx = _snowmanxxxxxxxxxxxxxxx + 1.0F;
               _snowmanxxxxx = _snowmanxxxxxxxxxxxxxxxx + 1.0F;
               _snowmanxxxxxxx = _snowmanxxxxxxxxxxxxxxxx + 1.0F;
               break;
            case LEFT:
               _snowmanxxxxxxxx = _snowmanxxxxxxxxxxxxxxxx;
               _snowmanxxxx = _snowmanxxxxxxxxxxxxxxxx;
               _snowmanxxxxxx = _snowmanxxxxxxxxxxxxxxxx;
               _snowmanxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxx + 1.0F;
               _snowmanxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxx;
               _snowmanxxxxx = _snowmanxxxxxxxxxxxxxx;
               _snowmanxxxxxxx = _snowmanxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxx + 1.0F;
               break;
            case RIGHT:
               _snowmanxxxxxxxx = _snowmanxxxxxxxxxxxxxxxx;
               _snowmanxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxx + 1.0F;
               _snowmanxxxx = _snowmanxxxxxxxxxxxxxxxx + 1.0F;
               _snowmanxxxxxx = _snowmanxxxxxxxxxxxxxxxx + 1.0F;
               _snowmanxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxx;
               _snowmanxxxxx = _snowmanxxxxxxxxxxxxxx;
               _snowmanxxxxxxx = _snowmanxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxx + 1.0F;
         }

         _snowmanxxxx *= _snowmanxxxxxxxxxxxx;
         _snowmanxxxxxx *= _snowmanxxxxxxxxxxxx;
         _snowmanxxxxx *= _snowmanxxxxxxxxxxxxx;
         _snowmanxxxxxxx *= _snowmanxxxxxxxxxxxxx;
         _snowmanxxxxx = 16.0F - _snowmanxxxxx;
         _snowmanxxxxxxx = 16.0F - _snowmanxxxxxxx;
         _snowmanxxxxxxxx *= _snowmanxxxxxxxxxxxx;
         _snowmanxxxxxxxxx *= _snowmanxxxxxxxxxxxx;
         _snowmanxxxxxxxxxx *= _snowmanxxxxxxxxxxxxx;
         _snowmanxxxxxxxxxxx *= _snowmanxxxxxxxxxxxxx;
         Map<Direction, ModelElementFace> _snowmanxxxx = Maps.newHashMap();
         _snowmanxxxx.put(
            _snowmanxxxxxxxxxxxxxxxxx.getDirection(),
            new ModelElementFace(null, layer, key, new ModelElementTexture(new float[]{_snowmanxxxxxxxx, _snowmanxxxxxxxxxx, _snowmanxxxxxxxxx, _snowmanxxxxxxxxxxx}, 0))
         );
         switch (_snowmanxxxxxxxxxxxxxxxxx) {
            case UP:
               _snowmanxx.add(new ModelElement(new Vector3f(_snowmanxxxx, _snowmanxxxxx, 7.5F), new Vector3f(_snowmanxxxxxx, _snowmanxxxxx, 8.5F), _snowmanxxxx, null, true));
               break;
            case DOWN:
               _snowmanxx.add(new ModelElement(new Vector3f(_snowmanxxxx, _snowmanxxxxxxx, 7.5F), new Vector3f(_snowmanxxxxxx, _snowmanxxxxxxx, 8.5F), _snowmanxxxx, null, true));
               break;
            case LEFT:
               _snowmanxx.add(new ModelElement(new Vector3f(_snowmanxxxx, _snowmanxxxxx, 7.5F), new Vector3f(_snowmanxxxx, _snowmanxxxxxxx, 8.5F), _snowmanxxxx, null, true));
               break;
            case RIGHT:
               _snowmanxx.add(new ModelElement(new Vector3f(_snowmanxxxxxx, _snowmanxxxxx, 7.5F), new Vector3f(_snowmanxxxxxx, _snowmanxxxxxxx, 8.5F), _snowmanxxxx, null, true));
         }
      }

      return _snowmanxx;
   }

   private List<ItemModelGenerator.Frame> getFrames(Sprite sprite) {
      int _snowman = sprite.getWidth();
      int _snowmanx = sprite.getHeight();
      List<ItemModelGenerator.Frame> _snowmanxx = Lists.newArrayList();

      for (int _snowmanxxx = 0; _snowmanxxx < sprite.getFrameCount(); _snowmanxxx++) {
         for (int _snowmanxxxx = 0; _snowmanxxxx < _snowmanx; _snowmanxxxx++) {
            for (int _snowmanxxxxx = 0; _snowmanxxxxx < _snowman; _snowmanxxxxx++) {
               boolean _snowmanxxxxxx = !this.isPixelTransparent(sprite, _snowmanxxx, _snowmanxxxxx, _snowmanxxxx, _snowman, _snowmanx);
               this.buildCube(ItemModelGenerator.Side.UP, _snowmanxx, sprite, _snowmanxxx, _snowmanxxxxx, _snowmanxxxx, _snowman, _snowmanx, _snowmanxxxxxx);
               this.buildCube(ItemModelGenerator.Side.DOWN, _snowmanxx, sprite, _snowmanxxx, _snowmanxxxxx, _snowmanxxxx, _snowman, _snowmanx, _snowmanxxxxxx);
               this.buildCube(ItemModelGenerator.Side.LEFT, _snowmanxx, sprite, _snowmanxxx, _snowmanxxxxx, _snowmanxxxx, _snowman, _snowmanx, _snowmanxxxxxx);
               this.buildCube(ItemModelGenerator.Side.RIGHT, _snowmanxx, sprite, _snowmanxxx, _snowmanxxxxx, _snowmanxxxx, _snowman, _snowmanx, _snowmanxxxxxx);
            }
         }
      }

      return _snowmanxx;
   }

   private void buildCube(ItemModelGenerator.Side _snowman, List<ItemModelGenerator.Frame> cubes, Sprite sprite, int frame, int x, int y, int _snowman, int _snowman, boolean _snowman) {
      boolean _snowmanxxxx = this.isPixelTransparent(sprite, frame, x + _snowman.getOffsetX(), y + _snowman.getOffsetY(), _snowman, _snowman) && _snowman;
      if (_snowmanxxxx) {
         this.buildCube(cubes, _snowman, x, y);
      }
   }

   private void buildCube(List<ItemModelGenerator.Frame> cubes, ItemModelGenerator.Side _snowman, int x, int y) {
      ItemModelGenerator.Frame _snowmanx = null;

      for (ItemModelGenerator.Frame _snowmanxx : cubes) {
         if (_snowmanxx.getSide() == _snowman) {
            int _snowmanxxx = _snowman.isVertical() ? y : x;
            if (_snowmanxx.getLevel() == _snowmanxxx) {
               _snowmanx = _snowmanxx;
               break;
            }
         }
      }

      int _snowmanxxx = _snowman.isVertical() ? y : x;
      int _snowmanxxxx = _snowman.isVertical() ? x : y;
      if (_snowmanx == null) {
         cubes.add(new ItemModelGenerator.Frame(_snowman, _snowmanxxxx, _snowmanxxx));
      } else {
         _snowmanx.expand(_snowmanxxxx);
      }
   }

   private boolean isPixelTransparent(Sprite sprite, int frame, int x, int y, int _snowman, int _snowman) {
      return x >= 0 && y >= 0 && x < _snowman && y < _snowman ? sprite.isPixelTransparent(frame, x, y) : true;
   }

   static class Frame {
      private final ItemModelGenerator.Side side;
      private int min;
      private int max;
      private final int level;

      public Frame(ItemModelGenerator.Side _snowman, int width, int depth) {
         this.side = _snowman;
         this.min = width;
         this.max = width;
         this.level = depth;
      }

      public void expand(int newValue) {
         if (newValue < this.min) {
            this.min = newValue;
         } else if (newValue > this.max) {
            this.max = newValue;
         }
      }

      public ItemModelGenerator.Side getSide() {
         return this.side;
      }

      public int getMin() {
         return this.min;
      }

      public int getMax() {
         return this.max;
      }

      public int getLevel() {
         return this.level;
      }
   }

   static enum Side {
      UP(Direction.UP, 0, -1),
      DOWN(Direction.DOWN, 0, 1),
      LEFT(Direction.EAST, -1, 0),
      RIGHT(Direction.WEST, 1, 0);

      private final Direction direction;
      private final int offsetX;
      private final int offsetY;

      private Side(Direction direction, int offsetX, int offsetY) {
         this.direction = direction;
         this.offsetX = offsetX;
         this.offsetY = offsetY;
      }

      public Direction getDirection() {
         return this.direction;
      }

      public int getOffsetX() {
         return this.offsetX;
      }

      public int getOffsetY() {
         return this.offsetY;
      }

      private boolean isVertical() {
         return this == DOWN || this == UP;
      }
   }
}
