CREATE TABLE sale (
  id BIGINT NOT NULL AUTO_INCREMENT,
  price INT NOT NULL,
  sell_date TIMESTAMP NOT NULL,

  ticket_account_id BIGINT NOT NULL,
  ticket_concert_id  BIGINT NOT NULL,

  CONSTRAINT pk_sale PRIMARY KEY(id),
  CONSTRAINT fk_account_id FOREIGN KEY (ticket_account_id) REFERENCES ticket(account_id),
  CONSTRAINT ticket_concert_id FOREIGN KEY(ticket_concert_id) REFERENCES ticket(concert_id)
);

CREATE TABLE audit_trail (
  id BIGINT NOT NULL AUTO_INCREMENT,
  audit_sale_id BIGINT NOT NULL,
  audit_account_id BIGINT NOT NULL,

  CONSTRAINT pk_audit PRIMARY KEY(id)
);