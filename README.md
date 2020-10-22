# react-native-argon2

React Native Wrapper around the Argon2 implementation by SignalApp

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
const result = await argon2(password, salt);
const { rawHash, encodedHash } = result;
```
