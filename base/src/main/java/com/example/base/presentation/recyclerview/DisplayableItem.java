package com.example.base.presentation.recyclerview;

import android.support.annotation.NonNull;

import com.google.auto.value.AutoValue;

/**
 * Created by Lucia on 11/07/2017.
 */
@AutoValue
public abstract class DisplayableItem<T> {

    @NonNull
    public static <T> Builder<T> builder() {
        return new AutoValue_DisplayableItem.Builder<>();
    }

    @NonNull
    public static DisplayableItem toDisplayableItem(@NonNull final Object model, final int type) {
        return DisplayableItem.builder().type(type).model(model).build();
    }

    public abstract int type();

    @NonNull
    public abstract T model();

    @SuppressWarnings("NullableProblems")
    @AutoValue.Builder
    public abstract static class Builder<T> {

        @NonNull
        public abstract Builder<T> type(@NonNull int type);

        @NonNull
        public abstract Builder<T> model(@NonNull T model);

        @NonNull
        public abstract DisplayableItem<T> build();
    }
}
