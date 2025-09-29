package com.github.ashvard.gdx.simple.common;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AnimFileRenamer {

    public static void main(String[] args) {
        String folderPath = "D:\\projects\\libgdx-example-happy-crab\\core\\assets\\animation\\green_fish";
        File folder = new File(folderPath);

        if (!folder.exists() || !folder.isDirectory()) {
            System.out.println("Ошибка: указанный путь не существует или не является папкой");
            return;
        }

        renameFiles(folder);
    }

    private static void renameFiles(File folder) {
        File[] files = folder.listFiles();
        if (files == null) return;

        // Более гибкий паттерн, который работает с разными префиксами
        Pattern pattern = Pattern.compile("^([a-zA-Z_]+?)[-_]?0+(\\d+)\\.([a-zA-Z0-9]+)$");

        int renamedCount = 0;

        for (File file : files) {
            if (file.isFile()) {
                String oldName = file.getName();
                Matcher matcher = pattern.matcher(oldName);

                if (matcher.matches()) {
                    String prefix = matcher.group(1);
                    String number = matcher.group(2);
                    String extension = matcher.group(3);

                    // Убираем подчеркивание или дефис с конца префикса, если есть
                    prefix = prefix.replaceAll("[-_]+$", "");

                    String newName = prefix + "_" + number + "." + extension;
                    File newFile = new File(folder, newName);

                    // Проверяем, не существует ли уже файл с таким именем
                    if (!newFile.exists()) {
                        if (file.renameTo(newFile)) {
                            System.out.println("Успех: " + oldName + " -> " + newName);
                            renamedCount++;
                        } else {
                            System.out.println("Ошибка: не удалось переименовать " + oldName);
                        }
                    } else {
                        System.out.println("Пропуск: файл " + newName + " уже существует");
                    }
                }
            }
        }

        System.out.println("Переименовано файлов: " + renamedCount);
    }
}
