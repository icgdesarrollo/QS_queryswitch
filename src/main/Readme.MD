Queryswitch

Paremetrizacion de envio de logs a broker:
ICG.logs.description: '"Instruccion recibida en QS”' ICG.logs.generalLOGExchange: logs ICG.logs.generalLOGRouting: general_log ICG.logs.location:ICG ICG.logs.loggingapp: QS_WS ICG.logs.system: QS

Nota: Importante diferenciar estos para diferentes ambientes.

Parametrizacion de consumidor de cola:
ICG.QS.SYNCMESSAGES: "001" jsa.rabbitmq.exchange: QS_INPUT_H jsa.rabbitmq.headername: routing jsa.rabbitmq.headervalue: QS_POST_ spring.rabbitmq.host: 190.190.1.11 spring.rabbitmq.password: achv@l spring.rabbitmq.username: achval

Nota: el headervaule es el prefijo que indica a que cola debe enviar qs el mensaje, estos mensajes seran consumidos por el rt respectivamente.

Parametrización de parametros de seguridad: keycloak.auth-server-url: 'http://190.190.1.11:8080/auth/' keycloak.bearer-only: 'true' keycloak.credentials.secret: 99b3271b-4c0b-4232-9985-6dc0f38c2b7b keycloak.principal-attribute: preferred_username keycloak.public-client: 'false' keycloak.realm: ValidaCuentas keycloak.resource: ACH_VALIDACION_PROD keycloak.ssl-required: none keycloak.use-resource-role-mappings: 'true' logging.level.org.keycloak: DEBUG
