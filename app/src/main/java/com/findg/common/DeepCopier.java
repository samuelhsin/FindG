package com.findg.common;

import com.rits.cloning.Cloner;

public final class DeepCopier extends Cloner {

    private static DeepCopier singleton;

    private static final Object clonePriority = new Object();

    public static DeepCopier getInstance() {
        if (singleton == null) {
            return createInstance();
        }
        return singleton;
    }

    public <T> T deepClone(final T o, boolean threadSafe) {
        if (threadSafe) {
            synchronized (clonePriority) {
                return super.deepClone(o);
            }
        } else {
            return super.deepClone(o);
        }
    }

    @Override
    public <T> T shallowClone(final T o) {
        synchronized (clonePriority) {
            return super.shallowClone(o);
        }
    }

    private static synchronized DeepCopier createInstance() {
        if (singleton == null) {
            singleton = new DeepCopier();
        }
        return singleton;
    }

    private DeepCopier() {
        super();
    }

}