CREATE TABLE Evento (id BIGINT NOT NULL PRIMARY KEY,nombre VARCHAR(255),descripcion VARCHAR(255),observaciones VARCHAR(255),lugar VARCHAR(255),duracionMinutos BIGINT,fechaHoraInicio TIMESTAMP);
CREATE TABLE Hueco (id BIGINT NOT NULL PRIMARY KEY,duracionMinutos BIGINT,fechaHoraInicio TIMESTAMP,reglaRecurrencia VARCHAR(255));