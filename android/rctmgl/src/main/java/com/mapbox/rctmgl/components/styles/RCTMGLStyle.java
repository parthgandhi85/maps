package com.mapbox.rctmgl.components.styles;

import android.support.annotation.NonNull;
import android.support.annotation.StringDef;

import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableMapKeySetIterator;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.rctmgl.utils.DownloadMapImageTask;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nickitaliano on 9/12/17.
 */

public class RCTMGLStyle {
    private ReadableMap mReactStyle;
    private MapboxMap mMap;

    public RCTMGLStyle(@NonNull ReadableMap reactStyle, @NonNull MapboxMap map) {
        mReactStyle = reactStyle;
        mMap = map;
    }

    public List<String> getAllStyleKeys() {
        if (mReactStyle == null) {
            return new ArrayList<>();
        }

        ReadableMapKeySetIterator it = mReactStyle.keySetIterator();
        List<String> keys = new ArrayList<>();

        while (it.hasNextKey()) {
            String key = it.nextKey();

            if (!key.equals("__MAPBOX_STYLESHEET__")) {
                keys.add(key);
            }
        }

        return keys;
    }

    public RCTMGLStyleValue getStyleValueForKey(String styleKey) {
        ReadableMap styleValueConfig = mReactStyle.getMap(styleKey);

        if (styleValueConfig == null) {
            // TODO: throw exeception here
            return null;
        }

        return new RCTMGLStyleValue(styleValueConfig);
    }

    public void addImage(String uriStr) {
        if (uriStr == null) {
            return;
        }
        DownloadMapImageTask task = new DownloadMapImageTask(uriStr, mMap);
        task.execute();
    }
}