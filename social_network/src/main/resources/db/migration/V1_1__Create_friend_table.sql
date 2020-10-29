CREATE TABLE IF NOT EXISTS Friendship (
  user_id SERIAL,
  friend_id SERIAL,
  CONSTRAINT Friendship_PK PRIMARY KEY (user_id, friend_id), -- Composite PRIMARY KEY.
  CONSTRAINT ToUser_FK FOREIGN KEY (user_id) REFERENCES Users(id),
  CONSTRAINT ToFriend_FK FOREIGN KEY (friend_id) REFERENCES Users(id)
);