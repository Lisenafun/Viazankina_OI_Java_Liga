CREATE TABLE IF NOT EXISTS Users (
  id SERIAL PRIMARY KEY,
  email VARCHAR(100) NOT NULL UNIQUE,
  first_name VARCHAR(50) NOT NULL,
  last_name VARCHAR(50) NOT NULL,
  age INT,
  gender VARCHAR(50),
  interests TEXT,
  town VARCHAR(250)
);