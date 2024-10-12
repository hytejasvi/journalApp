package com.hytejasvi.journalApp.Entity;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;


@Document(collection = "journal_Entries")//explicitly telling the application to look for the particular collection
// (in mongodb collection is similar to what we have as table in sql) and if not existing, then create it
//@Getter
//@Setter
@Data //--> this is equal to @Getter @Setter @RequiredArgsConstructor @ToString @EqualsAndHashCode.
@NoArgsConstructor //Generates a no-args constructor.
@AllArgsConstructor
@Builder
public class JournalEntry {

    @Id // this marks the variable as primary key
    private ObjectId id;

    private String title;
    private String content;
    private LocalDateTime localDateTime;


    /*public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }*/
}
