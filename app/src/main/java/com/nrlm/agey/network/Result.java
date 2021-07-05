package com.nrlm.agey.network;

import androidx.annotation.Nullable;

import okhttp3.ResponseBody;

public abstract class Result<T> {
    private Result() {}

    public static final class Success<T> extends Result<T> {
        public T data;

        public Success(T data) {
            this.data = data;
        }
    }

    public static final class Error<T> extends Result<T> {
        public Exception exception;
        @Nullable
        public Boolean isNetworkError;
        @Nullable
        public int errorCode;
        @Nullable
        public ResponseBody errorBodey;

        public Error(Exception exception, @Nullable Boolean isNetworkError, int errorCode, @Nullable ResponseBody errorBodey) {
            this.exception = exception;
            this.isNetworkError = isNetworkError;
            this.errorCode = errorCode;
            this.errorBodey = errorBodey;
        }
    }
}
