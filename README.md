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

```javascript
import argon2 from 'react-native-argon2';
const password = 'password';
const salt = '1234567891011121314151617181920212223242526272829303132333435363';
const result = await argon2(password, salt, {});
const { rawHash, encodedHash } = result;
// rawHash: 031d6c82ddede1200f4794605052745dd562bd4db358e23dac1b11c052eff8d9
// encodedHash: $argon2id$v=19$m=32768,t=2,p=1$MTIzNDU2Nzg5MTAxMTEyMTMxNDE1MTYxNzE4MTkyMDIxMjIyMzI0MjUyNjI3MjgyOTMwMzEzMjMzMzQzNTM2Mw$Ax1sgt3t4SAPR5RgUFJ0XdVivU2zWOI9rBsRwFLv+Nk
```

### Input
The package takes in the following variables:

| Parameter           | Type    |
|---------------------|---------|
| password            | string  |
| salt                | string  |
| config              | object  |
| config.iterations   | integer |
| config.memory       | integer |
| config.parallelism  | integer |
| config.hashLength   | integer |
| config.mode         | string  |

You are not required to configure the third parameter which is the `config` object, however you do have to provide an empty object to it if you are not changing any of the values. You can set config values with the following example:

```javascript
const result = await argon2(
    password,
    salt,
    {
      iterations: 5,
      memory: 16 * 1024,
      parallelism: 2,
      hashLength: 20,
      mode: 'argon2i'
    }
);
```


### Output

`rawHash` is the hexadecimal representation

`encodedHash` is the string representation
