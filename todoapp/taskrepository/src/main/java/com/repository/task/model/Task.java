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

package com.repository.task.model;

import android.support.annotation.Nullable;

import com.google.common.base.Objects;

import java.util.UUID;

/**
 * Immutable model class for a Task.
 */
public final class Task {

    private final String id;

    @Nullable
    private final String title;

    @Nullable
    private final String description;

    private final boolean isCompleted;
    /**
     * Use this constructor to create a new active Task.
     *
     * @param title
     * @param description
     */
    public Task(@Nullable String title, @Nullable String description) {
        id = UUID.randomUUID().toString();
        this.title = title;
        this.description = description;
        isCompleted = false;
    }

    /**
     * Use this constructor to create an active Task if the Task already has an id (copy of another
     * Task).
     *
     * @param title
     * @param description
     * @param id of the class
     */
    public Task(@Nullable String title, @Nullable String description, String id) {
        this.id = id;
        this.title = title;
        this.description = description;
        isCompleted = false;
    }

    /**
     * Use this constructor to create a new completed Task.
     *
     * @param title
     * @param description
     * @param completed
     */
    public Task(@Nullable String title, @Nullable String description, boolean completed) {
        id = UUID.randomUUID().toString();
        this.title = title;
        this.description = description;
        isCompleted = completed;
    }

    /**
     * Use this constructor to specify a completed Task if the Task already has an id (copy of
     * another Task).
     *
     * @param title
     * @param description
     * @param id
     * @param completed
     */
    public Task(@Nullable String title, @Nullable String description, String id, boolean completed) {
        this.id = id;
        this.title = title;
        this.description = description;
        isCompleted = completed;
    }

    public String getId() {
        return id;
    }

    @Nullable
    public String getTitle() {
        return title;
    }

    @Nullable
    public String getTitleForList() {
        if (title != null && !title.equals("")) {
            return title;
        } else {
            return description;
        }
    }

    @Nullable
    public String getDescription() {
        return description;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public boolean isActive() {
        return !isCompleted;
    }

    public boolean isEmpty() {
        return (title == null || "".equals(title)) &&
                (description == null || "".equals(description));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return Objects.equal(id, task.id) &&
                Objects.equal(title, task.title) &&
                Objects.equal(description, task.description);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, title, description);
    }

    @Override
    public String toString() {
        return "Task with title " + title;
    }
}
