export type Argon2Mode = 'argon2id' | 'argon2i' | 'argon2d';
export interface Argon2Options {
  memory?: number;
  iterations?: number;
  parallelism?: number;
  hashLength?: number;
  mode?: Argon2Mode;
// 'utf8' (default, for backward compatibility)
  saltEncoding?: 'utf8' | 'hex';
}

export interface Argon2Result {
  rawHash: string;
  encodedHash: string;
}

declare function Argon2(
  password: string,
  salt: string,
  options?: Argon2Options
): Promise<Argon2Result>;

export default Argon2;
