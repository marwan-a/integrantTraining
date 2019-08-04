INSERT IGNORE INTO privilege(name)
VALUES  ("READ_PRIVILEGE"),
		("WRITE_PRIVILEGE");
INSERT IGNORE INTO roles(name)
VALUES  ("ROLE_ADMIN"),			
		("ROLE_USER");	
INSERT IGNORE INTO roles_privileges(role_id,privilege_id)
VALUES  ((select role_id from roles where name="ROLE_ADMIN"),(select privilege_id from privilege where name="WRITE_PRIVILEGE")),
		((select role_id from roles where name="ROLE_ADMIN"),(select privilege_id from privilege where name="READ_PRIVILEGE")),
		((select role_id from roles where name="ROLE_USER"),(select privilege_id from privilege where name="READ_PRIVILEGE"));		
INSERT IGNORE INTO users(name,email,password,enabled)
VALUES  ("Frodo Baggins","Frodo@mordor.com","ring1234",true),
		("Bilbo Baggins","Bilbo@mordor.com","ring1234",false),
		("Samwise","Sam@mordor.com","ring1234",true);
INSERT IGNORE INTO users_roles(role_id,user_id)
VALUES  ((select role_id from roles where name="ROLE_ADMIN"),(SELECT user_id from users where email="Frodo@mordor.com" )),
		((select role_id from roles where name="ROLE_USER"),(SELECT user_id from users where email="Bilbo@mordor.com")),
		((select role_id from roles where name="ROLE_ADMIN"),(SELECT user_id from users where email="Sam@mordor.com"));
INSERT IGNORE INTO confirmationtoken(confirmation_token,user_id,createdDate,expiryDate)
VALUES 	(UUID(),(SELECT user_id from users where email="Frodo@mordor.com"),now(),NOW() + INTERVAL 1 DAY),
		(UUID(),(SELECT user_id from users where email="Bilbo@mordor.com"),now(),NOW() + INTERVAL 1 DAY);
