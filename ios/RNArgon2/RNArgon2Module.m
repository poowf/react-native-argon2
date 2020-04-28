#import "RNArgon2Module.h"
#import <Argon2.h>

@implementation RNArgon2Module

RCT_EXPORT_MODULE(RNArgon2Module);

RCT_EXPORT_METHOD(argon2:(NSString *)password salt:(NSString *)salt) resolver:(RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject) {
    const char* passwordChar = [password UTF8String];
    const char* saltChar = [salt UTF8String];

    @try {
        let result = Argon2.hash(
            iterations: 1,
            memoryInKiB: 32 * 1024,
            threads: 1,
            password: (uint8_t *)passwordChar,
            salt: (uint8_t *)saltChar,
            desiredLength: 32,
            variant: .id,
            version: .v13
        )
    }
    @catch (NSException * e) {
        NSError *error = [NSError errorWithDomain:@"com.poowf.argon2" code:200 userInfo:@{@"Error reason": @"Failed to generate argon2 hash"}];
        reject(@"Failed to generate argon2 hash", @"Error", error);
    }

    resolve(result);
}

@end
