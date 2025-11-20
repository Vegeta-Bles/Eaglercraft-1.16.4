/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.google.common.collect.Sets
 *  it.unimi.dsi.fastutil.Arrays
 *  it.unimi.dsi.fastutil.Swapper
 *  it.unimi.dsi.fastutil.ints.IntArrayList
 *  it.unimi.dsi.fastutil.ints.IntComparator
 *  it.unimi.dsi.fastutil.ints.IntList
 *  it.unimi.dsi.fastutil.ints.IntOpenHashSet
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package net.minecraft.client.search;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import it.unimi.dsi.fastutil.Swapper;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntComparator;
import it.unimi.dsi.fastutil.ints.IntList;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SuffixArray<T> {
    private static final boolean PRINT_COMPARISONS = Boolean.parseBoolean(System.getProperty("SuffixArray.printComparisons", "false"));
    private static final boolean PRINT_ARRAY = Boolean.parseBoolean(System.getProperty("SuffixArray.printArray", "false"));
    private static final Logger LOGGER = LogManager.getLogger();
    protected final List<T> objects = Lists.newArrayList();
    private final IntList characters = new IntArrayList();
    private final IntList textStarts = new IntArrayList();
    private IntList suffixIndexToObjectIndex = new IntArrayList();
    private IntList offsetInText = new IntArrayList();
    private int maxTextLength;

    public void add(T object, String text) {
        this.maxTextLength = Math.max(this.maxTextLength, text.length());
        int n = this.objects.size();
        this.objects.add(object);
        this.textStarts.add(this.characters.size());
        for (_snowman = 0; _snowman < text.length(); ++_snowman) {
            this.suffixIndexToObjectIndex.add(n);
            this.offsetInText.add(_snowman);
            this.characters.add((int)text.charAt(_snowman));
        }
        this.suffixIndexToObjectIndex.add(n);
        this.offsetInText.add(text.length());
        this.characters.add(-1);
    }

    public void build() {
        int n = this.characters.size();
        int[] _snowman2 = new int[n];
        final int[] _snowman3 = new int[n];
        final int[] _snowman4 = new int[n];
        int[] _snowman5 = new int[n];
        IntComparator _snowman6 = new IntComparator(){

            public int compare(int i, int j) {
                if (_snowman3[i] == _snowman3[j]) {
                    return Integer.compare(_snowman4[i], _snowman4[j]);
                }
                return Integer.compare(_snowman3[i], _snowman3[j]);
            }

            public int compare(Integer n, Integer n2) {
                return this.compare((int)n, (int)n2);
            }
        };
        Swapper _snowman7 = (i, j) -> {
            if (i != j) {
                int n = _snowman3[i];
                nArray[i] = _snowman3[j];
                nArray[j] = n;
                n = _snowman4[i];
                nArray2[i] = _snowman4[j];
                nArray2[j] = n;
                n = _snowman5[i];
                nArray3[i] = _snowman5[j];
                nArray3[j] = n;
            }
        };
        for (_snowman = 0; _snowman < n; ++_snowman) {
            _snowman2[_snowman] = this.characters.getInt(_snowman);
        }
        _snowman = 1;
        _snowman = Math.min(n, this.maxTextLength);
        while (_snowman * 2 < _snowman) {
            for (_snowman = 0; _snowman < n; ++_snowman) {
                _snowman3[_snowman] = _snowman2[_snowman];
                _snowman4[_snowman] = _snowman + _snowman < n ? _snowman2[_snowman + _snowman] : -2;
                _snowman5[_snowman] = _snowman;
            }
            it.unimi.dsi.fastutil.Arrays.quickSort((int)0, (int)n, (IntComparator)_snowman6, (Swapper)_snowman7);
            for (_snowman = 0; _snowman < n; ++_snowman) {
                _snowman2[_snowman5[_snowman]] = _snowman > 0 && _snowman3[_snowman] == _snowman3[_snowman - 1] && _snowman4[_snowman] == _snowman4[_snowman - 1] ? _snowman2[_snowman5[_snowman - 1]] : _snowman;
            }
            _snowman *= 2;
        }
        IntList _snowman8 = this.suffixIndexToObjectIndex;
        IntList _snowman9 = this.offsetInText;
        this.suffixIndexToObjectIndex = new IntArrayList(_snowman8.size());
        this.offsetInText = new IntArrayList(_snowman9.size());
        for (_snowman = 0; _snowman < n; ++_snowman) {
            _snowman = _snowman5[_snowman];
            this.suffixIndexToObjectIndex.add(_snowman8.getInt(_snowman));
            this.offsetInText.add(_snowman9.getInt(_snowman));
        }
        if (PRINT_ARRAY) {
            this.printArray();
        }
    }

    private void printArray() {
        for (int i = 0; i < this.suffixIndexToObjectIndex.size(); ++i) {
            LOGGER.debug("{} {}", (Object)i, (Object)this.getDebugString(i));
        }
        LOGGER.debug("");
    }

    private String getDebugString(int suffixIndex) {
        int n = this.offsetInText.getInt(suffixIndex);
        _snowman = this.textStarts.getInt(this.suffixIndexToObjectIndex.getInt(suffixIndex));
        StringBuilder _snowman2 = new StringBuilder();
        _snowman = 0;
        while (_snowman + _snowman < this.characters.size()) {
            if (_snowman == n) {
                _snowman2.append('^');
            }
            if ((_snowman = this.characters.get(_snowman + _snowman).intValue()) == -1) break;
            _snowman2.append((char)_snowman);
            ++_snowman;
        }
        return _snowman2.toString();
    }

    private int compare(String string, int suffixIndex) {
        int n = this.textStarts.getInt(this.suffixIndexToObjectIndex.getInt(suffixIndex));
        _snowman = this.offsetInText.getInt(suffixIndex);
        for (_snowman = 0; _snowman < string.length(); ++_snowman) {
            _snowman = this.characters.getInt(n + _snowman + _snowman);
            if (_snowman == -1) {
                return 1;
            }
            char c = string.charAt(_snowman);
            if (c < (_snowman = (char)_snowman)) {
                return -1;
            }
            if (c <= _snowman) continue;
            return 1;
        }
        return 0;
    }

    public List<T> findAll(String text) {
        int n = this.suffixIndexToObjectIndex.size();
        _snowman = 0;
        _snowman = n;
        while (_snowman < _snowman) {
            _snowman = _snowman + (_snowman - _snowman) / 2;
            _snowman = this.compare(text, _snowman);
            if (PRINT_COMPARISONS) {
                LOGGER.debug("comparing lower \"{}\" with {} \"{}\": {}", (Object)text, (Object)_snowman, (Object)this.getDebugString(_snowman), (Object)_snowman);
            }
            if (_snowman > 0) {
                _snowman = _snowman + 1;
                continue;
            }
            _snowman = _snowman;
        }
        if (_snowman < 0 || _snowman >= n) {
            return Collections.emptyList();
        }
        _snowman = _snowman;
        _snowman = n;
        while (_snowman < _snowman) {
            _snowman = _snowman + (_snowman - _snowman) / 2;
            _snowman = this.compare(text, _snowman);
            if (PRINT_COMPARISONS) {
                LOGGER.debug("comparing upper \"{}\" with {} \"{}\": {}", (Object)text, (Object)_snowman, (Object)this.getDebugString(_snowman), (Object)_snowman);
            }
            if (_snowman >= 0) {
                _snowman = _snowman + 1;
                continue;
            }
            _snowman = _snowman;
        }
        _snowman = _snowman;
        IntOpenHashSet _snowman2 = new IntOpenHashSet();
        for (_snowman = _snowman; _snowman < _snowman; ++_snowman) {
            _snowman2.add(this.suffixIndexToObjectIndex.getInt(_snowman));
        }
        int[] _snowman3 = _snowman2.toIntArray();
        Arrays.sort(_snowman3);
        LinkedHashSet _snowman4 = Sets.newLinkedHashSet();
        for (int n2 : _snowman3) {
            _snowman4.add(this.objects.get(n2));
        }
        return Lists.newArrayList((Iterable)_snowman4);
    }
}

