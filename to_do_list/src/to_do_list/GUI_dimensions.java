package to_do_list;
import java.awt.Dimension;
import java.awt.Color;
public class GUI_dimensions {
//	size for a frame
	public static final Dimension MIN_FRAME_SIZE = new Dimension(375, 667);
//	size for addtask and todolist
	public static final Dimension SIZE_COLOMNS = new Dimension(359, 0);
//	color for background
	public static final Color YELLOW_PAPER = new Color(255,252,190);
//	size for panel insise the todolist
	public static final Dimension TASKPANEL_SIZE = new Dimension(SIZE_COLOMNS.width-10, MIN_FRAME_SIZE.height-20);
	public static final Dimension TASK_LIST_SIZE = new Dimension(SIZE_COLOMNS.width-20, SIZE_COLOMNS.height-20);
	public static final Dimension ADDTASK_BUTTON_SIZE = new Dimension(375, 50);
	
	
	public static final Dimension TASK_SIZE = new Dimension(300, 110); // Fixed size for tasks
	public static final Dimension CHECKBOX_SIZE = new Dimension((int)(TASK_SIZE.width*0.10),60);
	public static final Dimension DELETE_BUTTON = new Dimension((int)(TASK_SIZE.width*0.10), 60);
}
