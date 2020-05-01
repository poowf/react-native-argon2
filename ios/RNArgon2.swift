import Foundation
import Argon2

@objc(RNArgon2)
class RNArgon2: NSObject {

  @objc
  static func requiresMainQueueSetup() -> Bool {
    return true
  }

  @objc
  func argon2(_ password: String, salt: String, resolver resolve: @escaping RCTPromiseResolveBlock, rejecter reject: @escaping RCTPromiseRejectBlock) -> Void {
    let passwordData: Data? = password.data(using: .utf8)
    let saltData: Data? = salt.data(using: .utf8)

    do {
        let (rawHash, encodedHash) = try Argon2.hash(
            iterations: 2,
            memoryInKiB: 32 * 1024,
            threads: 1,
            password: passwordData!,
            salt: saltData!,
            desiredLength: 32,
            variant: .id,
            version: .v13
        )

        let resultDictionary: NSDictionary = [
            "rawHash" : rawHash.hexEncodedString(),
            "encodedHash" : encodedHash,
        ]
        resolve(resultDictionary);
    }
    catch {
        let error = NSError(domain: "com.poowf.argon2", code: 200, userInfo: ["Error reason": "Failed to generate argon2 hash"])
        reject("E_ARGON2", "Failed to generate argon2 hash", error)
    }
  }

}

extension Data {
    struct HexEncodingOptions: OptionSet {
        let rawValue: Int
        static let upperCase = HexEncodingOptions(rawValue: 1 << 0)
    }

    func hexEncodedString(options: HexEncodingOptions = []) -> String {
        let hexDigits = Array((options.contains(.upperCase) ? "0123456789ABCDEF" : "0123456789abcdef").utf16)
        var chars: [unichar] = []
        chars.reserveCapacity(2 * count)
        for byte in self {
            chars.append(hexDigits[Int(byte / 16)])
            chars.append(hexDigits[Int(byte % 16)])
        }
        return String(utf16CodeUnits: chars, count: chars.count)
    }
}
