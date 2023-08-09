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



Utilizacion de 2 way tls


spring.security.oauth2.client.provider.keycloak.issuer-uri=https://accountsprevalidationsprb.icg.com.gt:8191/auth/realms/QS_GENERICspring.security.oauth2.client.provider.keycloak.user-name-attribute=preferred_usernamespring.security.oauth2.resourceserver.jwt.issuer-uri=https://accountsprevalidationsprb.icg.com.gt:8191/auth/realms/QS_GENERICICG.security.trustedissuers=https://accountsprevalidationsprb.icg.com.gt:8191/auth/realms/QS_AGROGTGC,https://accountsprevalidationsprb.icg.com.gt:8191/auth/realms/QS_INDLGTGC,https://accountsprevalidationsprb.icg.com.gt:8191/auth/realms/QS_AMCNGTGT,https://accountsprevalidationsprb.icg.com.gt:8191/auth/realms/QS_ANTGGTG1,https://accountsprevalidationsprb.icg.com.gt:8191/auth/realms/QS_AZTKGTGC,https://accountsprevalidationsprb.icg.com.gt:8191/auth/realms/QS_BAGUGTGC,https://accountsprevalidationsprb.icg.com.gt:8191/auth/realms/QS_BINMGTGC,https://accountsprevalidationsprb.icg.com.gt:8191/auth/realms/QS_BPRCGTGC,https://accountsprevalidationsprb.icg.com.gt:8191/auth/realms/QS_BRRLGTGC,https://accountsprevalidationsprb.icg.com.gt:8191/auth/realms/QS_CDSOGTG2,https://accountsprevalidationsprb.icg.com.gt:8191/auth/realms/QS_CITIGTGC,https://accountsprevalidationsprb.icg.com.gt:8191/auth/realms/QS_CHNAGTGC,https://accountsprevalidationsprb.icg.com.gt:8191/auth/realms/QS_DIBIGTGC,https://accountsprevalidationsprb.icg.com.gt:8191/auth/realms/QS_FCOHGTGC,https://accountsprevalidationsprb.icg.com.gt:8191/auth/realms/QS_FIVNGTG1,https://accountsprevalidationsprb.icg.com.gt:8191/auth/realms/QS_FSCGGTG1,https://accountsprevalidationsprb.icg.com.gt:8191/auth/realms/QS_GTCOGTGC,https://accountsprevalidationsprb.icg.com.gt:8191/auth/realms/QS_NSOAGTGT,https://accountsprevalidationsprb.icg.com.gt:8191/auth/realms/QS_TRAJGTGC,https://accountsprevalidationsprb.icg.com.gt:8191/auth/realms/QS_VIVBGTG1,https://accountsprevalidationsprb.icg.com.gt:8191`/auth/realms/QS_ICGSGTGC

Notar que en keycloak agregue el realm QS_GENERIC, el cual tiene como frontend url https://accountsprevalidationsprb.icg.com.gt:8191/auth/
El puerto 8191 debe estar expuesto en el load balancer (nginx) hacia keycloak SIN uso de 2 way tls. 
los clientes van a generar su token tal como se acordo, atraves del puerto 8081 usando tls mutuo (2 way).
para todos los reinos QS_{BIC}
el frontend url debe ser cambiado al puerto 8191
ejemplo:
https://accountsprevalidationsprb.icg.com.gt:8191/auth/

La explicacion de esto es que se esta inyectando el bean en la construccion del pod apuntando al reino generico mediante el oauth server sin usar 2 way tls, cuando el cliente en el banco o local a icg genere su token en el puerto con 2way, el frontend url de cada reino sobreescribe el issuer de ese jwt para que cuando el servicio de QS valide la emision de firmas de este token lo haga mediante el puerto sin 2 way (esto esta bien porque ya es local a ICG y es una opcion valida en arquitecturas donde se usan protecciones de borde sobre los k8s)



en esta version del modelo de autenticacion, los roles se crean asi

Agregar client_scope 
1. En la pestana "scope" (sin las comillas) del client_scope agregado, agregar el rol al que mapea este scope En el cliente, (este debe ser un rol de cliente cualquiera)
2. Agregar el rol de cliente (del paso 1) al usuario En el cliente,


El CLIENT_SCOPE debe llamarse validacion 