import Foundation
import CatCrypto

@objc(RNArgon2)
class RNArgon2: NSObject {

  @objc
  static func requiresMainQueueSetup() -> Bool {
    return true
  }

  @objc
  func argon2(_ password: String, salt: String, resolver resolve: @escaping RCTPromiseResolveBlock, rejecter reject: @escaping RCTPromiseRejectBlock) -> Void {
    let argon2Context = CatArgon2Context.init();
    argon2Context.iterations = 2;
    argon2Context.memory = 32 * 1024;
    argon2Context.parallelism = 1;
    argon2Context.salt = salt;
    argon2Context.hashLength = 32;
    argon2Context.mode = .argon2id;

    let argon2Crypto = CatArgon2Crypto.init(context: argon2Context);
    let encodedResult = argon2Crypto.hash(password: password);

    // Set the hasher to hash as raw since encoded is the default
    argon2Crypto.context.hashResultType = .hashRaw;
    let rawResult = argon2Crypto.hash(password: password);

    if ((rawResult.error) != nil || (encodedResult.error) != nil) {
        let error = NSError(domain: "com.poowf.argon2", code: 200, userInfo: ["Error reason": "Failed to generate argon2 hash"])
        reject("E_ARGON2", "Failed to generate argon2 hash", error)
    }

    let rawHash = rawResult.hexStringValue();
    let encodedHash = encodedResult.stringValue();

    let resultDictionary: NSDictionary = [
        "rawHash" : rawHash,
        "encodedHash" : encodedHash,
    ]
    resolve(resultDictionary);
  }

}
