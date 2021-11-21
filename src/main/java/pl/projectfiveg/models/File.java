package pl.projectfiveg.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.io.IOException;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class File {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    private String name;
    private String type;
    @Lob
    private byte[] data;
    @OneToOne
    private Task task;

    public File(MultipartFile file , Task task) throws IOException {
        this.name = file.getOriginalFilename();
        this.type = file.getContentType();
        this.data = file.getBytes();
        this.task = task;
    }

    public User getTaskOrderedUser() {
        return this.task.getUser();
    }

}
