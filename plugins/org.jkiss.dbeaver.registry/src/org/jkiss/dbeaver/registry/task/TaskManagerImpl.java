/*
 * DBeaver - Universal Database Manager
 * Copyright (C) 2010-2019 Serge Rider (serge@jkiss.org)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jkiss.dbeaver.registry.task;

import org.jkiss.code.NotNull;
import org.jkiss.dbeaver.DBException;
import org.jkiss.dbeaver.model.app.DBPProject;
import org.jkiss.dbeaver.model.task.DBTTaskConfiguration;
import org.jkiss.dbeaver.model.task.DBTTaskDescriptor;
import org.jkiss.dbeaver.model.task.DBTTaskManager;
import org.jkiss.dbeaver.model.task.DBTTaskRegistry;
import org.jkiss.dbeaver.registry.ProjectMetadata;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * TaskManagerImpl
 */
public class TaskManagerImpl implements DBTTaskManager {

    public static final String CONFIG_FILE = "tasks.json";

    private final ProjectMetadata projectMetadata;
    private final List<TaskConfigurationImpl> tasks = new ArrayList<>();

    public TaskManagerImpl(ProjectMetadata projectMetadata) {
        this.projectMetadata = projectMetadata;
        loadConfiguration();
    }

    @NotNull
    @Override
    public DBTTaskRegistry getRegistry() {
        return TaskRegistry.getInstance();
    }

    @NotNull
    @Override
    public DBPProject getProject() {
        return projectMetadata;
    }

    @NotNull
    @Override
    public DBTTaskConfiguration[] getTaskConfigurations() {
        return tasks.toArray(new DBTTaskConfiguration[0]);
    }

    @NotNull
    @Override
    public DBTTaskConfiguration createTaskConfiguration(String taskId, String label, String description, Map<String, Object> properties) throws DBException {
        DBTTaskDescriptor taskDescriptor = getRegistry().getTask(taskId);
        if (taskDescriptor == null) {
            throw new DBException("Task " + taskId + " not found");
        }
        Date createTime = new Date();
        TaskConfigurationImpl task = new TaskConfigurationImpl(label, description, createTime, createTime, taskDescriptor, properties);
        tasks.add(task);

        saveConfiguration();

        return task;
    }

    @Override
    public void updateTaskConfiguration(DBTTaskConfiguration task) {
        throw new RuntimeException("Not Implemented");
    }

    @Override
    public void deleteTaskConfiguration(DBTTaskConfiguration task) {
        throw new RuntimeException("Not Implemented");
    }

    private void loadConfiguration() {

    }

    private void saveConfiguration() {

    }

}