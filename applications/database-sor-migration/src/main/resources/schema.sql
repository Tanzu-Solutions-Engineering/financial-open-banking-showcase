

CREATE TABLE bank_accounts (
	acct_id text NOT NULL,
	bank_id text NOT NULL,
	acct_label text NOT NULL,
	acct_number text NOT NULL,
	acct_product_cd text NOT NULL,
	acct_routings text NOT NULL,
	acct_views_basic text NULL,
	acct_balance numeric NULL DEFAULT 0,
	update_ts timestamp(3) NULL DEFAULT CURRENT_TIMESTAMP,
	CONSTRAINT bank_accounts_pkey PRIMARY KEY (acct_id, bank_id)
);

drop table BANK_ATMS;

create TABLE BANK_ATMS (
   atm_id VARCHAR(50) NOT NULL,
   bank_id VARCHAR(50) NULL,
   atm_name VARCHAR(50) NULL,
    address_line_1 VARCHAR(50) NULL,
    address_line_2 VARCHAR(50) NULL,
    address_line_3 VARCHAR(50) NULL,
    address_city VARCHAR(50) NULL,
    address_county VARCHAR(50) NULL,
    address_state VARCHAR(50) NULL,
    address_postcode VARCHAR(50) NULL,
    address_country_code VARCHAR(50) NULL,
    meta_license_id VARCHAR(50) NULL,
    meta_license_name VARCHAR(50) NULL,
    monday_opening_time VARCHAR(50) NULL,
    monday_closing_time VARCHAR(50) NULL,
    tuesday_opening_time VARCHAR(50),
    tuesday_closing_time VARCHAR(50),
    wednesday_opening_time VARCHAR(50) NULL,
    wednesday_closing_time VARCHAR(50) NULL,
    thursday_opening_time VARCHAR(50) NULL,
    thursday_closing_time VARCHAR(50) NULL,
    friday_opening_time VARCHAR(50) NULL,
    friday_closing_time VARCHAR(50) NULL,
    saturday_opening_time VARCHAR(50) NULL,
    saturday_closing_time VARCHAR(50) NULL,
    sunday_opening_time VARCHAR(50) NULL,
    sunday_closing_time VARCHAR(50) NULL,
    is_accessible VARCHAR(50) NULL,
    located_at VARCHAR(50) NULL,
    more_info VARCHAR(50) NULL,
    has_deposit_capability VARCHAR(50) NULL,
    UPDATE_TS TIMESTAMP(3) DEFAULT CURRENT_TIMESTAMP
);



delete from bank_atms

update bank_atms set UPDATE_TS = CURRENT_TIMESTAMP

INSERT INTO bank_atms(atm_id, bank_id, atm_name, address_line_1, address_line_2, address_line_3, address_city, address_county, address_state, address_postcode, address_country_code, meta_license_id, meta_license_name, monday_opening_time, monday_closing_time, tuesday_opening_time, tuesday_closing_time, wednesday_opening_time, wednesday_closing_time, thursday_opening_time, thursday_closing_time, friday_opening_time, friday_closing_time, saturday_opening_time, saturday_closing_time, sunday_opening_time, sunday_closing_time, is_accessible, located_at, more_info, has_deposit_capability)
VALUES('test1', 'test1', 'test1', 'test1', '123 Street', '', '', 'Jersey City', '', 'NJ', '07306', 'US', 'test1', '', '9:00AM-9:00PM', '9:00AM-9:00PM', '9:00AM-9:00PM', '9:00AM-9:00PM', '9:00AM-9:00PM', '9:00AM-9:00PM', '9:00AM-9:00PM', '9:00AM-9:00PM', '9:00AM-9:00PM', '9:00AM-9:00PM', '9:00AM-9:00PM', '9:00AM-9:00PM', '9:00AM-9:00PM', '9:00AM-9:00PM', '9:00AM-9:00PM', '9:00AM-9:00PM', 'false');


SELECT atm_id, atm_id as id, bank_id, atm_name as name, concat('{"line_1":"', address_line_1,'",','"line_2": "',address_line_2,'"',', "line_3": "',address_line_3,'"',', "city": "',address_city,'"',', "county": "',address_county,'"',', "state": "',address_state,'"',', "postcode": "',address_postcode,'"',', "country_code": "',address_country_code,'"') as address,
concat('{"opening_time":','"',monday_opening_time,'"',', "closing_time":','"',monday_closing_time,'"}') as monday ,
concat('{"opening_time":','"',tuesday_opening_time,'"',', "closing_time":','"',tuesday_closing_time,'"}') as tuesday ,
concat('{"opening_time":','"',wednesday_opening_time,'"',', "closing_time":','"',wednesday_closing_time,'"}') as wednesday ,
concat('{"opening_time":','"',thursday_opening_time,'"',', "closing_time":','"',thursday_closing_time,'"}') as thursday ,
concat('{"opening_time":','"',friday_opening_time,'"',', "closing_time":','"',friday_closing_time,'"}') as friday ,
concat('{"opening_time":','"',saturday_opening_time,'"',', "closing_time":','"',saturday_closing_time,'"}') as saturday ,
concat('{"opening_time":','"',sunday_opening_time,'"',', "closing_time":','"',sunday_closing_time,'"}') as sunday ,
is_accessible, located_at, more_info, has_deposit_capability, update_ts
FROM bank_atms;

commit
