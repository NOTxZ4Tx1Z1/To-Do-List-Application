package to_do_list;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class ToDo extends JPanel {
    public JPanel tasksPanel;
    

    public ToDo() {
        this.setLayout(new BorderLayout());
        tasksPanel = new JPanel();
        
        // Set the layout for tasksPanel to BoxLayout
        tasksPanel.setLayout(new BoxLayout(tasksPanel, BoxLayout.Y_AXIS));
        
        // Add scroll pane for tasks
        JScrollPane scrollPane = new JScrollPane(tasksPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(20);  // Set scroll speed

        this.add(scrollPane, BorderLayout.CENTER);
        
    }

    public void addTask(todo_task todoItem) {
        tasksPanel.add(todoItem);  // Add the new task to the tasks panel
        todoItem.setAlignmentX(Component.LEFT_ALIGNMENT);  // Align task to the left
        tasksPanel.revalidate();  // Refresh the panel layout
        tasksPanel.repaint();      // Redraw the panel to reflect changes
    
    }
    public ArrayList<todo_task> getAllTasks() {
    	ArrayList<todo_task> tasks = new ArrayList<>();
    	for (Component comp : this.tasksPanel.getComponents()) {
    		if (comp instanceof todo_task) {
    			todo_task task = (todo_task) comp;
    			tasks.add(task);
    		}
    	}
        return tasks; // Return the list of tasks
    }
    public void print_content() {
    	ArrayList<todo_task> tasks = this.getAllTasks();
    	for (todo_task task : tasks) {
    		 String taskDescription = task.getTaskDescription().trim();
    		 System.out.println(taskDescription);
    	}
    }
    public void recover() {
        String filePath = "./taskStorage.txt";  // Path to your stored file

        // Read from the file
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            
            // Iterate over each line in the file
            while ((line = reader.readLine()) != null) {
                String taskDescription = line.trim();
                
                // Skip empty or malformed tasks
                if (taskDescription.isEmpty()) {
                    continue;
                }
                
                // Create a new todo_task and set its description
                todo_task task = new todo_task(tasksPanel);
                task.setTaskDescriptionRecover(taskDescription);  // Restore the task description
                
                // Add the task to the panel using addTask method
                addTask(task);  // Ensure proper addition and UI refresh
                
                tasksPanel.revalidate();  // Refresh the layout to reflect changes
                tasksPanel.repaint();     // Repaint the panel for visibility
            }
        } catch (IOException e) {
            e.printStackTrace();  // Handle the exception
        }
    }
}
