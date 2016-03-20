package domain;

import javax.persistence.*;

@Entity
@Table(name="BOOK_CONTENT")
public class BookContent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "BOOK_ID")
    private Book book;

    // store books, only if their size less than about 10 Mb
    @Column(name = "CONTENT", length = 10000000)
    @Lob
    private byte[] content;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }
}
