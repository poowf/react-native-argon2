# react-native-argon2

React Native Wrapper around native Argon2 implementations:

iOS: [CatCrypto](//github.com/ImKcat/CatCrypto)

Android: [argon2kt](//github.com/lambdapioneer/argon2kt)

## Getting started
```bash
npm install react-native-argon2 --save
```

Compatibility Table

| React Native Version | Package Version |
|----------------------|-----------------|
| 0.60 - 0.63.2        | ~0.1.0         |
| 0.63.3               | ^1.0.0         |

## Usage

`rawHash` is the hexadecimal representation

`encodedHash` is the string representation

```javascript
import argon2 from 'react-native-argon2';
const password = 'password';
const salt = '1234567891011121314151617181920212223242526272829303132333435363';
const result = await argon2(password, salt);
const { rawHash, encodedHash } = result;
// rawHash: 031d6c82ddede1200f4794605052745dd562bd4db358e23dac1b11c052eff8d9
// encodedHash: $argon2id$v=19$m=32768,t=2,p=1$MTIzNDU2Nzg5MTAxMTEyMTMxNDE1MTYxNzE4MTkyMDIxMjIyMzI0MjUyNjI3MjgyOTMwMzEzMjMzMzQzNTM2Mw$Ax1sgt3t4SAPR5RgUFJ0XdVivU2zWOI9rBsRwFLv+Nk
```
