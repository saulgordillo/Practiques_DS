package test;

import core.Activity;
import core.Project;
import core.Task;
import visitor.SearchByTagVisitor;

import java.util.ArrayList;
import java.util.List;

public class Test {
    public static Project createTree() {
        List<String> softwareDesignTags = new ArrayList<>();
        softwareDesignTags.add("java");
        softwareDesignTags.add("flutter");
        List<String> softwareTestingTags = new ArrayList<>();
        softwareTestingTags.add("c++");
        softwareTestingTags.add("Java");
        softwareTestingTags.add("python");
        List<String> databaseTags = new ArrayList<>();
        databaseTags.add("SQL");
        databaseTags.add("python");
        databaseTags.add("C++");
        List<String> firstListTags = new ArrayList<>();
        firstListTags.add("java");
        List<String> secondListTags = new ArrayList<>();
        secondListTags.add("Dart");
        List<String> firstMilestoneTags = new ArrayList<>();
        firstMilestoneTags.add("Java");
        firstMilestoneTags.add("IntelliJ");
        List<String> emptyTag = new ArrayList<>();

        // Root
        Project projectRoot = new Project();

        // top level
        Project softwareDesign = new Project("software design", projectRoot); //, softwareDesignTags);
        Project softwareTesting = new Project("software testing", projectRoot); //softwareTestingTags);
        Project databases = new Project("database", projectRoot); //, databaseTags);
        Task transportation = new Task("transportation", projectRoot); //, emptyTag);

        // under software design
        Project problems = new Project("problems", softwareDesign); //, emptyTag);
        Project projectTimeTracker = new Project("project time tracker", softwareDesign); //, emptyTag);

        // under problems
        Task firstList = new Task("first list", problems); //, firstListTags);
        Task secondList = new Task("second list", problems); //, secondListTags);

        // under project time tracker
        Task readHandout = new Task("read handout", projectTimeTracker); //, emptyTag);
        Task firstMilestone = new Task("first milestone", projectTimeTracker); //, firstMilestoneTags);

        return projectRoot;
    }

    public static Project countingTimeAndCreateTree() {
        List<String> softwareDesignTags = new ArrayList<>();
        softwareDesignTags.add("java");
        softwareDesignTags.add("flutter");
        List<String> softwareTestingTags = new ArrayList<>();
        softwareTestingTags.add("c++");
        softwareTestingTags.add("Java");
        softwareTestingTags.add("python");
        List<String> databaseTags = new ArrayList<>();
        databaseTags.add("SQL");
        databaseTags.add("python");
        databaseTags.add("C++");
        List<String> firstListTags = new ArrayList<>();
        firstListTags.add("java");
        List<String> secondListTags = new ArrayList<>();
        secondListTags.add("Dart");
        List<String> firstMilestoneTags = new ArrayList<>();
        firstMilestoneTags.add("Java");
        firstMilestoneTags.add("IntelliJ");
        List<String> emptyTag = new ArrayList<>();

        // root
        Project projectRoot = new Project();

        // top level
        Project softwareDesign = new Project("software design", projectRoot); //, softwareDesignTags);
        Project softwareTesting = new Project("software testing", projectRoot); //, softwareTestingTags);
        Project databases = new Project("database", projectRoot); //, databaseTags);
        Task transportation = new Task("transportation", projectRoot); //, emptyTag);

        // under software design
        Project problems = new Project("problems", softwareDesign); //, emptyTag);
        Project projectTimeTracker = new Project("project time tracker", softwareDesign); //, emptyTag);

        // under problems
        Task firstList = new Task("first list", problems); //, firstListTags);
        Task secondList = new Task("second list", problems); //, secondListTags);

        // under project time tracker
        Task readHandout = new Task("read handout", projectTimeTracker); //, emptyTag);
        Task firstMilestone = new Task("first milestone", projectTimeTracker); //, firstMilestoneTags);

        transportation.start();
        System.out.print("transportation starts\n");

        try {
            Thread.sleep(6000);
        } catch (Exception e) {
            System.out.print("Thread.sleep()");
        }

        transportation.stop();
        System.out.print("transportation stops\n");

        try {
            Thread.sleep(2000);
        } catch (Exception e) {
            System.out.print("Thread.sleep()");
        }

        System.out.print("firstList starts\n");
        firstList.start();

        try {
            Thread.sleep(6000);
        } catch (Exception e) {
            System.out.print("Thread.sleep()");
        }

        System.out.print("secondList starts\n");
        secondList.start();

        try {
            Thread.sleep(4000);
        } catch (Exception e) {
            System.out.print("Thread.sleep()");
        }

        firstList.stop();
        System.out.println("firstList stops");

        try {
            Thread.sleep(2000);
        } catch (Exception e) {
            System.out.print("Thread.sleep()");
        }

        secondList.stop();
        System.out.print("secondList stops\n");


        try {
            Thread.sleep(2000);
        } catch (Exception e) {
            System.out.print("Thread.sleep()");
        }
        System.out.print("transportation starts\n");
        transportation.start();
        try {
            Thread.sleep(4000);
        } catch (Exception e) {
            System.out.print("Thread.sleep()");
        }
        transportation.stop();
        System.out.print("transportation stops");

        return projectRoot;
    }

    public static List<Activity> searchingByTag(Project projectRoot, String tagToSearch) {
        SearchByTagVisitor sbt = new SearchByTagVisitor();
        return sbt.searchByTag(projectRoot, tagToSearch);
    }
}
