package net.minecraft.client.resource;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mojang.datafixers.util.Pair;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.block.enums.ChestType;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.resource.ResourcePack;
import net.minecraft.resource.ResourceType;
import net.minecraft.resource.metadata.ResourceMetadataReader;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;

public class Format4ResourcePack implements ResourcePack {
   private static final Map<String, Pair<ChestType, Identifier>> NEW_TO_OLD_CHEST_TEXTURES = Util.make(Maps.newHashMap(), _snowman -> {
      _snowman.put("textures/entity/chest/normal_left.png", new Pair(ChestType.LEFT, new Identifier("textures/entity/chest/normal_double.png")));
      _snowman.put("textures/entity/chest/normal_right.png", new Pair(ChestType.RIGHT, new Identifier("textures/entity/chest/normal_double.png")));
      _snowman.put("textures/entity/chest/normal.png", new Pair(ChestType.SINGLE, new Identifier("textures/entity/chest/normal.png")));
      _snowman.put("textures/entity/chest/trapped_left.png", new Pair(ChestType.LEFT, new Identifier("textures/entity/chest/trapped_double.png")));
      _snowman.put("textures/entity/chest/trapped_right.png", new Pair(ChestType.RIGHT, new Identifier("textures/entity/chest/trapped_double.png")));
      _snowman.put("textures/entity/chest/trapped.png", new Pair(ChestType.SINGLE, new Identifier("textures/entity/chest/trapped.png")));
      _snowman.put("textures/entity/chest/christmas_left.png", new Pair(ChestType.LEFT, new Identifier("textures/entity/chest/christmas_double.png")));
      _snowman.put("textures/entity/chest/christmas_right.png", new Pair(ChestType.RIGHT, new Identifier("textures/entity/chest/christmas_double.png")));
      _snowman.put("textures/entity/chest/christmas.png", new Pair(ChestType.SINGLE, new Identifier("textures/entity/chest/christmas.png")));
      _snowman.put("textures/entity/chest/ender.png", new Pair(ChestType.SINGLE, new Identifier("textures/entity/chest/ender.png")));
   });
   private static final List<String> BANNER_PATTERN_TYPES = Lists.newArrayList(
      new String[]{
         "base",
         "border",
         "bricks",
         "circle",
         "creeper",
         "cross",
         "curly_border",
         "diagonal_left",
         "diagonal_right",
         "diagonal_up_left",
         "diagonal_up_right",
         "flower",
         "globe",
         "gradient",
         "gradient_up",
         "half_horizontal",
         "half_horizontal_bottom",
         "half_vertical",
         "half_vertical_right",
         "mojang",
         "rhombus",
         "skull",
         "small_stripes",
         "square_bottom_left",
         "square_bottom_right",
         "square_top_left",
         "square_top_right",
         "straight_cross",
         "stripe_bottom",
         "stripe_center",
         "stripe_downleft",
         "stripe_downright",
         "stripe_left",
         "stripe_middle",
         "stripe_right",
         "stripe_top",
         "triangle_bottom",
         "triangle_top",
         "triangles_bottom",
         "triangles_top"
      }
   );
   private static final Set<String> SHIELD_PATTERN_TEXTURES = BANNER_PATTERN_TYPES.stream()
      .map(_snowman -> "textures/entity/shield/" + _snowman + ".png")
      .collect(Collectors.toSet());
   private static final Set<String> BANNER_PATTERN_TEXTURES = BANNER_PATTERN_TYPES.stream()
      .map(_snowman -> "textures/entity/banner/" + _snowman + ".png")
      .collect(Collectors.toSet());
   public static final Identifier OLD_SHIELD_BASE_TEXTURE = new Identifier("textures/entity/shield_base.png");
   public static final Identifier OLD_BANNER_BASE_TEXTURE = new Identifier("textures/entity/banner_base.png");
   public static final Identifier IRON_GOLEM_TEXTURE = new Identifier("textures/entity/iron_golem.png");
   private final ResourcePack parent;

   public Format4ResourcePack(ResourcePack parent) {
      this.parent = parent;
   }

   @Override
   public InputStream openRoot(String fileName) throws IOException {
      return this.parent.openRoot(fileName);
   }

   @Override
   public boolean contains(ResourceType type, Identifier id) {
      if (!"minecraft".equals(id.getNamespace())) {
         return this.parent.contains(type, id);
      } else {
         String _snowman = id.getPath();
         if ("textures/misc/enchanted_item_glint.png".equals(_snowman)) {
            return false;
         } else if ("textures/entity/iron_golem/iron_golem.png".equals(_snowman)) {
            return this.parent.contains(type, IRON_GOLEM_TEXTURE);
         } else if ("textures/entity/conduit/wind.png".equals(_snowman) || "textures/entity/conduit/wind_vertical.png".equals(_snowman)) {
            return false;
         } else if (SHIELD_PATTERN_TEXTURES.contains(_snowman)) {
            return this.parent.contains(type, OLD_SHIELD_BASE_TEXTURE) && this.parent.contains(type, id);
         } else if (!BANNER_PATTERN_TEXTURES.contains(_snowman)) {
            Pair<ChestType, Identifier> _snowmanx = NEW_TO_OLD_CHEST_TEXTURES.get(_snowman);
            return _snowmanx != null && this.parent.contains(type, (Identifier)_snowmanx.getSecond()) ? true : this.parent.contains(type, id);
         } else {
            return this.parent.contains(type, OLD_BANNER_BASE_TEXTURE) && this.parent.contains(type, id);
         }
      }
   }

   @Override
   public InputStream open(ResourceType type, Identifier id) throws IOException {
      if (!"minecraft".equals(id.getNamespace())) {
         return this.parent.open(type, id);
      } else {
         String _snowman = id.getPath();
         if ("textures/entity/iron_golem/iron_golem.png".equals(_snowman)) {
            return this.parent.open(type, IRON_GOLEM_TEXTURE);
         } else {
            if (SHIELD_PATTERN_TEXTURES.contains(_snowman)) {
               InputStream _snowmanx = openCroppedStream(this.parent.open(type, OLD_SHIELD_BASE_TEXTURE), this.parent.open(type, id), 64, 2, 2, 12, 22);
               if (_snowmanx != null) {
                  return _snowmanx;
               }
            } else if (BANNER_PATTERN_TEXTURES.contains(_snowman)) {
               InputStream _snowmanx = openCroppedStream(this.parent.open(type, OLD_BANNER_BASE_TEXTURE), this.parent.open(type, id), 64, 0, 0, 42, 41);
               if (_snowmanx != null) {
                  return _snowmanx;
               }
            } else {
               if ("textures/entity/enderdragon/dragon.png".equals(_snowman) || "textures/entity/enderdragon/dragon_exploding.png".equals(_snowman)) {
                  ByteArrayInputStream var23;
                  try (NativeImage _snowmanx = NativeImage.read(this.parent.open(type, id))) {
                     int _snowmanxx = _snowmanx.getWidth() / 256;

                     for (int _snowmanxxx = 88 * _snowmanxx; _snowmanxxx < 200 * _snowmanxx; _snowmanxxx++) {
                        for (int _snowmanxxxx = 56 * _snowmanxx; _snowmanxxxx < 112 * _snowmanxx; _snowmanxxxx++) {
                           _snowmanx.setPixelColor(_snowmanxxxx, _snowmanxxx, 0);
                        }
                     }

                     var23 = new ByteArrayInputStream(_snowmanx.getBytes());
                  }

                  return var23;
               }

               if ("textures/entity/conduit/closed_eye.png".equals(_snowman) || "textures/entity/conduit/open_eye.png".equals(_snowman)) {
                  return method_24199(this.parent.open(type, id));
               }

               Pair<ChestType, Identifier> _snowmanx = NEW_TO_OLD_CHEST_TEXTURES.get(_snowman);
               if (_snowmanx != null) {
                  ChestType _snowmanxx = (ChestType)_snowmanx.getFirst();
                  InputStream _snowmanxxx = this.parent.open(type, (Identifier)_snowmanx.getSecond());
                  if (_snowmanxx == ChestType.SINGLE) {
                     return cropSingleChestTexture(_snowmanxxx);
                  }

                  if (_snowmanxx == ChestType.LEFT) {
                     return cropLeftChestTexture(_snowmanxxx);
                  }

                  if (_snowmanxx == ChestType.RIGHT) {
                     return cropRightChestTexture(_snowmanxxx);
                  }
               }
            }

            return this.parent.open(type, id);
         }
      }
   }

   @Nullable
   public static InputStream openCroppedStream(InputStream _snowman, InputStream _snowman, int _snowman, int _snowman, int _snowman, int _snowman, int _snowman) throws IOException {
      ByteArrayInputStream var71;
      try (
         NativeImage _snowmanxxxxxxx = NativeImage.read(_snowman);
         NativeImage _snowmanxxxxxxxx = NativeImage.read(_snowman);
      ) {
         int _snowmanxxxxxxxxx = _snowmanxxxxxxx.getWidth();
         int _snowmanxxxxxxxxxx = _snowmanxxxxxxx.getHeight();
         if (_snowmanxxxxxxxxx != _snowmanxxxxxxxx.getWidth() || _snowmanxxxxxxxxxx != _snowmanxxxxxxxx.getHeight()) {
            return null;
         }

         try (NativeImage _snowmanxxxxxxxxxxx = new NativeImage(_snowmanxxxxxxxxx, _snowmanxxxxxxxxxx, true)) {
            int _snowmanxxxxxxxxxxxx = _snowmanxxxxxxxxx / _snowman;

            for (int _snowmanxxxxxxxxxxxxx = _snowman * _snowmanxxxxxxxxxxxx; _snowmanxxxxxxxxxxxxx < _snowman * _snowmanxxxxxxxxxxxx; _snowmanxxxxxxxxxxxxx++) {
               for (int _snowmanxxxxxxxxxxxxxx = _snowman * _snowmanxxxxxxxxxxxx; _snowmanxxxxxxxxxxxxxx < _snowman * _snowmanxxxxxxxxxxxx; _snowmanxxxxxxxxxxxxxx++) {
                  int _snowmanxxxxxxxxxxxxxxx = NativeImage.getRed(_snowmanxxxxxxxx.getPixelColor(_snowmanxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxx));
                  int _snowmanxxxxxxxxxxxxxxxx = _snowmanxxxxxxx.getPixelColor(_snowmanxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxx);
                  _snowmanxxxxxxxxxxx.setPixelColor(
                     _snowmanxxxxxxxxxxxxxx,
                     _snowmanxxxxxxxxxxxxx,
                     NativeImage.getAbgrColor(
                        _snowmanxxxxxxxxxxxxxxx,
                        NativeImage.getBlue(_snowmanxxxxxxxxxxxxxxxx),
                        NativeImage.getGreen(_snowmanxxxxxxxxxxxxxxxx),
                        NativeImage.getRed(_snowmanxxxxxxxxxxxxxxxx)
                     )
                  );
               }
            }

            var71 = new ByteArrayInputStream(_snowmanxxxxxxxxxxx.getBytes());
         }
      }

      return var71;
   }

   public static InputStream method_24199(InputStream _snowman) throws IOException {
      ByteArrayInputStream var7;
      try (NativeImage _snowmanx = NativeImage.read(_snowman)) {
         int _snowmanxx = _snowmanx.getWidth();
         int _snowmanxxx = _snowmanx.getHeight();

         try (NativeImage _snowmanxxxx = new NativeImage(2 * _snowmanxx, 2 * _snowmanxxx, true)) {
            loadBytes(_snowmanx, _snowmanxxxx, 0, 0, 0, 0, _snowmanxx, _snowmanxxx, 1, false, false);
            var7 = new ByteArrayInputStream(_snowmanxxxx.getBytes());
         }
      }

      return var7;
   }

   public static InputStream cropLeftChestTexture(InputStream _snowman) throws IOException {
      ByteArrayInputStream var8;
      try (NativeImage _snowmanx = NativeImage.read(_snowman)) {
         int _snowmanxx = _snowmanx.getWidth();
         int _snowmanxxx = _snowmanx.getHeight();

         try (NativeImage _snowmanxxxx = new NativeImage(_snowmanxx / 2, _snowmanxxx, true)) {
            int _snowmanxxxxx = _snowmanxxx / 64;
            loadBytes(_snowmanx, _snowmanxxxx, 29, 0, 29, 0, 15, 14, _snowmanxxxxx, false, true);
            loadBytes(_snowmanx, _snowmanxxxx, 59, 0, 14, 0, 15, 14, _snowmanxxxxx, false, true);
            loadBytes(_snowmanx, _snowmanxxxx, 29, 14, 43, 14, 15, 5, _snowmanxxxxx, true, true);
            loadBytes(_snowmanx, _snowmanxxxx, 44, 14, 29, 14, 14, 5, _snowmanxxxxx, true, true);
            loadBytes(_snowmanx, _snowmanxxxx, 58, 14, 14, 14, 15, 5, _snowmanxxxxx, true, true);
            loadBytes(_snowmanx, _snowmanxxxx, 29, 19, 29, 19, 15, 14, _snowmanxxxxx, false, true);
            loadBytes(_snowmanx, _snowmanxxxx, 59, 19, 14, 19, 15, 14, _snowmanxxxxx, false, true);
            loadBytes(_snowmanx, _snowmanxxxx, 29, 33, 43, 33, 15, 10, _snowmanxxxxx, true, true);
            loadBytes(_snowmanx, _snowmanxxxx, 44, 33, 29, 33, 14, 10, _snowmanxxxxx, true, true);
            loadBytes(_snowmanx, _snowmanxxxx, 58, 33, 14, 33, 15, 10, _snowmanxxxxx, true, true);
            loadBytes(_snowmanx, _snowmanxxxx, 2, 0, 2, 0, 1, 1, _snowmanxxxxx, false, true);
            loadBytes(_snowmanx, _snowmanxxxx, 4, 0, 1, 0, 1, 1, _snowmanxxxxx, false, true);
            loadBytes(_snowmanx, _snowmanxxxx, 2, 1, 3, 1, 1, 4, _snowmanxxxxx, true, true);
            loadBytes(_snowmanx, _snowmanxxxx, 3, 1, 2, 1, 1, 4, _snowmanxxxxx, true, true);
            loadBytes(_snowmanx, _snowmanxxxx, 4, 1, 1, 1, 1, 4, _snowmanxxxxx, true, true);
            var8 = new ByteArrayInputStream(_snowmanxxxx.getBytes());
         }
      }

      return var8;
   }

   public static InputStream cropRightChestTexture(InputStream _snowman) throws IOException {
      ByteArrayInputStream var8;
      try (NativeImage _snowmanx = NativeImage.read(_snowman)) {
         int _snowmanxx = _snowmanx.getWidth();
         int _snowmanxxx = _snowmanx.getHeight();

         try (NativeImage _snowmanxxxx = new NativeImage(_snowmanxx / 2, _snowmanxxx, true)) {
            int _snowmanxxxxx = _snowmanxxx / 64;
            loadBytes(_snowmanx, _snowmanxxxx, 14, 0, 29, 0, 15, 14, _snowmanxxxxx, false, true);
            loadBytes(_snowmanx, _snowmanxxxx, 44, 0, 14, 0, 15, 14, _snowmanxxxxx, false, true);
            loadBytes(_snowmanx, _snowmanxxxx, 0, 14, 0, 14, 14, 5, _snowmanxxxxx, true, true);
            loadBytes(_snowmanx, _snowmanxxxx, 14, 14, 43, 14, 15, 5, _snowmanxxxxx, true, true);
            loadBytes(_snowmanx, _snowmanxxxx, 73, 14, 14, 14, 15, 5, _snowmanxxxxx, true, true);
            loadBytes(_snowmanx, _snowmanxxxx, 14, 19, 29, 19, 15, 14, _snowmanxxxxx, false, true);
            loadBytes(_snowmanx, _snowmanxxxx, 44, 19, 14, 19, 15, 14, _snowmanxxxxx, false, true);
            loadBytes(_snowmanx, _snowmanxxxx, 0, 33, 0, 33, 14, 10, _snowmanxxxxx, true, true);
            loadBytes(_snowmanx, _snowmanxxxx, 14, 33, 43, 33, 15, 10, _snowmanxxxxx, true, true);
            loadBytes(_snowmanx, _snowmanxxxx, 73, 33, 14, 33, 15, 10, _snowmanxxxxx, true, true);
            loadBytes(_snowmanx, _snowmanxxxx, 1, 0, 2, 0, 1, 1, _snowmanxxxxx, false, true);
            loadBytes(_snowmanx, _snowmanxxxx, 3, 0, 1, 0, 1, 1, _snowmanxxxxx, false, true);
            loadBytes(_snowmanx, _snowmanxxxx, 0, 1, 0, 1, 1, 4, _snowmanxxxxx, true, true);
            loadBytes(_snowmanx, _snowmanxxxx, 1, 1, 3, 1, 1, 4, _snowmanxxxxx, true, true);
            loadBytes(_snowmanx, _snowmanxxxx, 5, 1, 1, 1, 1, 4, _snowmanxxxxx, true, true);
            var8 = new ByteArrayInputStream(_snowmanxxxx.getBytes());
         }
      }

      return var8;
   }

   public static InputStream cropSingleChestTexture(InputStream _snowman) throws IOException {
      ByteArrayInputStream var8;
      try (NativeImage _snowmanx = NativeImage.read(_snowman)) {
         int _snowmanxx = _snowmanx.getWidth();
         int _snowmanxxx = _snowmanx.getHeight();

         try (NativeImage _snowmanxxxx = new NativeImage(_snowmanxx, _snowmanxxx, true)) {
            int _snowmanxxxxx = _snowmanxxx / 64;
            loadBytes(_snowmanx, _snowmanxxxx, 14, 0, 28, 0, 14, 14, _snowmanxxxxx, false, true);
            loadBytes(_snowmanx, _snowmanxxxx, 28, 0, 14, 0, 14, 14, _snowmanxxxxx, false, true);
            loadBytes(_snowmanx, _snowmanxxxx, 0, 14, 0, 14, 14, 5, _snowmanxxxxx, true, true);
            loadBytes(_snowmanx, _snowmanxxxx, 14, 14, 42, 14, 14, 5, _snowmanxxxxx, true, true);
            loadBytes(_snowmanx, _snowmanxxxx, 28, 14, 28, 14, 14, 5, _snowmanxxxxx, true, true);
            loadBytes(_snowmanx, _snowmanxxxx, 42, 14, 14, 14, 14, 5, _snowmanxxxxx, true, true);
            loadBytes(_snowmanx, _snowmanxxxx, 14, 19, 28, 19, 14, 14, _snowmanxxxxx, false, true);
            loadBytes(_snowmanx, _snowmanxxxx, 28, 19, 14, 19, 14, 14, _snowmanxxxxx, false, true);
            loadBytes(_snowmanx, _snowmanxxxx, 0, 33, 0, 33, 14, 10, _snowmanxxxxx, true, true);
            loadBytes(_snowmanx, _snowmanxxxx, 14, 33, 42, 33, 14, 10, _snowmanxxxxx, true, true);
            loadBytes(_snowmanx, _snowmanxxxx, 28, 33, 28, 33, 14, 10, _snowmanxxxxx, true, true);
            loadBytes(_snowmanx, _snowmanxxxx, 42, 33, 14, 33, 14, 10, _snowmanxxxxx, true, true);
            loadBytes(_snowmanx, _snowmanxxxx, 1, 0, 3, 0, 2, 1, _snowmanxxxxx, false, true);
            loadBytes(_snowmanx, _snowmanxxxx, 3, 0, 1, 0, 2, 1, _snowmanxxxxx, false, true);
            loadBytes(_snowmanx, _snowmanxxxx, 0, 1, 0, 1, 1, 4, _snowmanxxxxx, true, true);
            loadBytes(_snowmanx, _snowmanxxxx, 1, 1, 4, 1, 2, 4, _snowmanxxxxx, true, true);
            loadBytes(_snowmanx, _snowmanxxxx, 3, 1, 3, 1, 1, 4, _snowmanxxxxx, true, true);
            loadBytes(_snowmanx, _snowmanxxxx, 4, 1, 1, 1, 2, 4, _snowmanxxxxx, true, true);
            var8 = new ByteArrayInputStream(_snowmanxxxx.getBytes());
         }
      }

      return var8;
   }

   @Override
   public Collection<Identifier> findResources(ResourceType type, String namespace, String prefix, int maxDepth, Predicate<String> pathFilter) {
      return this.parent.findResources(type, namespace, prefix, maxDepth, pathFilter);
   }

   @Override
   public Set<String> getNamespaces(ResourceType type) {
      return this.parent.getNamespaces(type);
   }

   @Nullable
   @Override
   public <T> T parseMetadata(ResourceMetadataReader<T> metaReader) throws IOException {
      return this.parent.parseMetadata(metaReader);
   }

   @Override
   public String getName() {
      return this.parent.getName();
   }

   @Override
   public void close() {
      this.parent.close();
   }

   private static void loadBytes(NativeImage source, NativeImage target, int _snowman, int _snowman, int _snowman, int _snowman, int _snowman, int _snowman, int _snowman, boolean _snowman, boolean _snowman) {
      _snowman *= _snowman;
      _snowman *= _snowman;
      _snowman *= _snowman;
      _snowman *= _snowman;
      _snowman *= _snowman;
      _snowman *= _snowman;

      for (int _snowmanxxxxxxxxx = 0; _snowmanxxxxxxxxx < _snowman; _snowmanxxxxxxxxx++) {
         for (int _snowmanxxxxxxxxxx = 0; _snowmanxxxxxxxxxx < _snowman; _snowmanxxxxxxxxxx++) {
            target.setPixelColor(
               _snowman + _snowmanxxxxxxxxxx, _snowman + _snowmanxxxxxxxxx, source.getPixelColor(_snowman + (_snowman ? _snowman - 1 - _snowmanxxxxxxxxxx : _snowmanxxxxxxxxxx), _snowman + (_snowman ? _snowman - 1 - _snowmanxxxxxxxxx : _snowmanxxxxxxxxx))
            );
         }
      }
   }
}
