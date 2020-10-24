#import <React/RCTBridgeModule.h>

@interface RCT_EXTERN_MODULE(RNArgon2, NSObject)

RCT_EXTERN_METHOD(argon2: (NSString *)password salt:(NSString *)salt config:(NSDictionary *)config resolver:(RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject)

@end
