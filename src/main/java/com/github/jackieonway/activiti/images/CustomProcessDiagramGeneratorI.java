package com.github.jackieonway.activiti.images;

import java.awt.Color;
import java.io.InputStream;
import java.util.List;
import java.util.Set;

import org.activiti.bpmn.model.BpmnModel;
import org.activiti.image.ProcessDiagramGenerator;

public interface CustomProcessDiagramGeneratorI extends ProcessDiagramGenerator {

    InputStream generateDiagram(BpmnModel bpmnModel, String imageType, List<String> highLightedActivities,
            List<String> highLightedFlows, String activityFontName, String labelFontName, String annotationFontName,
            ClassLoader customClassLoader, double scaleFactor, Color[] colors, Set<String> currIds);
}
