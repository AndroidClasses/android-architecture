/*
 * Copyright 2016, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.clean.common.usecase;

/**
 * Use cases are the entry points to the domain layer.
 *
 * @param <Q> the request type
 * @param <P> the response type
 */
public abstract class UseCase<Q extends UseCase.RequestValues, P extends UseCase.ResponseValue> {
    private static final EmptyRequestValues EMPTY_REQUEST_VALUES = new EmptyRequestValues();
    public static final EmptyResponseValue EMPTY_RESPONSE_VALUE = new EmptyResponseValue();

    private Q requestValues;

    private UseCaseCallback<P> useCaseCallback;

    public void setRequestValues(Q requestValues) {
        this.requestValues = requestValues;
    }

    public Q getRequestValues() {
        return requestValues;
    }

    public UseCaseCallback<P> getUseCaseCallback() {
        return useCaseCallback;
    }

    public void setUseCaseCallback(UseCaseCallback<P> useCaseCallback) {
        this.useCaseCallback = useCaseCallback;
    }

    public void run() {
       executeUseCase(requestValues);
    }

    protected abstract void executeUseCase(Q requestValues);

    /**
     * Data passed to a request.
     */
    public interface RequestValues {
    }

    /**
     * Data received from a request.
     */
    public interface ResponseValue {
    }

    public interface UseCaseCallback<R> {
        void onSuccess(R response);
        void onError();
    }

    public static final class EmptyRequestValues implements RequestValues { }
    public static final class EmptyResponseValue implements ResponseValue { }

    public static EmptyRequestValues emptyRequestValues() {
        return EMPTY_REQUEST_VALUES;
    }

    public static EmptyResponseValue emptyResponseValue() {
        return EMPTY_RESPONSE_VALUE;
    }
}
