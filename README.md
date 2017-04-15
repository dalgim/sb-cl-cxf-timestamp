# sb-cl-cxf-ws-ssl-p12-producer

##Utworzenie klucza i certyfikatu serwera
###Generowanie klucza
openssl genrsa -des3 -out producer.key 4096
###Generowanie certyfikatu
openssl req -new -x509 -days 365 -key producer.key -out producer.crt
###Utworzenie pkcs12 keystore z wygenerowanego klucza i certyfikatu
openssl pkcs12 -export -name producercert -in producer.crt -inkey producer.key -out keystore.p12
openssl pkcs12 -export -name producercert -in server.crt -inkey server.key -out keystore.p12
###PRzekonwertowanie pkcs12 na plik jks
keytool -importkeystore -destkeystore producer-keystore.jks -srckeystore keystore.p12 -srcstoretype pkcs12 -alias producercert
keytool -importkeystore -destkeystore server-keystore.jks -srckeystore keystore.p12 -srcstoretype pkcs12 -alias producercert
###Weryfikacja zawrtości pliku .jks
keytool -list -v -keystore producer-keystore.jks

##Utworzenie certyfikatu i klucza dla klienta
###Tworzenie klucza
openssl genrsa -des3 -out consumer.key 4096
###Tworzenie certyfikatu
openssl req -new -key consumer.key -out consumer.csr
###Podpisanie certyfikatu
openssl x509 -req -days 1000 -in consumer.csr -CA producer.crt -CAkey producer.key -set_serial 01 -out consumer.crt
###Utworzenie pkcs12 z utworzeonego klucza i certyfikatu
openssl pkcs12 -export -clcerts -in consumer.crt -inkey consumer.key -out consumer.p12
openssl pkcs12 -export -name clientcert -in userA.crt -inkey userA.key -out truststore.p12


##Utworzenie truststore dla serwera
###Sprawdzenie aliasu certyfikatu klienta
keytool -v -list -keystore consumer.p12
###Wygenerowanie truststore
keytool -importkeystore -destkeystore producer-truststore.jks -srckeystore consumer.p12 -srcstoretype pkcs12 -alias 1

keytool -import -file userA.crt -alias consumer -keystore producer-truststore.jks