CREATE TABLE shoppingCart (
	sessionId varchar(36) NOT NULL,
	itemName varchar(100) NOT NULL,
	price numeric(6,2) NOT NULL,
	quantity integer NOT NULL,
	insertedAt timestamp NOT NULL default current_timestamp
);

ALTER TABLE shoppingCart OWNER TO oghaffar;
GRANT ALL ON TABLE shoppingcart TO oghaffar;