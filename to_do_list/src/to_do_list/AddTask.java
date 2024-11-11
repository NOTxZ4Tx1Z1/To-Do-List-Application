package to_do_list;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

public class AddTask extends JPanel implements ActionListener {
    private ToDo todoshka;
    private JTextPane new_task;  // Make this field accessible

    private final String placeholderText = "Enter your task here";

    public AddTask(ToDo todoshka) {
        this.todoshka = todoshka;  // Store the reference to the ToDo panel
        this.setPreferredSize(new Dimension(375, 0));  // Initial width of 375, height adjusts
        this.setMinimumSize(GUI_dimensions.SIZE_COLOMNS);  // Minimum width of 375
        this.setBackground(GUI_dimensions.YELLOW_PAPER);  // "Yellow paper" background color (mirrored with ToDo)

        // Label inside the AddTask panel
        JLabel label = new JLabel("Add Task");
        label.setFont(new Font("Comic Sans MS", Font.PLAIN, 16));
        label.setHorizontalAlignment(JLabel.CENTER);

        this.setLayout(new BorderLayout());
        this.add(label, BorderLayout.NORTH);  // Label at the top of the AddTask panel

        // Add Task Button at the bottom of the panel
        JButton addTaskButton = new JButton("Add Task");
        addTaskButton.setFont(new Font("Comic Sans MS", Font.PLAIN, 18));  // Using Comic Sans MS
        addTaskButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        addTaskButton.setPreferredSize(new Dimension(this.getWidth(), 50)); // Set button width equal to panel width
        addTaskButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        this.add(addTaskButton, BorderLayout.SOUTH);
        
        JPanel new_task_home = new JPanel();
        new_task_home.setPreferredSize(new Dimension(60, 60));
        new_task_home.setMaximumSize(new Dimension(60, 60));
        new_task_home.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        
        // Create the JTextPane for input
        new_task = new JTextPane() {
            @Override
            public boolean getScrollableTracksViewportWidth() {
                return true;  // Enable line wrapping
            }
        };
        new_task.setEditable(true);
        new_task.setPreferredSize(new Dimension(220, 100));
        new_task.setMaximumSize(new Dimension(220, 100));
        
        // Add a 2px black border
        addPlaceholderText(new_task, placeholderText);

        // Wrap the JTextPane inside a JScrollPane to enable scrolling
        JScrollPane scrollPane = new JScrollPane(new_task);
        scrollPane.setPreferredSize(new Dimension(220, 100));  // Set scroll pane size
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);  // Show scrollbar as needed

        new_task_home.setLayout(new BorderLayout());
        new_task_home.add(scrollPane, BorderLayout.NORTH);

        this.add(new_task_home);
        
        // Button action
        addTaskButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equalsIgnoreCase("Add Task")) {
            String taskText = new_task.getText().trim();
            
            // Check if the text is not the placeholder
            if (!taskText.equals(placeholderText) && !taskText.isEmpty()) {
                // Create a new todo_task and set the task description
                todo_task todoItem = new todo_task(todoshka.tasksPanel);
                todoItem.setTaskDescription(taskText);  // Set the task text to the new task
                
                // Add task to the ToDo panel
                todoshka.addTask(todoItem);  

                // Clear the input field and restore the placeholder
                new_task.setText(placeholderText);
                new_task.setForeground(Color.GRAY);  // Reset color to gray
            }else {
            	todo_task todoItem = new todo_task(todoshka.tasksPanel);
            	todoshka.addTask(todoItem);
            	new_task.setText(placeholderText);
            	new_task.setForeground(Color.GRAY);
            }
        }
    }

    private void addPlaceholderText(JTextPane textPane, String placeholderText) {
        // Set the initial placeholder text and color
        textPane.setText(placeholderText);
        textPane.setForeground(Color.GRAY);

        // Add a FocusListener to handle focus gained/lost events
        textPane.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                // When focus is gained, if the text is the placeholder, clear it
                if (textPane.getText().equals(placeholderText)) {
                    textPane.setText("");
                    textPane.setForeground(Color.BLACK);  // Change text color to black
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                // When focus is lost, if the field is empty, restore the placeholder
                if (textPane.getText().isEmpty()) {
                    textPane.setText(placeholderText);
                    textPane.setForeground(Color.GRAY);  // Change text color to gray
                }
            }
        });
    }
}
