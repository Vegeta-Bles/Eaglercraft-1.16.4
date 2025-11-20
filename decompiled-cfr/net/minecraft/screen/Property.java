/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.screen;

import net.minecraft.screen.PropertyDelegate;

public abstract class Property {
    private int oldValue;

    public static Property create(PropertyDelegate delegate, int index) {
        return new Property(delegate, index){
            final /* synthetic */ PropertyDelegate field_17308;
            final /* synthetic */ int field_17309;
            {
                this.field_17308 = propertyDelegate;
                this.field_17309 = n;
            }

            public int get() {
                return this.field_17308.get(this.field_17309);
            }

            public void set(int value) {
                this.field_17308.set(this.field_17309, value);
            }
        };
    }

    public static Property create(int[] array, int index) {
        return new Property(array, index){
            final /* synthetic */ int[] field_17310;
            final /* synthetic */ int field_17311;
            {
                this.field_17310 = nArray;
                this.field_17311 = n;
            }

            public int get() {
                return this.field_17310[this.field_17311];
            }

            public void set(int value) {
                this.field_17310[this.field_17311] = value;
            }
        };
    }

    public static Property create() {
        return new Property(){
            private int value;

            @Override
            public int get() {
                return this.value;
            }

            @Override
            public void set(int value) {
                this.value = value;
            }
        };
    }

    public abstract int get();

    public abstract void set(int var1);

    public boolean hasChanged() {
        int n = this.get();
        boolean _snowman2 = n != this.oldValue;
        this.oldValue = n;
        return _snowman2;
    }
}

