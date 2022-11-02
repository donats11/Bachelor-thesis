package loginApp.utils;

import io.smallrye.mutiny.Uni;

public class ApplicationUtils {
    public static Uni<Void> uniVoid() {
        return Uni.createFrom().voidItem();
    }

    public static <T> Uni<T> uniItem(T item) {
        return Uni.createFrom().item(item);
    }

    public static <T> Uni<T> uniFail(Throwable throwable) {
        return Uni.createFrom().failure(throwable);
    }

    public static <T> boolean notBlank(T entity) {
        return entity != null && entity.toString().getBytes().length > 0;
    }

    public static <T> boolean isBlank(T entity) {
        return entity == null || entity.toString().getBytes().length == 0;
    }
}

