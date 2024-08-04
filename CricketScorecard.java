import java.util.Scanner;
import java.util.Random;

public class CricketScorecard {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Step 1: User Input
        System.out.print("Enter name of Team 1: ");
        String team1 = scanner.nextLine();

        System.out.print("Enter name of Team 2: ");
        String team2 = scanner.nextLine();

        // Simulate a toss
        String battingTeam, bowlingTeam;
        Random rand = new Random();
        if (rand.nextInt(2) == 0) {
            battingTeam = team1;
            bowlingTeam = team2;
        } else {
            battingTeam = team2;
            bowlingTeam = team1;
        }

        System.out.println("Toss Result: " + battingTeam + " won the toss and will bat first.");

        // Step 2: Player Data
        String[] team1Players = new String[11];
        String[] team2Players = new String[11];

        inputPlayers(scanner, team1, team1Players);
        inputPlayers(scanner, team2, team2Players);

        // Initialize teams
        Team team1Data = new Team(team1, team1Players);
        Team team2Data = new Team(team2, team2Players);

        // Step 3: Simulate Batting
        simulateBatting(team1Data);
        simulateBatting(team2Data);

        // Step 4: Display Scorecard
        displayScorecard(team1Data);
        displayScorecard(team2Data);

        // Step 5: Determine Winner
        determineWinner(team1Data, team2Data);
    }

    // Function to input player names
    public static void inputPlayers(Scanner scanner, String team, String[] players) {
        System.out.println("Enter the names of players for " + team + ":");
        for (int i = 0; i < players.length; i++) {
            System.out.print("Player " + (i + 1) + ": ");
            players[i] = scanner.nextLine();
        }
    }

    // Function to simulate batting
    public static void simulateBatting(Team team) {
        Random rand = new Random();
        int balls = 0;
        for (int i = 0; i < team.players.length && balls < 20 * 6; i++) {
            Player player = team.players[i];
            while (balls < 20 * 6 && !player.isOut) {
                int outcome = rand.nextInt(8);
                if (outcome == 7) { // Wicket
                    player.isOut = true;
                    team.wicketsFallen++;
                } else {
                    player.runs += outcome;
                    team.totalRuns += outcome;
                }
                player.ballsFaced++;
                balls++;
                team.oversBowled = balls / 6;
            }
        }
    }

    // Function to display scorecard
    public static void displayScorecard(Team team) {
        System.out.println("Scorecard for " + team.name + ":");
        for (Player player : team.players) {
            System.out.println(player.name + " - " +
                    (player.isOut ? "Out" : "Not Out") + ", " +
                    "Runs: " + player.runs + ", " +
                    "Balls: " + player.ballsFaced + ", " +
                    "Strike Rate: " + (player.ballsFaced > 0 ? (player.runs * 100.0 / player.ballsFaced) : 0));
        }
        System.out.println("Total score: " + team.totalRuns + "/" + team.wicketsFallen + " in " + team.oversBowled + " overs");
    }

    // Function to determine winner
    public static void determineWinner(Team team1, Team team2) {
        if (team1.totalRuns > team2.totalRuns) {
            System.out.println(team1.name + " wins!");
        } else if (team1.totalRuns < team2.totalRuns) {
            System.out.println(team2.name + " wins!");
        } else {
            System.out.println("The match is a tie!");
        }
    }
}

// Player class to store player details
class Player {
    String name;
    int runs;
    int ballsFaced;
    boolean isOut;

    Player(String name) {
        this.name = name;
        this.runs = 0;
        this.ballsFaced = 0;
        this.isOut = false;
    }
}

// Team class to store team details
class Team {
    String name;
    Player[] players;
    int totalRuns;
    int wicketsFallen;
    int oversBowled;

    Team(String name, String[] playerNames) {
        this.name = name;
        this.totalRuns = 0;
        this.wicketsFallen = 0;
        this.oversBowled = 0;
        this.players = new Player[playerNames.length];
        for (int i = 0; i < playerNames.length; i++) {
            this.players[i] = new Player(playerNames[i]);
        }
    }
}
