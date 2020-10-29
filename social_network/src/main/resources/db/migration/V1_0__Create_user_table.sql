CREATE TABLE IF NOT EXISTS Users (
  id SERIAL PRIMARY KEY,
  first_name VARCHAR(50) NOT NULL,
  last_name VARCHAR(50) NOT NULL,
  age INT,
  gender VARCHAR(50),
  interests VARCHAR(1045),
  town VARCHAR(250)
);