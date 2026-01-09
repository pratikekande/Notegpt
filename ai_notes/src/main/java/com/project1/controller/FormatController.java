package com.project1.controller;

import org.fxmisc.richtext.InlineCssTextArea;

public class FormatController {
    
    public void formatAndDisplayAIResponse(InlineCssTextArea area, String response) {

        area.clear();

        for(String line : response.split("\n")) {
            line = line.trim();

            if(line.isEmpty()) {
                area.appendText("\n");
                continue;
            }

            if(line.endsWith(":") && !line.contains("*")) {
                area.appendText(line + "\n");
                area.setStyle(area.getLength() - line.length() -1, area.getLength(),
                "-fx-font-weight: bold; -fx-font-size: 14px; -fx-fill: rgb(255, 255, 255);");
                continue;
            }

            if(line.startsWith("*")) {
                String clean = line.replace("\\*+", "").trim();
                area.appendText(". " + clean + "\n");
                area.setStyle(area.getLength() - clean.length() - 2, area.getLength(),
                "-fx-font-size: 14px; -fx-fill: rgb(255, 255, 255);");
                continue;
            }

            area.appendText(line + "\n");
            area.setStyle(area.getLength() - line.length() - 1, area.getLength(), "-fx-font-size: 14px; -fx-fill: rgb(255, 255, 255);");
        }
    }
}
