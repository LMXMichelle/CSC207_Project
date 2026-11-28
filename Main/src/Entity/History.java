package Entity;

import java.time.LocalDateTime;

public class History {
    private final String username;
    private final String difficulty;
    private final LocalDateTime completedTime;

    public History(String username, String difficulty, LocalDateTime completedTime){
        this.username = username;
        this.difficulty = difficulty;
        this.completedTime = completedTime;
    }

    public String getUsername() {
        return username;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public LocalDateTime getCompletedTime() {
        return completedTime;
    }
}
