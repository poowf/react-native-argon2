import { NativeModules } from 'react-native';

const RNArgon2Module = NativeModules.RNArgon2;

export default async function argon2 (password, salt) {
  return RNArgon2Module.argon2(password, salt)
}
