CREATE TABLE IF NOT EXISTS Friendship (
  frienship_id SERIAL PRIMARY KEY,
  user_id INT NOT NULL,
  friend_id INT NOT NULL,
  CONSTRAINT ToOwner_FK FOREIGN KEY (user_id) REFERENCES Users(id),
  CONSTRAINT ToFriend_FK FOREIGN KEY (friend_id) REFERENCES Users(id)
);