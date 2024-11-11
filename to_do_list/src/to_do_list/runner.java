package to_do_list;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.MatteBorder;

public class runner {

    public static void main(String[] args) {
    	
        // Create a JFrame
        JFrame frame = new JFrame();
        frame.setTitle("To-do");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(true);  // Allow resizing
        frame.setSize(736, 900);  // Initial size of the window
        frame.setMinimumSize(GUI_dimensions.MIN_FRAME_SIZE); 

        // Set background color of the JFrame
        frame.getContentPane().setBackground(GUI_dimensions.YELLOW_PAPER);

        frame.setLayout(new BorderLayout());

        frame.add(new Header(), BorderLayout.NORTH);
        JPanel sidePanelsContainer = new JPanel();
        sidePanelsContainer.setLayout(new GridLayout(1, 2));  // Grid layout for side-by-side panels
        sidePanelsContainer.setPreferredSize(new Dimension(720, 0));
        ToDo todoshka = new ToDo();
        sidePanelsContainer.add(todoshka);
        sidePanelsContainer.add(new AddTask(todoshka));
        frame.add(sidePanelsContainer, BorderLayout.WEST);
        todoshka.recover();

        // Limit maximum frame size
        Dimension maxDimension = new Dimension(736, Integer.MAX_VALUE);

        frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                Dimension currentSize = frame.getSize();
                if (currentSize.width > maxDimension.width) {
                    frame.setSize(maxDimension.width, currentSize.height);
                }
            }
        });
        
        // Save tasks before closing
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
            	
                saveTasksToFile(todoshka); // Save tasks before closing
                System.exit(0); // Exit the application
            }
        });

        // Make the frame visible after all components are added
        frame.setVisible(true);
//        while(true) {
//        	todoshka.print_content();
//        }
//        was needed for testing
    }

    // Custom Header class extending JPanel
    public static class Header extends JPanel {
        public Header() {
            this.setPreferredSize(new Dimension(0, 50));  // Width adjusts, height fixed at 50
            this.setBackground(GUI_dimensions.YELLOW_PAPER);
            this.setBorder(new MatteBorder(0, 0, 2, 0, Color.BLACK));
            JLabel label = new JLabel("To-do List");
            label.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
            label.setHorizontalAlignment(JLabel.CENTER);
            this.setLayout(new BorderLayout());
            this.add(label, BorderLayout.CENTER);
        }
    }


    
    
    // Method to save tasks to a file
    private static void saveTasksToFile(ToDo todoshka) {
        File file = new File("taskStorage.txt"); // Specify the file name
        try (FileWriter writer = new FileWriter(file, false)) { // Using try-with-resources for automatic closure
            ArrayList<todo_task> tasks = todoshka.getAllTasks(); // Get all tasks

            for (todo_task task : tasks) {
                String taskDescription = task.getTaskDescription().trim();
                boolean isCompleted = task.isTaskCompleted();

                // Skip empty tasks or completed tasks
                if (!taskDescription.isEmpty() && !isCompleted) {
                    // Replace actual line breaks with "\n"
                	String formattedDescription = task.getTaskDescription().replace("\n", "\\n");
                    writer.append(formattedDescription); // Write the formatted task description
                    writer.append(System.lineSeparator()); // Write a new line to separate tasks
                } else {
                    System.out.println("Skipping Task: " + taskDescription); // Log skipped tasks
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}

