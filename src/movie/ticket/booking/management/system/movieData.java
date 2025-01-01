package movie.ticket.booking.management.system;

import java.sql.Date;

public class movieData {

    private  Integer id;
    private String title;
    private String genre;
    private String duration;
    private String image;
    private Date date;


    public movieData(Integer id, String title, String genre, String duration, String image, Date date) {
        this.id = id;
        this.title = title;
        this.genre = genre;
        this.duration = duration;
        this.image = image;
        this.date = date;
    }

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }
    public String getGenre() {
        return genre;
    }
    public String getDuration() {
        return duration;
    }

    public String getImage() {
        return image;
    }

    public Date getDate() {
        return date;
    }
}
