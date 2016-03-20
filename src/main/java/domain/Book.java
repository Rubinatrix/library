package domain;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="BOOK")
public class Book implements Comparable<Book>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name="TITLE")
    private String title;

    @Column(name="DESCRIPTION", length = 2000)
    private String description;

    @Column(name="YEAR")
    private int year;

    @Column(name="AUTHOR")
    private String author;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "GENRE_ID")
    private Genre genre;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade =
                    {
                            CascadeType.DETACH,
                            CascadeType.MERGE,
                            CascadeType.REFRESH,
                            CascadeType.PERSIST
                    })
    @JoinTable(name = "USER_BOOK", joinColumns=@JoinColumn(name="BOOK_ID"), inverseJoinColumns=@JoinColumn(name="USER_ID"))
    private List<User> users;

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String name) {
        this.title = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    // to be able to do simple check whether the book is favorite
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Book book = (Book) o;

        return !(id != null ? !id.equals(book.id) : book.id != null);

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    // to be able to sort books in favorites list
    @Override
    public int compareTo(Book o) {

        int res = author.compareTo(o.author);
        if (res!=0) {
            return res;
        }

        res = title.compareTo(o.title);
        if (res!=0) {
            return res;
        }

        if (year>o.year) {
            return 1;
        } else if (year<o.year) {
            return -1;
        } else {
            return 0;
        }

    }
}
