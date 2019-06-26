

INSERT INTO USER(USERNAME,PASSWORD,ENABLE) VALUES ('liutaiq','{noop}123456',1);

INSERT INTO OAUTH_CLIENT_DETAILS(client_id,client_secret,scope,authorized_grant_types,web_server_redirect_uri,autoapprove) VALUES
('acau','{noop}acausecret','user_info','authorization_code','http://aa.ccdd','true'),
('accc','{noop}acccsecret','resource','	client_credentials',null,null),
('acme','{noop}acmesecret','oauth-test','password',null,null),
