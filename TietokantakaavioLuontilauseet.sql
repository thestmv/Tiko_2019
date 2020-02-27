CREATE TABLE asiakas(
    asiakasID SERIAL,
    osoite VARCHAR(255),
    puhelinnumero VARCHAR(255),
    nimi VARCHAR(255),
    PRIMARY KEY(asiakasID)
);

CREATE TABLE tyoprojekti (
    projektiID SERIAL,
    asiakasID INT,
    tila BOOLEAN,
    projektihinta FLOAT,
    kohteenOsoite VARCHAR(255),
    PRIMARY KEY (projektiID),
    FOREIGN KEY (asiakasID) REFERENCES asiakas
);

CREATE TABLE tyosuoritus (
    tyosuoritusID SERIAL,
	projektiID INT,
    kokonaishinta FLOAT,
    tyosuoritustyyppi VARCHAR(255),
    tuntimaara INT,
    tuntihinta FLOAT,
    kotitalousvahennys FLOAT,
    alv FLOAT,
    tyoTyyppi VARCHAR(255),
    PRIMARY KEY(tyosuoritusID),
	FOREIGN KEY(projektiID)REFERENCES tyoprojekti
);

CREATE TABLE tarvike(
    tarvikeID SERIAL,
    tarvikenimi VARCHAR(255),
    sisaanostohinta FLOAT(2),
    myyntihinta FLOAT(2),
    yksikkotyyppi VARCHAR(255),
    alv FLOAT(2),
    PRIMARY KEY (tarvikeID)
);



CREATE TABLE tarvikeluettelo(
    TyosuoritusID INT,
    TarvikeID INT,
    maara INT,
    FOREIGN KEY (TyosuoritusID) REFERENCES tyosuoritus,
    FOREIGN KEY (tarvikeID) REFERENCES tarvike
);

---Laskutuspaska

CREATE TABLE lasku(
    laskuID SERIAL,
    ProjektiID INT,
    laskutuslisa FLOAT,
    maksettuPvm DATE,
    laskunPaivamaara DATE,
    laskutyyppi VARCHAR(255),
    laskujarjestys INT,
    viivastyskorko FLOAT,
    erapaiva DATE,
    PRIMARY KEY(laskuID),
	FOREIGN KEY (ProjektiID) REFERENCES tyoprojekti
);


CREATE TABLE laskunTila(
    laskuID INT,
	asiakasID INT,
    tila BOOLEAN,
    FOREIGN KEY(laskuID) REFERENCES lasku,
	FOREIGN KEY(asiakasID) REFERENCES asiakas
);




---Luontilauseet---


INSERT INTO asiakas VALUES(1, 'Järvitie 1', '044000000', 'Olli Asiakas');
INSERT INTO asiakas VALUES(2, 'Kuusitie 1 A3', '045324000', 'Anssi Ollikas');
INSERT INTO asiakas VALUES(3, 'Aasitie 3', '0453734060', 'Pasi Anssi');
INSERT INTO asiakas VALUES(4, 'Paasi 5', '045234650', 'Anssi Olli');
INSERT INTO asiakas VALUES(5, 'Järvitie 5', '045344780', 'Maija Olli');
INSERT INTO asiakas VALUES(6, 'Tuomiotie 6', '050487456', 'Tuomo Päivänen');

INSERT INTO tyosuoritus VALUES ( 1, 560, 'suunnittelu', 14, 40, null , 0.24, false);
INSERT INTO tyosuoritus VALUES ( 2, 450, 'työ', 10, 45, 0.5 , 0.24, false);
INSERT INTO tyosuoritus VALUES ( 3, 350, 'aputyo', 10, 35, 0.24, null, false);
INSERT INTO tyosuoritus VALUES ( 4, 500, null, 40, null, 0.24, null, true);
INSERT INTO tyosuoritus VALUES ( 6, 500, null, 32, null, 0.24, null, true);

INSERT INTO tarvike VALUES (1, 'Naula', 0.5, 0.75, 'Kpl', 0.24);
INSERT INTO tarvike VALUES (2, '5mm putki', 1, 1.25, 'M', 0.24);
INSERT INTO tarvike VALUES (3, '7mm putki', 0.5, 0.75, 'M', 0.24);
INSERT INTO tarvike VALUES (4, 'Vasara', 10, 15, 'Kpl', 0.24);
INSERT INTO tarvike VALUES (5, 'Ohjekirja', 10, 15, 'Kpl', 0.10);

INSERT INTO lasku VALUES(0001, 1, 5, '01-01-2018', '01-01-2018', 1, 1, 0, '01-04-2018');
INSERT INTO lasku VALUES(0002, 2, 5, '06-06-2018', '02-02-2018', 1, 1, 0, '03-04-2018');
INSERT INTO lasku VALUES(0003, 3, 5, '01-01-2018', '01-01-2018', 1, 1, 0, '03-04-2018');

INSERT INTO tarvikeluettelo VALUES(1, 1, 2);
INSERT INTO tarvikeluettelo VALUES(1, 2, 5);

INSERT INTO laskunTila VALUES(001, 0001, false, 1);
INSERT INTO laskunTila VALUES(002, 0002, false, 1);
INSERT INTO laskunTila VALUES(003, 0003, true, 3);

INSERT INTO tyoprojekti VALUES(1, 1, false, 560, 'Lintukuja 5');
INSERT INTO tyoprojekti VALUES(2, 2, false, 750, 'Pääkuja 2');
INSERT INTO tyoprojekti VALUES(3, 3, false, 350, 'Aasitie 3');


DROP TABLE asiakas, laskuntila, lasku, 
työprojekti, työt, Työsuoritus, Tarvikeluettelo, tarvike;

DROP TABLE asiakas, laskuntila, lasku, 
tyoprojekti, tyot, tyosuoritus, Tarvikeluettelo, tarvike;


DROP TABLE asiakas, laskuntila, lasku, tyosuoritus, tarvike;


DROP TABLE asiakas,lasku,laskuntila,
tyoprojekti, tarvikeluettelo, tarvike,tyosuoritus;

DROP TABLE asiakas, lasku, laskuntila, tarvike, tyoprojekti


GRANT USAGE, SELECT ON ALL SEQUENCES IN SCHEMA public TO ps427933;

DROP TABLE lasku CASCADE;

-- Tarvikeluettelolistan hakulause --
SELECT t.tarvikenimi, t.yksikkotyyppi, tl.maara, tl.tarvikeID, tl.tyosuoritusID
FROM tarvikeluettelo as tl, tarvike as t
WHERE tl.tarvikeID = t.tarvikeID

SELECT t.tarvikenimi, t.yksikkotyyppi, tl.maara, tl.tarvikeID, tl.tyosuoritusID
FROM tarvikeluettelo as tl, tarvike as t, tyosuoritus as ty
WHERE tl.tyosuoritusID = ty.tyosuoritusID AND tl.tarvikeID = t.tarvikeID;

SELECT DISTINCT t.tarvikenimi, t.yksikkotyyppi, tl.maara, tl.tarvikeID, tl.tyosuoritusID
FROM tarvikeluettelo as tl, tarvike as t, tyosuoritus as ty
WHERE tl.tyosuoritusID = 12 AND tl.tarvikeID = t.tarvikeID;

-- Toimiva taulu -- 
SELECT DISTINCT tl.tarvikeID, tl.maara, t.yksikkotyyppi, t.tarvikenimi, "+
			"tl.tyosuoritusID " +
			"FROM tarvikeluettelo as tl, tarvike as t, tyosuoritus as ty " +
			"WHERE tl.tarvikeID = t.tarvikeID AND " +
			"tl.tyosuoritusID = '" + aktiivinenSuoritus.getId() + "');
-- Tarvikeluettelolistan hakulause --

UPDATE TABLE tarvikeluettelo 
SET 
WHERE 


SELECT tl.tarvikeID, tl.maara, ta.myyntihinta, ta.alv, ty.tyosuoritusID, ty.kokonaishinta
FROM tyoprojekti as ty, tyosuoritus as ts, tarvikeluettelo as tl, tarvike as ta
WHERE 


SELECT DISTINCT * 
FROM tarvike
WHERE tarvikenimi LIKE '%pallo%';


