package cn.edu.nju.software.parallel;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import org.processmining.contexts.uitopia.UIPluginContext;
import org.processmining.contexts.uitopia.annotations.Visualizer;
import org.processmining.framework.plugin.annotations.Plugin;
import org.processmining.framework.plugin.annotations.PluginVariant;



// »¹Ã»×ö
public class ExecuteTimeView {

	public static Color colorBg = new Color(30, 144, 255);
	
	@Plugin(name = "Visualize Execute Time", returnLabels = { "Visualized Execute Time" }, 
			returnTypes = { JComponent.class }, parameterLabels = { "Execute Time" }, userAccessible = true)
    @Visualizer
    @PluginVariant(requiredParameterLabels = { 0 })
    public JComponent runUI(UIPluginContext context, ExecuteTime executeTime) {
            JComponent component = new JComponent() {
				private static final long serialVersionUID = 1L;
			};
			
			component.setBackground(colorBg);
	        component.setLayout(new GridLayout(1,1));
			
			JTextArea textArea = new JTextArea("Model Steps and Execute Time");
			Font font = new Font("Serif",0, 40);
    		textArea.setFont(font);
    		textArea.append("\n");
    		textArea.append("\n");
    		JScrollPane scroll = new JScrollPane(textArea);
      		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED); 
    		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
    		
    		//textArea.append("Decompose Event Log Time : " + executeTime.getDecomposeTime() + "\n");
    		textArea.append("Group Task Set Time      : " + executeTime.getGroupTime() + "\n");
    		textArea.append("Mine Sub Model Time      : " + executeTime.getMineTime() + "\n");
    		textArea.append("Clear Sub Model Time     : " + executeTime.getClearTime() + "\n");
    		textArea.append("Merge Sub Model Time     : " + executeTime.getMergeTime() + "\n");
			
    		textArea.append("\n");		
    		textArea.setVisible(true);
    		scroll.setVisible(true);    		  		
    		component.add(scroll);
            return component;

	}
}