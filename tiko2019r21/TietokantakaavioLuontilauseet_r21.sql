


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
    projektihinta DECIMAL,
    kohteenOsoite VARCHAR(255),
    PRIMARY KEY (projektiID),
    FOREIGN KEY (asiakasID) REFERENCES asiakas ON DELETE CASCADE
);

CREATE TABLE tyosuoritus (
    tyosuoritusID SERIAL,
	projektiID INT,
    kokonaishinta DECIMAL,
    tyosuoritustyyppi VARCHAR(255),
    tuntimaara INT,
    tuntihinta FLOAT(2),
    kotitalousvahennys FLOAT(2),
    alv FLOAT(2),
    tyoTyyppi VARCHAR(255),
    PRIMARY KEY(tyosuoritusID),
	FOREIGN KEY(projektiID)REFERENCES tyoprojekti ON DELETE CASCADE
);

CREATE TABLE tarvike(
    tarvikeID SERIAL,
    tarvikenimi VARCHAR(255),
    sisaanostohinta FLOAT(2),
    myyntihinta FLOAT(2),
    yksikkotyyppi VARCHAR(255),
    alv DECIMAL,
    PRIMARY KEY (tarvikeID)
);



CREATE TABLE tarvikeluettelo(
    TyosuoritusID INT,
    TarvikeID INT,
    maara INT,
    FOREIGN KEY (TyosuoritusID) REFERENCES tyosuoritus ON DELETE CASCADE,
    FOREIGN KEY (tarvikeID) REFERENCES tarvike 
);

---Laskutus----

CREATE TABLE lasku(
    laskuID SERIAL,
    ProjektiID INT,
    laskutuslisa FLOAT(2),
    maksettuPvm DATE,
    laskunPaivamaara DATE,
    laskutyyppi VARCHAR(255),
    laskujarjestys INT,
    viivastyskorko FLOAT(2),
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
