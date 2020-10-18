# react-native-argon2

React Native Wrapper around the Argon2 implementation by SignalApp

## Getting started
```bash
npm install react-native-argon2 --save
```

## Usage

```javascript
import argon2 from 'react-native-argon2';
const result = await argon2(password, salt);
const { rawHash, encodedHash } = result;
```
