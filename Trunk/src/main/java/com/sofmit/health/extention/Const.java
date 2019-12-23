package com.sofmit.health.extention;

import com.querydsl.core.types.dsl.EntityPathBase;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class Const {
    public static final long serialVersionUID = 1390655718244486136L;
    public static final Map<EntityPathBase, Map<String, Object>> fieldMap;
    public static final Map<EntityPathBase, Map<String, Class<?>>> fieldClass;

    static {
        fieldMap = new HashMap<>();
        fieldClass = new HashMap<>();
        try {
            String basepackge = "com.sofmit.health.entity";
            String baseSourcePath = "/" + basepackge.replace(".", "/");
            URL url = Const.class.getResource("");
            // ====================================打jar是读取的jar包文件，所以使用这个====================
            if ("jar".equals(url.getProtocol())) {
                File rootPath = new File(URLDecoder.decode(System.getProperty("user.dir"), "UTF-8"));
                File[] rootfiles = rootPath.listFiles();
                for (File file : rootfiles) {
                    if (file.getName().endsWith(".jar")) {
                        JarFile jarFile = new JarFile(file.getPath());
                        Enumeration<JarEntry> entryEnumeration = jarFile.entries();

                        while (entryEnumeration.hasMoreElements()) {
                            JarEntry jarEntry = entryEnumeration.nextElement();
                            String name = jarEntry.getName();
                            if (name.endsWith(".class") && name.contains(baseSourcePath)) {
                                name = name.replace("/", ".");
                                name = name.substring(name.indexOf(basepackge));
                                name = name.substring(0, name.lastIndexOf("."));
                                Class clss = Class.forName(name);
                                if (EntityPathBase.class.isAssignableFrom(clss)) {
                                    String fieldName = name.substring(name.lastIndexOf(".") + 1);
                                    fieldName = fieldName.substring(1, 2).toLowerCase() + fieldName.substring(2);
                                    EntityPathBase entityPathBase = (EntityPathBase) clss.getField(fieldName).get(null);
                                    Map<String, Object> stringFieldMap = new HashMap<>();
                                    Map<String, Class<?>> classFieldMap = new HashMap<>();
                                    for (Field field : clss.getFields()) {
                                        if (!Modifier.isStatic(field.getModifiers())) {
                                            stringFieldMap.put(field.getName(), field.get(entityPathBase));
                                        }
                                    }
                                    name = name.substring(0, name.lastIndexOf("."))
                                            + "." + fieldName.substring(0, 1).toUpperCase()
                                            + fieldName.substring(1);
                                    Class clzz = Class.forName(name);
                                    while (clzz != null) {
                                        for (Field field : clzz.getDeclaredFields()) {
                                            if (!Modifier.isStatic(field.getModifiers())) {
                                                classFieldMap.put(field.getName(), field.getType());
                                            }
                                        }
                                        clzz = clzz.getSuperclass();
                                    }
                                    fieldMap.put(entityPathBase, stringFieldMap);
                                    fieldClass.put(entityPathBase, classFieldMap);
                                }
                            }
                        }
                    }
                }
            } else {
                // =========================测试是没打包的文件路径===========================
                File file = new File(URLDecoder.decode(Const.class.getResource(baseSourcePath).getPath(), "UTF-8"));
                String classPath = new File(URLDecoder.decode(Const.class.getResource("/").getPath(), "UTF-8"))
                        .getPath() + File.separator;
                File[] files = file.listFiles();
                String constansSource = basepackge.replace(".", File.separator);
                for (File file1 : files) {
                    String filename = file1.getName();
                    if (file1.getPath().contains(constansSource) && filename.endsWith(".class")) {
                        String className = file1.getPath().replace(File.separator, ".");
                        className = className.substring(className.indexOf(basepackge));
                        className = className.substring(0, className.lastIndexOf(".class"));
                        Class clss = Class.forName(className);
                        if (!EntityPathBase.class.isAssignableFrom(clss)) {
                            continue;
                        }
                        String fieldName = className.substring(className.lastIndexOf(".") + 1);
                        fieldName = fieldName.substring(1, 2).toLowerCase() + fieldName.substring(2);
                        EntityPathBase entityPathBase = (EntityPathBase) clss.getField(fieldName).get(null);
                        Map<String, Object> stringFieldMap = new HashMap<>();
                        Map<String, Class<?>> classFieldMap = new HashMap<>();
                        for (Field field : clss.getFields()) {
                            if (!Modifier.isStatic(field.getModifiers())) {
                                stringFieldMap.put(field.getName(), field.get(entityPathBase));
                            }
                        }
                        className = className.substring(0, className.lastIndexOf(".")) + "." + filename.substring(1)
                                .replace(".class", "");
                        Class clzz = Class.forName(className);
                        while (clzz != null) {
                            for (Field field : clzz.getDeclaredFields()) {
                                if (!Modifier.isStatic(field.getModifiers())) {
                                    classFieldMap.put(field.getName(), field.getType());
                                }
                            }
                            clzz = clzz.getSuperclass();
                        }
                        fieldMap.put(entityPathBase, stringFieldMap);
                        fieldClass.put(entityPathBase, classFieldMap);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
