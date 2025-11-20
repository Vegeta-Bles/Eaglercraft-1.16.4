/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 *  com.google.common.collect.ImmutableSet
 *  com.google.common.collect.Lists
 *  com.mojang.datafixers.DSL
 *  com.mojang.datafixers.DataFix
 *  com.mojang.datafixers.DataFixUtils
 *  com.mojang.datafixers.OpticFinder
 *  com.mojang.datafixers.TypeRewriteRule
 *  com.mojang.datafixers.Typed
 *  com.mojang.datafixers.schemas.Schema
 *  com.mojang.datafixers.types.Type
 *  com.mojang.datafixers.types.templates.List$ListType
 *  com.mojang.datafixers.util.Pair
 *  com.mojang.serialization.Dynamic
 *  it.unimi.dsi.fastutil.ints.Int2IntMap
 *  it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap
 *  it.unimi.dsi.fastutil.ints.Int2ObjectMap
 *  it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap
 *  it.unimi.dsi.fastutil.ints.IntIterator
 *  it.unimi.dsi.fastutil.ints.IntOpenHashSet
 *  it.unimi.dsi.fastutil.ints.IntSet
 *  it.unimi.dsi.fastutil.objects.Object2IntMap
 *  it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap
 *  javax.annotation.Nullable
 */
package net.minecraft.datafixer.fix;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.types.templates.List;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import it.unimi.dsi.fastutil.ints.Int2IntMap;
import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.ints.IntIterator;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import it.unimi.dsi.fastutil.ints.IntSet;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.datafixer.TypeReferences;
import net.minecraft.util.math.WordPackedArray;

public class LeavesFix
extends DataFix {
    private static final int[][] field_5687 = new int[][]{{-1, 0, 0}, {1, 0, 0}, {0, -1, 0}, {0, 1, 0}, {0, 0, -1}, {0, 0, 1}};
    private static final Object2IntMap<String> LEAVES_MAP = (Object2IntMap)DataFixUtils.make((Object)new Object2IntOpenHashMap(), object2IntOpenHashMap -> {
        object2IntOpenHashMap.put((Object)"minecraft:acacia_leaves", 0);
        object2IntOpenHashMap.put((Object)"minecraft:birch_leaves", 1);
        object2IntOpenHashMap.put((Object)"minecraft:dark_oak_leaves", 2);
        object2IntOpenHashMap.put((Object)"minecraft:jungle_leaves", 3);
        object2IntOpenHashMap.put((Object)"minecraft:oak_leaves", 4);
        object2IntOpenHashMap.put((Object)"minecraft:spruce_leaves", 5);
    });
    private static final Set<String> LOGS_MAP = ImmutableSet.of((Object)"minecraft:acacia_bark", (Object)"minecraft:birch_bark", (Object)"minecraft:dark_oak_bark", (Object)"minecraft:jungle_bark", (Object)"minecraft:oak_bark", (Object)"minecraft:spruce_bark", (Object[])new String[]{"minecraft:acacia_log", "minecraft:birch_log", "minecraft:dark_oak_log", "minecraft:jungle_log", "minecraft:oak_log", "minecraft:spruce_log", "minecraft:stripped_acacia_log", "minecraft:stripped_birch_log", "minecraft:stripped_dark_oak_log", "minecraft:stripped_jungle_log", "minecraft:stripped_oak_log", "minecraft:stripped_spruce_log"});

    public LeavesFix(Schema outputSchema, boolean changesType) {
        super(outputSchema, changesType);
    }

    protected TypeRewriteRule makeRule() {
        Type type = this.getInputSchema().getType(TypeReferences.CHUNK);
        OpticFinder _snowman2 = type.findField("Level");
        OpticFinder _snowman3 = _snowman2.type().findField("Sections");
        _snowman = _snowman3.type();
        if (!(_snowman instanceof List.ListType)) {
            throw new IllegalStateException("Expecting sections to be a list.");
        }
        _snowman = ((List.ListType)_snowman).getElement();
        OpticFinder _snowman4 = DSL.typeFinder((Type)_snowman);
        return this.fixTypeEverywhereTyped("Leaves fix", type, typed2 -> typed2.updateTyped(_snowman2, typed -> {
            int[] nArray = new int[]{0};
            Typed _snowman2 = typed.updateTyped(_snowman3, typed22 -> {
                Typed typed22;
                Int2ObjectOpenHashMap int2ObjectOpenHashMap = new Int2ObjectOpenHashMap(typed22.getAllTyped(_snowman4).stream().map(typed -> new LeavesLogFixer((Typed<?>)typed, this.getInputSchema())).collect(Collectors.toMap(ListFixer::method_5077, leavesLogFixer -> leavesLogFixer)));
                if (int2ObjectOpenHashMap.values().stream().allMatch(ListFixer::isFixed)) {
                    return typed22;
                }
                ArrayList _snowman2 = Lists.newArrayList();
                for (int i = 0; i < 7; ++i) {
                    _snowman2.add(new IntOpenHashSet());
                }
                for (LeavesLogFixer _snowman3 : int2ObjectOpenHashMap.values()) {
                    if (_snowman3.isFixed()) continue;
                    for (int i = 0; i < 4096; ++i) {
                        _snowman = _snowman3.needsFix(i);
                        if (_snowman3.isLog(_snowman)) {
                            ((IntSet)_snowman2.get(0)).add(_snowman3.method_5077() << 12 | i);
                            continue;
                        }
                        if (!_snowman3.isLeaf(_snowman)) continue;
                        n = this.method_5052(i);
                        _snowman = this.method_5050(i);
                        nArray[0] = nArray[0] | LeavesFix.method_5061(n == 0, n == 15, _snowman == 0, _snowman == 15);
                    }
                }
                for (int i = 1; i < 7; ++i) {
                    LeavesLogFixer _snowman3;
                    _snowman3 = (IntSet)_snowman2.get(i - 1);
                    IntSet intSet = (IntSet)_snowman2.get(i);
                    IntIterator _snowman4 = _snowman3.iterator();
                    while (_snowman4.hasNext()) {
                        int n = _snowman4.nextInt();
                        _snowman = this.method_5052(n);
                        _snowman = this.method_5062(n);
                        _snowman = this.method_5050(n);
                        for (int[] nArray2 : field_5687) {
                            int n2 = _snowman + nArray2[0];
                            _snowman = _snowman + nArray2[1];
                            _snowman = _snowman + nArray2[2];
                            if (n2 < 0 || n2 > 15 || _snowman < 0 || _snowman > 15 || _snowman < 0 || _snowman > 255 || (_snowman = (LeavesLogFixer)int2ObjectOpenHashMap.get(_snowman >> 4)) == null || _snowman.isFixed() || !_snowman.isLeaf(_snowman = _snowman.needsFix(_snowman = LeavesFix.method_5051(n2, _snowman & 0xF, _snowman))) || (_snowman = _snowman.getDistanceToLog(_snowman)) <= i) continue;
                            _snowman.computeLeafStates(_snowman, _snowman, i);
                            intSet.add(LeavesFix.method_5051(n2, _snowman, _snowman));
                        }
                    }
                }
                return typed22.updateTyped(_snowman4, arg_0 -> LeavesFix.method_5058((Int2ObjectMap)int2ObjectOpenHashMap, arg_0));
            });
            if (nArray[0] != 0) {
                _snowman2 = _snowman2.update(DSL.remainderFinder(), dynamic -> {
                    _snowman = (Dynamic)DataFixUtils.orElse((Optional)dynamic.get("UpgradeData").result(), (Object)dynamic.emptyMap());
                    return dynamic.set("UpgradeData", _snowman.set("Sides", dynamic.createByte((byte)(_snowman.get("Sides").asByte((byte)0) | nArray[0]))));
                });
            }
            return _snowman2;
        }));
    }

    public static int method_5051(int n, int n2, int n3) {
        return n2 << 8 | n3 << 4 | n;
    }

    private int method_5052(int n) {
        return n & 0xF;
    }

    private int method_5062(int n) {
        return n >> 8 & 0xFF;
    }

    private int method_5050(int n) {
        return n >> 4 & 0xF;
    }

    public static int method_5061(boolean bl, boolean bl2, boolean bl3, boolean bl4) {
        int n = 0;
        if (bl3) {
            n = bl2 ? (n |= 2) : (bl ? (n |= 0x80) : (n |= 1));
        } else if (bl4) {
            n = bl ? (n |= 0x20) : (bl2 ? (n |= 8) : (n |= 0x10));
        } else if (bl2) {
            n |= 4;
        } else if (bl) {
            n |= 0x40;
        }
        return n;
    }

    private static /* synthetic */ Typed method_5058(Int2ObjectMap int2ObjectMap, Typed typed) {
        return ((LeavesLogFixer)int2ObjectMap.get(((Dynamic)typed.get(DSL.remainderFinder())).get("Y").asInt(0))).method_5083(typed);
    }

    public static final class LeavesLogFixer
    extends ListFixer {
        @Nullable
        private IntSet leafIndices;
        @Nullable
        private IntSet logIndices;
        @Nullable
        private Int2IntMap leafStates;

        public LeavesLogFixer(Typed<?> typed, Schema schema) {
            super(typed, schema);
        }

        @Override
        protected boolean needsFix() {
            this.leafIndices = new IntOpenHashSet();
            this.logIndices = new IntOpenHashSet();
            this.leafStates = new Int2IntOpenHashMap();
            for (int i = 0; i < this.properties.size(); ++i) {
                Dynamic dynamic = (Dynamic)this.properties.get(i);
                String _snowman2 = dynamic.get("Name").asString("");
                if (LEAVES_MAP.containsKey((Object)_snowman2)) {
                    boolean bl = Objects.equals(dynamic.get("Properties").get("decayable").asString(""), "false");
                    this.leafIndices.add(i);
                    this.leafStates.put(this.computeFlags(_snowman2, bl, 7), i);
                    this.properties.set(i, this.createLeafProperties(dynamic, _snowman2, bl, 7));
                }
                if (!LOGS_MAP.contains(_snowman2)) continue;
                this.logIndices.add(i);
            }
            return this.leafIndices.isEmpty() && this.logIndices.isEmpty();
        }

        private Dynamic<?> createLeafProperties(Dynamic<?> dynamic, String string, boolean bl, int n) {
            Dynamic dynamic2 = dynamic.emptyMap();
            dynamic2 = dynamic2.set("persistent", dynamic2.createString(bl ? "true" : "false"));
            dynamic2 = dynamic2.set("distance", dynamic2.createString(Integer.toString(n)));
            _snowman = dynamic.emptyMap();
            _snowman = _snowman.set("Properties", dynamic2);
            _snowman = _snowman.set("Name", _snowman.createString(string));
            return _snowman;
        }

        public boolean isLog(int n) {
            return this.logIndices.contains(n);
        }

        public boolean isLeaf(int n) {
            return this.leafIndices.contains(n);
        }

        private int getDistanceToLog(int n) {
            if (this.isLog(n)) {
                return 0;
            }
            return Integer.parseInt(((Dynamic)this.properties.get(n)).get("Properties").get("distance").asString(""));
        }

        private void computeLeafStates(int n4, int n2, int n3) {
            int n4;
            int n5;
            Dynamic dynamic = (Dynamic)this.properties.get(n2);
            String _snowman2 = dynamic.get("Name").asString("");
            int _snowman3 = this.computeFlags(_snowman2, _snowman = Objects.equals(dynamic.get("Properties").get("persistent").asString(""), "true"), n3);
            if (!this.leafStates.containsKey(_snowman3)) {
                n5 = this.properties.size();
                this.leafIndices.add(n5);
                this.leafStates.put(_snowman3, n5);
                this.properties.add(this.createLeafProperties(dynamic, _snowman2, _snowman, n3));
            }
            n5 = this.leafStates.get(_snowman3);
            if (1 << this.blockStateMap.getUnitSize() <= n5) {
                WordPackedArray wordPackedArray = new WordPackedArray(this.blockStateMap.getUnitSize() + 1, 4096);
                for (int i = 0; i < 4096; ++i) {
                    wordPackedArray.set(i, this.blockStateMap.get(i));
                }
                this.blockStateMap = wordPackedArray;
            }
            this.blockStateMap.set(n4, n5);
        }
    }

    public static abstract class ListFixer {
        private final Type<Pair<String, Dynamic<?>>> field_5695 = DSL.named((String)TypeReferences.BLOCK_STATE.typeName(), (Type)DSL.remainderType());
        protected final OpticFinder<List<Pair<String, Dynamic<?>>>> field_5693 = DSL.fieldFinder((String)"Palette", (Type)DSL.list(this.field_5695));
        protected final List<Dynamic<?>> properties;
        protected final int field_5694;
        @Nullable
        protected WordPackedArray blockStateMap;

        public ListFixer(Typed<?> typed, Schema schema) {
            if (!Objects.equals(schema.getType(TypeReferences.BLOCK_STATE), this.field_5695)) {
                throw new IllegalStateException("Block state type is not what was expected.");
            }
            Optional optional = typed.getOptional(this.field_5693);
            this.properties = optional.map(list -> list.stream().map(Pair::getSecond).collect(Collectors.toList())).orElse((List)ImmutableList.of());
            Dynamic _snowman2 = (Dynamic)typed.get(DSL.remainderFinder());
            this.field_5694 = _snowman2.get("Y").asInt(0);
            this.computeFixableBlockStates(_snowman2);
        }

        protected void computeFixableBlockStates(Dynamic<?> dynamic) {
            if (this.needsFix()) {
                this.blockStateMap = null;
            } else {
                long[] lArray = dynamic.get("BlockStates").asLongStream().toArray();
                int _snowman2 = Math.max(4, DataFixUtils.ceillog2((int)this.properties.size()));
                this.blockStateMap = new WordPackedArray(_snowman2, 4096, lArray);
            }
        }

        public Typed<?> method_5083(Typed<?> typed) {
            if (this.isFixed()) {
                return typed;
            }
            return typed.update(DSL.remainderFinder(), dynamic -> dynamic.set("BlockStates", dynamic.createLongList(Arrays.stream(this.blockStateMap.getAlignedArray())))).set(this.field_5693, this.properties.stream().map(dynamic -> Pair.of((Object)TypeReferences.BLOCK_STATE.typeName(), (Object)dynamic)).collect(Collectors.toList()));
        }

        public boolean isFixed() {
            return this.blockStateMap == null;
        }

        public int needsFix(int n) {
            return this.blockStateMap.get(n);
        }

        protected int computeFlags(String leafBlockName, boolean persistent, int n) {
            return LEAVES_MAP.get((Object)leafBlockName) << 5 | (persistent ? 16 : 0) | n;
        }

        int method_5077() {
            return this.field_5694;
        }

        protected abstract boolean needsFix();
    }
}

