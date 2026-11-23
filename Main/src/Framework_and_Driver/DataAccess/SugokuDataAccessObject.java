package Framework_and_Driver.DataAccess;

import Entity.SudokuGame;

import UseCase.NewGame.NewGameDataAccessInterface;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Arrays;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class SugokuDataAccessObject implements NewGameDataAccessInterface {
    private static final Set<String> DIFFICULTIES = Set.of("easy", "medium", "hard");
    private static final String ROW = "\\[\\s*[0-9]\\s*(?:,\\s*[0-9]\\s*){8}\\]";
    private static final String GRID = "\\[\\s*" + ROW + "\\s*(?:,\\s*" + ROW + "\\s*){8}\\]";
    private static final Pattern BOARD = gridField("board");
    private static final Pattern SOLUTION = gridField("solution");
    private static final Pattern SOLVED =
            Pattern.compile("[{,]\\s*\"status\"\\s*:\\s*\"solved\"\\s*(?=[,}])");
    private static final Pattern DIGIT = Pattern.compile("[0-9]");

    private final HttpClient client;
    private final URI baseUri;
    private final Duration timeout;

    public SugokuDataAccessObject() {
        this(
                HttpClient.newBuilder()
                        .connectTimeout(Duration.ofSeconds(20))
                        .followRedirects(HttpClient.Redirect.NORMAL)
                        .build(),
                URI.create("https://sugoku.onrender.com/"),
                Duration.ofSeconds(90));
    }

    public SugokuDataAccessObject(HttpClient client, URI baseUri, Duration timeout) {
        this.client = client;
        this.baseUri = baseUri;
        this.timeout = timeout;
    }

    @Override
    public SudokuGame fetchGame(String difficulty) throws IOException, InterruptedException {
        if (difficulty == null || !DIFFICULTIES.contains(difficulty)) {
            throw new IllegalArgumentException("Unsupported difficulty");
        }

        HttpRequest boardRequest =
                HttpRequest.newBuilder(baseUri.resolve("board?difficulty=" + difficulty))
                        .timeout(timeout)
                        .GET()
                        .build();
        int[][] board = readGrid(send(boardRequest), BOARD, "board");

        // Arrays.deepToString gives the JSON array syntax for an int[][].
        String form =
                "board=" + URLEncoder.encode(Arrays.deepToString(board), StandardCharsets.UTF_8);
        HttpRequest solveRequest =
                HttpRequest.newBuilder(baseUri.resolve("solve"))
                        .timeout(timeout)
                        .header("Content-Type", "application/x-www-form-urlencoded")
                        .POST(HttpRequest.BodyPublishers.ofString(form))
                        .build();
        String solved = send(solveRequest);
        if (!SOLVED.matcher(solved).find()) {
            throw new IOException("Sugoku did not solve the board");
        }

        int[][] solution = readGrid(solved, SOLUTION, "solution");
        try {
            return new SudokuGame(board, solution, difficulty);
        } catch (IllegalArgumentException exception) {
            throw new IOException("Sugoku returned an invalid puzzle or solution", exception);
        }
    }

    private String send(HttpRequest request) throws IOException, InterruptedException {
        HttpResponse<String> response =
                client.send(request, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
        if (response.statusCode() != 200) {
            throw new IOException("Sugoku returned HTTP " + response.statusCode());
        }
        String body = response.body().strip();
        if (!body.startsWith("{") || !body.endsWith("}")) {
            throw new IOException("Sugoku did not return a JSON object");
        }
        return body;
    }

    private static Pattern gridField(String name) {
        return Pattern.compile("[{,]\\s*\"" + name + "\"\\s*:\\s*(" + GRID + ")\\s*(?=[,}])");
    }

    // The API's grid is exactly nine rows of nine single-digit numbers.
    private static int[][] readGrid(String body, Pattern field, String name) throws IOException {
        Matcher grid = field.matcher(body);
        if (!grid.find()) {
            throw new IOException("Invalid " + name + " grid from Sugoku");
        }
        Matcher digit = DIGIT.matcher(grid.group(1));
        int[][] values = new int[9][9];
        for (int[] row : values) {
            for (int column = 0; column < row.length; column++) {
                digit.find();
                row[column] = digit.group().charAt(0) - '0';
            }
        }
        return values;
    }
}
