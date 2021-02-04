package sk.westland.core.database.player;

import sk.westland.core.quest.QuestState;

import javax.persistence.*;

@Table(name = "wl_player_quests")
@Entity
public class UserQuestData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "nickname")
    private String name;
    private String uuid;

    private String quest_id;


    @Enumerated(EnumType.STRING)
    private QuestState questState;

    private int lastActiveTask;
}
