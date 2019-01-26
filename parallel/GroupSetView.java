package cn.edu.nju.software.parallel;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Map.Entry;

import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import org.processmining.contexts.uitopia.UIPluginContext;
import org.processmining.contexts.uitopia.annotations.Visualizer;
import org.processmining.framework.plugin.annotations.Plugin;
import org.processmining.framework.plugin.annotations.PluginVariant;

import cn.edu.nju.software.parallel.algorithm.GroupTaskSet;

public class GroupSetView {

	public static Color colorBg = new Color(30, 144, 255);
	
	@Plugin(name = "Visualize SubModel", returnLabels = { "Visualized SubModel" }, 
			returnTypes = { JComponent.class }, parameterLabels = { "Sub Model" }, userAccessible = true)
    @Visualizer
    @PluginVariant(requiredParameterLabels = { 0 })
    public JComponent runUI(UIPluginContext context, GroupTaskSet gts) {
            JComponent component = new JComponent() {
				private static final long serialVersionUID = 1L;
			};
            
            component.setBackground(colorBg);
            component.setLayout(new GridLayout(1,1));
            
    		JTextArea textArea = new JTextArea("Sub Models and Sub Loops");
    		Font font = new Font("Serif",0, 40);
    		textArea.setFont(font);
    		textArea.append("\n");
    		textArea.append("\n");
    		JScrollPane scroll = new JScrollPane(textArea);
    		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED); 
    		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
    		
    		for(Entry<String, ArrayList<String>> pairs : gts.getSng().entrySet()){
    			textArea.append("First element of the nomal group \"" + pairs.getKey() + "\" :\n");
    			textArea.append("Nomal group members :\n");
    			for(String str : pairs.getValue()) {
    				textArea.append(str + "\n");
    			}
    			textArea.append("\n");
    		}		
    		textArea.append("\n");		
    		textArea.setVisible(true);
    		scroll.setVisible(true);    		  		
    		component.add(scroll);
            return component;

	}
}