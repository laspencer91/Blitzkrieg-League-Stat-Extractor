import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main
{
    private static String NAME_CLASS_STRING = "data-name";
    private static String KILLS_CLASS_STRING = "data-kills";
    private static String DEATHS_CLASS_STRING = "data-deaths";
    private static String ASSISTS_CLASS_STRING = "data-assists";

    private static String PlayerStatDataFile = "D:\\Git Projects\\blitzleagueextractor\\src\\main\\resources\\PlayerData.json";

    public static void main(String[] args) throws IOException
    {
        List<String> matchLinks = getAllMatchPageLinks("http://blitzkrieg.theplays.co.uk/nacl-schedule-week-1");
        List<Player> players = new ArrayList<>();

        for (String link : matchLinks)
            addPlayerStats(players, getPlayersFromMatchPage(link));

        writePlayerDataFile(PlayerStatDataFile, players);
        List<Player> playersDataList = readPlayerDataFile(PlayerStatDataFile);
    }

    private static List<String> getAllMatchPageLinks(String baseWeekPage) throws IOException
    {
        Document doc = Jsoup.connect(baseWeekPage).get();
        Elements links = doc.select("a");
        List<String> matchLinks = new ArrayList<>();

        for (Element link : links)
        {
            String linkString = link.attr("abs:href");
            if (linkString.contains("-vs-"))
                matchLinks.add(linkString);
        }

        return matchLinks;
    }

    /**
     * Write Player Data Json To File
     * @param filePath The file path to the file
     * @param players The list of players to write data for
     * @throws IOException
     */
    private static void writePlayerDataFile(String filePath, List<Player> players) throws IOException
    {
        String jsonData = new GsonBuilder().setPrettyPrinting().create().toJson(players);
        Files.write(Paths.get(filePath), jsonData.getBytes());
    }

    /**
     * Read from a json file, player data into an object model.
     * @param filePath The path to find the file at
     * @return List of players as read by file
     * @throws IOException
     */
    private static List<Player> readPlayerDataFile(String filePath) throws IOException
    {
        try(Reader reader = new InputStreamReader(new FileInputStream(filePath)))
        {
            Type listType = new TypeToken<ArrayList<Player>>(){}.getType();
            return new GsonBuilder().setPrettyPrinting().create().fromJson(reader, listType);
        }
    }

    /**
     * Add stats from one player list to an original list. New Players will be added to original list
     * @param original The list to be added to
     * @param newPlayerData The list that contains data to be added to the original
     */
    private static void addPlayerStats(List<Player> original, List<Player> newPlayerData)
    {
        Map<String, Player> playerMap1 = new HashMap<>();

        for (Player player : original) { playerMap1.put(player.getProfileLink(), player); }

        for (Player player : newPlayerData)
        {
            if (playerMap1.containsKey(player.getProfileLink()))
            {
                Player toAddTo = playerMap1.get(player.getProfileLink());
                toAddTo.addStats(player);
            }
            else
            {
                original.add(player);                            // Add new player to the original list
                playerMap1.put(player.getProfileLink(), player); // Update Current Map
            }
        }
    }

    /**
     * Retrieve list of players and their data from a match webpage
     * @param matchPageLink The link the a match page to parse
     * @return List of players found in the match page
     * @throws IOException
     */
    private static List<Player> getPlayersFromMatchPage(String matchPageLink) throws IOException
    {
        Document doc = Jsoup.connect(matchPageLink).get();

        List<Player> players = new ArrayList();

        // Get all lineups from single doc
        Elements lineups = doc.getElementsByClass("lineup");

        // Process All Lineups. Loop through each player on the page
        for (Element player : lineups)
        {
            String playerKey = player.getElementsByClass(NAME_CLASS_STRING).select("a").attr("abs:href");
            String playerName = player.getElementsByClass(NAME_CLASS_STRING).text();
            Integer kills = Integer.parseInt(player.getElementsByClass(KILLS_CLASS_STRING).text());
            Integer deaths = Integer.parseInt(player.getElementsByClass(DEATHS_CLASS_STRING).text());
            Integer assists = Integer.parseInt(player.getElementsByClass(ASSISTS_CLASS_STRING).text());

            String team = "Team Not Found";
            if (player.parent().parent().parent().parent().hasClass("sp-template"))
                team = player.parent().parent().parent().parent().getElementsByClass("sp-table-caption").text();

            players.add(new Player(playerKey, playerName, kills, deaths, assists, team));
        }

        return players;
    }
}
