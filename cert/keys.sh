#/usr/bin/env bash

## Generar contraseñas seguras
keyStorePassword=$(openssl rand -base64 32)
privateKeyPassword=$(openssl rand -base64 32)

## Server KeyStore: Private Key + Public Certificate (PKCS12)
keytool -genkeypair -alias serverKeyPair -keyalg RSA -keysize 4096 -validity 365 -storetype PKCS12 -keystore server_keystore.p12 -storepass "$keyStorePassword"

## Server KeyStore: Private Key + Public Certificate (JKS)
keytool -genkeypair -alias serverKeyPair -keyalg RSA -keysize 4096 -validity 365 -storetype JKS -keystore server_keystore.jks -storepass "$keyStorePassword"


## Server KeyStore: Private Key + Public Certificate (PKCS12)
##keytool -genkeypair -alias serverKeyPair -keyalg RSA -keysize 4096 -validity 365 -storetype PKCS12 -keystore server_keystore.p12 -storepass 1234567

## Server KeyStore: Private Key + Public Certificate (JKS)
##keytool -genkeypair -alias serverKeyPair -keyalg RSA -keysize 4096 -validity 365 -storetype JKS -keystore server_keystore.jks -storepass 1234567