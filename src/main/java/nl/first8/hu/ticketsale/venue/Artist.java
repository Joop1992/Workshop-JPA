package nl.first8.hu.ticketsale.venue;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Artist {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "name")
    public String name;

    @Column(name = "genre")
    public Genre genre;
}
