ALTER TABLE account
ADD COLUMN id BIGINT NOT NULL AUTO_INCREMENT,
DROP PRIMARY KEY,
ADD PRIMARY KEY (id);