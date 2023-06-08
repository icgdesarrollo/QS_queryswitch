Queryswitch

Paremetrizacion de envio de logs a broker:
ICG.logs.description: '"Instruccion recibida en QS”' ICG.logs.generalLOGExchange: logs ICG.logs.generalLOGRouting: general_log ICG.logs.location:ICG ICG.logs.loggingapp: QS_WS ICG.logs.system: QS

Nota: Importante diferenciar estos para diferentes ambientes.

Parametrizacion de consumidor de cola:
ICG.QS.SYNCMESSAGES: "001" jsa.rabbitmq.exchange: QS_INPUT_H jsa.rabbitmq.headername: routing jsa.rabbitmq.headervalue: QS_POST_ spring.rabbitmq.host: 190.190.1.11 spring.rabbitmq.password: achv@l spring.rabbitmq.username: achval

Nota: el headervaule es el prefijo que indica a que cola debe enviar qs el mensaje, estos mensajes seran consumidos por el rt respectivamente.

Parametrización de parametros de seguridad: (nuevos en esta version)


spring.security.oauth2.client.registration.keycloak.client-id=qs_client
spring.security.oauth2.client.registration.keycloak.authorization-grant-type=client_credentials
spring.security.oauth2.client.registration.keycloak.scope=openid
spring.security.oauth2.client.provider.keycloak.issuer-uri=http://190.190.70.204:8080/auth/realms/QS_AGROGTGC
spring.security.oauth2.client.provider.keycloak.user-name-attribute=preferred_username
spring.security.oauth2.resourceserver.jwt.issuer-uri=http://190.190.70.204:8080/auth/realms/QS_AGROGTGC
logging.level.root=INFO
ICG.security.trustedissuers=http://190.190.70.204:8080/auth/realms/QS_AGROGTGC,http://190.190.70.204:8080/auth/realms/QS_INDLGTGC,http://190.190.70.204:8080/auth/realms/QS_AMCNGTGT,http://190.190.70.204:8080/auth/realms/QS_ANTGGTG1,http://190.190.70.204:8080/auth/realms/QS_AZTKGTGC,http://190.190.70.204:8080/auth/realms/QS_BAGUGTGC,http://190.190.70.204:8080/auth/realms/QS_BINMGTGC,http://190.190.70.204:8080/auth/realms/QS_BPRCGTGC,http://190.190.70.204:8080/auth/realms/QS_BRRLGTGC,http://190.190.70.204:8080/auth/realms/QS_CDSOGTG2,http://190.190.70.204:8080/auth/realms/QS_CITIGTGC,http://190.190.70.204:8080/auth/realms/QS_CHNAGTGC,http://190.190.70.204:8080/auth/realms/QS_DIBIGTGC,http://190.190.70.204:8080/auth/realms/QS_FCOHGTGC,http://190.190.70.204:8080/auth/realms/QS_FIVNGTG1,http://190.190.70.204:8080/auth/realms/QS_FSCGGTG1,http://190.190.70.204:8080/auth/realms/QS_GTCOGTGC,http://190.190.70.204:8080/auth/realms/QS_NSOAGTGT,http://190.190.70.204:8080/auth/realms/QS_TRAJGTGC,http://190.190.70.204:8080/auth/realms/QS_VIVBGTG1,http://190.190.70.204:8080/auth/realms/QS_ICGSGTGC


Si se desea agregar un realm, unicamente se agrega en ICG.security.trustedissuers. El backend maneja todo lo necesario para entenderlo.
