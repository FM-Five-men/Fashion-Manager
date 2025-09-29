package fashionmanager.kim.develop.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "message")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Message {
    @Id
    @Column(name = "num")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int messageNum;

    @Column(name = "title")
    private String messageTitle;

    @Column(name = "content")
    private String messageContent;

    @Column(name = "date")
    private LocalDateTime messageDate;

    @Column(name = "exp_date")
    private LocalDateTime messageExpDate;

    @Column(name = "sender_num")
    private int messageSenderNum;

    @Column(name = "receiver_num")
    private int messageReceiverNum;

    @Column(name = "message_category_num")
    private int messageCategoryNum;

    @Column(name = "message_confirmed")
    private boolean messageConfirmed;
}
