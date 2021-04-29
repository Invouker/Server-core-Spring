package sk.westland.core.hibernate;

public abstract class RunicException extends Exception {

    private String detailedMessage;

    RunicException(String message, String detailedMessage) {
        super(message);
        this.detailedMessage = detailedMessage;
    }

    private String getDetailedMessage() {
        return detailedMessage;
    }

    @Override
    public String getMessage() {
        return this.getMessage();
    }
}

class EntityNotFoundException extends RunicException {

    public EntityNotFoundException(Class<?> type) {
        super("[Hibernate] " + type.getName() + " could not be found!", "");
    }
}

class EntityManagerNotInitializedException extends RunicException {

    public EntityManagerNotInitializedException() {
        super("[Hibernate] EntityManager could not be initialized!", "");
    }
}