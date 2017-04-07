package com.zenkun.wunder.utils;

import rx.Subscription;

/**
 * Created by Zen zenyagami@gmail.com on 07/04/2017.
 */

public class UtilRx {
    public static Subscription unsubscribe(Subscription sub) {
        if (sub != null && !sub.isUnsubscribed()) {
            sub.unsubscribe();
        }
        return null;
    }
}
