package main.java.framework.utils;

import org.apache.jmeter.engine.StandardJMeterEngine;
import org.apache.jmeter.save.SaveService;
import org.apache.jmeter.util.JMeterUtils;
import org.apache.jorphan.collections.HashTree;

import java.io.File;


public class JMeterFromExistingJMX extends Utility{

    public void run() throws Exception {
        // JMeter Engine
        StandardJMeterEngine jmeter = new StandardJMeterEngine();

        // Initialize Properties, logging, locale, etc.
        JMeterUtils.loadJMeterProperties(properties.getProperty("jmeter.properties.path"));
        JMeterUtils.setJMeterHome(properties.getProperty("jmeter.home"));
        JMeterUtils.initLocale();

        // Initialize JMeter SaveService
        SaveService.loadProperties();

        // Load existing .jmx Test Plan
        File testPlan = new File(properties.getProperty("jmeter.test.plan"));
        HashTree testPlanTree = SaveService.loadTree(testPlan);


        // Run JMeter Test
        jmeter.configure(testPlanTree);
        jmeter.run();
    }
}