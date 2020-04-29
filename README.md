# react-native-argon2

React Native Wrapper around the Argon2 implementation by SignalApp

## Getting started
```bash
npm install react-native-argon2 --save
```

### iOS Setup
Add the following line to your Podfile
```
pod 'Argon2', git: 'https://github.com/signalapp/Argon2.git', submodules: true
```

## Usage

In your code add `import argon2 from 'react-native-argon2';`.

```javascript
const result = await argon2(password, salt);
const { rawHash, encodedHash } = result;
```
