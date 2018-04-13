/**
 * Object Model For A Single Player
 */
public class Player
{
    private String profileLink;
    private String name;
    private Integer kills;
    private Integer deaths;
    private Integer assists;
    private String team;
    private Integer gamesPlayed = 0;

    public Player(String playerKey, String name, Integer kills, Integer deaths, Integer assists, String team) {
        this.profileLink = playerKey;
        this.name = name;
        this.kills = kills;
        this.deaths = deaths;
        this.assists = assists;
        this.team = team;

        if (kills != 0 || deaths != 0 || assists != 0)
            gamesPlayed = 1;
    }

    public String getProfileLink() {
        return profileLink;
    }

    public void setProfileLink(String profileLink) {
        this.profileLink = profileLink;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getKills() {
        return kills;
    }

    public void setKills(Integer kills) {
        this.kills = kills;
    }

    public Integer getDeaths() {
        return deaths;
    }

    public void setDeaths(Integer deaths) {
        this.deaths = deaths;
    }

    public Integer getAssists() {
        return assists;
    }

    public void setAssists(Integer assists) {
        this.assists = assists;
    }

    public String toString() {
        return profileLink + "\n" + name + "\n" + kills + "\n" + deaths + "\n" + assists + "\n" + team;
    }

    public void addStats(Player player) {
        if (player.kills != 0 && player.deaths != 0 && player.assists != 0)
        {
            kills += player.kills;
            deaths += player.deaths;
            assists += player.assists;
            gamesPlayed += 1;
        }
    }

    public Integer getGamesPlayed()
    {
        return gamesPlayed;
    }

    public void setGamesPlayed(Integer gamesPlayed)
    {
        this.gamesPlayed = gamesPlayed;
    }
}
