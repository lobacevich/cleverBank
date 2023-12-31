DROP DATABASE IF EXISTS clever_bank;
CREATE DATABASE clever_bank;

CREATE TABLE users(
    id SERIAL PRIMARY KEY,
    firstname VARCHAR(31) NOT NULL,
    lastname VARCHAR(31) NOT NULL,
    surname VARCHAR(31) NOT NULL,
    personal_number VARCHAR(23) NOT NULL UNIQUE,
    address VARCHAR(135) NOT NULL
);

CREATE TABLE banks(
    id SERIAL PRIMARY KEY,
    name VARCHAR(31) NOT NULL UNIQUE,
    address VARCHAR(135) NOT NULL
);

CREATE TABLE accounts(
 id SERIAL PRIMARY KEY,
	user_id INT NOT NULL,
	bank_id INT NOT NULL,
	account_number VARCHAR(23) NOT NULL UNIQUE,
	creation_date TIMESTAMP NOT NULL,
	balance DEC NOT NULL,
	last_interest TIMESTAMP NOT NULL,
	FOREIGN KEY(user_id) REFERENCES users(id) ON DELETE CASCADE,
	FOREIGN KEY(bank_id) REFERENCES banks(id) ON DELETE CASCADE
);

CREATE TABLE transactions(
    id SERIAL PRIMARY KEY,
    account_sender_id INT,
    account_receiver_id INT,
    date_time TIMESTAMP NOT NULL,
    summ DEC NOT NULL,
    FOREIGN KEY(account_sender_id) REFERENCES accounts(id),
    FOREIGN KEY(account_receiver_id) REFERENCES accounts(id)
);

INSERT INTO banks(name, address) VALUES
('Clever-Bank', '�����, ��.������ 40'),
('Priorbank', '�����, ��.�.������� 31�'),
('Alfabank', '�����, ��.��������� 43'),
('Belarusbank', '�����, �������� ������������ 18'),
('���� ��������', '�����, ��.������������� 185');

INSERT INTO users(firstname, lastname, surname, personal_number, address) VALUES
('������', '�����', '����������' , '3280296A022PB6', '�.����� ,������ ��., ��� 38/1, ��. 27'),
('�������', '�������', '���������', '3280376A013PB1', '�.�����,	����������� ��., ��� 5, ��. 150'),
('������', '������', '����������', '4010697A036PB3', '�.�����,	��������� ��., ��� 28, ��. 142'),
('�������', '�������', '����������', '3210273A049PB4', '�.�����,	������������ ��., ��� 4, ��. 122'),
('������', '�������', '���������', '4130879A012PB4', '�.�����,	������������� ��., ��� 82, ��. 95'),
('���������', '���', '����������', '4230590A028PB3', '�.�����,	����������� ��., ��� 164, ��. 48'),
('�����', '����', '��������', '3010777A042PB6', '�.�����,	������������ ��., ��� 20/2, ��. 30'),
('��������', '������', '���������', '4040179A049PB5', '�.�����,	��������� ��., ��� 27, ��. 54'),
('�������', '�������', '��������', '3110987A011PB3', '�.�����,	������� ��., ��� 54, ��. 1'),
('��������', '������', '��������������', '3030472A038PB2', '�.�����,	�������� ��., ��� 23, ��. 66'),
('������', '�������', '�������������', '3130188A024PB8', '�.�����,	����������� ��., ��� 41, ��. 133'),
('�����', '������', '����������', '3230974A049PB9', '�.�����,	��������� ��., ��� 61, ��. 42'),
('������', '�������', '����������', '3070773A010PB4', '�.�����,	�������� ��., ��� 143/1, ��. 201'),
('���������', '����', '����������', '4240583A025PB3', '�.�����,	������ ��., ��� 6, ��. 8'),
('�������', '���������', '����������', '4070781A037PB8', '�.�����,	������� ��., ��� 2, ��. 397'),
('���������', '�����', '��������', '4070779A037PB5', '�.�����,	������������� ��., ��� 101, ��. 79'),
('������', '����', '����������', '3060286A040PB4', '�.�����,	������� ��., ��� 9, ��. 37'),
('�������', '���������', '�����������', '3210577A024PB3', '�.�����,	������� ��., ��� 23, ��. 268'),
('����������', '�����', '�������������', '4270795A017PB9', '�.�����,	���������� ��., ��� 61, ��. 19'),
('��������', '����', '��������', '3230277A027PB3', '�.�����,	�������� ��., ��� 36/1, ��. 598');

INSERT INTO accounts(user_id, bank_id, account_number, creation_date, balance, last_interest) VALUES
(1, 5, '5814247048', '2007-05-12', 2655.09, '2023-08-05T22:20:28'),
(2, 2, '4316835699', '2020-05-24', 342.82, '2023-09-01T12:08:28'),
(3, 3, '2884293475', '1999-04-15', 214.24, '2023-08-06T01:48:28'),
(4, 4, '9252600454', '2021-08-28', 1032.23, '2023-08-12T03:46:28'),
(5, 4, '7216935986', '1998-02-28', 5514.44, '2023-08-02T23:21:28'),
(6, 4, '4903816229', '2015-05-18', 8693.54, '2023-08-17T04:43:28'),
(7, 2, '4051447839', '2019-09-17', 873.55, '2023-08-29T01:51:28'),
(8, 3, '4283511613', '2022-01-18', 5201.24, '2023-07-29T01:59:28'),
(9, 2, '8913792674', '2005-03-16', 2153.70, '2023-08-22T18:04:28'),
(10, 1, '6438427242', '2013-07-19', 4200.66, '2023-08-15T00:45:28'),
(11, 5, '4778607891', '1999-09-14', 4972.08, '2023-08-21T02:27:28'),
(12, 1, '3307497379', '2001-07-23', 6927.72, '2023-08-24T18:13:28'),
(13, 1, '7716504592', '1991-08-24', 5136.85, '2023-08-06T11:47:28'),
(14, 1, '4515667744', '2001-07-10', 1622.20, '2023-08-04T20:25:28'),
(15, 2, '3850048750', '1994-07-26', 9605.17, '2023-07-26T21:50:28'),
(16, 2, '2727500509', '2017-08-16', 4549.26, '2023-08-07T17:16:28'),
(17, 2, '3243833571', '2007-09-22', 8346.17, '2023-07-29T21:50:28'),
(18, 3, '2060310942', '2020-04-20', 7016.23, '2023-08-01T01:53:28'),
(19, 1, '1250076642', '2001-01-23', 1563.77, '2023-07-28T04:12:28'),
(20, 2, '4340847252', '2022-05-19', 5059.50, '2023-08-10T15:11:28'),
(1, 1, '1168089196', '2005-08-10', 6410.07, '2023-08-22T21:41:28'),
(2, 1, '5289538753', '2009-03-16', 3759.88, '2023-08-13T07:39:28'),
(3, 5, '7228756667', '2002-04-23', 2527.08, '2023-08-18T14:41:28'),
(4, 3, '8586709083', '1996-03-25', 5843.99, '2023-08-30T19:53:28'),
(5, 5, '5400395735', '2005-01-14', 9540.05, '2023-07-25T08:55:28'),
(6, 5, '3335010590', '2007-01-24', 9631.39, '2023-07-24T16:05:28'),
(7, 3, '6884826581', '1993-07-11', 3954.13, '2023-08-08T14:29:28'),
(8, 4, '4385150403', '2022-04-16', 6347.89, '2023-08-16T05:29:28'),
(9, 5, '3265589615', '1992-07-18', 2881.07, '2023-07-30T02:46:28'),
(10, 3, '5488974545', '1992-02-22', 6818.29, '2023-08-25T07:45:28'),
(11, 1, '7771326863', '2018-03-16', 5992.15, '2023-07-24T22:14:28'),
(12, 4, '7557456329', '1995-04-26', 7298.21, '2023-08-24T07:01:28'),
(13, 3, '1593052577', '2012-05-23', 3204.25, '2023-08-10T14:04:28'),
(14, 4, '3875673729', '2006-05-15', 642.46, '2023-08-05T02:02:28'),
(15, 3, '1177447353', '1992-06-14', 817.61, '2023-07-30T01:46:28'),
(16, 4, '4863084646', '2003-08-21', 5307.62, '2023-08-28T11:55:28'),
(17, 5, '2371178300', '2015-01-23', 7885.58, '2023-08-03T08:57:28'),
(18, 2, '8645651636', '1990-01-11', 6300.19, '2023-08-09T19:27:28'),
(19, 3, '8215272151', '2005-03-13', 7136.52, '2023-08-01T11:55:28'),
(20, 1, '2334588433', '1992-04-21', 9461.26, '2023-08-01T01:48:28');