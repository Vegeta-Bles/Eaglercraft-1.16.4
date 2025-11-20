/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.AbstractIterator
 *  com.google.common.collect.Iterators
 *  com.google.common.collect.Lists
 *  com.google.common.collect.PeekingIterator
 *  it.unimi.dsi.fastutil.objects.Object2IntMap
 *  it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap
 */
package net.minecraft.client.search;

import com.google.common.collect.AbstractIterator;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.google.common.collect.PeekingIterator;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.function.Function;
import java.util.stream.Stream;
import net.minecraft.client.search.SearchableContainer;
import net.minecraft.client.search.SuffixArray;
import net.minecraft.util.Identifier;

public class IdentifierSearchableContainer<T>
implements SearchableContainer<T> {
    protected SuffixArray<T> byNamespace = new SuffixArray();
    protected SuffixArray<T> byPath = new SuffixArray();
    private final Function<T, Stream<Identifier>> identifierFinder;
    private final List<T> entries = Lists.newArrayList();
    private final Object2IntMap<T> entryIds = new Object2IntOpenHashMap();

    public IdentifierSearchableContainer(Function<T, Stream<Identifier>> function) {
        this.identifierFinder = function;
    }

    @Override
    public void reload() {
        this.byNamespace = new SuffixArray();
        this.byPath = new SuffixArray();
        for (T t : this.entries) {
            this.index(t);
        }
        this.byNamespace.build();
        this.byPath.build();
    }

    @Override
    public void add(T t) {
        this.entryIds.put(t, this.entries.size());
        this.entries.add(t);
        this.index(t);
    }

    @Override
    public void clear() {
        this.entries.clear();
        this.entryIds.clear();
    }

    protected void index(T t) {
        this.identifierFinder.apply(t).forEach(identifier -> {
            this.byNamespace.add(t, identifier.getNamespace().toLowerCase(Locale.ROOT));
            this.byPath.add(t, identifier.getPath().toLowerCase(Locale.ROOT));
        });
    }

    protected int compare(T object1, T object2) {
        return Integer.compare(this.entryIds.getInt(object1), this.entryIds.getInt(object2));
    }

    @Override
    public List<T> findAll(String text) {
        int n = text.indexOf(58);
        if (n == -1) {
            return this.byPath.findAll(text);
        }
        List<T> _snowman2 = this.byNamespace.findAll(text.substring(0, n).trim());
        String _snowman3 = text.substring(n + 1).trim();
        List<T> _snowman4 = this.byPath.findAll(_snowman3);
        return Lists.newArrayList(new Iterator<T>(_snowman2.iterator(), _snowman4.iterator(), this::compare));
    }

    public static class Iterator<T>
    extends AbstractIterator<T> {
        private final PeekingIterator<T> field_5490;
        private final PeekingIterator<T> field_5491;
        private final Comparator<T> field_5492;

        public Iterator(java.util.Iterator<T> iterator, java.util.Iterator<T> iterator2, Comparator<T> comparator) {
            this.field_5490 = Iterators.peekingIterator(iterator);
            this.field_5491 = Iterators.peekingIterator(iterator2);
            this.field_5492 = comparator;
        }

        protected T computeNext() {
            while (this.field_5490.hasNext() && this.field_5491.hasNext()) {
                int n = this.field_5492.compare(this.field_5490.peek(), this.field_5491.peek());
                if (n == 0) {
                    this.field_5491.next();
                    return (T)this.field_5490.next();
                }
                if (n < 0) {
                    this.field_5490.next();
                    continue;
                }
                this.field_5491.next();
            }
            return (T)this.endOfData();
        }
    }
}

