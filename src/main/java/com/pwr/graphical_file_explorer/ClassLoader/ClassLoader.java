package com.pwr.graphical_file_explorer.ClassLoader;

import javafx.scene.image.Image;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.LinkedList;
import java.util.List;


public class ClassLoader extends java.lang.ClassLoader {

    private List<Class> loadedClasses;
    private String directory;
    private Class classTemp=null;
    private Method method = null;


    public ClassLoader(java.lang.ClassLoader parent, String directory) {
        super(parent);
        this.directory=directory;
        loadedClasses=new LinkedList<>();
    }

    public Method getMethod(Class loadedClass, String methodName)
    {
        try
        {
            return loadedClass.getMethod(methodName, Image.class);
        } catch (NoSuchMethodException ex)
        {
            return null;
        }
    }


    public Class load(String name){

        if(getClass(name)==null){
            try {

                String file = name.replace('.', File.separatorChar) + ".class";
                String url = directory+file;

                URL myUrl = new URL(url);
                URLConnection connection = myUrl.openConnection();
                InputStream input = connection.getInputStream();
                ByteArrayOutputStream buffer = new ByteArrayOutputStream();
                int data = input.read();

                while(data != -1){
                    buffer.write(data);
                    data = input.read();
                }

                input.close();

                byte[] classData = buffer.toByteArray();

                classTemp =defineClass(name,
                        classData, 0, classData.length);
                resolveClass(classTemp);
                loadedClasses.add(classTemp);
                return classTemp;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            return getClass(name);
        }


        return null;
    }

    public Class getClass(String className)
    {

        for (Class pluginClass : loadedClasses) {
            System.out.println(pluginClass.getName());
            if (pluginClass.getName().equals(className))
                return pluginClass;
        }
        return null;
    }

    public Boolean unLoadObject(){

        loadedClasses=null;
        classTemp=null;
        method=null;
        System.gc();
        return true;
    }


    public Image invokeImage(String pluginClassName, String methodName, Image input) {
        try {
            try {
                classTemp = load(pluginClassName);
                method = classTemp.getMethod(methodName, Image.class);
                return (Image) method.invoke(classTemp.newInstance(), input);
            }
            catch (NullPointerException e) {
                e.printStackTrace();
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }
}
