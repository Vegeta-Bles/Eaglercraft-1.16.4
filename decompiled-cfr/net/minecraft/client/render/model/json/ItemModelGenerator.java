/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.google.common.collect.Maps
 *  com.mojang.datafixers.util.Either
 */
package net.minecraft.client.render.model.json;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mojang.datafixers.util.Either;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Function;
import net.minecraft.client.render.model.json.JsonUnbakedModel;
import net.minecraft.client.render.model.json.ModelElement;
import net.minecraft.client.render.model.json.ModelElementFace;
import net.minecraft.client.render.model.json.ModelElementTexture;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.util.math.Direction;

public class ItemModelGenerator {
    public static final List<String> LAYERS = Lists.newArrayList((Object[])new String[]{"layer0", "layer1", "layer2", "layer3", "layer4"});

    public JsonUnbakedModel create(Function<SpriteIdentifier, Sprite> textureGetter, JsonUnbakedModel blockModel) {
        HashMap hashMap = Maps.newHashMap();
        ArrayList _snowman2 = Lists.newArrayList();
        for (int i = 0; i < LAYERS.size() && blockModel.textureExists(_snowman = LAYERS.get(i)); ++i) {
            SpriteIdentifier spriteIdentifier = blockModel.resolveSprite(_snowman);
            hashMap.put(_snowman, Either.left((Object)spriteIdentifier));
            Sprite _snowman3 = textureGetter.apply(spriteIdentifier);
            _snowman2.addAll(this.addLayerElements(i, _snowman, _snowman3));
        }
        hashMap.put("particle", blockModel.textureExists("particle") ? Either.left((Object)blockModel.resolveSprite("particle")) : (Either)hashMap.get("layer0"));
        JsonUnbakedModel jsonUnbakedModel = new JsonUnbakedModel(null, _snowman2, hashMap, false, blockModel.getGuiLight(), blockModel.getTransformations(), blockModel.getOverrides());
        jsonUnbakedModel.id = blockModel.id;
        return jsonUnbakedModel;
    }

    private List<ModelElement> addLayerElements(int layer, String key, Sprite sprite) {
        HashMap hashMap = Maps.newHashMap();
        hashMap.put(Direction.SOUTH, new ModelElementFace(null, layer, key, new ModelElementTexture(new float[]{0.0f, 0.0f, 16.0f, 16.0f}, 0)));
        hashMap.put(Direction.NORTH, new ModelElementFace(null, layer, key, new ModelElementTexture(new float[]{16.0f, 0.0f, 0.0f, 16.0f}, 0)));
        ArrayList _snowman2 = Lists.newArrayList();
        _snowman2.add(new ModelElement(new Vector3f(0.0f, 0.0f, 7.5f), new Vector3f(16.0f, 16.0f, 8.5f), hashMap, null, true));
        _snowman2.addAll(this.addSubComponents(sprite, key, layer));
        return _snowman2;
    }

    private List<ModelElement> addSubComponents(Sprite sprite, String key, int layer) {
        float f = sprite.getWidth();
        _snowman = sprite.getHeight();
        ArrayList _snowman2 = Lists.newArrayList();
        for (Frame frame : this.getFrames(sprite)) {
            float f2 = 0.0f;
            _snowman = 0.0f;
            _snowman = 0.0f;
            _snowman = 0.0f;
            _snowman = 0.0f;
            _snowman = 0.0f;
            _snowman = 0.0f;
            _snowman = 0.0f;
            _snowman = 16.0f / f;
            _snowman = 16.0f / _snowman;
            _snowman = frame.getMin();
            _snowman = frame.getMax();
            _snowman = frame.getLevel();
            Side _snowman3 = frame.getSide();
            switch (_snowman3) {
                case UP: {
                    f2 = _snowman = _snowman;
                    _snowman = _snowman = _snowman + 1.0f;
                    _snowman = _snowman = _snowman;
                    _snowman = _snowman;
                    _snowman = _snowman + 1.0f;
                    break;
                }
                case DOWN: {
                    _snowman = _snowman;
                    _snowman = _snowman + 1.0f;
                    f2 = _snowman = _snowman;
                    _snowman = _snowman = _snowman + 1.0f;
                    _snowman = _snowman + 1.0f;
                    _snowman = _snowman + 1.0f;
                    break;
                }
                case LEFT: {
                    f2 = _snowman = _snowman;
                    _snowman = _snowman;
                    _snowman = _snowman + 1.0f;
                    _snowman = _snowman = _snowman;
                    _snowman = _snowman = _snowman + 1.0f;
                    break;
                }
                case RIGHT: {
                    _snowman = _snowman;
                    _snowman = _snowman + 1.0f;
                    f2 = _snowman + 1.0f;
                    _snowman = _snowman + 1.0f;
                    _snowman = _snowman = _snowman;
                    _snowman = _snowman = _snowman + 1.0f;
                }
            }
            f2 *= _snowman;
            _snowman *= _snowman;
            _snowman *= _snowman;
            _snowman *= _snowman;
            _snowman = 16.0f - _snowman;
            _snowman = 16.0f - _snowman;
            HashMap _snowman4 = Maps.newHashMap();
            _snowman4.put(_snowman3.getDirection(), new ModelElementFace(null, layer, key, new ModelElementTexture(new float[]{_snowman *= _snowman, _snowman *= _snowman, _snowman *= _snowman, _snowman *= _snowman}, 0)));
            switch (_snowman3) {
                case UP: {
                    _snowman2.add(new ModelElement(new Vector3f(f2, _snowman, 7.5f), new Vector3f(_snowman, _snowman, 8.5f), _snowman4, null, true));
                    break;
                }
                case DOWN: {
                    _snowman2.add(new ModelElement(new Vector3f(f2, _snowman, 7.5f), new Vector3f(_snowman, _snowman, 8.5f), _snowman4, null, true));
                    break;
                }
                case LEFT: {
                    _snowman2.add(new ModelElement(new Vector3f(f2, _snowman, 7.5f), new Vector3f(f2, _snowman, 8.5f), _snowman4, null, true));
                    break;
                }
                case RIGHT: {
                    _snowman2.add(new ModelElement(new Vector3f(_snowman, _snowman, 7.5f), new Vector3f(_snowman, _snowman, 8.5f), _snowman4, null, true));
                }
            }
        }
        return _snowman2;
    }

    private List<Frame> getFrames(Sprite sprite) {
        int n = sprite.getWidth();
        _snowman = sprite.getHeight();
        ArrayList _snowman2 = Lists.newArrayList();
        for (_snowman = 0; _snowman < sprite.getFrameCount(); ++_snowman) {
            for (_snowman = 0; _snowman < _snowman; ++_snowman) {
                for (_snowman = 0; _snowman < n; ++_snowman) {
                    boolean bl = !this.isPixelTransparent(sprite, _snowman, _snowman, _snowman, n, _snowman);
                    this.buildCube(Side.UP, _snowman2, sprite, _snowman, _snowman, _snowman, n, _snowman, bl);
                    this.buildCube(Side.DOWN, _snowman2, sprite, _snowman, _snowman, _snowman, n, _snowman, bl);
                    this.buildCube(Side.LEFT, _snowman2, sprite, _snowman, _snowman, _snowman, n, _snowman, bl);
                    this.buildCube(Side.RIGHT, _snowman2, sprite, _snowman, _snowman, _snowman, n, _snowman, bl);
                }
            }
        }
        return _snowman2;
    }

    private void buildCube(Side side, List<Frame> cubes, Sprite sprite, int frame, int x, int y, int n, int n2, boolean bl) {
        boolean bl2 = _snowman = this.isPixelTransparent(sprite, frame, x + side.getOffsetX(), y + side.getOffsetY(), n, n2) && bl;
        if (_snowman) {
            this.buildCube(cubes, side, x, y);
        }
    }

    private void buildCube(List<Frame> cubes, Side side, int x, int y) {
        Frame frame = null;
        for (Frame frame2 : cubes) {
            if (frame2.getSide() != side) continue;
            int n = _snowman = side.isVertical() ? y : x;
            if (frame2.getLevel() != _snowman) continue;
            frame = frame2;
            break;
        }
        int _snowman2 = side.isVertical() ? y : x;
        int n = _snowman = side.isVertical() ? x : y;
        if (frame == null) {
            cubes.add(new Frame(side, _snowman, _snowman2));
        } else {
            frame.expand(_snowman);
        }
    }

    private boolean isPixelTransparent(Sprite sprite, int frame, int x, int y, int n, int n2) {
        if (x < 0 || y < 0 || x >= n || y >= n2) {
            return true;
        }
        return sprite.isPixelTransparent(frame, x, y);
    }

    static class Frame {
        private final Side side;
        private int min;
        private int max;
        private final int level;

        public Frame(Side side, int width, int depth) {
            this.side = side;
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

        public Side getSide() {
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

