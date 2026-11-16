ALTER TABLE health_agent drop constraint fk_agent_user;

ALTER TABLE health_agent
ADD CONSTRAINT fk_agent_user
FOREIGN KEY (user_id)
REFERENCES users(id);
