package ohtu;

public class TennisGame {
    
    private int firstPlayersScore = 0;
    private int secondPlayersScore = 0;
    private final int POINT = 1;
    private final int ADVANTAGE_FOR_FIRST_PLAYER = 1;
    private final int ADVANTAGE_FOR_SECOND_PLAYER = -1;
    private final int WIN_FOR_FIRST_PLAYER = 2;
    private String firstPlayersName;
    private String secondPlayersName;


    public TennisGame(String firstPlayersName, String secondPlayersName) {
        this.firstPlayersName = firstPlayersName;
        this.secondPlayersName = secondPlayersName;
    }

    public void wonPoint(String playerName) {
        if (playerName.equals(firstPlayersName))
            firstPlayersScore += this.POINT;
        else
            secondPlayersScore += this.POINT;
    }

    public String getScore() {
        String score = "";
        if (firstPlayersScore==secondPlayersScore) {
            return scoreWhenPlayersAreEqual();
        } else if (firstPlayersScore>=4 || secondPlayersScore>=4) {
            return getResult();
        } else {
            return scoreWhenPlayersAreNotEqual(score);
        }
    }

    private String scoreWhenPlayersAreEqual() {
        switch (firstPlayersScore) {
                case 0:
                    return "Love-All";
                case 1:
                    return "Fifteen-All";
                case 2:
                    return "Thirty-All";
                case 3:
                    return "Forty-All";
                default:
                    return "Deuce";
            }
    }

    private String getResult() {
        String message = "";
        int difference = firstPlayersScore-secondPlayersScore;
        if (difference==this.ADVANTAGE_FOR_FIRST_PLAYER) message = "Advantage " + this.firstPlayersName;
        else if (difference == this.ADVANTAGE_FOR_SECOND_PLAYER) message = "Advantage " + this.secondPlayersName;
        else if (difference>=this.WIN_FOR_FIRST_PLAYER) message = "Win for " + this.firstPlayersName;
        else message = "Win for " + this.secondPlayersName;
        return message;
    }

    private String scoreWhenPlayersAreNotEqual(String score) {
        int tempScore=0;
        for (int i=1; i<3; i++) {
                if (i==1) {
                    tempScore = firstPlayersScore;
                } else {
                    score+="-";
                    tempScore = secondPlayersScore;
                }
                switch(tempScore) {
                    case 0:
                        score+="Love";
                        break;
                    case 1:
                        score+="Fifteen";
                        break;
                    case 2:
                        score+="Thirty";
                        break;
                    case 3:
                        score+="Forty";
                        break;
                }
        }
        return score;
    }
}