package sk.westland.core.quest;

public enum ESetQuestStateResult {

    QuestNotFound("Quest not found for player !"),
    SameState("The quest has already this state !"),
    CannotChange("Cannot change from current state !"),
    Changed("State successfully changed.");

    private final String message;

    ESetQuestStateResult(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
