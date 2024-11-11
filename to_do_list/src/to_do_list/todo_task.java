package to_do_list;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class todo_task extends JPanel {
    private JTextPane taskDescription;
    private JCheckBox completedCheckbox;
    private JButton moveUpButton;
    private JButton moveDownButton;
    private JButton deleteButton;
    private JPanel tasksPanel;  // Reference to the parent tasks panel

    public todo_task(JPanel tasksPanel) {
        this.tasksPanel = tasksPanel;  // Store reference to the parent panel
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        this.setPreferredSize(new Dimension(330, 110)); // Fixed height
        this.setMaximumSize(new Dimension(330, 110));
        this.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        // Task description using JTextPane inside JScrollPane for word wrapping
        taskDescription = new JTextPane() {
            @Override
            public boolean getScrollableTracksViewportWidth() {
                return true;  // Enable line wrapping
            }
        };
        taskDescription.setEditable(true);
        taskDescription.setPreferredSize(new Dimension(220, 60));
        taskDescription.setMaximumSize(new Dimension(220, 60));

        // Wrap JTextPane in JScrollPane to enable scrolling if needed
        JScrollPane scrollPane = new JScrollPane(taskDescription);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setPreferredSize(new Dimension(220, 60));  // Adjust size

        this.add(scrollPane);  // Add the JScrollPane (with JTextPane inside) to the panel

        // Set a document filter to limit the text input
        int maxCharacters = 72; // You can adjust this value based on your desired limit
        ((AbstractDocument) taskDescription.getDocument()).setDocumentFilter(new TaskDocumentFilter(maxCharacters));

        // Checkbox for completion
        completedCheckbox = new JCheckBox();
        completedCheckbox.setAlignmentX(Component.CENTER_ALIGNMENT);
        completedCheckbox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateTextStyle(); // Update text style based on checkbox state
            }
        });
        this.add(completedCheckbox);

        // Button panel for actions
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setPreferredSize(new Dimension(50, 110));
        buttonPanel.setMaximumSize(new Dimension(50, 110));

        // Move up button
        moveUpButton = new JButton("↑");
        moveUpButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        moveUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                moveUp();
            }
        });
        buttonPanel.add(moveUpButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 5)));

        // Delete button styled as a red cross
        deleteButton = new JButton("✖");
        deleteButton.setBackground(Color.RED);
        deleteButton.setForeground(Color.WHITE);
        deleteButton.setBorderPainted(false);
        deleteButton.setFocusPainted(false);
        deleteButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteTask();
            }
        });
        buttonPanel.add(deleteButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 5)));

        // Move down button
        moveDownButton = new JButton("↓");
        moveDownButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        moveDownButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                moveDown();
            }
        });
        buttonPanel.add(moveDownButton);

        this.add(buttonPanel);
    }

    private void updateTextStyle() {
        StyledDocument doc = taskDescription.getStyledDocument();
        SimpleAttributeSet attributes = new SimpleAttributeSet();

        if (completedCheckbox.isSelected()) {
            StyleConstants.setStrikeThrough(attributes, true);  // Enable strikethrough
        } else {
            StyleConstants.setStrikeThrough(attributes, false); // Disable strikethrough
        }

        // Apply the attributes to the entire document
        doc.setCharacterAttributes(0, doc.getLength(), attributes, false);
    }

    private void moveUp() {
        int index = tasksPanel.getComponentZOrder(this);
        if (index > 0) {
            tasksPanel.remove(this);
            tasksPanel.add(this, index - 1);
            tasksPanel.revalidate();
            tasksPanel.repaint();
        }
    }

    private void moveDown() {
        int index = tasksPanel.getComponentZOrder(this);
        if (index < tasksPanel.getComponentCount() - 1) {
            tasksPanel.remove(this);
            tasksPanel.add(this, index + 1);
            tasksPanel.revalidate();
            tasksPanel.repaint();
        }
    }

    private void deleteTask() {
        tasksPanel.remove(this);
        tasksPanel.revalidate();
        tasksPanel.repaint();
    }

    // Custom DocumentFilter to limit input in task description
    private static class TaskDocumentFilter extends DocumentFilter {
        private int maxCharacters;

        public TaskDocumentFilter(int maxCharacters) {
            this.maxCharacters = maxCharacters;
        }

        @Override
        public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
            if ((fb.getDocument().getLength() + string.length()) <= maxCharacters) {
                super.insertString(fb, offset, string, attr);
            } else {
                Toolkit.getDefaultToolkit().beep();  // Sound feedback when limit is reached
            }
        }

        @Override
        public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
            if ((fb.getDocument().getLength() + text.length() - length) <= maxCharacters) {
                super.replace(fb, offset, length, text, attrs);
            } else {
                Toolkit.getDefaultToolkit().beep();  // Sound feedback when limit is reached
            }
        }
    }
    
    // Method to set the task description from the AddTask section
    public void setTaskDescription(String taskText) {
        taskDescription.setText(taskText);
        updateTextStyle(); // Call this to apply any styling changes if needed
    }
    public void setTaskDescriptionRecover(String taskText) {
    	taskText = taskText.replace("\\n", "\n");
        taskDescription.setText(taskText);
        updateTextStyle(); // Call this to apply any styling changes if needed
    }
    public String getTaskDescription() {
        return taskDescription.getText();
    }

    public boolean isTaskCompleted() {
        return completedCheckbox.isSelected();
    }
}
