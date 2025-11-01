package com.poowf.argon2;

import android.app.Activity;
import android.content.Intent;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
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

    private byte[] hexStringToByteArray(String hex) {
        if (hex == null || hex.isEmpty()) {
            throw new IllegalArgumentException("Hex salt cannot be null or empty");
        }
        
        // Validate hex format
        if (hex.length() % 2 != 0) {
            throw new IllegalArgumentException("Hex salt must have even length, got: " + hex.length());
        }
        
        int len = hex.length();
        byte[] result = new byte[len / 2];
        
        try {
            for (int i = 0; i < len; i += 2) {
                int high = Character.digit(hex.charAt(i), 16);
                int low = Character.digit(hex.charAt(i + 1), 16);
                
                if (high == -1 || low == -1) {
                    throw new IllegalArgumentException("Invalid hex character at position " + i + " in: " + hex);
                }
                
                result[i / 2] = (byte) ((high << 4) + low);
            }
        } catch (StringIndexOutOfBoundsException e) {
            throw new IllegalArgumentException("Malformed hex string: " + hex, e);
        }
        
        return result;
    }

    @ReactMethod
    public void argon2(String password, String salt, ReadableMap config, Promise promise) {
        try {
            final byte[] passwordBytes = password.getBytes("UTF-8");
            
            String saltEncoding = config.hasKey("saltEncoding") ? config.getString("saltEncoding") : "utf8";
            final byte[] saltBytes = ("hex".equalsIgnoreCase(saltEncoding)) ? hexStringToByteArray(salt) : salt.getBytes("UTF-8");

            Integer iterations = config.hasKey("iterations") ? new Integer(config.getInt("iterations")) : new Integer(2);
            Integer memory = config.hasKey("memory") ? new Integer(config.getInt("memory")) : new Integer(32 * 1024);
            Integer parallelism = config.hasKey("parallelism") ? new Integer(config.getInt("parallelism")) : new Integer(1);
            Integer hashLength = config.hasKey("hashLength") ? new Integer(config.getInt("hashLength")) : new Integer(32);
            Argon2Mode mode = config.hasKey("mode") ? getArgon2Mode(config.getString("mode")) : Argon2Mode.ARGON2_ID;

            final Argon2Kt argon2Kt = new Argon2Kt();

            final Argon2KtResult hashResult = argon2Kt.hash(
                    mode,
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

    public Argon2Mode getArgon2Mode(String mode) {
        Argon2Mode selectedMode;
        switch (mode) {
            case "argon2d":
                selectedMode = Argon2Mode.ARGON2_D;
                break;
            case "argon2i":
                selectedMode = Argon2Mode.ARGON2_I;
                break;
            case "argon2id":
                selectedMode = Argon2Mode.ARGON2_ID;
                break;
            default:
                selectedMode = Argon2Mode.ARGON2_ID;
                break;
        }

        return selectedMode;
    }
}
