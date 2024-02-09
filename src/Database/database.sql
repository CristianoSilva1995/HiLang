--
-- File generated with SQLiteStudio v3.3.3 on Wed May 4 04:14:38 2022
--
-- Text encoding used: System
--
PRAGMA foreign_keys = off;
BEGIN TRANSACTION;

-- Table: context
DROP TABLE IF EXISTS context;

CREATE TABLE context (
                         idContext   INTEGER      PRIMARY KEY AUTOINCREMENT
                             NOT NULL,
                         contextName STRING (255) NOT NULL
                             UNIQUE
);


-- Table: conversation
DROP TABLE IF EXISTS conversation;

CREATE TABLE conversation (
                              idConversation   INTEGER      NOT NULL
                                  PRIMARY KEY AUTOINCREMENT,
                              idSubContext     INT          REFERENCES subcontext (idSubContext),
                              idLanguage       INT          REFERENCES language (idLanguage),
                              languageLevel    STRING (2)   NOT NULL,
                              grammarStructure STRING (255),
                              vocabulary       STRING (255),
                              dateTimeCreated  DATETIME
);


-- Table: conversationHistory
DROP TABLE IF EXISTS conversationHistory;

CREATE TABLE conversationHistory (
                                     idConversationHistory INTEGER  PRIMARY KEY AUTOINCREMENT,
                                     idUser                INT      REFERENCES user (idUser)
                                                                    NOT NULL,
                                     idSecondUser          INT      REFERENCES user (idUser)
                                                                    NOT NULL,
                                     idConversation        INT      NOT NULL
                                         REFERENCES conversation (idConversation),
                                     dateTimeFirstAccessed DATETIME NOT NULL,
                                     dateTimeLastAccessed  DATETIME NOT NULL,
                                     timesAccessed         INT      NOT NULL,
                                     completed             BOOLEAN
);


-- Table: language
DROP TABLE IF EXISTS language;

CREATE TABLE language (
                          idLanguage   INTEGER      PRIMARY KEY AUTOINCREMENT,
                          languageName STRING (100) UNIQUE
                              NOT NULL,
                          imgFile      STRING (100)
);


-- Table: speech
DROP TABLE IF EXISTS speech;

CREATE TABLE speech (
                        idSpeech       INTEGER      PRIMARY KEY AUTOINCREMENT,
                        idConversation INTEGER      REFERENCES conversation (idConversation),
                        person         CHAR         NOT NULL,
                        message        TEXT         NOT NULL,
                        translation    STRING (255)
);


-- Table: subcontext
DROP TABLE IF EXISTS subcontext;

CREATE TABLE subcontext (
                            idSubContext   INTEGER      PRIMARY KEY AUTOINCREMENT,
                            idContext      INT          REFERENCES context (idContext),
                            subContextName STRING (255) NOT NULL,
                            UNIQUE (
                                    idContext,
                                    subContextName
                                )
);


-- Table: user
DROP TABLE IF EXISTS user;

CREATE TABLE user (
                      idUser         INTEGER      PRIMARY KEY AUTOINCREMENT,
                      userName       STRING (32)  UNIQUE
                          NOT NULL,
                      firstName      STRING       NOT NULL,
                      lastName       STRING (32)  NOT NULL,
                      password       STRING (255) NOT NULL,
                      passwordSecret STRING (128) NOT NULL,
                      email          STRING (255) UNIQUE
                          NOT NULL,
                      registeredDate DATE         NOT NULL,
                      lastAccess     DATETIME     NOT NULL,
                      userType       INT          NOT NULL
);

--
-- File generated with SQLiteStudio v3.3.3 on Wed May 4 04:39:32 2022
--
-- Text encoding used: System
--
PRAGMA foreign_keys = off;
BEGIN TRANSACTION;

INSERT INTO language (
    idLanguage,
    languageName,
    imgFile
)
VALUES (
           1,
           'Spanish',
           'spain.png'
       );

COMMIT TRANSACTION;
PRAGMA foreign_keys = on;
