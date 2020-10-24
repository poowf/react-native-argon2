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

import com.lambdapioneer.argon2kt.Argon2Kt;
import com.lambdapioneer.argon2kt.Argon2KtResult;
import com.lambdapioneer.argon2kt.Argon2Mode;

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
    public void argon2(String password, String salt, Promise promise) {
        try {
            final byte[] passwordBytes = password.getBytes("UTF-8");
            final byte[] saltBytes = salt.getBytes("UTF-8");
            Integer iterations = new Integer(2);
            Integer memory = new Integer(32 * 1024);
            Integer parallelism = new Integer(1);
            Integer hashLength = new Integer(32);

            final Argon2Kt argon2Kt = new Argon2Kt();

            final Argon2KtResult hashResult = argon2Kt.hash(
                    Argon2Mode.ARGON2_ID,
                    passwordBytes,
                    saltBytes,
                    iterations,
                    memory,
                    parallelism,
                    hashLength);
            final String rawHash = hashResult.rawHashAsHexadecimal(false);
            final String encodedHash = hashResult.encodedOutputAsString();

            WritableMap resultMap = new WritableNativeMap();
            resultMap.putString("rawHash", rawHash);
            resultMap.putString("encodedHash", encodedHash);

            promise.resolve(resultMap);
        } catch (Exception exception) {
            promise.reject("Failed to generate argon2 hash", exception);
        }
    }
}
