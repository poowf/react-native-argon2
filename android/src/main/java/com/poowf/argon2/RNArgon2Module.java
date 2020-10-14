package com.poowf.argon2;

import android.app.Activity;
import android.content.Intent;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableNativeMap;
import com.facebook.react.bridge.Promise;

import org.signal.argon2.Argon2;
import org.signal.argon2.Version;
import org.signal.argon2.Type;
import org.signal.argon2.MemoryCost;

public class RNArgon2Module extends ReactContextBaseJavaModule {
    private ReactContext mReactContext;

    public RNArgon2Module(ReactApplicationContext reactContext) {
        super(reactContext);
        mReactContext = reactContext;
    }

    @Override
    public String getName() {
        return "RNArgon2";
    }

    @ReactMethod
    public void argon2(
        String password, 
        String salt, 
        Integer iterations, 
        Integer memoryCost, 
        Promise promise
    ) {
        try {
            Argon2 argon2 = new Argon2.Builder(Version.V13)
                    .iterations(iterations)
                    .memoryCost(MemoryCost.MiB(memoryCost))
                    .parallelism(1)
                    .hashLength(32)
                    .type(Type.Argon2id)
                    .build();

            final byte[] passwordBytes = password.getBytes("UTF-8");
            final byte[] saltBytes = salt.getBytes("UTF-8");

            Argon2.Result result = argon2.hash(passwordBytes, saltBytes);

            WritableMap resultMap = new WritableNativeMap();
            resultMap.putString("rawHash", result.getHashHex());
            resultMap.putString("encodedHash", result.getEncoded());

            promise.resolve(resultMap);
        } catch (Exception exception) {
            promise.reject("Failed to generate argon2 hash", exception);
        }

    }
}
