package com.example.atgproject.Utils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class Resources<T> {

    public enum Status { SUCCESS, ERROR, LOADING }

    @NonNull
    public final Status status;
    @Nullable
    public final T data;
    @Nullable public final String message;

    private Resources(@NonNull Status status, @Nullable T data,
                     @Nullable String message) {
        this.status = status;
        this.data = data;
        this.message = message;
    }

    public static <T> Resources<T> success(@NonNull T data) {
        return new Resources<>(Status.SUCCESS, data, null);
    }

    public static <T> Resources<T> error(String msg, @Nullable T data) {
        return new Resources<>(Status.ERROR, data, msg);
    }

    public static <T> Resources<T> loading(@Nullable T data) {
        return new Resources<>(Status.LOADING, data, null);
    }
}