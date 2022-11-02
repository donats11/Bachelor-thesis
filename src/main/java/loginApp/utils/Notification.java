package loginApp.utils;

public class Notification {
    public String message;
    public NotificationType notificationType;

    private Notification() {
    }

    public static Notification NotificationOk(String message) {
        Notification notification = new Notification();
        notification.notificationType = NotificationType.OK;
        notification.message = message;
        return notification;
    }

    public static Notification NotificationError(String message) {
        Notification notification = new Notification();
        notification.notificationType = NotificationType.ERROR;
        notification.message = message;
        return notification;
    }
}
