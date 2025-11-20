/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.AbstractIterator
 *  com.google.common.collect.Iterators
 *  com.google.common.collect.Lists
 *  com.google.common.collect.PeekingIterator
 */
package net.minecraft.client.search;

import com.google.common.collect.AbstractIterator;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.google.common.collect.PeekingIterator;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.function.Function;
import java.util.stream.Stream;
import net.minecraft.client.search.IdentifierSearchableContainer;
import net.minecraft.client.search.SuffixArray;
import net.minecraft.util.Identifier;

public class TextSearchableContainer<T>
extends IdentifierSearchableContainer<T> {
    protected SuffixArray<T> byText = new SuffixArray();
    private final Function<T, Stream<String>> textFinder;

    public TextSearchableContainer(Function<T, Stream<String>> function, Function<T, Stream<Identifier>> function2) {
        super(function2);
        this.textFinder = function;
    }

    @Override
    public void reload() {
        this.byText = new SuffixArray();
        super.reload();
        this.byText.build();
    }

    @Override
    protected void index(T t) {
        super.index(t);
        this.textFinder.apply(t).forEach(string -> this.byText.add(t, string.toLowerCase(Locale.ROOT)));
    }

    @Override
    public List<T> findAll(String text) {
        int n = text.indexOf(58);
        if (n < 0) {
            return this.byText.findAll(text);
        }
        List _snowman2 = this.byNamespace.findAll(text.substring(0, n).trim());
        String _snowman3 = text.substring(n + 1).trim();
        List _snowman4 = this.byPath.findAll(_snowman3);
        List<T> _snowman5 = this.byText.findAll(_snowman3);
        return Lists.newArrayList(new IdentifierSearchableContainer.Iterator(_snowman2.iterator(), new Iterator(_snowman4.iterator(), _snowman5.iterator(), this::compare), this::compare));
    }

    static class Iterator<T>
    extends AbstractIterator<T> {
        private final PeekingIterator<T> field_5499;
        private final PeekingIterator<T> field_5500;
        private final Comparator<T> field_5501;

        public Iterator(java.util.Iterator<T> iterator, java.util.Iterator<T> iterator2, Comparator<T> comparator) {
            this.field_5499 = Iterators.peekingIterator(iterator);
            this.field_5500 = Iterators.peekingIterator(iterator2);
            this.field_5501 = comparator;
        }

        protected T computeNext() {
            boolean bl = !this.field_5499.hasNext();
            boolean bl2 = _snowman = !this.field_5500.hasNext();
            if (bl && _snowman) {
                return (T)this.endOfData();
            }
            if (bl) {
                return (T)this.field_5500.next();
            }
            if (_snowman) {
                return (T)this.field_5499.next();
            }
            int _snowman2 = this.field_5501.compare(this.field_5499.peek(), this.field_5500.peek());
            if (_snowman2 == 0) {
                this.field_5500.next();
            }
            return (T)(_snowman2 <= 0 ? this.field_5499.next() : this.field_5500.next());
        }
    }
}

