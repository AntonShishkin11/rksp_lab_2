package ru.shishkin.data_service.data;

@Getter
@Setter
@Entity
@Table(name = "student", schema = "utmn")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "fio")
    private String name;

    @Column(name = "passport")
    private String passport;
}
